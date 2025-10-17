import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new  byte[MIDA_IV];
    private static final String CLAU = "SoyProgramador";

    public static byte[] xifraAES(String msg, String clau) throws Exception {
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

    public static String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {
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

    public static void main(String[] args) {
        String msgs[] = {
                "Lorem ipsum dicet",
                "Hola Andrés cómo está tu cuñado",
                "Ágora illa Ôtto"
        };

        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat = "";

            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: " + e.getLocalizedMessage());
            }

            System.out.println("----------------------------");
            System.out.println("Msg : " + msg);
            System.out.println("Enc : " + Base64.getEncoder().encodeToString(bXifrats));
            System.out.println("Dec : " + desxifrat);
        }
    }
}
