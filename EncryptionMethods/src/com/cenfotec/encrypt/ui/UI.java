package com.cenfotec.encrypt.ui;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import com.cenfotec.factory.EncryptFactory;
import com.cenfotec.functions.EncryptFunctions;


public class UI {
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PrintStream out = System.out;
	private static EncryptFunctions encryptFunctions;
	
	
	public static void main(String[] args) throws Exception {
		
		menuInicial();

	}
	
	public static void menuInicial() throws Exception {
		
		
		int type = 0;

		do {

			out.println("<---- select the type of encryption ---->");
			out.println();
			out.println("1. Asymmetric encryption.");
			out.println("2. Symmetric encryption.");
			out.println("3. Exit.");
			out.println();
			out.println("Select option: ");
			out.println();
			type = Integer.parseInt(br.readLine());
			
			encryptFunctions = EncryptFactory.type(type);
			menuEncrypt();
			
		} while (type != 3);
		
		
	}
	
	

	private static void menuEncrypt() throws Exception {
		
		int option = 0;

		do {
			System.out.println();
			System.out.println("1. Create key.");
			System.out.println("2. Encript Message.");
			System.out.println("3. Decrypt Message.");
			System.out.println("4. finish the encryption.");
			option = Integer.parseInt(br.readLine());
			System.out.println();
			executeAction(option);
			
		} while (option != 4);
	}

	private static void executeAction(int option) throws Exception {

		if (option == 1) {
			out.println("Key name: ");
			String name = br.readLine();
			encryptFunctions.createKey(name);
		}
		if (option == 2) {
			out.println("Key name: ");
			String keyName = br.readLine();
			out.println("Message name: ");
			String messageName = br.readLine();
			out.println("Message: ");
			String message = br.readLine();

			encryptFunctions.encryptMessage(messageName, message, keyName);
		}
		if (option == 3) {
			out.println("Key name: ");
			String keyName = br.readLine();
			out.println("Message name: ");
			String messageName = br.readLine();

			encryptFunctions.decryptMessage(messageName, keyName);
		}
	}
}
