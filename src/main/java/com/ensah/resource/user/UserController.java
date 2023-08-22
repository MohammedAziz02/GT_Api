package com.ensah.resource.user;

import com.ensah.domain.User;
import com.ensah.payload.response.MessageResponse;
import com.ensah.service.UserService;
import com.ensah.utils.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
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


    @PostMapping("/resetpassword")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody  String userEmail) throws MessagingException, IOException {
        User user = userService.findUserByAcademicEmail(userEmail);
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        String url = "<p>\n" +
                "    hello Dear, \n" +
                "    you're in the reset Password page\n" +
                "    <a href='http://localhost:4200/accueil/verify-token/"+token+"'> click here</a>" +
                "</p>";

        emailService.sendMessageWithAttachment(user.getNormalemail(),"Reset Password",url);
        return  new ResponseEntity<>(new MessageResponse("Email reset password Sended successfully verify your Email : "+user.getNormalemail()), HttpStatus.OK);
    }


    @PostMapping("/verify-token")
    public ResponseEntity<Boolean> showChangePasswordPage(@RequestBody String token) {
        log.info("Verifying token... 1"+ token);
        boolean isvalid = userService.validatePasswordResetToken(token);
        log.info("Verifying token... 2 :  "+isvalid);
        if (isvalid){
            log.info("Verifying token : rah S7i7 ");
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        log.info("Verifying token : rah ghalat ");
        return new ResponseEntity<>(false, HttpStatus.OK);

    }


}
