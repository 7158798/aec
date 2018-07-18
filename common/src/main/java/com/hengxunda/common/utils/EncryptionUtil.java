package com.hengxunda.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
	public static String MD5(String sourceStr){
		final char[] HEX_DIGEST = "0123456789abcdef".toCharArray();
        byte[] bytes = null;
        try {
            MessageDigest md5Digest = MessageDigest.getInstance("md5");
            bytes = md5Digest.digest(sourceStr.getBytes());
        }catch (NoSuchAlgorithmException e1){
            e1.printStackTrace();
        }
        StringBuffer result = new StringBuffer(bytes.length*2);
        for (int index = 0; index<bytes.length; index++){
            result.append(HEX_DIGEST[(bytes[index] >> 4) & 0x0f]);
            result.append(HEX_DIGEST[(bytes[index]) & 0x0f]);
        }
        return result.toString();

    }
}
