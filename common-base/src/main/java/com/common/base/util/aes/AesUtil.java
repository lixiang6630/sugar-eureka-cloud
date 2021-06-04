package com.common.base.util.aes;

import com.common.base.util.StrKit;
import lombok.Getter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * AES 秘钥工具
 *
 * @author: LX
 */
public class AesUtil {

    @Getter
    public enum KeyLength {
        ShortLength(128),
        MiddleLength(192),
        LongLength(256),;

        private int code;

        KeyLength(int code) {
            this.code = code;
        }
    }
    
    /**
     * 自动生成AES128位密钥
     */
    public static String genAESKey(KeyLength keyLength) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(keyLength.getCode());
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            return byteToHexString(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 二进制byte[]转十六进制string
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex = Integer.toHexString(bytes[i]);
            if (strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if (strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 十六进制string转二进制byte[]
     */
    public static byte[] hexStringToByte(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baKeyword;
    }

    /**
     * 使用对称密钥进行加密
     */
    public static String encryption(String word, String keys) {
        try {
            byte[] keyb = hexStringToByte(keys);
            SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
            byte[] bjiamihou = cipher.doFinal(word.getBytes());
            return byteToHexString(bjiamihou);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 使用对称密钥进行解密
     */
    public static String decryption(String word, String keys) {
        try {
            if (StrKit.isBlank(word)) {
                return "";
            }
            byte[] keyb = hexStringToByte(keys);
            byte[] miwen = hexStringToByte(word);
            SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
            byte[] bjiemihou = cipher.doFinal(miwen);
            return new String(bjiemihou);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
