package com.example.LunchMatch.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotEmpty
    @NotBlank
    private String firstname;
    @NotEmpty
    @NotBlank
    private String lastname;
    @NotEmpty
    @NotBlank
    private String email;
    @NotEmpty
    @NotBlank
    @Size(min = 8)
    private String password;
}