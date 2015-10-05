package library.utils.security.encryptation;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encrypt
 */
public class Encrypt {

    // Llave Simetrica es un String de tamaño múltiplo de 8
    // en este caso si es de tamaño 32 nos permite AES-256 (32 * 8)
    private String SYMMETRI_KEY = "7x-_2bIjMSp2IYCxGw4&o5K1xG#4X#SQ";

    public Encrypt() {
    }

    void setAESKey(String key) {
        SYMMETRI_KEY = key;
    }

    /**
     * Ecrypt AES.
     *
     * @param message the message
     * @return the string
     */
    public String encryptAES(String message) throws EncryptException {

        if (message == null || "".equals(message)) return null;

        SecretKeySpec key = new SecretKeySpec(SYMMETRI_KEY.getBytes(), "AES");
        Cipher cipher;
        try {

            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(new byte[16]);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encryptionField = cipher.doFinal(message.getBytes("utf-8"));
            return new String(Base64.encode(encryptionField, Base64.DEFAULT | Base64.NO_WRAP));

        } catch (IllegalBlockSizeException e) {
            throw new EncryptException(e.toString());
        } catch (InvalidKeyException e) {
            throw new EncryptException(e.toString());
        } catch (BadPaddingException e) {
            throw new EncryptException(e.toString());
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e.toString());
        } catch (NoSuchPaddingException e) {
            throw new EncryptException(e.toString());
        } catch (UnsupportedEncodingException e) {
            throw new EncryptException(e.toString());
        } catch (InvalidAlgorithmParameterException e) {
            throw new EncryptException(e.toString());
        }
    }

    /**
     * Decrypt AES.
     *
     * @param message the message
     * @return the string
     */
    public String decryptAES(String message) throws EncryptException {

        if (message == null || "".equals(message)) return null;

        SecretKeySpec key = new SecretKeySpec(SYMMETRI_KEY.getBytes(), "AES");
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(new byte[16]);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] inputByte = message.getBytes("UTF-8");
            byte[] dencryptionField = cipher.doFinal(Base64.decode(inputByte, Base64.DEFAULT));
            return new String(dencryptionField, "UTF-8");

        } catch (Exception e) {
            throw new EncryptException(e.toString());
        }
    }

    /**
     * Ecrypt MD5.
     *
     * @param s the string to encript
     * @return the string
     */
    public String encryptMD5(String s) throws EncryptException {
        try {

            MessageDigest md;
            byte[] buffer, digest;
            String hash = "";

            buffer = s.getBytes();
            md = MessageDigest.getInstance("MD5");
            md.update(buffer);
            digest = md.digest();

            for (byte aux : digest) {
                int b = aux & 0xff;
                if (Integer.toHexString(b).length() == 1)
                    hash += "0";
                hash += Integer.toHexString(b);
            }

            return new String(Base64.encode(hash.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP));

        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e.toString());
        }
    }

    /**
     * Ecrypt SHA256.
     *
     * @param s the string to encript
     * @return the string
     */
    public String encryptSHA256(String s) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(s.getBytes());
        return bytesToHex(md.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

}
