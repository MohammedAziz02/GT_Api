package com.ensah.service;

import com.ensah.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author med_Aziz
 * @version 1.0
 */


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String academicemail) throws UsernameNotFoundException {
        com.ensah.domain.User user = userRepository.findUserByAcademicemail(academicemail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with academic Email: " + academicemail));

        return User.builder()
                .username(user.getAcademicemail())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }

}