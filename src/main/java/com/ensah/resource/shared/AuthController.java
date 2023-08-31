package com.ensah.resource.shared;

import com.ensah.domain.User;
import com.ensah.payload.request.LoginRequest;
import com.ensah.payload.response.JwtResponse;
import com.ensah.payload.response.MessageResponse;
import com.ensah.repository.UserRepository;
import com.ensah.config.security.jwt.JwtUtils;
import com.ensah.service.AuthService;
import com.ensah.utils.EmailService;
import com.ensah.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author med_Aziz
 * @version 1.0
 */

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {
  @Autowired
  private AuthService authService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {

     return authService.authenticateUser(loginRequest);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestParam("firstName") @NotNull(message = "firstName must be not null") String firstName,
                                          @RequestParam("lastName") @NotNull(message = "lastName must be not null") String lastName,
                                          @RequestParam("normalEmail") @NotNull(message = "normalEmail must be not null") @Email(message = "normal email should be a valid email") String normalEmail,
                                          @RequestParam("academicEmail") @NotNull(message = "Academic Email must be not null") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@etu\\.uae\\.ac\\.ma$", message = "Invalid email format") String academicEmail,
                                          @RequestParam("mobilePhone") @NotNull(message = "mobilePhone must be not null") String mobilePhone,
                                          @RequestParam("role") @NotNull(message = "role must be not null") String role,
                                          @RequestParam("grade") @NotNull(message = "grade must be not null") String grade,
                                          @RequestParam("password") @NotNull(message = "password must be not null") String password,
                                          @RequestParam("picture") @NotNull(message = "picture must be not null") MultipartFile picture
                                          ) throws IOException, MessagingException {

                        return authService.registerUser(firstName,lastName,normalEmail,academicEmail,mobilePhone,role,grade,password,picture);


}


}