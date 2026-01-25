package edu.zut.awir.awir1.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto(
        Long id,
        @NotBlank String name,
        @Email @NotBlank String email
) {}
