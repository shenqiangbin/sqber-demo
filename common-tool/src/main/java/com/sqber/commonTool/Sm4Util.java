package com.sqber.commonTool;

import java.net.URLEncoder;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class Sm4Util {

    public static void main(String[] args) {
        try {
            String json = "{\"name\":\"Marydon\",\"website\":\"http://www.cnblogs.com/Marydon20170307\"}";
            // 自定义的32位16进制密钥
            String key = "86C63180C2806ED1F47B859DE501215B";
            //String cipher = Sm4Util.encryptEcb(key, json);

            key = "2gd#@sX4S@:ILlO{";
            json = "platform=NZKPT&product=CDFD&filename=2008167170.nh&tablename=CDFD9908&type=CDFD&dflag=cajdown&pages=&catalog=&language=zh&timestamp=1606790488000&preplatform=kns&danpian=1&paysource=test";
            String cipherBase64 = Sm4Util.encryptEcbToBase64(key,json);
            String result = URLEncoder.encode(cipherBase64, "UTF-8");
//            System.out.println(cipher);//05a087dc798bb0b3e80553e6a2e73c4ccc7651035ea056e43bea9d125806bf41c45b4263109c8770c48c5da3c6f32df444f88698c5c9fdb5b0055b8d042e3ac9d4e3f7cc67525139b64952a3508a7619
//            System.out.println(Sm4Util.verifyEcb(key, cipher, json));// true
//            json = Sm4Util.decryptEcb(key, cipher);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ENCODING = "UTF-8";
    public static final String ALGORITHM_NAME = "SM4";
    // 加密算法/分组加密模式/分组填充方式
    // PKCS5Padding-以8个字节为一组进行分组加密
    // 定义分组加密模式使用：PKCS5Padding
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";
    // 128-32位16进制；256-64位16进制
    //public static final int DEFAULT_KEY_SIZE = 128;
    public static final int DEFAULT_KEY_SIZE = 64;

    /**
     * 生成ECB暗号
     *
     * @param algorithmName 算法名称
     * @param mode          模式
     * @param key
     * @return
     * @throws Exception
     * @explain ECB模式（电子密码本模式：Electronic codebook）
     */
    private static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    /**
     * 自动生成密钥
     * @explain
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static byte[] generateKey() throws Exception {
        return generateKey(DEFAULT_KEY_SIZE);
    }

    /**
     * @explain
     * @param keySize
     * @return
     * @throws Exception
     */
    public static byte[] generateKey(int keySize) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(keySize, new SecureRandom());
        return kg.generateKey().getEncoded();
    }



    /**
     * sm4加密
     * @explain 加密模式：ECB
     *          密文长度不固定，会随着被加密字符串长度的变化而变化
     * @param hexKey
     *            16进制密钥（忽略大小写）
     * @param paramStr
     *            待加密字符串
     * @return 返回16进制的加密字符串
     * @throws Exception
     */
    public static String encryptEcb(String hexKey, String paramStr) throws Exception {
        String cipherText = "";
        // 16进制字符串-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // String-->byte[]
        byte[] srcData = paramStr.getBytes(ENCODING);
        // 加密后的数组
        byte[] cipherArray = encrypt_Ecb_Padding(keyData, srcData);
        // byte[]-->hexString
        cipherText = ByteUtils.toHexString(cipherArray);
        return cipherText;
    }

    /**
     * sm4加密
     * @explain 加密模式：ECB
     *          密文长度不固定，会随着被加密字符串长度的变化而变化
     * @param hexKey
     *            16进制密钥（忽略大小写）
     * @param paramStr
     *            待加密字符串
     * @return 返回16进制的加密字符串
     * @throws Exception
     */
    public static String encryptEcbToBase64(String hexKey, String paramStr) throws Exception {
        String cipherText = "";
        // 16进制字符串-->byte[]
        //byte[] keyData = ByteUtils.fromHexString(hexKey);
        byte[] keyData = hexKey.getBytes(ENCODING);
        // String-->byte[]
        byte[] srcData = paramStr.getBytes(ENCODING);
        // 加密后的数组
        byte[] cipherArray = encrypt_Ecb_Padding(keyData, srcData);
        // byte[]-->hexString
        return Base64.getEncoder().encodeToString(cipherArray);
    }

    /**
     * 加密模式之Ecb
     * @explain
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encrypt_Ecb_Padding(byte[] key, byte[] data) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * sm4解密
     * @explain 解密模式：采用ECB
     * @param hexKey
     *            16进制密钥
     * @param cipherText
     *            16进制的加密字符串（忽略大小写）
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decryptEcb(String hexKey, String cipherText) throws Exception {
        // 用于接收解密后的字符串
        String decryptStr = "";
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // hexString-->byte[]
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        // 解密
        byte[] srcData = decrypt_Ecb_Padding(keyData, cipherData);
        // byte[]-->String
        decryptStr = new String(srcData, ENCODING);
        return decryptStr;
    }


    /**
     * 解密
     * @explain
     * @param key
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static byte[] decrypt_Ecb_Padding(byte[] key, byte[] cipherText) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }




    /**
     * 校验加密前后的字符串是否为同一数据
     * @explain
     * @param hexKey
     *            16进制密钥（忽略大小写）
     * @param cipherText
     *            16进制加密后的字符串
     * @param paramStr
     *            加密前的字符串
     * @return 是否为同一数据
     * @throws Exception
     */
    public static boolean verifyEcb(String hexKey, String cipherText, String paramStr) throws Exception {
        // 用于接收校验结果
        boolean flag = false;
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // 将16进制字符串转换成数组
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        // 解密
        byte[] decryptData = decrypt_Ecb_Padding(keyData, cipherData);
        // 将原字符串转换成byte[]
        byte[] srcData = paramStr.getBytes(ENCODING);
        // 判断2个数组是否一致
        flag = Arrays.equals(decryptData, srcData);
        return flag;
    }


}
