package com.cfm.SmartOTP.SmartOTPService.component;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSACryptosystem {

    public RSAKEY KeyPairGenerate() {
        try {
            RSAKEY rsakey = new RSAKEY();
            SecureRandom sr = new SecureRandom();
            //Thuật toán phát sinh khóa - Rivest Shamir Adleman (RSA)
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048, sr);
            //Phát sinh cặp khóa
            KeyPair kp = kpg.genKeyPair();
            //PublicKey
            PublicKey pubKey = kp.getPublic();
            //PrivateKey
            PrivateKey priKey = kp.getPrivate();
            //Lưu Public Key
            rsakey.PublicKey = pubKey.getEncoded();
            //Lưu Private Key
            rsakey.PrivateKey = priKey.getEncoded();

            FileOutputStream fos = new FileOutputStream("D:/pubKey.bin");
            fos.write(pubKey.getEncoded());
            fos.close();

            fos = new FileOutputStream("D:/priKey.bin");
            fos.write(priKey.getEncoded());
            fos.close();

            return rsakey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String Encrpytion(byte[] publickey, String secretkey) {
        try {
            // Tạo public key
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publickey);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(spec);
            // Mã hoá dữ liệu
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pubKey);
            byte encryptOut[] = c.doFinal(secretkey.getBytes());
            // String strEncrypt = Base64.encode(encryptOut);
            String strEncrypt = Base64.getEncoder().encodeToString(encryptOut);
            return strEncrypt;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String Decryption(byte[] publickey, String encrpytion) {
        try {
            // Tạo private key
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(publickey);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = factory.generatePrivate(spec);
            // Giải mã dữ liệu
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, priKey);
            // byte decryptOut[] = c.doFinal(Base64.decode(encrpytion));
            byte decryptOut[] = c.doFinal(Base64.getDecoder().decode(encrpytion));

            return new String(decryptOut);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public class RSAKEY {
        public byte[] PublicKey;
        public byte[] PrivateKey;
    }
}
