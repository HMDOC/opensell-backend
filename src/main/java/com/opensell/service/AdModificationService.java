package com.opensell.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.opensell.entities.ad.AdTag;
import com.opensell.entities.ad.AdType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.opensell.entities.Ad;
import com.opensell.entities.ad.AdShape;
import com.opensell.entities.ad.AdVisibility;
import com.opensell.entities.verification.HtmlCode;
import com.opensell.repository.AdRepository;
import com.opensell.repository.AdTagRepository;
import com.opensell.repository.AdTypeRepository;

/**
 * Service that contain the functions to update each value of an ad 
 * that are updable in the frontend.
 * 
 * @author Achraf
 */
@Service
public class AdModificationService {
	@Autowired
	private AdRepository adRepo;

	@Autowired
	private AdTagRepository adTagRepo;

	@Autowired
	private AdTypeRepository adTypeRepo;

    public class ModifType {
        public static final int TITLE = 0;
        public static final int REFERENCE = 1;
        public static final int PRICE = 2;
        public static final int AD_TYPE = 3;
        public static final int ADDRESS = 4;
        public static final int IS_SOLD = 5;
        public static final int DESCRIPTION = 6;
        public static final int VISIBILITY = 7;
        public static final int SHAPE = 8;
    }

    public static class ModifBody {
        public Object value;
    }

    public int changeTitle(String title, int idAd) {
        if(title == null) return HtmlCode.NULL_VALUE;
        if(title.length() == 0) return HtmlCode.LENGTH_EMPTY;
        if(title.length() > Ad.TITLE_MAX_LENGTH) return HtmlCode.LENGTH_OVERFLOW;
        
        // If it throws an Exception, that mean that the a user already have a title.
        try {
            return adRepo.updateTitle(title, idAd) == 1 ? HtmlCode.SUCCESS : HtmlCode.ID_NOT_FOUND;
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("ERREUR UNIQUE");
            return HtmlCode.UNIQUE_FAILED;
        }
    }

    public int changeReference(String reference, int idAd) {
        if(reference == null) return HtmlCode.NULL_VALUE;
        if(reference.length() == 0) return HtmlCode.LENGTH_EMPTY;
        if(reference.length() > 255) return HtmlCode.LENGTH_OVERFLOW;

        try {
            return adRepo.updateReference(reference, idAd) == 1 ? HtmlCode.SUCCESS : HtmlCode.ID_NOT_FOUND;
        } catch(Exception e) {
            e.printStackTrace();
            return HtmlCode.UNIQUE_FAILED;
        }
    }

    public int changePrice(double price, int idAd) {
        if(price < 0) return HtmlCode.LESS_THAN_ZERO;
        adRepo.updatePrice(price, idAd);
        try {
            return adRepo.updatePrice(price, idAd) == 1 ? HtmlCode.SUCCESS : HtmlCode.ID_NOT_FOUND;
        } catch(Exception e) {
            return HtmlCode.DOUBLE_OVERFLOW;
        }
    }

    // JPA
    public int changeAdType(String adTypeName, int idAd) {
        try {
            // IF the new tag name is valid.
            if(adTypeName == null) return HtmlCode.NULL_VALUE;
            if(adTypeName.length() > AdType.MAX_LENGTH) return HtmlCode.WRONG_VALUE;

            var ad = adRepo.findOneByIdAdAndIsDeletedFalse(idAd);
            if(ad == null) return HtmlCode.ID_NOT_FOUND;

            // IF a tag already exists
            var tag = adTypeRepo.findOneByName(adTypeName);
            if(tag != null) {
                ad.setAdType(tag);
            } else {
                var registerTag = adTypeRepo.save(new AdType(adTypeName));
                ad.setAdType(registerTag);
            }

            adRepo.save(ad);
            return HtmlCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return HtmlCode.SERVER_ERROR;
        }
    }

    public int changeAddress(String address, int idAd) {
        if(address == null) return HtmlCode.NULL_VALUE;
        if(address.length() == 0) return HtmlCode.LENGTH_EMPTY;
        if(address.length() > 255) return HtmlCode.LENGTH_OVERFLOW;

        try {
            return adRepo.updateAddress(address, idAd) == 1 ? HtmlCode.SUCCESS : HtmlCode.ID_NOT_FOUND;
        } catch(Exception e) {
            return HtmlCode.SERVER_ERROR;
        }
    }

    public int changeIsSold(boolean isSold, int idAd) {
        try {
            return adRepo.updateIsSold(isSold, idAd) == 1 ? HtmlCode.SUCCESS : HtmlCode.ID_NOT_FOUND;
        } catch(Exception e) {
            return HtmlCode.SERVER_ERROR;
        }
    }

    public int changeDescription(String description, int idAd) {
        if(description == null) return HtmlCode.NULL_VALUE;
        if(description.length() == 0) return HtmlCode.LENGTH_EMPTY;
        if(description.length() > Ad.DESCRIPTION_MAX_LENGTH) return HtmlCode.LENGTH_OVERFLOW;

        try {
            return adRepo.updateDescription(description, idAd) == 1 ? HtmlCode.SUCCESS : HtmlCode.ID_NOT_FOUND;
        } catch(Exception e) {
            return HtmlCode.SERVER_ERROR;
        }
    }

    // JPA
    public int changeAdImages(List<String> adImages, int idAd) {
        return 0;
    }

    /**
     * To change the tags of an Ad.
     *
     * @author Achraf
     * @param frontendTags
     * @param ad
     */
    public int changeAdTags(List<String> frontendTags, int idAd) {
        try {
            if (frontendTags == null) return HtmlCode.NULL_VALUE;
            Ad ad = adRepo.findOneByIdAdAndIsDeletedFalse(idAd);

            if (ad == null) return HtmlCode.ID_NOT_FOUND;
            Set<AdTag> adTags = new LinkedHashSet<>();

            // Map over the set of string
            frontendTags.forEach(tag -> {
                if (AdTag.isNameValid(tag)) {
                    // Get the old tag from the database
                    AdTag tagTemp = adTagRepo.findOneByName(tag);

                    // If the tag already exists
                    if (tagTemp != null) adTags.add(tagTemp);

                    // If the tag does exists
                    else adTags.add(adTagRepo.save(new AdTag(tag)));
                }
            });

            ad.setAdTags(adTags);
            adRepo.save(ad);
            return HtmlCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return HtmlCode.SERVER_ERROR;
        }

    }

    public int changeVisibility(int visibility, int idAd) {
        boolean isUpdatable = false;

        for(AdVisibility adVisibility : AdVisibility.values()) {
            if(adVisibility.ordinal() == visibility) {
                isUpdatable = true;
                break;
            }
        }

        if(isUpdatable) {
            try {
                return adRepo.updateVisibility(visibility, idAd) == 1 ? HtmlCode.SUCCESS : HtmlCode.ID_NOT_FOUND;
            } catch(Exception e) {
                e.printStackTrace();
                return HtmlCode.SERVER_ERROR;
            }
        }

        else return HtmlCode.WRONG_VALUE;
    }

    public int changeShape(int shape, int idAd) {
        boolean isUpdatable = false;

        for(AdShape adShape : AdShape.values()) {
            if(adShape.ordinal() == shape) {
                isUpdatable = true;
                break;
            }
        }

        if(isUpdatable) {
            try {
                return adRepo.updateShape(shape, idAd) == 1 ? HtmlCode.SUCCESS : HtmlCode.ID_NOT_FOUND;
            } catch(Exception e) {
                e.printStackTrace();
                return HtmlCode.SERVER_ERROR;
            }
        }

        else return HtmlCode.WRONG_VALUE;
    }
}
