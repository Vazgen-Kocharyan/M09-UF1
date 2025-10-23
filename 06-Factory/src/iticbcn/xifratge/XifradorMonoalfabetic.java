package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;

public class XifradorMonoalfabetic implements Xifrador {

    private static final char[] alfabet = "AÁÀÄBCÇDEÈÉFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();
    private char[] permutat = permutaAlfabet(alfabet);

    public char[] permutaAlfabet(char[] alfabet) {
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

    public String aplicaMonoAlfa(String cadena, char[] alfabet, char[] permutat) {
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

    public String xifraMonoAlfa(String cadena) {
        return aplicaMonoAlfa(cadena, alfabet, permutat);
    }

    public String desxifraMonoAlfa(String cadena) {
        return aplicaMonoAlfa(cadena, permutat, alfabet);
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            if (clau != null) throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
            return new TextXifrat(xifraMonoAlfa(msg).getBytes());
        } catch (Exception e) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            if (clau != null) throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
            return desxifraMonoAlfa(xifrat.toString());
        } catch (Exception e) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
    }
}