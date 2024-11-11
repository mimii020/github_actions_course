package com.example.LunchMatch.services;

import com.example.LunchMatch.DTO.InterestViewDTO;
import com.example.LunchMatch.DTO.NewInterestDTO;
import com.example.LunchMatch.models.Interest;
import com.example.LunchMatch.models.User;
import com.example.LunchMatch.repositories.InterestRepository;
import com.example.LunchMatch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InterestRepository interestRepository;
    // add a new interest
    public void addInterest(NewInterestDTO newInterestDTO, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Interest interest = Interest.builder()
                .name(newInterestDTO.getName())
                .category(newInterestDTO.getCategory())
                .description(newInterestDTO.getDescription())
                .user(user)
                .build();
        interestRepository.save(interest);
    }
    // update a new interest
    public void updateInterest(Integer interestId, NewInterestDTO newInterestDTO) {
        Interest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new RuntimeException("interest not found"));
        if (newInterestDTO.getName() != null && !interest.getName().equals(newInterestDTO.getName())) {
            interest.setName(newInterestDTO.getName());
        }

        if (newInterestDTO.getCategory() != null && !interest.getCategory().equals(newInterestDTO.getCategory())) {
            interest.setCategory(newInterestDTO.getCategory());
        }

        if (newInterestDTO.getDescription() != null && !interest.getDescription().equals(newInterestDTO.getDescription())) {
            interest.setDescription(newInterestDTO.getDescription());
        }
        interestRepository.save(interest);

    }
    //view all interests
    public List<InterestViewDTO> viewAllInterests() {
        return interestRepository.findAll().stream().map(
                interest -> InterestViewDTO.builder()
                        .id(interest.getId())
                        .name(interest.getName())
                        .category(interest.getCategory())
                        .description(interest.getDescription())
                        .build()
        ).toList();
    }
    //view a specific interest

    public InterestViewDTO viewInterestById(Integer interestId) {
        Interest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new RuntimeException(("interest not found")));
        return InterestViewDTO.builder()
                .name(interest.getName())
                .category(interest.getCategory())
                .description(interest.getDescription())
                .build();
    }

    //delete an interest
    public void deleteInterest(Integer interestId) {
        Interest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new RuntimeException("interest not found"));
        interestRepository.delete(interest);
    }

}
