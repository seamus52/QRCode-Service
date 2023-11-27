package qrcodeapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {
    static final Map<String, MediaType> validImageFormats =
            // TODO [lo-pri] convert to enum & enumset
            Map.of("png", MediaType.IMAGE_PNG,
                    "jpeg", MediaType.IMAGE_JPEG,
                    "gif", MediaType.IMAGE_GIF);
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/qrcode")
    public ResponseEntity<?> getImage(@RequestParam String contents, @RequestParam int size, @RequestParam String type) {
        // TODO [lo-pri] eliminate magic numbers and hard-coded messages
        if (contents.isBlank() || contents.isEmpty()) {
            return new ResponseEntity<>(Map.of("error","Contents cannot be null or blank"), HttpStatus.BAD_REQUEST);
        }

        if (size < 150 || size > 350) {
            return new ResponseEntity<>(Map.of("error","Image size must be between 150 and 350 pixels"), HttpStatus.BAD_REQUEST);
        }

        if (!validImageFormats.containsKey(type.toLowerCase())) {
            return new ResponseEntity<>(Map.of("error","Only png, jpeg and gif image types are supported"), HttpStatus.BAD_REQUEST);
        }

        BufferedImage bufferedImage = ImageSource.createImage(contents, size);
        return ResponseEntity
                .ok()
                .contentType(validImageFormats.get(type))
                .body(bufferedImage);
    }
}
