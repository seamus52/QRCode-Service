package qrcodeapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/api")
public class Controller {
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/qrcode")
    public ResponseEntity<BufferedImage> getImage() {
        BufferedImage bufferedImage = ImageSource.createImage();
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(bufferedImage);
    }
}
