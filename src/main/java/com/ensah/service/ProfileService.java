package com.ensah.service;
import com.ensah.dto.ChangePasswordRequest;
import com.ensah.dto.UserDto;
import com.ensah.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;



public interface ProfileService {
    ResponseEntity<UserDto> getProfile(String emailacademic);
    ResponseEntity<UserDto> updateProfile(UserDto user);
    public ResponseEntity<MessageResponse> changePassword(ChangePasswordRequest request);
}
