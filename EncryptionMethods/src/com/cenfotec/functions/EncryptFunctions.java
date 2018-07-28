package com.cenfotec.functions;

public interface EncryptFunctions {
	
	public void createKey(String name) throws Exception;
	public void encryptMessage(String messageName, String message, String keyName) throws Exception;
	public String decryptMessage(String messageName, String keyName) throws Exception;

}
