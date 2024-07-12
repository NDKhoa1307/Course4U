package com.mgmtp.cfu.service.impl;

import com.mgmtp.cfu.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public String uploadThumbnail(MultipartFile thumbnail, String directory) throws IOException {
        String uuidFilename = UUID.randomUUID().toString() + ".jpg"; // Default extension
        String thumbnailUrl = uuidFilename;
        if (thumbnail != null && !thumbnail.isEmpty()) {
            Path uploadPath = Paths.get(directory).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String originalFilename = thumbnail.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            uuidFilename = UUID.randomUUID().toString() + fileExtension; // Update UUID filename with correct extension
            Path filePath = uploadPath.resolve(uuidFilename);
            thumbnail.transferTo(filePath.toFile());
            thumbnailUrl = uuidFilename;
        }
        return thumbnailUrl;
    }
}