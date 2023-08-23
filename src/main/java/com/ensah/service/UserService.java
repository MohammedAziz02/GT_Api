package com.ensah.service;

import com.ensah.domain.User;
import com.ensah.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.IOException;

public interface UserService {






    public ResponseEntity<Boolean> showChangePasswordPage(String token);

    public ResponseEntity<MessageResponse> resetPassword(String userEmail) throws MessagingException, IOException;
}
