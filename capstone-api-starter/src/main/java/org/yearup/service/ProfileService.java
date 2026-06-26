package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

@Service
public class ProfileService
{
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }

    public Profile create(Profile profile)
    {
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Profile profile, int id) {
        profile.setUserId(id);
        return profileRepository.save(profile);
    }

    public Profile getProfile(int id){
        return profileRepository.findById(id).orElseThrow(()->new RuntimeException("Profile not found"));
    }


}
