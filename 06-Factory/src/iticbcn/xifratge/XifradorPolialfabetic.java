package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {

    private static final char[] ALFABET = "AÁÀÄBCÇDEÈÉFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();

    private Random r;

    public void initRandom(Long clau) {
        r = new Random(clau);
    }

    public char[] permutaAlfabet() {
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

    public String aplicaPoliAlfa(String msg, boolean desxifra) {
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

    public String xifraPoliAlfa(String msg) {
        return aplicaPoliAlfa(msg, false);
    }

    public String desxifraPoliAlfa(String msg) {
        return aplicaPoliAlfa(msg, true);
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            initRandom(Long.parseLong(clau));
            return new TextXifrat(xifraPoliAlfa(msg).getBytes());
        } catch (Exception e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            initRandom(Long.parseLong(clau));
            return desxifraPoliAlfa(xifrat.toString());
        } catch (Exception e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
    }
}