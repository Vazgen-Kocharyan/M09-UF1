import java.security.MessageDigest;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
	public String getSHA512AmbSalt(String pw, String salt) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-512");

		md.update(salt.getBytes("UTF-8"));
		byte[] hashBytes = md.digest(pw.getBytes("UTF-8"));

		HexFormat hex = HexFormat.of();

		return hex.formatHex(hashBytes);
	}

	public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {

		int it = 65536;
		int keyLength = 512;

		PBEKeySpec spec = new PBEKeySpec(
			pw.toCharArray(),
			salt.getBytes("UTF-8"),
			it,
			keyLength	
		);

		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		byte[] hash = skf.generateSecret(spec).getEncoded();

		HexFormat hex = HexFormat.of();

		return hex.formatHex(hash);
	}

	public String forcaBruta(String alg, String hash, String salt) throws Exception {

		char[] charset = "abcdefABCDEF1234567890!".toCharArray();

		int cont = 0;
		// for (int i = 0; i < charset.length; i++) {
		// 	String s = String.format("%s", charset[i]);
	
		// 	String intent = comprobar(alg, hash, salt, s);
		// 	cont++;
		// 	if (intent != null) {
		// 		return intent;
		// 	}		
		// }
		
		// for (int i = 0; i < charset.length; i++) {
		// 	for (int j = 0; j < charset.length; j++) {
		// 		String s = String.format("%s%s", charset[i], charset[j]);

		// 		String intent = comprobar(alg, hash, salt, s);
		// 		cont++;
		// 		System.out.println(cont);

		// 		if (intent != null) {
		// 			return intent;
		// 		}	
		// 	}
		// }
		
		// for (int i = 0; i < charset.length; i++) {
		// 	for (int j = 0; j < charset.length; j++) {
		// 		for (int k = 0; k < charset.length; k++) {	
		// 			String s = String.format("%s%s%s", charset[i], charset[j], charset[k]);
					
		// 			String intent = comprobar(alg, hash, salt, s);
		// 			cont++;
		// 		System.out.println(cont);

		// 			if (intent != null) {
		// 				return intent;
		// 			}	
		// 		}
		// 	}
		// }
		// for (int i = 0; i < charset.length; i++) {
		// 	for (int j = 0; j < charset.length; j++) {
		// 		for (int k = 0; k < charset.length; k++) {	
		// 			for (int l = 0; l < charset.length; l++) {	
		// 				String s = String.format("%s%s%s%s", charset[i], charset[j], charset[k], charset[l]);
						
		// 				String intent = comprobar(alg, hash, salt, s);
		// 				cont++;
		// 		System.out.println(cont);
						
		// 				if (intent != null) {
		// 					return intent;
		// 				}	
		// 			}
		// 		}
		// 	}
		// }
		// for (int i = 0; i < charset.length; i++) {
		// 	for (int j = 0; j < charset.length; j++) {
		// 		for (int k = 0; k < charset.length; k++) {	
		// 			for (int l = 0; l < charset.length; l++) {
		// 				for (int m = 0; m < charset.length; m++) {	
		// 					String s = String.format("%s%s%s%s%s", charset[i], charset[j], charset[k], charset[l], charset[m]);
							
		// 					String intent = comprobar(alg, hash, salt, s);
		// 					cont++;
		// 		System.out.println(cont);

		// 					if (intent != null) {
		// 						return intent;
		// 					}	
		// 				}
		// 			}
		// 		}
		// 	}
		// }
		for (int i = 0; i < charset.length; i++) {
			for (int j = 0; j < charset.length; j++) {
				for (int k = 0; k < charset.length; k++) {	
					for (int l = 0; l < charset.length; l++) {
						for (int m = 0; m < charset.length; m++) {
							for (int n = 0; n < charset.length; n++) {
								String s = String.format("%s%s%s%s%s%s", charset[i], charset[j], charset[k], charset[l], charset[m], charset[n]);
								
								String intent = comprobar(alg, hash, salt, s);
								cont++;
				// System.out.println(cont);
								
								if (intent != null) {
									return intent;
								}	
							}	
						}
					}
				}
			}
		}

		
		return null;
	}

	public String comprobar(String alg, String hash, String salt, String s) throws Exception {
		String h;
		if (alg.equals("PBKDF2")) {
			h = getPBKDF2AmbSalt(s, salt);
		} else {
			h = getSHA512AmbSalt(s, salt);
		}
		
		if (h.equals(hash)) {
			return s;
		}
		return null;
	}
}
