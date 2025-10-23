package iticbcn.xifratge;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class XifradorAES implements Xifrador {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private byte[] iv = new  byte[MIDA_IV];

    public byte[] xifraAES(String msg, String clau) throws Exception {
        // Obtenir els bytes de l'String
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);

        // Genera IvParameterSpec
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Genera hash
        MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = sha.digest(clau.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec key = new SecretKeySpec(hash, ALGORISME_XIFRAT);

        // Encrypt.
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] xifrat = cipher.doFinal(bytes);

        // Combinar IV i part xifrada.
        byte[] resultat = new byte[xifrat.length + iv.length];
        System.arraycopy(iv, 0, resultat, 0, iv.length);
        System.arraycopy(xifrat, 0, resultat, iv.length, xifrat.length);

        // return iv+msgxifrat
        return resultat;
    }

    public String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {
        // Extreure l'IV.
        byte[] xifrat =  new byte[bIvIMsgXifrat.length - MIDA_IV];
        System.arraycopy(bIvIMsgXifrat, 0, iv, 0, MIDA_IV);
        System.arraycopy(bIvIMsgXifrat,  MIDA_IV, xifrat, 0, xifrat.length);

        // Extreure la part xifrada.
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Fer hash de la clau
        MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = sha.digest(clau.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec key = new SecretKeySpec(hash, ALGORISME_XIFRAT);

        // Desxifrar.
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        // return String desxifrat
        return new String(cipher.doFinal(xifrat), StandardCharsets.UTF_8);
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            return new TextXifrat(xifraAES(msg, clau));
        } catch (Exception e) {
            throw new ClauNoSuportada("");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            return desxifraAES(xifrat.getBytes(), clau);
        } catch (Exception e) {
            throw new ClauNoSuportada("");
        }
    }
}
