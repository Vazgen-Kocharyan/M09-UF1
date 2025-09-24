public class Rot13 {
	private static char[] minuscules = "aáàäbcçdeèéfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();
	private static char[] majuscules = "AÁÀÄBCÇDEÈÉFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ".toCharArray();

    public static int buscarIndex(char caracter, char[] llista, boolean encriptar) {
        for (int j = 0; j < llista.length; j++) {
				if (caracter == llista[j]) {
                    if (encriptar) {
                        return (j+13) % llista.length;
                    } else {
                        return (j - 13 + llista.length) % llista.length;
                    }
			}					
		}
        return 0;
    }

	public static void xifraRot13(String cadena) {
		aplicaRot13(cadena, true);
	}

    public static void desxifraRot13(String cadena) {
        aplicaRot13(cadena, false);
    }

    public static void aplicaRot13(String cadena, boolean encriptar) {
        for (int i = 0; i < cadena.length(); i++) {
            if (Character.isLowerCase(cadena.charAt(i))) {
                System.out.print(minuscules[buscarIndex(cadena.charAt(i), minuscules, encriptar)]);
            } else if (Character.isUpperCase(cadena.charAt(i))) {
                System.out.print(majuscules[buscarIndex(cadena.charAt(i), majuscules, encriptar)]);
            } else {
                System.out.print(cadena.charAt(i));
            }
        }
        System.out.println();
    }


    public static void main(String[] args) {
        String[] noXifrat = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};
        
        System.out.println("Xifrat");
        System.out.println("------");

        for (int i = 0; i < noXifrat.length; i++) {
            System.out.print(noXifrat[i] + " => ");
            xifraRot13(noXifrat[i]);
        }
        System.out.println();

        String[] xifrat = {"HÏJ", "ÉFG", "Òwúh, Ùá. jhúkwä", "Zmálx, zmá äb abn ñà?"};

        System.out.println("Desxifrat");
        System.out.println("---------");

        for (int i = 0; i < xifrat.length; i++) {
            System.out.print(xifrat[i] + " => ");
            desxifraRot13(xifrat[i]);
        }
    }
}