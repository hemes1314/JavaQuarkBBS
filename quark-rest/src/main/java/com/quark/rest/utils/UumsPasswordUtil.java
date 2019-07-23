package com.quark.rest.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class UumsPasswordUtil {

    private String uumsPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJnBfLtWamZ3hKPcmsoDTGBmOc6H/fsfZStPTOj7R6x5/XP628vN6LHRACk99hg3yYbah+pHfzzDe4U1EWSsC418cRCBoxZZp6SHBbIkpwgbdXNmA6R3iJd6FlEuwwSFcNwPYQ0xv7U6UUTrYmWCCoUa+KFY9cbYkSsa06+ykemxAgMBAAECgYAXbPxVCpCBdho2YQkQWDpNwaVzCxMuLJVcaOOd55L++0MbZZARWBjo5p/wqKkS/YTtz+O/VQ9Usa/jFrfEr9W+htMYHIWbJTXWsvE18vs022ciybkP4Fx00Ljs7qll+dkIZtYql3cqo1ndXQQ8qpruzqaKMVoXv9riE57+P2gUQQJBAOE2+mUIAizvaXPHuq6FF7a3DT5hgmo+8Sba9Dtzvz54nxkb8J1TlJjsA1YNZDhvl/AWtI5E22EPZR2ctDUh5XkCQQCuxeuPzn4Q9B/g3WOtLKJpTr0ZM+XeGroP6XUCh8l9Cnx/WB/YZWhT9FoLo11BuAP4hQp1TsXu90a09rxbnK/5AkApTmAWb6WWgEKjDZrbr2VuCZzQOConOmwYaEgrL0uANbdYb5tt/4pdkcv62HHtN+pyCngLL+3cm2o8SCV1KUZhAkApEuabc2H5RgY/6IfGaRj6OsECLUo2en2Dw8/1+keGFXLQ0rsZNivgnyqSVaBTE5YLT+j3TL4DvSVm3h3CQf6xAkEAvdgMSzfESqPZHQZ/u03JyzaMb4RF3ma86jHaf6Uxs2QvsplgjKACpUvYWSgrcgfnrpAgcSVLH6veLaJo6uF6RA==";
    private String uumsPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZwXy7Vmpmd4Sj3JrKA0xgZjnOh/37H2UrT0zo+0esef1z+tvLzeix0QApPfYYN8mG2ofqR388w3uFNRFkrAuNfHEQgaMWWaekhwWyJKcIG3VzZgOkd4iXehZRLsMEhXDcD2ENMb+1OlFE62JlggqFGvihWPXG2JErGtOvspHpsQIDAQAB";
    private static final String KEY_ALGORITHM = "RSA";

    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJnBfLtWamZ3hKPcmsoDTGBmOc6H/fsfZStPTOj7R6x5/XP628vN6LHRACk99hg3yYbah+pHfzzDe4U1EWSsC418cRCBoxZZp6SHBbIkpwgbdXNmA6R3iJd6FlEuwwSFcNwPYQ0xv7U6UUTrYmWCCoUa+KFY9cbYkSsa06+ykemxAgMBAAECgYAXbPxVCpCBdho2YQkQWDpNwaVzCxMuLJVcaOOd55L++0MbZZARWBjo5p/wqKkS/YTtz+O/VQ9Usa/jFrfEr9W+htMYHIWbJTXWsvE18vs022ciybkP4Fx00Ljs7qll+dkIZtYql3cqo1ndXQQ8qpruzqaKMVoXv9riE57+P2gUQQJBAOE2+mUIAizvaXPHuq6FF7a3DT5hgmo+8Sba9Dtzvz54nxkb8J1TlJjsA1YNZDhvl/AWtI5E22EPZR2ctDUh5XkCQQCuxeuPzn4Q9B/g3WOtLKJpTr0ZM+XeGroP6XUCh8l9Cnx/WB/YZWhT9FoLo11BuAP4hQp1TsXu90a09rxbnK/5AkApTmAWb6WWgEKjDZrbr2VuCZzQOConOmwYaEgrL0uANbdYb5tt/4pdkcv62HHtN+pyCngLL+3cm2o8SCV1KUZhAkApEuabc2H5RgY/6IfGaRj6OsECLUo2en2Dw8/1+keGFXLQ0rsZNivgnyqSVaBTE5YLT+j3TL4DvSVm3h3CQf6xAkEAvdgMSzfESqPZHQZ/u03JyzaMb4RF3ma86jHaf6Uxs2QvsplgjKACpUvYWSgrcgfnrpAgcSVLH6veLaJo6uF6RA==";
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZwXy7Vmpmd4Sj3JrKA0xgZjnOh/37H2UrT0zo+0esef1z+tvLzeix0QApPfYYN8mG2ofqR388w3uFNRFkrAuNfHEQgaMWWaekhwWyJKcIG3VzZgOkd4iXehZRLsMEhXDcD2ENMb+1OlFE62JlggqFGvihWPXG2JErGtOvspHpsQIDAQAB";
    private static Logger logger= LoggerFactory.getLogger("UumsPasswordUtil");

    @PostConstruct
    private void keyInit(){
        logger.info("=============> UumsPasswordUtil keyInit...");
        this.privateKey = uumsPrivateKey;
        this.publicKey = uumsPublicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
    
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    
    public String getPrivateKey() {
        return privateKey;
    }
    
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    
    private UumsPasswordUtil(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
    
    private static UumsPasswordUtil instance;
    
    private static UumsPasswordUtil getInstance() {
        if(null == instance){
            logger.info("=============> UumsPasswordUtil init...");
            instance = new UumsPasswordUtil(publicKey, privateKey);
        }
        return instance;
    }

    public UumsPasswordUtil() {
    }
    /*    public static void initKeys(String publicKey, String privateKey) {
        instance = new UumsPasswordUtil(publicKey, privateKey);
    }*/
    
    /**
     * 加密
     * 
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        try {
            byte[] encodedData = getInstance().encryptByPublicKey(password, getInstance().getPublicKey());
            return encryptBASE64(encodedData);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 解密
     * 
     * @param password
     * @return
     */
    public static String decrypt(String password) {
        try {
            return new String(getInstance().decryptByPrivateKey(password, getInstance().getPrivateKey()));
        } catch (Exception e) {
            return null;
        }
    }
    
    private static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }
    
    private static String encryptBASE64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }
    
    private static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
    
    private byte[] decryptByPrivateKey(String data, String key)
            throws Exception {
        return decryptByPrivateKey(decryptBASE64(data), key);
    }
    
    //private static byte[] encryptByPublicKey(String data, String key)
    private byte[] encryptByPublicKey(String data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }
    
/*    public static void main(String[] args) throws Exception{
        UumsPasswordUtil uumsPasswordUtil =  new UumsPasswordUtil();
        uumsPasswordUtil.setPublicKey(
"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZwXy7Vmpmd4Sj3JrKA0xgZjnOh/37H2UrT0zo+0esef1z+tvLzeix0QApPfYYN8mG2ofqR388w3uFNRFkrAuNfHEQgaMWWaekhwWyJKcIG3VzZgOkd4iXehZRLsMEhXDcD2ENMb+1OlFE62JlggqFGvihWPXG2JErGtOvspHpsQIDAQAB");
        System.out.println(UumsPasswordUtil.encrypt("lwc2018LWC"));
    }*/
    
}
