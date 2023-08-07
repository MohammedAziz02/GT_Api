package com.ensah.service;
import com.ensah.dto.ChangePasswordRequest;
import com.ensah.dto.UserDto;
import com.ensah.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;


public interface ProfileService {
    ResponseEntity<UserDto> getProfile(String emailacademic);
    ResponseEntity<UserDto> updateProfile(UserDto user);
    public ResponseEntity<MessageResponse> changePassword(ChangePasswordRequest request);

    public ResponseEntity<MessageResponse> changePicture(MultipartFile picture) throws IOException;
}
