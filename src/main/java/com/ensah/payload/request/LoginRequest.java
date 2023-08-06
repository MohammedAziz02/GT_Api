package com.ensah.payload.request;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author med_Aziz
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "academicmail should not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@etu\\.uae\\.ac\\.ma$", message = "Invalid email format")
    private String academicmail;
    @NotBlank(message = "password should not be blank")
    private String password;
}