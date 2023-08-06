package com.ensah.dto;

import com.ensah.commons.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends AbstractDto {
    private  Long id;
    private String firstName;
    private String lastName;
    private String phonenumber;
    private String normalemail;
    private String academicemail;
    private String picture;
    private String role;
    private String grade;
}

