import java.util.ArrayList;
import java.util.Collections;

public class Monoalfabetic {

    private static final char[] alfabet = "AÁÀÄBCÇDEÈÉFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
    private static char[] permutat = permutaAlfabet(alfabet);

    public static char[] permutaAlfabet(char[] alfabet) {
        ArrayList<Character> arr = new ArrayList<>();
        
        for (int i = 0; i < alfabet.length; i++) {
            arr.add(alfabet[i]);
        }

        Collections.shuffle(arr);

        char[] llista = new char[alfabet.length];

        for (int i = 0; i < llista.length; i++) {
            llista[i] = arr.get(i);
        }
        
        return llista;
    }

    public static String aplicaMonoAlfa(String cadena, char[] alfabet, char[] permutat) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cadena.length(); i++) {
            for (int j = 0; j < alfabet.length; j++) {
                if (Character.isUpperCase(cadena.charAt(i))) {
                    if (cadena.charAt(i) == alfabet[j]) {
                        sb.append(permutat[j]);
                    }
                } else if (Character.isLowerCase(cadena.charAt(i))) {
                    if (cadena.charAt(i) == (new String(alfabet).toLowerCase().toCharArray())[j]) {
                        sb.append((new String(permutat).toLowerCase().toCharArray())[j]);
                    }
                } else {
                    sb.append(cadena.charAt(i));
                    break;
                }
            }
        }
        return sb.toString();
    }

    public static String xifraMonoAlfa(String cadena) {
        return aplicaMonoAlfa(cadena, alfabet, permutat);
    }

    public static String desxifraMonoAlfa(String cadena) {
        return aplicaMonoAlfa(cadena, permutat, alfabet);
    }


    public static void main(String[] args) {

        String[] noXifrades = {"Hola", "Qué tal estas", "2Hola32Adios"};
        String[] xifrades = new String[noXifrades.length];

        for (int i = 0; i < noXifrades.length; i++) {
            xifrades[i] = xifraMonoAlfa(noXifrades[i]);
        }
        
        for (int i = 0; i < xifrades.length; i++) {
            System.out.println(xifrades[i] + " -> " + desxifraMonoAlfa(xifrades[i]));
        }
    }
}