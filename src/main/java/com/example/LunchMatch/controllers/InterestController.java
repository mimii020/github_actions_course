package com.example.LunchMatch.controllers;

import com.example.LunchMatch.DTO.InterestViewDTO;
import com.example.LunchMatch.DTO.NewInterestDTO;
import com.example.LunchMatch.services.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interests")
@RequiredArgsConstructor
public class InterestController {
    @Autowired
    private InterestService interestService;
    //add a new interest
    @PostMapping
    public ResponseEntity<String> addInterest(
            @RequestBody NewInterestDTO newInterestDTO,
            Authentication authentication) {
        interestService.addInterest(newInterestDTO, authentication);
        return ResponseEntity.ok("new interest added successfully");
    }
    //update an existing interest
    @PutMapping("/{id}")
    public ResponseEntity<String> updateInterest(
            @PathVariable Integer id,
            @RequestBody NewInterestDTO newInterestDTO) {
        interestService.updateInterest(id, newInterestDTO);
        return ResponseEntity.ok("interest updated successfully");
    }
    //view all interest
    @GetMapping
    public ResponseEntity<List<InterestViewDTO>> viewAllInterests() {
        List<InterestViewDTO> interests = interestService.viewAllInterests();
        return ResponseEntity.ok(interests);
    }
    //view a specific interest
    @GetMapping("/{id}")
    public ResponseEntity<InterestViewDTO> viewInterestById(@PathVariable Integer id) {
        InterestViewDTO interestViewDTO = interestService.viewInterestById(id);
        return ResponseEntity.ok(interestViewDTO);
    }
    //delete a specific interest
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInterestByIdd(@PathVariable Integer id) {
        interestService.deleteInterest(id);
        return ResponseEntity.noContent().build();
    }
}
