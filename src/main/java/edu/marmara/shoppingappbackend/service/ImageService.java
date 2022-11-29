package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.ImageResponse;
import edu.marmara.shoppingappbackend.model.Image;
import edu.marmara.shoppingappbackend.repository.ImageRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageResponse saveImage(MultipartFile file) {
        try {
            Image image = new Image();
            image.setData(file.getBytes());
            image.setSize(file.getSize());
            image.setFileType(file.getContentType());
            image.setUuid(UUID.randomUUID().toString());
            Image savedImage = imageRepository.save(image);
            return MappingHelper.map(savedImage, ImageResponse.class);
        } catch (Exception exception) {
            throw new RuntimeException("Error while saving image");
        }
    }

    public Image getImage(String uuid) {
        Optional<Image> optionalImage = imageRepository.findByUuid(uuid);
        if (optionalImage.isPresent()) {
            return optionalImage.get();
        } else {
            throw new RuntimeException("Image not found");
        }
    }
}
