package qrcodeapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class Controller {
    static final Map<String, MediaType> validImageFormats =
            Map.of("png", MediaType.IMAGE_PNG,
                    "jpeg", MediaType.IMAGE_JPEG,
                    "gif", MediaType.IMAGE_GIF);
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/qrcode")
    public ResponseEntity<BufferedImage> getImage(@RequestParam int size, @RequestParam String type) {
        // TODO eliminate magic numbers
        if (size < 150 || size > 350) {
            throw new InvalidImageSizeException("Image size must be between 150 and 350 pixels");
        }

        if (!validImageFormats.containsKey(type.toLowerCase())) {
            throw new InvalidImageTypeException("Only png, jpeg and gif image types are supported");
        }

        BufferedImage bufferedImage = ImageSource.createImage(size);
        return ResponseEntity
                .ok()
                .contentType(validImageFormats.get(type))
                .body(bufferedImage);
    }

    @ExceptionHandler(InvalidImageSizeException.class)
    public ResponseEntity<Map<String, String>> handleRequestProcessingException(InvalidImageSizeException e) {
        // customize response format for exception:
        // {
        //    "error": "... exception message comes here ..."
        // }
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidImageTypeException.class)
    public ResponseEntity<Map<String, String>> handleRequestProcessingException(InvalidImageTypeException e) {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
