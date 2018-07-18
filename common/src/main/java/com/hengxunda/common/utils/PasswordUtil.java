package com.hengxunda.common.utils;

import org.apache.commons.codec.digest.Sha2Crypt;

/**
 * 密码加密工具类
 * Created by jerry on 2017/12/29.
 */
public class PasswordUtil {

//    public static String[] getEncryptPassword(String password) {
//        String salt = B64Copy.getRandomSaltForSha256();
//        return new String[]{Sha2Crypt.sha256Crypt(password.getBytes(), salt), salt};
//    }
//
    public static boolean check(String input, String password, String salt) {
        return password.equals(Sha2Crypt.sha256Crypt(input.getBytes(), salt));
    }

    public static String getSalt(){
        String salt = B64Copy.getRandomSaltForSha256();
        return salt;
    }

    public static String encrypt(String password,String salt){
        return Sha2Crypt.sha256Crypt(password.getBytes(), salt);
    }

    public static void main(String[] args){

//        $5$b7EXu8iQb1RYqKVo
//        $5$b7EXu8iQb1RYqKVo$J.RPhvYbQqNHjOSog5B5YpukhlNDpxBVFfkKo8cl.P6
//        String salt = getSalt();
//        System.out.println(salt);
        System.out.println(encrypt("123456","$5$b7EXu8iQb1RYqKVo").equals("$5$b7EXu8iQb1RYqKVo$J.RPhvYbQqNHjOSog5B5YpukhlNDpxBVFfkKo8cl.P6"));
    }


}
