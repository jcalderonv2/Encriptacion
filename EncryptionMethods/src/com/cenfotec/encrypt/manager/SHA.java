package com.cenfotec.encrypt.manager;

import java.security.MessageDigest;

public class SHA {
	
	public static String hashPassword(String password) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(password.getBytes());
        byte[] b = md.digest();
        StringBuffer sb = new StringBuffer();
        for(byte b1 : b){
            sb.append(Integer.toHexString(b1 & 0xff).toString());
        }
        return sb.toString();
    }

}
