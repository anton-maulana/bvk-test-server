package com.bvk.bvktest.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bvk.bvktest.model.Product;
import com.bvk.bvktest.model.User;
import com.bvk.bvktest.payload.request.UpdateProfileRequest;
import com.bvk.bvktest.payload.response.ApiResponse;
import com.bvk.bvktest.repository.ProductRepository;
import com.bvk.bvktest.repository.UserRepository;

@Service
public class ProfileService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse updateProfile(UpdateProfileRequest request) throws UsernameNotFoundException, IOException {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new UsernameNotFoundException("User not found with email : " + request.getEmail())
            );

        user.setName(request.getName());
        user.setPosition(request.getPosition());

        
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(request.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            // UUID uuid = UUID.randomUUID();
            // String fileName = uuid.toString();
            final String fileName = StringUtils.cleanPath(request.getImage().getOriginalFilename());
            String uploadDir = "user-photos/" + user.getId();
            saveFile(uploadDir, fileName, request.getImage());

            user.setImageUrl(uploadDir+"/"+fileName);
        }

        userRepository.save(user);

        return new ApiResponse(true, "Update profile successfully");

    }

    private void saveFile(String uploadDir, String fileName,
        MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }      
    }
}
