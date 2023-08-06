package com.ensah.payload.request;
import com.ensah.constantes.ERole;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author med_Aziz
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank(message = "firstname should not be blank")
    private String firstname;
    @NotBlank(message = "lastname should not be blank")
    private String lastname;
    @NotBlank(message = "phoneNumber should not be blank")
    private String phonenumber;
    @NotBlank(message = "your Normal Email should not be blank")
    private String normalemail;
    @NotBlank(message = "your Academic Email should not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@etu\\.uae\\.ac\\.ma$", message = "Invalid email format")
    private String academicemail;
    @NotBlank(message = "password should not be blank")
    private String password;
    @NotBlank(message = "picture should not be blank")
    private String picture;
    @NotNull(message = "Role should not be null")
    @Enumerated(EnumType.STRING)
    private ERole role;
    @NotBlank(message = "picture should not be blank")
    private String grade;
}