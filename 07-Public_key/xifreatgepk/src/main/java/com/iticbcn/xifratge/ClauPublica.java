package com.iticbcn.xifratge;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

public class ClauPublica {
	public KeyPair generaParellClausRSA() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048, new SecureRandom());
		KeyPair	pair = generator.generateKeyPair();
		return pair;
	}

	public byte[] xifraRSA(String msg, PublicKey clauPublica) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, clauPublica);
		byte[] msgXifrat = cipher.doFinal(msg.getBytes("UTF-8"));

		return msgXifrat;
	}

	public String desxifraRSA(byte[] msgXifrat, PrivateKey ClauPrivada) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, ClauPrivada);
		byte[] msgDesxifrat = cipher.doFinal(msgXifrat);

		return new String(msgDesxifrat, "UTF-8");
	}
}
