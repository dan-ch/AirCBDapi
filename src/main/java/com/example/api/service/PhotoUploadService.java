package com.example.api.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.api.model.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class PhotoUploadService {

  private final Cloudinary cloudinary;

  public PhotoUploadService(@Value("${cloudinary.cloud_name}") String cloudName,
                            @Value("${cloudinary.api_key}") String apiKey,
                            @Value("${cloudinary.api_secret}") String apiSecret) {
    this.cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", cloudName,
        "api_key", apiKey,
        "api_secret", apiSecret,
        "secure", true));
  }

  public Image uploadPhoto(MultipartFile photo) throws IOException {
    Map uploadResult = cloudinary.uploader().upload(photo.getBytes(), Map.of("folder", "LuftBnB"));
    Image image = new Image((String) uploadResult.get("url"), (String) uploadResult.get("public_id"));
    return image;
  }

  public boolean deletePhoto(String cloudId) throws Exception {
    Map deleteResult = cloudinary.uploader().destroy(cloudId, ObjectUtils.emptyMap());
    return "ok".equals((String)deleteResult.get("result"));
  }

}
