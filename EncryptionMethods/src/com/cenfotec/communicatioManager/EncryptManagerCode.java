package com.cenfotec.communicatioManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptManagerCode {

	private final static int KEYSIZE = 8;
	private final static String MESSAGE_ENCRYPT_EXTENSION = ".encript";
	private final static String KEY_EXTENSION = ".key";
	private final static String PUBLIC = "public";
	private final static String PATH = "C:/encrypt/asymetric/";

	public static void writeBytesFile(String path, String name, byte[] content, String type) throws Exception {

		FileOutputStream fos = new FileOutputStream(path + name + type);
		fos.write(content);
		fos.close();

	}
	

	public static void cipher(String path, String name, Cipher cipher, String message) throws Exception {

		byte[] encryptedData = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
		Encoder oneEncoder = Base64.getEncoder();
		encryptedData = oneEncoder.encode(encryptedData);

		writeBytesFile(path, name, encryptedData, MESSAGE_ENCRYPT_EXTENSION);
	}

	public static String Message(byte[] decryptedData) {

		String message = new String(decryptedData, StandardCharsets.UTF_8);

		System.out.println("El mensaje era: ");
		System.out.println(message);

		return message;

	}

	public static Cipher encryptMode(String keyName) throws Exception {

		PublicKey pubKey = (PublicKey) readKeyFromFile(keyName, PUBLIC);

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		return cipher;
	}

	public static byte[] decryptMode(String messageName, PrivateKey privKey) throws Exception {

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privKey);

		byte[] encryptedMessage = readMessageFile(PATH, messageName);
		byte[] decryptedData = cipher.doFinal(encryptedMessage);

		return decryptedData;
	}

	public static Key readKeyFromFile(String keyFileName, String type) throws IOException {

		InputStream in = new FileInputStream(PATH + keyFileName + type + KEY_EXTENSION);
		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));

		try {

			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();

			if (type.equals("public")) {

				RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
				KeyFactory fact = KeyFactory.getInstance("RSA");
				PublicKey pubKey = fact.generatePublic(keySpec);

				return pubKey;

			} else {

				RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
				KeyFactory fact = KeyFactory.getInstance("RSA");
				PrivateKey pubKey = fact.generatePrivate(keySpec);

				return pubKey;
			}
			
		} catch (Exception e) {
			
			throw new RuntimeException("Spurious serialisation error", e);
			
		} finally {
			
			oin.close();
			
		}
		
	}
	


	public static byte[] readMessageFile(String path, String messageName) throws Exception {
		
		File file = new File(path + messageName + MESSAGE_ENCRYPT_EXTENSION);
		
		int length = (int) file.length();
		
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[length];
		
		reader.read(bytes, 0, length);
		reader.close();
		
		Decoder oneDecoder = Base64.getDecoder();
		return oneDecoder.decode(bytes);
		

	}
	
	public static Cipher encryptModeSymetric(byte[] key) throws Exception {
		
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec k = new SecretKeySpec(key, "AES");

		cipher.init(Cipher.ENCRYPT_MODE, k);
		
		return cipher;
		
	}
	
	public static Cipher decryptModeSymetric(byte[] key) throws Exception {
		
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec k = new SecretKeySpec(key, "AES");
		
		cipher.init(Cipher.DECRYPT_MODE, k);
		
		return cipher;
		
	}
	
	
	public static byte[] generatedSequenceOfBytes() throws Exception {
		
		StringBuilder randomkey = new StringBuilder();
		
		for (int i = 0; i < KEYSIZE; i++) {
			
			randomkey.append(Integer.parseInt(Double.toString((Math.random() + 0.1) * 1000).substring(0, 2)));
		}
		
		return randomkey.toString().getBytes("UTF-8");
		
	}

}
