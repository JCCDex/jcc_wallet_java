package com.jccdex.core.client;

import com.jccdex.core.config.Config;
import com.jccdex.core.crypto.ecdsa.IKeyPair;
import com.jccdex.core.crypto.ecdsa.K256KeyPair;
import com.jccdex.core.crypto.ecdsa.SM2KeyPair;
import com.jccdex.core.crypto.ecdsa.Seed;
import com.jccdex.core.encoding.B58IdentiferCodecs;
import com.jccdex.core.encoding.common.B16;
import java.util.Arrays;

/**
 * 井通联盟链钱包工具类(非国密)
 * @author xdjiang
 */
public class Wallet {

	// 钱包keypairs属性
	private IKeyPair keypairs = null;

	//钱包密钥
	private String secret = null;

	//钱包字母表
	private String alphabet = Config.DEFAULT_ALPHABET;


	/**
	 * 创建钱包对象(新钱包)
	 */
	public Wallet() {
		Config.setAlphabet(Config.DEFAULT_ALPHABET);
		this.alphabet = Config.DEFAULT_ALPHABET;
		String secret = Seed.random();
		this.keypairs = Seed.fromBase58(secret).keyPair();
		this.secret = secret;
	}

	/**
	 * 创建钱包对象，已知密钥
	 * @param secret 钱包密钥
	 */
	public Wallet(String secret) {
		Config.setAlphabet(Config.DEFAULT_ALPHABET);
		this.alphabet = Config.DEFAULT_ALPHABET;
		this.keypairs = Seed.fromBase58(secret).keyPair();
		this.secret = secret;
	}

	/**
	 * 创建钱包对象，已知密钥和联盟链字母表
	 * @param secret 钱包密钥
	 * @param alphabet 字母表，每一条联盟链都可以用不同的或者相同alphabet
	 */
	public Wallet(String secret, String alphabet) {
		Config.setAlphabet(alphabet);
		this.alphabet = alphabet;

		this.keypairs = Seed.fromBase58(secret).keyPair();
		this.secret = secret;
	}

	/**
	 * 随机生成钱包地址
	 * @return 钱包对象
	 */
	public static Wallet generate() {
		return new Wallet();
	}

	/**
	 * 随机生成钱包地址
	 * @param alphabet 字母表，每一条联盟链都可以用不同的或者相同alphabet
	 * @return 钱包对象
	 */
	public static Wallet generate(String alphabet) {
		Config.setAlphabet(alphabet);
		String secret = Seed.random();
		IKeyPair keypairs = Seed.fromBase58(secret).keyPair();
		Wallet wallet = new Wallet();
		wallet.setKeypairs(keypairs);
		wallet.setSecret(secret);
		wallet.setAlphabet(alphabet);
		return wallet;
	}

	/**
	 * 根据密钥生成钱包
	 * @param secret
	 * @return 钱包对象
	 */
	public static Wallet fromSecret(String secret) {
		Wallet wallet = new Wallet(secret);
		return wallet;
	}

	/**
	 * 根据密钥生成钱包
	 * @param secret 钱包密钥
	 * @param alphabet 字母表，每一条联盟链都可以用不同的或者相同alphabet
	 * @return 钱包对象
	 */
	public static Wallet fromSecret(String secret, String alphabet) {
		Wallet wallet = new Wallet(secret, alphabet);
		return wallet;
	}

	/**
	 * 判断钱包地址是否有效
	 * @param address 钱包地址
	 * @return 有效返回true，无效返回false
	 */
	public static boolean isValidAddress(String address) {
		try {
			Config.getB58IdentiferCodecs().decode(address, B58IdentiferCodecs.VER_ACCOUNT_ID);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断钱包地址是否有效
	 * @param address 钱包地址
	 * @param alphabet 字母表，每一条联盟链都可以用不同的或者相同alphabet
	 * @return 有效返回true，无效返回false
	 */
	public static boolean isValidAddress(String address, String alphabet) {
		try {
			Config.setAlphabet(alphabet);
			Config.getB58IdentiferCodecs().decode(address, B58IdentiferCodecs.VER_ACCOUNT_ID);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断钱包密钥是否有效
	 * @param secret 钱包密钥
	 * @return 有效返回true，无效返回false
	 */
	public static boolean isValidSecret(String secret) {
		try {
			Seed.fromBase58(secret).keyPair();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断钱包密钥是否有效
	 * @param secret 钱包密钥
	 * @param alphabet 字母表，每一条联盟链都可以用不同的或者相同alphabet
	 * @return 有效返回true，无效返回false
	 */
	public static boolean isValidSecret(String secret, String alphabet) {
		try {
			Config.setAlphabet(alphabet);
			Seed.fromBase58(secret).keyPair();
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * 获取钱包公钥
	 * @return 钱包公钥
	 */
	public String getPublicKey() {
		if (this.keypairs == null) {
			return null;
		}
		return this.keypairs.canonicalPubHex();
	}
	
	/**
	 * 使用钱包密钥对信息进行签名
	 * @param message 需要签名的原文
	 * @return 签名后的内容
	 */
	public String sign(String message) {
		return signData(message.getBytes());
	}

	/**
	 * 使用钱包密钥对信息进行签名
	 * @param data 需要签名的原文
	 * @return 签名后的内容
	 */
	public String signData(byte[] data) {
		byte[] der = this.keypairs.signMessage(data);
		return B16.toStringTrimmed(der);
	}

	/**
	 * 校验信息的自作签名是否正确
	 * @param signature 签名的原文
	 * @param signature 签名后的内容
	 * @return true:校验通过，false:校验不通过
	 */
	public boolean verifyData(byte[] data, String signature) {
		return this.keypairs.verifySignature(data, B16.decode(signature));
	}

	/**
	 * 校验信息的自作签名是否正确
	 * @param message 签名的原文
	 * @param signature 签名后的内容
	 * @return true:校验通过，false:校验不通过
	 */
	public boolean verify(String message, String signature) {
		return verifyData(message.getBytes(), signature);
	}

	/**
	 * 校验信息的自作签名是否正确
	 * @param message 签名的原文
	 * @param signature 签名后的内容
	 * @param publicKey 公钥
	 * @return true:校验通过，false:校验不通过
	 */
	public static boolean verify(String message, String signature,String publicKey) {
		return K256KeyPair.verify(message.getBytes(),B16.decode(signature), publicKey);
	}

	/**
	 * 获取钱包地址
	 * @return 钱包地址
	 */
	public String getAddress() {
		byte[] bytes = this.keypairs.pub160Hash();

		return Config.getB58IdentiferCodecs().encodeAddress(bytes);
	}

	/**
	 * 获取钱包密钥
	 * @return 钱包密钥
	 */
	public String getSecret() {
		return this.secret;
	}

	/**
	 * 获取钱包keypairs属性
	 * @return keypairs属性
	 */
	public IKeyPair getKeypairs() {
		return keypairs;
	}

	/**
	 * 设置钱包keypairs属性
	 * @param keypairs 钱包keypairs值
	 */
	public void setKeypairs(IKeyPair keypairs) {
		this.keypairs = keypairs;
	}

	/**
	 * 设置钱包密钥
	 * @param secret 钱包密钥
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * 设置钱包字母表
	 * @param alphabet 字母表，每一条联盟链都可以用不同的或者相同alphabet
	 */
	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
		Config.setAlphabet(alphabet);
	}

	/**
	 * 获取钱包字母表
	 * @return 钱包字母表
	 */
	public String getAlphabet() {
		return this.alphabet;
	}

}