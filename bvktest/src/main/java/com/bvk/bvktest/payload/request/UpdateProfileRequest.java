package com.bvk.bvktest.payload.request;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

@Data
public class UpdateProfileRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private String password;

    private MultipartFile image;

    private String position;
    
    private String reportsTo;
}
