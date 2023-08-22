package com.ensah.service;

import com.ensah.domain.User;

public interface UserService {

    User  findUserByAcademicEmail(String academicEmail);

    void createPasswordResetTokenForUser(User user, String token);

     Boolean validatePasswordResetToken(String token);
}
