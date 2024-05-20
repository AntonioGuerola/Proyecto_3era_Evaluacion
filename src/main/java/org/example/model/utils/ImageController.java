package org.example.model.utils;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageController {
    public static byte[] imageToBytes(String imagePath) {
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            System.err.println("El archivo de imagen no existe: " + imagePath);
            return null;
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            if (bufferedImage == null) {
                System.err.println("El archivo no es una imagen válida: " + imagePath);
                return null;
            }

            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteOutStream);
            return byteOutStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
