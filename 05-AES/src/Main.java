import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class Main {

    private static final String CLAU = "SoyProgramador";

    public static void main(String[] args) {
        // Lista de frases de prueba
        String[] frases = {
                "Hello World!",
                "Soy programador Java.",
                "1234567890",
                "¡Hola, mundo!",
                "Prueba con símbolos: @#$_&-+()*",
                "Frase con acentos: programación, café, canción",
                "Texto largo de prueba para verificar que el cifrado funciona correctamente incluso con frases más extensas.",
                "",
                " "
        };

        // Probar cada frase
        for (String original : frases) {
            String encriptado = encriptar(original);
            String desencriptado = decriptar(encriptado);

            System.out.println("-------------------------------------------");
            System.out.println("Texto original   : " + original);
            System.out.println("Texto encriptado : " + encriptado);
            System.out.println("Texto desencriptado: " + desencriptado);

            if (original.equals(desencriptado)) {
                System.out.println("✅ Correcto");
            } else {
                System.out.println("❌ Error");
            }
        }
    }

    public static SecretKeySpec crearClave(String CLAU) {
        try {
            byte[] cadena = CLAU.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            cadena = md.digest(cadena);
            cadena = Arrays.copyOf(cadena, 16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(cadena, "AES");
            return secretKeySpec;
        } catch (Exception e) {
            return null;
        }
    }

    public static String encriptar(String cadena) {
        try {
            SecretKeySpec secretKeySpec = crearClave(CLAU);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] bytes = cadena.getBytes("UTF-8");
            byte[] encrypted = cipher.doFinal(bytes);
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            return "";
        }

    }

    public static String decriptar(String cadena) {
        try {
            SecretKeySpec secretKeySpec = crearClave(CLAU);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(cadena));
            return new String(bytes);
        } catch (Exception e) {
            return "";
        }
    }


}
