package org.example.model.utils;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * A utility class for handling image-related operations.
 */
public class ImageController {
    /**
     * Converts an image file to a byte array.
     *
     * @param imagePath The path to the image file.
     * @return The byte array representing the image, or null if an error occurs.
     */
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
