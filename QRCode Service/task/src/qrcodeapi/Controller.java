package qrcodeapi;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
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
    private static final String CONTENT_EMPTY_ERROR_MSG = "Contents cannot be null or blank";
    private static final String IMAGE_SIZE_INVALID_ERROR_MSG = "Image size must be between 150 and 350 pixels";
    private static final String CORRECTION_LEVEL_INVALID_ERROR_MSG = "Permitted error correction levels are L, M, Q, H";
    private static final String IMAGE_FORMAT_INVALID_ERROR_MSG = "Only png, jpeg and gif image types are supported";
    private static final Map<String, MediaType> validImageFormats =
            Map.of("PNG", MediaType.IMAGE_PNG,
                    "JPEG", MediaType.IMAGE_JPEG,
                    "GIF", MediaType.IMAGE_GIF);
    private static final Set<String> validCorrectionValues = Set.of("L", "M", "Q", "H");
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/qrcode")
    public ResponseEntity<?> getImage(@RequestParam String contents, @RequestParam(defaultValue = "250") int size,
                                      @RequestParam(defaultValue = "PNG") String type,
                                      @RequestParam(defaultValue = "L") String correction) {
        if (contents.isBlank() || contents.isEmpty()) {
            return new ResponseEntity<>(Map.of("error",CONTENT_EMPTY_ERROR_MSG), HttpStatus.BAD_REQUEST);
        }

        if (size < 150 || size > 350) {
            return new ResponseEntity<>(Map.of("error",IMAGE_SIZE_INVALID_ERROR_MSG), HttpStatus.BAD_REQUEST);
        }

        if (!validCorrectionValues.contains(correction.toUpperCase())) {
            return new ResponseEntity<>(Map.of("error",CORRECTION_LEVEL_INVALID_ERROR_MSG), HttpStatus.BAD_REQUEST);
        }

         if (!validImageFormats.containsKey(type.toUpperCase())) {
            return new ResponseEntity<>(Map.of("error",IMAGE_FORMAT_INVALID_ERROR_MSG), HttpStatus.BAD_REQUEST);
        }

        BufferedImage bufferedImage = ImageSource.createImage(contents, size, ErrorCorrectionLevel.valueOf(correction));
        return ResponseEntity
                .ok()
                .contentType(validImageFormats.get(type.toUpperCase()))
                .body(bufferedImage);
    }
}
