package com.payment.transactionTracker.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "INVALID EMAIL FORMAT")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "INVALID PASSWORD")
    private String password;
}
