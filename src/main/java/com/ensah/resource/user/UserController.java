package com.ensah.resource.user;

import com.ensah.domain.PasswordResetToken;
import com.ensah.domain.User;
import com.ensah.dto.PasswordResetDto;
import com.ensah.payload.response.MessageResponse;
import com.ensah.repository.PasswordTokenRepository;
import com.ensah.repository.UserRepository;
import com.ensah.service.UserService;
import com.ensah.utils.EmailService;
import com.ensah.web.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.swing.text.StyledEditorKit;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("v1/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @PostMapping("/resetpassword")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody  String userEmail) throws MessagingException, IOException {
       return  userService.resetPassword(userEmail);
    }


    @PostMapping("/verify-token")
    public ResponseEntity<Boolean> showChangePasswordPage(@RequestBody String token) {
      return  userService.showChangePasswordPage(token);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Boolean> changePasswordwithToken(@RequestBody PasswordResetDto resetToken){
        User user= passwordTokenRepository.findUserByToken(resetToken.getTokenPassword()).orElseThrow(()-> new NotFoundException("User nout found with this token "+resetToken.getTokenPassword()));
        user.setPassword(bCryptPasswordEncoder.encode(resetToken.getNewPassword()));
        userRepository.save(user);
        return  new ResponseEntity<>(true,HttpStatus.OK);
    }









}
