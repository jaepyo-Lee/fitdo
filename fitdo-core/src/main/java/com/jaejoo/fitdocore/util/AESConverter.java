package com.jaejoo.fitdocore.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class AESConverter {
    private final static String alg = "AES/CBC/PKCS5Padding";

    @Value("${deeplink.key}")
    private String key;

    public String serialize(String id) throws Exception {
        String iv = key.substring(0, 16); //초기화 벡터 16byte로 자르기
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
        byte[] encrypted = cipher.doFinal(id.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String deserialize(String cyper) throws Exception {
        String iv = key.substring(0, 16);
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
        byte[] decodeBytes = Base64.getDecoder().decode(cyper);
        byte[] devrypted = cipher.doFinal(decodeBytes);
        return new String(devrypted, "UTF-8");
    }
}
