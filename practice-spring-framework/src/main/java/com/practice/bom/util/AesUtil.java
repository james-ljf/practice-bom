package com.practice.bom.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @author ljf
 * @description 加密算法工具类
 * @date 2022/11/24 2:08 PM
 */
@Slf4j
public class AesUtil {

    private AesUtil() {
    }

    private static final String AES_CBC_NO_PADDING = "AES/CBC/PKCS5Padding";

    private static final String AES = "AES";

    private static final String RANDOM_ALGORITHM = "SHA1PRNG";

    /**
     * AES加密
     *
     * @param content 加密内容
     * @param key     加密的key
     * @return 加密串
     */
    public static String aesEncrypt(String content, String key) {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(key)) {
            return null;
        }
        Key secretKey = getKey(key);
        byte[] ivByte = key.getBytes();
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivByte);
        IvParameterSpec iv = new IvParameterSpec(ivByte);
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_NO_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] p = content.getBytes(StandardCharsets.UTF_8);
            byte[] result = cipher.doFinal(p);
            return DatatypeConverter.printBase64Binary(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES解密
     *
     * @param content 解密内容
     * @param key     解密key
     * @return 原内容
     */
    public static String aesDecrypt(String content, String key) {
        Key secretKey = getKey(key);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_NO_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] c = DatatypeConverter.parseBase64Binary(content);
            byte[] result = cipher.doFinal(c);
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取转换后的key
     *
     * @param key 加解密的key
     * @return Key.class
     */
    private static Key getKey(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new NullPointerException("key不能为null");
        }
        try {
            KeyGenerator generator = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance(RANDOM_ALGORITHM);
            secureRandom.setSeed(key.getBytes());
            generator.init(128, secureRandom);
            return generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
