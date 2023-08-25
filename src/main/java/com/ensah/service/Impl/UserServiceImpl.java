package com.ensah.service.Impl;

import com.ensah.domain.PasswordResetToken;
import com.ensah.domain.User;
import com.ensah.payload.response.MessageResponse;
import com.ensah.repository.PasswordTokenRepository;
import com.ensah.repository.UserRepository;
import com.ensah.service.UserService;
import com.ensah.utils.EmailService;
import com.ensah.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Autowired
    EmailService emailService;


    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    public Boolean validatePasswordResetToken(String token) {
        PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if(passToken==null) return false;
        return passToken.getToken().equals(token);
    }



    @Override
    public ResponseEntity<MessageResponse> resetPassword(String userEmail) throws MessagingException, IOException {
        User user =  userRepository.findUserByAcademicemail(userEmail).orElseThrow(()->new NotFoundException("academic email not found " + userEmail));
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        // context adding variable data to template like binding data
        Context context = new Context();
        context.setVariable("firstName", user.getFirstName());
        context.setVariable("lastName", user.getLastName());
        context.setVariable("token",token);
        emailService.sendEmailWithHtmlTemplate(user.getNormalemail(),"Reset Password","reset-password-template",context);
        return  new ResponseEntity<>(new MessageResponse("Email reset password Sended successfully verify your Email : "+user.getNormalemail()), HttpStatus.OK);

    }


    @Override
    public ResponseEntity<Boolean> showChangePasswordPage(String token) {
        boolean isvalid = validatePasswordResetToken(token);
        if (isvalid){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }
}
