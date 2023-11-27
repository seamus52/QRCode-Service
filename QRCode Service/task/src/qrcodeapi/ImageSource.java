package qrcodeapi;

import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


public class ImageSource {

    public static BufferedImage createImage(String data, int size) {
        BufferedImage bufferedImage = null;
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, size, size);
            bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            // TODO log and handle the WriterException
            throw new RuntimeException("Failed to generate QR code from string: " + data);
        }

        return bufferedImage;
    }
}
