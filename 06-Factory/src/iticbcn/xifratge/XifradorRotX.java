package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {
    private static final char[] MINUS = "aáàäbcçdeèéfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();
    private static final char[] MAYUS = "aáàäbcçdeèéfghiíìïjklmnñoóòpqrstuúùüvwxyz".toUpperCase().toCharArray();

    public String aplicaRotX(String cadena, int desplacament, boolean encriptar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cadena.length(); i++) {
            if (contiene(MINUS, cadena.charAt(i))) {
                sb.append(MINUS[buscarIndex(cadena.charAt(i), MINUS, desplacament, encriptar)]);
            } else if (contiene(MAYUS, cadena.charAt(i))) {
                sb.append(MAYUS[buscarIndex(cadena.charAt(i), MAYUS, desplacament, encriptar)]);
            } else {
                sb.append(cadena.charAt(i));
            }
        }
        return sb.toString();
    }

    public boolean contiene(char[] lista, char caracter) {
        for (char c : lista) {
            if (c == caracter) return true;
        }
        return false;
    }

    public int buscarIndex(char caracter, char[] llista, int desplacament, boolean encriptar) {
        for (int i = 0; i < llista.length; i++) {
            if (caracter == llista[i]) {
                if (encriptar) {
                    return (i+desplacament) % llista.length;
                } else {
                    return (i-desplacament + llista.length) % llista.length;
                }
            }
        }
        return 0;
    }

    public String xifraRotX(String cadena, int desplacament) {
        return aplicaRotX(cadena, desplacament, true);
    }

    public String desxifraRotX(String cadena, int desplacament) {
        return aplicaRotX(cadena, desplacament, false);
    }

    public void forcaBrutaRotX(String cadenaXifrada) {
        for (int i = 0; i < MINUS.length; i++) {
            System.out.println("(" + i + ")->" + aplicaRotX(cadenaXifrada, i, false));
        }
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            if (Integer.parseInt(clau) > 40 || Integer.parseInt(clau) < 0) throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
            return new TextXifrat(xifraRotX(msg, Integer.parseInt(clau)).getBytes());
        } catch (Exception e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            if (Integer.parseInt(clau) > 40 || Integer.parseInt(clau) < 0) throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
            return desxifraRotX(xifrat.toString(), Integer.parseInt(clau));
        } catch (Exception e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
    }
}