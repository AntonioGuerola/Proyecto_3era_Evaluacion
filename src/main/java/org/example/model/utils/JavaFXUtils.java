package org.example.model.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.App;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A utility class for JavaFX-related operations.
 */
public class JavaFXUtils {
    /**
     * Displays an error alert dialog.
     *
     * @param title           The title of the alert dialog.
     * @param textAboutAlert  The content text of the alert dialog.
     */
    public static void showErrorAlert(String title, String textAboutAlert) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        if (errorAlert.getDialogPane().getScene().getWindow() != null) {
            Stage alertStage = (Stage) errorAlert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("images/LogoG3DMedio.png"))));
        }
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(textAboutAlert);
        errorAlert.showAndWait();
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param hash The byte array to convert.
     * @return The hexadecimal string representing the byte array.
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Calculates the SHA3-256 hash of a given password.
     *
     * @param password The password to hash.
     * @return The SHA3-256 hash of the password.
     * @throws NoSuchAlgorithmException If the hash algorithm is not available.
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        String sha3Hex = bytesToHex(hashbytes);
        return sha3Hex;
    }

    /**
     * Validates an email address using a regular expression pattern.
     *
     * @param email The email address to validate.
     * @return True if the email is valid, otherwise false.
     */
    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        return pattern.matcher(email).matches();
    }

}
