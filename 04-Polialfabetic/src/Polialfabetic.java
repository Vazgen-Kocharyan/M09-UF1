import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Polialfabetic {
    private static final Long CLAU = Long.MAX_VALUE;

    private static final char[] ALFABET = "AÁÀÄBCÇDEÈÉFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();

    private static Random r;

    public static void initRandom(Long clau) {
        r = new Random(clau);
    }

    public static char[] permutaAlfabet() {
        ArrayList<Character> arr = new ArrayList<>();
        
        for (int i = 0; i < ALFABET.length; i++) {
            arr.add(ALFABET[i]);
        }

        Collections.shuffle(arr, r);

        char[] llista = new char[ALFABET.length];

        for (int i = 0; i < llista.length; i++) {
            llista[i] = arr.get(i);
        }
        
        return llista;
    }

    public static String aplicaPoliAlfa(String msg, boolean desxifra) {
        StringBuilder sb = new StringBuilder();
        char[] permutat;
        char[] alfabet = ALFABET;
        for (int i = 0; i < msg.length(); i++) {
            permutat = permutaAlfabet();
            if (desxifra) {
                alfabet = permutat;
                permutat = ALFABET;
            }

            for (int j = 0; j < alfabet.length; j++) {
                if (Character.isUpperCase(msg.charAt(i))) {
                    if (msg.charAt(i) == alfabet[j]) {
                        sb.append(permutat[j]);
                    }
                } else if (Character.isLowerCase(msg.charAt(i))) {
                    if (msg.charAt(i) == (new String(alfabet).toLowerCase().toCharArray())[j]) {
                        sb.append((new String(permutat).toLowerCase().toCharArray())[j]);
                    }
                } else {
                    sb.append(msg.charAt(i));
                    break;
                }
            }
        }
        return sb.toString();
    }

    public static String xifraPoliAlfa(String msg) {
        return aplicaPoliAlfa(msg, false);
    }

    public static String desxifraPoliAlfa(String msg) {
        return aplicaPoliAlfa(msg, true);
    }


    public static void main(String[] args) {
        String msgs[] = {"Test 01 àrbitre, coixí, Perímetre",
                        "Test 02 Taüll, DÍA, año",
                        "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(CLAU);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifratge:\n-----------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(CLAU);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }

}
