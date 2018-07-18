package com.hengxunda.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA工具
 */
public class RSAUtils {

    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";

    //私钥
    public static final String PRIVATE_KEY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJOhOeap9ncw9ruMp6q8aaaSfjq4bLuY9yX97MPdz97PQFK1IOpYOHNjgX5_vzMauk6fdT6PNLPLUeE55qv-6f0M_FnbMYMkKwK6xpqNDo8hIOZOS2BTTO7pyqZXk7zbrYPIWB6T2A2khuu6B-iZ0cOyJj6xu3QL_M4rr1n8lgX1AgMBAAECgYBGSmmrLhmvvpx7yAi8_mC_ctivVMhQuhBTAogM6jvjNoLF3oABlGesaSBq_qAm0P8MGFhWpRDO2mvt9QejtQfrP3NyAw3uSghyMbcmN4ON97F8v1xKfbOCpZKVfyJlPuOf4xTXZVPyWDLakVsH4ysqfEmWCR9KtX9rn1vmtpuMsQJBAMTqaARH002DLVglVzawZfhJ632Q8NlLC2w8FFwd4nvO1K2lPhgchHFCw06DBppsW0-rB01rQar6760utyD-D_MCQQC_7QwScSyjovmQXWZDz-PTKS5Ueip6-bbqwm1599XV1lvTvnDTD-jK_776v8cix6VtcP3PUPxbWG3pUs6-0_R3AkEAnKpWOVmfvqKn9-I1ghhT-HdvOUaQqICxRNqD5EoOgCwtPz4hqfM-WBIi6xsJrOCawUa0F59G7q6Y87MgFcqsiwJAC8FLEf4Yi9U5j9wInXKoM5C8I7Rv0aRlza8m4WeKk1RTv35UjrjUu0o6ukTwv3KW8UMQaJg_PKEH6liGDS4SNQJBAKOuCbtFJ0IuPRe7sll_qTQNAa5l0hWiUnKii7bpVJ2ARc4g6DVItpZpRLU6VXlwsK3rcsR1nt6T6JndEKwT8ls";

    //公钥
    public static final String PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCToTnmqfZ3MPa7jKeqvGmmkn46uGy7mPcl_ezD3c_ez0BStSDqWDhzY4F-f78zGrpOn3U-jzSzy1HhOear_un9DPxZ2zGDJCsCusaajQ6PISDmTktgU0zu6cqmV5O8262DyFgek9gNpIbrugfomdHDsiY-sbt0C_zOK69Z_JYF9QIDAQAB";

    public static Map<String, String> createKeys(int keySize){
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try{
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        }catch(NoSuchAlgorithmException e){
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 得到公钥
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 私钥加密
     * @param data
     * @return
     */

    public static String privateEncrypt(String data){
        try{
            RSAPrivateKey privateKey =  RSAUtils.getPrivateKey(PRIVATE_KEY);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     * @param data
     * @return
     */

    public static String publicDecrypt(String data){
        try{
            RSAPublicKey publicKey =  RSAUtils.getPublicKey(PUBLIC_KEY);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE){
            maxBlock = keySize / 8;
        }else{
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try{
            while(datas.length > offSet){
                if(datas.length-offSet > maxBlock){
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                }else{
                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        }catch(Exception e){
            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }


    public static void main(String[] args) throws Exception{
//        Map<String, String> keyMap = sdfdsf.createKeys(1024);
//        String  publicKey = keyMap.get("publicKey");
//        String  privateKey = keyMap.get("privateKey");
//        System.out.println("公钥: \n\r" + publicKey);
//        System.out.println("私钥： \n\r" + privateKey);

        String str="LTAIg8IlrH22IFBS";
        String str1="WwGh5qlsTuErC0MLjeeXFnuCQT4cVa";
        String str2="blockbank-test";
        String str3="oss-cn-beijing.aliyuncs.com";

        String encodedData = null;

        String privateKey1="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJOhOeap9ncw9ruMp6q8aaaSfjq4bLuY9yX97MPdz97PQFK1IOpYOHNjgX5_vzMauk6fdT6PNLPLUeE55qv-6f0M_FnbMYMkKwK6xpqNDo8hIOZOS2BTTO7pyqZXk7zbrYPIWB6T2A2khuu6B-iZ0cOyJj6xu3QL_M4rr1n8lgX1AgMBAAECgYBGSmmrLhmvvpx7yAi8_mC_ctivVMhQuhBTAogM6jvjNoLF3oABlGesaSBq_qAm0P8MGFhWpRDO2mvt9QejtQfrP3NyAw3uSghyMbcmN4ON97F8v1xKfbOCpZKVfyJlPuOf4xTXZVPyWDLakVsH4ysqfEmWCR9KtX9rn1vmtpuMsQJBAMTqaARH002DLVglVzawZfhJ632Q8NlLC2w8FFwd4nvO1K2lPhgchHFCw06DBppsW0-rB01rQar6760utyD-D_MCQQC_7QwScSyjovmQXWZDz-PTKS5Ueip6-bbqwm1599XV1lvTvnDTD-jK_776v8cix6VtcP3PUPxbWG3pUs6-0_R3AkEAnKpWOVmfvqKn9-I1ghhT-HdvOUaQqICxRNqD5EoOgCwtPz4hqfM-WBIi6xsJrOCawUa0F59G7q6Y87MgFcqsiwJAC8FLEf4Yi9U5j9wInXKoM5C8I7Rv0aRlza8m4WeKk1RTv35UjrjUu0o6ukTwv3KW8UMQaJg_PKEH6liGDS4SNQJBAKOuCbtFJ0IuPRe7sll_qTQNAa5l0hWiUnKii7bpVJ2ARc4g6DVItpZpRLU6VXlwsK3rcsR1nt6T6JndEKwT8ls";
        String publicKey1="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCToTnmqfZ3MPa7jKeqvGmmkn46uGy7mPcl_ezD3c_ez0BStSDqWDhzY4F-f78zGrpOn3U-jzSzy1HhOear_un9DPxZ2zGDJCsCusaajQ6PISDmTktgU0zu6cqmV5O8262DyFgek9gNpIbrugfomdHDsiY-sbt0C_zOK69Z_JYF9QIDAQAB";

       String str_1="GFP7r2y0qvz817Cj7nLl7L-GinUrYTrjWZP-FA6S7KYBAZXSXR5XpHA1ynjLJ6g-1_cbBByUIMxNc4L8tM9qEqGPAyGvXxr3-G6iIA9Qml2dXJcwNxmXsTYlqkQH2yl9M3ZAcPe_KRzkd3iN5flgmLUranR0aAk6NNJZBmj4F4I";
       String str_2="cppkeEhqSm60yOAMB2FAjy3aeVbrV5q76Fds0KjOSF6ul8N-N1lsXIdxfpXgUATbdq6xZjtEp74BRncslNY8u54W_-BGIV2IHlwD3kL6yNRBcKoUWRG2_YpTc_kEf0jPKux79s3riwCVUcRCo2g1pyq-LDzn9Harl-Qpvn6RoDs";
       String str_3="S2hfOCOlBiv1wpHa8ZK20-RGFiJGtplLsztPWQx3KT133RyZ5uoE6d1tOMTND8lqO2pd-OZqyN-49jpDJK6-BIyUBQ21nRHoIgdFe1uyYwfjhMMw1ILFhGyTdO5OdBDlMARNy8ouuzJlnNWS1WLijkFoAtgZjeG3p3eBGDZ0hVs";
       String str_4="WFyQeGox-D-mJ9zBQyPvGCMF7ZzCXoRBRgmXN-NoijobVY5Gn86nRvn33YTTXUZYg8DQv2aXok-L52QA4aE8Fj2iV_T_TjKY_MXnN442SKzKJU1QWhcMwLJiKRBh_2CNZ8fkPf8SvVlI-ARfzI6yqa1Jw5bnupZway1Z8AwArJg";
        encodedData = RSAUtils.privateEncrypt(str1);
        System.out.println("密文：\r\n" + encodedData);
        String decodedData = RSAUtils.publicDecrypt(encodedData);
        System.out.println("解密后文字: \r\n" + decodedData);




    }
}
