package com.ensah.resource.shared;

import com.ensah.dto.ChangePasswordRequest;
import com.ensah.dto.UserDto;
import com.ensah.payload.response.MessageResponse;
import com.ensah.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api/shared/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;


    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getProfileByacAdemicEmail(@RequestBody @PathVariable("email") String email) throws ChangeSetPersister.NotFoundException {
        return profileService.getProfile(email);
    }

    @PutMapping("/")
    public ResponseEntity<UserDto> updateProfile(@RequestBody UserDto user) {
        return profileService.updateProfile(user);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<MessageResponse> changePassword(@RequestBody ChangePasswordRequest request){
        return profileService.changePassword(request);
    }


    @PostMapping("/changePicture")
    public ResponseEntity<MessageResponse> changePicture(@RequestParam("picture") @NotNull MultipartFile picture) throws IOException {
        return profileService.changePicture(picture);
    }



}
