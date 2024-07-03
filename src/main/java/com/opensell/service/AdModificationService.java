package com.opensell.service;

import java.util.*;
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

    @Deprecated(forRemoval = true)
    public static class ModifType {
        public static final int TITLE = 0;
        public static final int PRICE = 1;
        public static final int AD_TYPE = 2;
        public static final int ADDRESS = 3;
        public static final int IS_SOLD = 4;
        public static final int DESCRIPTION = 5;
        public static final int VISIBILITY = 6;
        public static final int SHAPE = 7;
    }

    @Deprecated(forRemoval = true)
    public static class ModifBody {
        public Object value;
    }

    @Deprecated(forRemoval = true)
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

    @Deprecated(forRemoval = true)
    public int changePrice(double price, int idAd) {
        if(price < 0) return HtmlCode.LESS_THAN_ZERO;
        adRepo.updatePrice(price, idAd);
        try {
            return adRepo.updatePrice(price, idAd) == 1 ? HtmlCode.SUCCESS : HtmlCode.ID_NOT_FOUND;
        } catch(Exception e) {
            return HtmlCode.DOUBLE_OVERFLOW;
        }
    }

    @Deprecated(forRemoval = true)
    public int changeAdType(Map<String, Object> adTypeMap, int idAd) {
        try {
            if(adTypeMap == null) return HtmlCode.NULL_VALUE;
            var ad = adRepo.findOneByIdAdAndIsDeletedFalse(idAd);
            if(ad == null) return HtmlCode.ID_NOT_FOUND;

            ad.setAdType(new AdType(Integer.parseInt(adTypeMap.get("idAdType").toString()), adTypeMap.get("name").toString()));
            adRepo.save(ad);
            return HtmlCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return HtmlCode.SERVER_ERROR;
        }
    }

    @Deprecated(forRemoval = true)
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

    @Deprecated(forRemoval = true)
    public int changeIsSold(boolean isSold, int idAd) {
        try {
            return adRepo.updateIsSold(isSold, idAd) == 1 ? HtmlCode.SUCCESS : HtmlCode.ID_NOT_FOUND;
        } catch(Exception e) {
            return HtmlCode.SERVER_ERROR;
        }
    }

    @Deprecated(forRemoval = true)
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

    public Set<AdTag> getAdTagsFromStringSet(Set<String> tags) {
        Set<AdTag> adTags = new LinkedHashSet<>();

        // Map over the set of string
        tags.forEach(tag -> {
            if (AdTag.isNameValid(tag)) {
                // Get the old tag from the database
                AdTag tagTemp = adTagRepo.findOneByName(tag);

                // If the tag already exists
                if (tagTemp != null) adTags.add(tagTemp);

                    // If the tag does exists
                else adTags.add(adTagRepo.save(new AdTag(tag)));
            }
        });

        return adTags;
    }

    /**
     * To change the tags of an Ad.
     *
     * @author Achraf
     * @param frontendTags
     * @param ad
     */
    @Deprecated(forRemoval = true)
    public int changeAdTags(List<String> frontendTags, int idAd) {
        try {
            if (frontendTags == null) return HtmlCode.NULL_VALUE;
            Ad ad = adRepo.findOneByIdAdAndIsDeletedFalse(idAd);

            if (ad == null) return HtmlCode.ID_NOT_FOUND;
            Set<AdTag> adTags = getAdTagsFromStringSet((Set<String>) frontendTags);

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
