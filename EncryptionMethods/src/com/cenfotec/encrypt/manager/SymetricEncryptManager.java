package com.cenfotec.encrypt.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;

import com.cenfotec.communicatioManager.EncryptManagerCode;
import com.cenfotec.functions.EncryptFunctions;;

public class SymetricEncryptManager implements EncryptFunctions{

	private final static String KEY_EXTENSION = ".key";
	private final static String PATH = "C:/encrypt/symetric/";

	public void createKey(String name) throws Exception {

		byte[] key = EncryptManagerCode.generatedSequenceOfBytes();
		EncryptManagerCode.writeBytesFile(PATH, name, key, KEY_EXTENSION);

	}

	public void encryptMessage(String messageName, String message, String keyName) throws Exception {

		byte[] key = readKeyFile(keyName);
		Cipher cipher = EncryptManagerCode.encryptModeSymetric(key);
		EncryptManagerCode.cipher(PATH, messageName, cipher, message);

	}

	public String decryptMessage(String messageName, String keyName) throws Exception {

		byte[] key = readKeyFile(keyName);
		byte[] encryptedMessage = EncryptManagerCode.readMessageFile(PATH, messageName);

		Cipher cipher = EncryptManagerCode.decryptModeSymetric(key);
		byte[] DecryptedData = cipher.doFinal(encryptedMessage);

		return EncryptManagerCode.Message(DecryptedData);

	}

	public static byte[] readKeyFile(String keyName) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(PATH + keyName + KEY_EXTENSION));
		String everything = "";

		try {

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {

				sb.append(line);
				line = br.readLine();

			}

			everything = sb.toString();

		} finally {

			br.close();

		}

		return everything.getBytes(StandardCharsets.UTF_8);

	}
}
