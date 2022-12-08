package edu.marmara.shoppingappbackend.controller;

import edu.marmara.shoppingappbackend.dto.ImageResponse;
import edu.marmara.shoppingappbackend.model.Image;
import edu.marmara.shoppingappbackend.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/image")
public class ImageController {

    ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Operation(summary = "this endpoint takes multipart file as image and saves it into database")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponse> uploadSingleFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imageService.saveImage(file));
    }

    @Operation(summary = "this endpoint takes uuid and returns image data")
    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> getImage(@PathVariable String uuid) {
        Image image = imageService.getImage(uuid);
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
    }

}
