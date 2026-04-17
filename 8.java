import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws Exception {

        String message = "Hello world";

        // Generate RSA key pair
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        PublicKey pubKey = kp.getPublic();
        PrivateKey priKey = kp.getPrivate();

        // Encrypt
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] encrypted = cipher.doFinal(message.getBytes());
        String encMsg = Base64.getEncoder().encodeToString(encrypted);

        // Decrypt
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        byte[] decrypted = cipher.doFinal(encrypted);
        String decMsg = new String(decrypted);

        System.out.println("Original: " + message);
        System.out.println("Encrypted: " + encMsg);
        System.out.println("Decrypted: " + decMsg);
    }
}
