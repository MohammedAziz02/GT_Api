package com.ensah.service;

import com.ensah.payload.request.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;


public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUser( String firstName,String lastName, String normalEmail,
                                    String academicEmail,
                                    String mobilePhone,
                                    String role,
                                    String grade,
                                    String password,
                                    MultipartFile picture ) throws IOException, MessagingException;
}
