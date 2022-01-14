package com.jccdex.core.client;

import com.jccdex.core.config.Config;
import com.jccdex.core.config.ConfigSM;
import com.jccdex.core.crypto.ecdsa.IKeyPair;
import com.jccdex.core.crypto.ecdsa.K256KeyPair;
import com.jccdex.core.crypto.ecdsa.SM2KeyPair;
import com.jccdex.core.crypto.ecdsa.SeedSM;
import com.jccdex.core.encoding.B58IdentiferCodecs;
import com.jccdex.core.encoding.common.B16;

/**
 * 井通联盟链钱包工具类(国密)
 * @author xdjiang
 */
public class WalletSM {

	// 钱包keypairs属性
	private IKeyPair keypairs = null;

	//钱包密钥
	private String secret = null;

	//钱包字母表
	private String alphabet = ConfigSM.DEFAULT_ALPHABET;


	/**
	 * 创建钱包对象(新钱包)
	 */
	public WalletSM() {
		ConfigSM.setAlphabet(alphabet);
		this.alphabet = alphabet;

		String secret = SeedSM.random();
		this.keypairs = SeedSM.fromBase58(secret).keyPair();
		this.secret = secret;
	}

	/**
	 * 创建钱包对象，已知密钥
	 * @param secret 钱包密钥
	 */
	public WalletSM(String secret) {
		ConfigSM.setAlphabet(alphabet);
		this.alphabet = alphabet;

		this.keypairs = SeedSM.fromBase58(secret).keyPair();
		this.secret = secret;
	}

	/**
	 * 创建钱包对象，已知密钥和联盟链字母表
	 * @param secret 钱包密钥
	 * @param alphabet 字母表，每一条联盟链都可以用不同的或者相同alphabet
	 */
	public WalletSM(String secret, String alphabet) {
		ConfigSM.setAlphabet(alphabet);
		this.alphabet = alphabet;

		this.keypairs = SeedSM.fromBase58(secret).keyPair();
		this.secret = secret;
	}

	/**
	 * 随机生成钱包地址
	 * @return 钱包对象
	 */
	public static WalletSM generate() {
		return new WalletSM();
	}

	/**
	 * 随机生成钱包地址
	 * @param alphabet 字母表，每一条联盟链都可以用不同的或者相同alphabet
	 * @return 钱包对象
	 */
	public static WalletSM generate(String alphabet) {
		ConfigSM.setAlphabet(alphabet);
		String secret = SeedSM.random();
		IKeyPair keypairs = SeedSM.fromBase58(secret).keyPair();
		WalletSM wallet = new WalletSM();
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
	public static WalletSM fromSecret(String secret) {
		WalletSM wallet = new WalletSM(secret);
		return wallet;
	}

	/**
	 * 根据密钥生成钱包
	 * @param secret 钱包密钥
	 * @param alphabet 字母表，每一条联盟链都可以用不同的或者相同alphabet
	 * @return 钱包对象
	 */
	public static WalletSM fromSecret(String secret, String alphabet) {
		WalletSM wallet = new WalletSM(secret, alphabet);
		return wallet;
	}

	/**
	 * 判断钱包地址是否有效
	 * @param address 钱包地址
	 * @return 有效返回true，无效返回false
	 */
	public static boolean isValidAddress(String address) {
		try {
			ConfigSM.getB58IdentiferCodecs().decode(address, B58IdentiferCodecs.VER_ACCOUNT_ID);
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
			ConfigSM.setAlphabet(alphabet);
			ConfigSM.getB58IdentiferCodecs().decode(address, B58IdentiferCodecs.VER_ACCOUNT_ID);
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
			SeedSM.fromBase58(secret).keyPair();
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
			ConfigSM.setAlphabet(alphabet);
			SeedSM.fromBase58(secret).keyPair();
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
		byte[] der = this.keypairs.signMessage(message.getBytes());
		return B16.encode(der);
	}

	/**
	 * 校验信息的自作签名是否正确
	 * @param message 签名的原文
	 * @param signature 签名后的内容
	 * @param publicKey 公钥
	 * @return true:校验通过，false:校验不通过
	 */
	public static boolean verify(String message, String signature,String publicKey) {
		return SM2KeyPair.verify(message.getBytes(),B16.decode(signature), publicKey);
	}

	/**
	 * 校验信息的自作签名是否正确
	 * @param message 签名的原文
	 * @param signature 签名后的内容
	 * @return true:校验通过，false:校验不通过
	 */
	public boolean verify(String message, String signature) {
		return this.keypairs.verifySignature(message.getBytes(), B16.decode(signature));
	}

	/**
	 * 获取钱包地址
	 * @return 钱包地址
	 */
	public String getAddress() {
		byte[] bytes = this.keypairs.pub160Hash();

		return ConfigSM.getB58IdentiferCodecs().encodeAddress(bytes);
	}

	/**
	 * 通过公钥获取钱包地址
	 * @param pubKey 公钥
	 * @return 钱包地址
	 */
	public static  String getAddress(String pubKey) {
		byte[] bytes = SM2KeyPair.pub160Hash(pubKey);

		return ConfigSM.getB58IdentiferCodecs().encodeAddress(bytes);
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
		ConfigSM.setAlphabet(alphabet);
	}

	/**
	 * 获取钱包字母表
	 * @return 钱包字母表
	 */
	public String getAlphabet() {
		return this.alphabet;
	}

}