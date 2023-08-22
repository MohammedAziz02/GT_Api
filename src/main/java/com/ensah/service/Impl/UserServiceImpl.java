package com.ensah.service.Impl;

import com.ensah.domain.PasswordResetToken;
import com.ensah.domain.User;
import com.ensah.repository.PasswordTokenRepository;
import com.ensah.repository.UserRepository;
import com.ensah.service.UserService;
import com.ensah.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Override
    public User findUserByAcademicEmail(String academicEmail) {
        return userRepository.findUserByAcademicemail(academicEmail).orElseThrow(()->new NotFoundException("academic email not found" + academicEmail));
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public Boolean validatePasswordResetToken(String token) {
        PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if(passToken==null) return false;
        return passToken.getToken().equals(token);
    }




}
