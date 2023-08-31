package com.ensah.service.Impl;

import com.ensah.config.security.jwt.JwtUtils;
import com.ensah.domain.User;
import com.ensah.payload.request.LoginRequest;
import com.ensah.payload.response.JwtResponse;
import com.ensah.payload.response.MessageResponse;
import com.ensah.repository.UserRepository;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

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

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
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

    @Override
    public ResponseEntity<?> registerUser(String firstName, String lastName, String normalEmail, String academicEmail, String mobilePhone, String role, String grade, String password, MultipartFile picture) throws IOException, MessagingException {
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
//        emailService.sendSimpleMessage(normalEmail, "ENSAH : SERVICE DE GESTION DE TERRAIN ", "Bienvenue " + firstName + " " + lastName + "\r\n" + " nous vous informorons que vous avez bien inscrit dans l'application GESTION de TERRAIN DE L'ENSAH \n Merci");
//        emailService.sendMessageWithAttachment(normalEmail,"ENSAH : SERVICE DE GESTION DE TERRAIN",String.format("<html><body><h1>Welcome %s %s </h1><p>Thank you for your registration .ENSAH TERRAIN SERVICE COPIRIGHTS / AZIZ MOHAMMED </p><img width=\"100\" height=\"100\" src='cid:logo'>" +
//                "</body></html>",firstName,lastName));
        Context context = new Context();
        context.setVariable("firstName", firstName);
        context.setVariable("lastName", lastName);
        emailService.sendEmailWithHtmlTemplate(normalEmail,"Account successfully created","account-created-template",context);
        log.info("User saved " + x);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));


    }
}
