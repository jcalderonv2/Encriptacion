package com.cenfotec.encrypt.manager;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;

import com.cenfotec.communicatioManager.EncryptManagerCode;
import com.cenfotec.functions.EncryptFunctions;

public class AsymetricEncryptManager implements EncryptFunctions{

	private final String PRIVATE = "private";
	private final String PATH = "C:/encrypt/asymetric/";
	
	public void createKey(String name) throws Exception {
		
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		KeyFactory fact = KeyFactory.getInstance("RSA");
		
		kpg.initialize(2048);
		KeyPair kp = kpg.genKeyPair();
		
		RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
		RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);

		saveToFile(PATH + name+"public.key", pub.getModulus(), pub.getPublicExponent());
		saveToFile(PATH + name+"private.key", priv.getModulus(), priv.getPrivateExponent());
		
	}
	
	public void saveToFile(String fileName,BigInteger mod, BigInteger exp) throws IOException {
		
		ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		
		try {
			
			oout.writeObject(mod);
			oout.writeObject(exp);
			
		} catch (Exception e) {
			
			throw new IOException("Unexpected error", e);
			
		} finally {
			
		    oout.close();
		    
		}
		
	}

	public void encryptMessage(String messageName, String message, String keyName) throws Exception {
		
		Cipher cipher = EncryptManagerCode.encryptMode(keyName);
	    
	    EncryptManagerCode.cipher(PATH, messageName, cipher, message);
	}

	
	public String decryptMessage(String messageName, String keyName) throws Exception {
		
		PrivateKey privKey = (PrivateKey)EncryptManagerCode.readKeyFromFile(keyName, PRIVATE);
		
		byte[] decryptedData = EncryptManagerCode.decryptMode(messageName, privKey);
	    
	    String message = EncryptManagerCode.Message(decryptedData);
		return message;
	    

	}

}
