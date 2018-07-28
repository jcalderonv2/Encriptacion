package com.cenfotec.factory;

import com.cenfotec.encrypt.manager.AsymetricEncryptManager;
import com.cenfotec.encrypt.manager.SymetricEncryptManager;
import com.cenfotec.functions.EncryptFunctions;

public class EncryptFactory {
	
public static EncryptFunctions type(int type) {
		
		switch (type) {
		
		case 1:
			
			return new AsymetricEncryptManager();
			
		case 2:
			
			return new SymetricEncryptManager();
			
		default:
			
			return null;

		}
		
	}

}
