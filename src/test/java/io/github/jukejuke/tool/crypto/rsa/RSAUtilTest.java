/**
 * RSAUtil测试类
 */
package io.github.jukejuke.tool.crypto.rsa;

import io.github.jukejuke.tool.log.LogUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAUtilTest {

    private static final String TEST_DATA = "test_rsa_util_data_123456";

    @Test
    void generateKeyPair_DefaultSize_Success() throws Exception {
        KeyPair keyPair = RSAUtil.generateKeyPair();
        assertNotNull(keyPair, "密钥对生成失败");
        assertNotNull(keyPair.getPublic(), "公钥为空");
        assertNotNull(keyPair.getPrivate(), "私钥为空");
        assertEquals(2048, keyPair.getPublic().getEncoded().length * 8, "默认密钥长度应为2048位");
    }

    @Test
    void generateKeyPair_CustomSize_Success() throws Exception {
        KeyPair keyPair = RSAUtil.generateKeyPair(4092);
        assertTrue(keyPair.getPublic().getEncoded().length * 8 >= 4096, "自定义密钥长度应不小于4096位");
    }

    @Test
    void encryptByPublicKey_DecryptByPrivateKey_Success() throws Exception {
        KeyPair keyPair = RSAUtil.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String encryptedData = RSAUtil.encryptByPublicKey(TEST_DATA, publicKey);
        LogUtil.info("Encrypted Data: " + encryptedData);
        assertNotNull(encryptedData, "加密结果为空");
        assertNotEquals(TEST_DATA, encryptedData, "加密后数据不应与原数据相同");

        String decryptedData = RSAUtil.decryptByPrivateKey(encryptedData, privateKey);
        LogUtil.info("Decrypted Data: " + decryptedData);
        assertEquals(TEST_DATA, decryptedData, "解密后数据与原数据不一致");
    }

    @Test
    void sign_Verify_Success() throws Exception {
        KeyPair keyPair = RSAUtil.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        String sign = RSAUtil.sign(TEST_DATA, privateKey);
        LogUtil.info("Signature: " + sign);
        assertNotNull(sign, "签名结果为空");

        boolean verifyResult = RSAUtil.verify(TEST_DATA, publicKey, sign);
        LogUtil.info("Verify Result: " + verifyResult);
        assertTrue(verifyResult, "签名验证失败");
    }

    @Test
    void verify_TamperedData_Failure() throws Exception {
        KeyPair keyPair = RSAUtil.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        String sign = RSAUtil.sign(TEST_DATA, privateKey);
        String tamperedData = TEST_DATA + "_tampered";

        boolean verifyResult = RSAUtil.verify(tamperedData, publicKey, sign);
        assertFalse(verifyResult, "篡改数据的签名验证应失败");
    }

    @Test
    void importKey_Success() throws Exception {
        KeyPair keyPair = RSAUtil.generateKeyPair();
        String publicKeyStr = RSAUtil.exportPublicKey(keyPair.getPublic());
        LogUtil.info("Public Key: " + publicKeyStr);
        String privateKeyStr = RSAUtil.exportPrivateKey(keyPair.getPrivate());
        LogUtil.info("Private Key: " + privateKeyStr);

        PublicKey importedPublicKey = RSAUtil.importPublicKey(publicKeyStr);
        PrivateKey importedPrivateKey = RSAUtil.importPrivateKey(privateKeyStr);

        assertNotNull(importedPublicKey, "公钥导入失败");
        assertNotNull(importedPrivateKey, "私钥导入失败");

        String encryptedData = RSAUtil.encryptByPublicKey(TEST_DATA, importedPublicKey);
        String decryptedData = RSAUtil.decryptByPrivateKey(encryptedData, importedPrivateKey);
        assertEquals(TEST_DATA, decryptedData, "导入的密钥无法正常使用");
    }

    @Test
    void importInvalidKey_ThrowsException() {
        assertThrows(Exception.class, () -> RSAUtil.importPublicKey("invalid_public_key"), "导入无效公钥应抛出异常");
        assertThrows(Exception.class, () -> RSAUtil.importPrivateKey("invalid_private_key"), "导入无效私钥应抛出异常");
    }
}