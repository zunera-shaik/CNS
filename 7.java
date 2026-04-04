import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.KeyStore;
import java.io.FileInputStream;
import java.util.Base64;

public class BlowfishKeyTool {

    private static final String KEYSTORE_FILE = "blowfish.jks";
    private static final String STORE_PASSWORD = "store@123";
    private static final String KEY_ALIAS = "blowfishkey";
    private static final String KEY_PASSWORD = "key@123";

    public static SecretKey loadKeyFromKeyStore() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        FileInputStream fis = new FileInputStream(KEYSTORE_FILE);
        keyStore.load(fis, STORE_PASSWORD.toCharArray());

        KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry)
                keyStore.getEntry(KEY_ALIAS,
                        new KeyStore.PasswordProtection(KEY_PASSWORD.toCharArray()));

        return entry.getSecretKey();
    }

    public static String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String cipherText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes =
                cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decryptedBytes, "UTF-8");
    }

    public static void main(String[] args) {
        try {
            String plainText = "Hello World";

            System.out.println("Original Text : " + plainText);

            SecretKey secretKey = loadKeyFromKeyStore();

            System.out.println("Key Algorithm : " + secretKey.getAlgorithm());
            System.out.println("Key (Base64) : " +
                    Base64.getEncoder().encodeToString(secretKey.getEncoded()));

            String encrypted = encrypt(plainText, secretKey);
            System.out.println("Encrypted Text : " + encrypted);

            String decrypted = decrypt(encrypted, secretKey);
            System.out.println("Decrypted Text : " + decrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
