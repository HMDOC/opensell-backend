package com.opensell.service;

import java.util.*;
import com.opensell.model.ad.AdTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.opensell.repository.AdRepository;
import com.opensell.repository.AdTagRepository;

/**
 * Service that contain the functions to update each value of an ad 
 * that are updatable in the frontend.
 * 
 * @author Achraf
 */
@Service
@RequiredArgsConstructor
public class AdModificationService {
	private final AdRepository adRepo;

	private final AdTagRepository adTagRepo;

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
}
