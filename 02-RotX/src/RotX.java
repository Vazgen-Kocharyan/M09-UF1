public class RotX {
    private static final char[] MINUS = "aáàäbcçdeèéfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();
    private static final char[] MAYUS = "aáàäbcçdeèéfghiíìïjklmnñoóòpqrstuúùüvwxyz".toUpperCase().toCharArray();

    public static String aplicaRotX(String cadena, int desplacament, boolean encriptar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cadena.length(); i++) {
            if (Character.isLowerCase(cadena.charAt(i))) {
                sb.append(MINUS[buscarIndex(cadena.charAt(i), MINUS, desplacament, encriptar)]);
            } else if (Character.isUpperCase(cadena.charAt(i))) {
                sb.append(MAYUS[buscarIndex(cadena.charAt(i), MAYUS, desplacament, encriptar)]);
            } else {
                sb.append(cadena.charAt(i));
            }
        }
        return sb.toString();
    }

    public static int buscarIndex(char caracter, char[] llista, int desplacament, boolean encriptar) {
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

    public static String xifraRotX(String cadena, int desplacament) {
        return aplicaRotX(cadena, desplacament, true);
    }

    public static String desxifraRotX(String cadena, int desplacament) {
        return aplicaRotX(cadena, desplacament, false);
    }

    public static void forcaBrutaRotX(String cadenaXifrada) {
        for (int i = 0; i < MINUS.length; i++) {
            System.out.println("(" + i + ")->" + aplicaRotX(cadenaXifrada, i, false));
        }
    }

    public static void main(String[] args) {
        String[] noXifrat = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};

        System.out.println("Xifrat");
        System.out.println("------");

        String[] xifrat = new String[noXifrat.length];
        for (int i = 0; i < noXifrat.length; i++) {
            System.out.print("(" + (i*2) + ")-" + noXifrat[i] + " => ");
            xifrat[i] = xifraRotX(noXifrat[i], i*2);
            System.out.println(xifrat[i]);
        }

        System.out.println("\nDesxifrat");
        System.out.println("------");

        for (int i = 0; i < xifrat.length; i++) {
            System.out.print("(" + (i*2) + ")-" + xifrat[i] + " => ");
            System.out.println(desxifraRotX(xifrat[i], i*2));
        }
        System.out.println();

        System.out.println("Missatge xifrat: Úiüht, úiü wx ùxì ív?");
        System.out.println("--------------------");
        forcaBrutaRotX("Úiüht, úiü wx ùxì ív?");
    }

}