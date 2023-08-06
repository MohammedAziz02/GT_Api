package com.ensah.resource.shared;
import com.ensah.domain.User;
import com.ensah.payload.request.LoginRequest;
import com.ensah.payload.request.SignupRequest;
import com.ensah.payload.response.JwtResponse;
import com.ensah.payload.response.MessageResponse;
import com.ensah.repository.UserRepository;
import com.ensah.config.security.jwt.JwtUtils;
import com.ensah.utils.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

/**
 * @author med_Aziz
 * @version 1.0
 */


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
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
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest signUpRequest) throws MessagingException, IOException {
        log.info("request singup "+signUpRequest);
        if (userRepository.existsByAcademicemail(signUpRequest.getAcademicemail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: academic email is already taken!"));
        }
        User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getAcademicemail(), signUpRequest.getNormalemail(), signUpRequest.getPhonenumber(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getPicture(), signUpRequest.getRole().name(),signUpRequest.getGrade());
        User x=userRepository.save(user);
        emailService.sendSimpleMessage(signUpRequest.getNormalemail(),"ENSAH : SERVICE DE GESTION DE TERRAIN ","Bienvenue "+signUpRequest.getFirstname() + " " + signUpRequest.getLastname() + "\r\n" + " nous vous informorons que vous avez bien inscrit dans l'application GESTION de TERRAIN DE L'ENSAH \n Merci");
        log.info("User saved "+ x);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}