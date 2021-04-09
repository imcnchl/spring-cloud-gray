package cn.caohongliang.gray.common.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA工具类
 * 公钥和私钥都可以进行加密，但一般情况都是使用公钥加密，私钥解密、签名
 *
 * @author caohongliang
 */
public class RSAUtils {

	/**
	 * 获取密钥对
	 *
	 * @param keySize 密钥长度，512、1024、2048、4096
	 * @return 密钥对
	 */
	public static KeyPair getKeyPair(int keySize) {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(keySize);
			return generator.generateKeyPair();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取私钥
	 *
	 * @param privateKey 私钥字符串
	 * @return 私钥
	 */
	public static PrivateKey getPrivateKey(String privateKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] decodedKey = Base64.getDecoder().decode(privateKey.getBytes());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
			return keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取公钥
	 *
	 * @param publicKey 公钥字符串
	 * @return 公钥
	 */
	public static PublicKey getPublicKey(String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] decodedKey = Base64.getDecoder().decode(publicKey.getBytes());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
			return keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取密钥长度
	 *
	 * @param key 公钥/私钥 extends RSAKey
	 * @return 密钥长度
	 */
	public static int getKeySize(Key key) {
		return ((RSAKey) key).getModulus().bitLength();
	}

	/**
	 * 获取一次解密最大的数据长度
	 *
	 * @param key 公钥/私钥 extends RSAKey
	 * @return 最大的解密长度
	 */
	public static int getMaxDecryptBlock(Key key) {
		return getKeySize(key) / 8;
	}

	/**
	 * 获取一次加密最大的数据长度
	 *
	 * @param key 公钥/私钥 extends RSAKey
	 * @return 最大的加密长度
	 */
	public static int getMaxEncryptBlock(Key key) {
		return getKeySize(key) / 8 - 11;
	}

	/**
	 * RSA加密
	 *
	 * @param key       公钥/私钥，需要是 RSAPublicKey 或 RSAPrivateKey
	 * @param plaintext 待加密数据
	 * @return 返回密文
	 */
	public static String encrypt(Key key, String plaintext) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedData = doFinal(cipher, plaintext.getBytes(), getMaxEncryptBlock(key));
			// 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
			// 加密后的字符串
			return Base64.getEncoder().encodeToString(encryptedData);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * RSA解密
	 *
	 * @param key        公钥/私钥，需要是 RSAPublicKey 或 RSAPrivateKey
	 * @param ciphertext 待解密数据
	 * @return 返回明文
	 */
	public static String decrypt(Key key, String ciphertext) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] dataBytes = Base64.getDecoder().decode(ciphertext);
			byte[] decryptedData = doFinal(cipher, dataBytes, getMaxDecryptBlock(key));
			// 解密后的内容
			return new String(decryptedData, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static byte[] doFinal(Cipher cipher, byte[] dataBytes, int maxBlock) throws IllegalBlockSizeException, BadPaddingException, IOException {
		int inputLen = dataBytes.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offset = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offset > 0) {
			if (inputLen - offset > maxBlock) {
				cache = cipher.doFinal(dataBytes, offset, maxBlock);
			} else {
				cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
			}
			out.write(cache, 0, cache.length);
			i++;
			offset = i * maxBlock;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 签名
	 *
	 * @param privateKey 私钥
	 * @param plaintext  待签名数据
	 * @return 签名
	 */
	public static String sign(PrivateKey privateKey, String plaintext) {
		try {
			byte[] keyBytes = privateKey.getEncoded();
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey key = keyFactory.generatePrivate(keySpec);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(key);
			signature.update(plaintext.getBytes());
			return Base64.getEncoder().encodeToString(signature.sign());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 验签
	 *
	 * @param publicKey 公钥
	 * @param srcData   原始字符串
	 * @param sign      签名
	 * @return 是否验签通过
	 */
	public static boolean verify(PublicKey publicKey, String srcData, String sign) {
		try {
			byte[] keyBytes = publicKey.getEncoded();
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey key = keyFactory.generatePublic(keySpec);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(key);
			signature.update(srcData.getBytes());
			return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(Key key) {
		return Base64.getEncoder().encodeToString((key.getEncoded()));
	}
}
