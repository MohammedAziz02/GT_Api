package com.ensah.repository;

import com.ensah.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByAcademicemail(String academicemail);

    Boolean existsByAcademicemail(String academicemail);

}
