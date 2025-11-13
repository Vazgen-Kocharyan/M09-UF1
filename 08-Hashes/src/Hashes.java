import java.security.MessageDigest;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
	public int npass = 0;

	public String getSHA512AmbSalt(String pw, String salt) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-512");

		md.update(salt.getBytes("UTF-8"));
		byte[] hashBytes = md.digest(pw.getBytes("UTF-8"));

		HexFormat hex = HexFormat.of();

		return hex.formatHex(hashBytes);
	}

	public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {

		int it = 65536;
		int keyLength = 128;

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

		char[] CHARSET = "abcdefABCDEF1234567890!".toCharArray();

		npass = 0;
		char[] pwd = new char[6];
		String s;

		for (int i0 = 0; i0 < CHARSET.length; i0++) {
            pwd[0] = CHARSET[i0];
            
			s = comprobar(alg, hash, salt, new String(pwd, 0, 1));
			npass++;

			if (s != null) {
				return s;
			}

            for (int i1 = 0; i1 < CHARSET.length; i1++) {
                pwd[1] = CHARSET[i1];
           
				s = comprobar(alg, hash, salt, new String(pwd, 0, 2));
				npass++;


				if (s != null) {
					return s;
				}

                for (int i2 = 0; i2 < CHARSET.length; i2++) {
                    pwd[2] = CHARSET[i2];
           
					s = comprobar(alg, hash, salt, new String(pwd, 0, 3));
					npass++;
					
					if (s != null) {
						return s;
					}


                    for (int i3 = 0; i3 < CHARSET.length; i3++) {
                        pwd[3] = CHARSET[i3];

						s = comprobar(alg, hash, salt, new String(pwd, 0, 4));
						npass++;

						if (s != null) {
							return s;
						}

                        for (int i4 = 0; i4 < CHARSET.length; i4++) {
                            pwd[4] = CHARSET[i4];

							s = comprobar(alg, hash, salt, new String(pwd, 0, 5));
							npass++;

							if (s != null) {
								return s;
							}

                            for (int i5 = 0; i5 < CHARSET.length; i5++) {
                                pwd[5] = CHARSET[i5];

								s = comprobar(alg, hash, salt, new String(pwd, 0, 6));
								npass++;
								
								if (s != null) {
									return s;
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

	public String getInterval(long t1, long t2) {
		long millis = t2 - t1;
		
		long dias = millis / 86_400_000L;
		millis %= 86_400_000L;

		long horas = millis / 3_600_000L;
		millis %= 3_600_000L;

		long minutos = millis / 60_000L;
		millis %= 60_000L;

		long segundos = millis / 1000L;

		long remMillis = millis % 1000L;

		return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis", dias, horas, minutos, segundos, remMillis);
	}
}
