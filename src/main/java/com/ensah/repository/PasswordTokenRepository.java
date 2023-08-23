package com.ensah.repository;

import com.ensah.domain.PasswordResetToken;
import com.ensah.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordTokenRepository  extends JpaRepository<PasswordResetToken,Long> {
    PasswordResetToken findByToken(String token);

    @Query("select p.user from PasswordResetToken p where p.token=:token")
    Optional<User> findUserByToken(String token);


}
