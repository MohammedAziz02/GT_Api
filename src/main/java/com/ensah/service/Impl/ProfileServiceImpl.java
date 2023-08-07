package com.ensah.service.Impl;


import com.ensah.domain.User;
import com.ensah.dto.ChangePasswordRequest;
import com.ensah.dto.UserDto;
import com.ensah.payload.response.MessageResponse;
import com.ensah.repository.UserRepository;
import com.ensah.service.ProfileService;
import com.ensah.utils.ImageUtils;
import com.ensah.utils.UserUtils;
import com.ensah.web.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<UserDto> getProfile(String emailacademic) {
        User foundedUser = userRepository.findUserByAcademicemail(emailacademic).orElseThrow(()-> new NotFoundException("Could not find user " + emailacademic));
        UserDto userDto = modelMapper.map(foundedUser,UserDto.class);
        return  new ResponseEntity<>(userDto,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<UserDto> updateProfile(UserDto user) {
        // getting academic email
        String academicEmail = user.getAcademicemail();
        log.info("Updating with Academic email {}",user);
        User foundedUser= userRepository.findUserByAcademicemail(academicEmail).orElseThrow(()-> new NotFoundException("Could not find user " + academicEmail));
        foundedUser.setGrade(user.getGrade());
        foundedUser.setNormalemail(user.getNormalemail());
        foundedUser.setPhonenumber(user.getPhonenumber());
        foundedUser.setFirstName(user.getFirstName());
        foundedUser.setLastName(user.getLastName());
        User savedUser= userRepository.save(foundedUser);
        UserDto userDto = modelMapper.map(savedUser,UserDto.class);
        return  new ResponseEntity<>(userDto,HttpStatus.OK);
    }


    public ResponseEntity<MessageResponse> changePassword(ChangePasswordRequest request) {
        // Récupérer l'utilisateur à partir de la base de données (vous pouvez utiliser UserRepository)
        String academicEmail = request.getAcademicEmail();
        User user = userRepository.findUserByAcademicemail(academicEmail).orElseThrow(()-> new NotFoundException("Could not find user " + academicEmail));
        // Vérifier si l'ancien mot de passe fourni correspond à celui enregistré en base de données
        if (!bCryptPasswordEncoder.matches(request.getOldPassword(), user.getPassword())) {
//            throw new IllegalArgumentException("Ancien mot de passe incorrect");
            return new ResponseEntity<>(new MessageResponse("Incrorrect Old Password !!"),HttpStatus.BAD_REQUEST);
        }

        // Générer un nouveau hachage pour le nouveau mot de passe
        String newHashedPassword = bCryptPasswordEncoder.encode(request.getNewPassword());

        // Mettre à jour le mot de passe dans la base de données avec le nouveau hachage
        user.setPassword(newHashedPassword);
        userRepository.save(user);
        return new ResponseEntity<>(new MessageResponse("Yeah ! Your Password is changed "),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageResponse> changePicture(MultipartFile picture) throws IOException {
            String academicEmail= UserUtils.getLoggedinUser();
            log.info("changePicture  of user "+ academicEmail);
            User loggedUser= userRepository.findUserByAcademicemail(academicEmail).orElseThrow(()-> new NotFoundException("user with email " +academicEmail+ " not found"));
            String nameofpicture = loggedUser.getPicture();
            log.info("old picture of user is "+ nameofpicture);
            ImageUtils.deleteImage(nameofpicture);
            String nameofnewpicture = ImageUtils.saveImage(picture);
        log.info("new picture of user is "+ nameofnewpicture);
            loggedUser.setPicture(nameofnewpicture);
            userRepository.save(loggedUser);
            return  new ResponseEntity<>(new MessageResponse(nameofnewpicture),HttpStatus.OK);
    }

}
