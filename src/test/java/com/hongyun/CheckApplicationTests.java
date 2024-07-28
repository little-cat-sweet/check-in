package com.hongyun;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.hongyun.util.PasswordUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootTest

class CheckApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("hello world");
    }

    @Test
    void test2(){
        String key = "1234567890123456"; // 16 字节长度的密钥
        String password = "hellloword";

        try {
            // 加密
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            String encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("Encrypted Password: " + encryptedPassword);

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            String decryptedPassword = new String(decryptedBytes);
            System.out.println("Decrypted Password: " + decryptedPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
