package com.ensah.resource.shared;

import com.ensah.domain.User;
import com.ensah.payload.request.LoginRequest;
import com.ensah.payload.request.SignupRequest;
import com.ensah.payload.response.JwtResponse;
import com.ensah.payload.response.MessageResponse;
import com.ensah.repository.UserRepository;
import com.ensah.config.security.jwt.JwtUtils;
import com.ensah.utils.EmailService;
import com.ensah.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@Slf4j
@Validated
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;




    @Autowired
    EmailService emailService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {

        log.info("Authenticating " + loginRequest);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getAcademicmail(), loginRequest.getPassword()));

        log.info("authentication " + authentication.isAuthenticated());


        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("authentication {} and authentication {} ", authentication.isAuthenticated(),SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        String jwt = jwtUtils.generateJwtToken(authentication);

        log.info("jwt generated is " + jwt);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        log.info("user authority " + roles.iterator().next().getAuthority());
        String role = roles.iterator().next().getAuthority();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(),
                role));
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
                                          ) throws IOException {

                         log.info(" firstname {} lastname {} email {} password", firstName, lastName,normalEmail,password);

        log.info("singup  User ");

        if (userRepository.existsByAcademicemail(academicEmail)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: academic email is already taken!"));
        }


        String pathImage;
        if (role.equals("ADMIN")) {
            pathImage = ImageUtils.saveImage(picture);
        } else {
            pathImage = ImageUtils.saveImage(picture);
        }

        User user = new User(firstName, lastName, academicEmail, normalEmail, mobilePhone, encoder.encode(password), pathImage, role, grade);
        User x = userRepository.save(user);
        emailService.sendSimpleMessage(normalEmail, "ENSAH : SERVICE DE GESTION DE TERRAIN ", "Bienvenue " + firstName + " " + lastName + "\r\n" + " nous vous informorons que vous avez bien inscrit dans l'application GESTION de TERRAIN DE L'ENSAH \n Merci");
        log.info("User saved " + x);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));


}

    @GetMapping("/user")
    public String getUserDetails(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Map<String, String> test) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            String currentUserName = authentication.getName();
//            return currentUserName;
//        }else{
//            throw new RuntimeException("No User Authentication");
//        }
//        return "User Details: " + userDetails.getUsername()+ test.get("name");
        return null;
    }

}