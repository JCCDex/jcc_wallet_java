package com.jccdex.core.client;

import org.junit.Assert;
import org.junit.Test;

import com.utils.JsonUtils;

public class WalletSMTest {
	WalletSM wallet = null;
	String VALID_SECRET = "sncXGdy6qAroErnTveCY6J8Z28Ua9";
	String VALID_ADDRESS = "jpVvaRgkwHwzi1MZm9Qmj3VbyEXEYEagUF";
	String VAILD_PUBLIC_KEY = "02E14F2156E8D9CDEFC42FD99B17D4906E7A9DD64290D10E50D8895ACBA7294581";
	String VAILD_PRIVATE_KEY = 	"000745C30AFE8C78FCD4705B047463B71E363EA76ED7C617A0A1C885DBF9744D0C";

	String INVALID_ADDRESS1 = null;
	String INVALID_ADDRESS2 = null;
	String INVALID_ADDRESS3 = "";
	String INVALID_ADDRESS4 = "xxxx";
	String INVALID_ADDRESS5 = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	String INVALID_ADDRESS6 = "jahbmVT3T9yf5D4Ykw8x6nRUtUfAAMzBRVxxx";
	String INVALID_ADDRESS7 = "ahbmVT3T9yf5D4Ykw8x6nRUtUfAAMzBRV";
	String INVALID_ADDRESS8 = "jahbmVT3T9yf5D4Ykw8x6nRUtUfAAMzBRVjahbmVT3T9yf5D4Ykw8x6nRUtUfAAMzBRV";
	String INVALID_SECRET1 = null;
	String INVALID_SECRET2 = null;
	String INVALID_SECRET3 = "";
	String INVALID_SECRET4 = "xxxx";
	String INVALID_SECRET5 = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	String INVALID_SECRET6 = "sszWqvtbDzzMQEVWqGDSA5DbMYDBNxx";
	String INVALID_SECRET7 = "zWqvtbDzzMQEVWqGDSA5DbMYDBN";
	String INVALID_SECRET8 = "sszWqvtbDzzMQEVWqGDSA5DbMYDBNsszWqvtbDzzMQEVWqGDSA5DbMYDBN";
	String SIGNATURE1 = "3045022100B53E6A54B71E44A4D449C76DECAE44169204744D639C14D22D941157F5A1418F02201D029783B31EE3DA88F18C56D055CF47606A9708FDCA9A42BAD9EFD335FA29FD";
	String MESSAGE1 = "hello";
	String MESSAGE2 = null;
	String MESSAGE4 = "";
	String SIGNATURE5 = "3045022100E9532A94BF33D4E094C0E0DA131B8BFB28D8275F0004341A5D76218C3134B40802201C8A32706AD5A719B21297B590D9AC52726C08773A65F54FD027C61ED65BCC77";

	/**
	 * 测试钱包生成程序
	 */
	@Test
	public void generate() {
		try {
			WalletSM wallet = WalletSM.generate();
			// assert wallet == null : wallet.getAddress();
			System.out.println("---------generate----------");
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
			System.out.println("publicKey=" + wallet.getPublicKey());
			System.out.println(JsonUtils.toJsonString(wallet));
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("INVALID_SECRET1  为 空时 ：" + e.getMessage());
			Assert.assertTrue(false);
		}

	}

	/**
	 * 测试根据密钥生成
	 */
	@Test
	public void fromSecret() {
		try {
			System.out.println("---------fromSecret----------");
			wallet = WalletSM.fromSecret(VALID_SECRET);
			Assert.assertEquals(VALID_ADDRESS, wallet.getAddress());
			Assert.assertEquals(VAILD_PUBLIC_KEY, wallet.getPublicKey());
		} catch (Exception e) {
			System.out.println("INVALID_SECRET1  为 NULL时 ：" + e.getMessage());
		}

		try {
			System.out.println("---------fromSecret----------");
			wallet = WalletSM.fromSecret(INVALID_SECRET1);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			System.out.println("INVALID_SECRET1  为 NULL时 ：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			wallet = WalletSM.fromSecret(INVALID_SECRET3);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			System.out.println("INVALID_SECRET1  为 空时 ：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			wallet = WalletSM.fromSecret(INVALID_SECRET4);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			System.out.println("INVALID_SECRET1  为 过短时 异常：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			wallet = WalletSM.fromSecret(INVALID_SECRET5);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("INVALID_SECRET5  为 过长时 异常：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			wallet = WalletSM.fromSecret(INVALID_SECRET6);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			System.out.println("should fail when tail string 异常：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			wallet = WalletSM.fromSecret(INVALID_SECRET7);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			System.out.println("should fail when not start with s 异常：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			wallet = WalletSM.fromSecret(INVALID_SECRET8);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			System.out.println("should fail when double secret 异常：" + e.getMessage());
		}
	}

	/**
	 * 校验密钥
	 */
	@Test
	public void isValidSecret() {
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + WalletSM.isValidSecret(VALID_SECRET));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥 异常：" + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + WalletSM.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when null secret" + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + WalletSM.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when undefined secret" + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + WalletSM.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when empty secret" + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + WalletSM.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when too short secret" + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + WalletSM.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when too long secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + WalletSM.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when tail string " + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + WalletSM.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when not start with s " + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + WalletSM.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
	}

	/**
	 * 校验公钥
	 */
	@Test
	public void isValidAddress() {
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + WalletSM.isValidAddress(VALID_ADDRESS));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + WalletSM.isValidAddress(INVALID_ADDRESS1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + WalletSM.isValidAddress(INVALID_ADDRESS2));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + WalletSM.isValidAddress(INVALID_ADDRESS3));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + WalletSM.isValidAddress(INVALID_ADDRESS4));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + WalletSM.isValidAddress(INVALID_ADDRESS5));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + WalletSM.isValidAddress(INVALID_ADDRESS6));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + WalletSM.isValidAddress(INVALID_ADDRESS7));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + WalletSM.isValidAddress(INVALID_ADDRESS8));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
	}

	/**
	 * 根据密码生成钱包
	 */
	@Test
	public void structure() {
		try {
			wallet = new WalletSM(VALID_SECRET);
			System.out.println(wallet.getAddress() + "  " + VALID_SECRET);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			wallet = new WalletSM(INVALID_SECRET1);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			wallet = new WalletSM(INVALID_SECRET2);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			wallet = new WalletSM(INVALID_SECRET3);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			wallet = new WalletSM(INVALID_SECRET4);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			wallet = new WalletSM(INVALID_SECRET5);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			wallet = new WalletSM(INVALID_SECRET6);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			wallet = new WalletSM(INVALID_SECRET7);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			wallet = new WalletSM(INVALID_SECRET8);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
	}

	/**
	 *
	 */
	@Test
	public void structureInit() {
		wallet = new WalletSM(VALID_SECRET);
		try {
			String signStr = wallet.sign(MESSAGE1);
			System.out.println(signStr.equals(SIGNATURE1) + "\t" + signStr + "\t" + SIGNATURE1);
		} catch (Exception e) {
			System.out.println("钱包签名  异常：" + e.getMessage());
		}
		try {
			String signStr = wallet.sign(MESSAGE2);
			System.out.println(signStr.equals(null));
		} catch (Exception e) {
			System.out.println("钱包签名  异常：" + e.getMessage());
		}
		try {
			String signStr = wallet.sign(MESSAGE4);
			System.out.println(signStr.equals(null));
		} catch (Exception e) {
			System.out.println("钱包签名  异常：" + e.getMessage());
		}
		try {
			String MESSAGE5 = "";
			for (int i = 0; i < 1000; ++i) {
				MESSAGE5 = MESSAGE5 + 'x';
			}
			String signStr = wallet.sign(MESSAGE5);
			System.out.println(signStr.equals(SIGNATURE5) + "\t" + signStr + "\t" + SIGNATURE5);
		} catch (Exception e) {
			System.out.println("钱包签名  异常：" + e.getMessage());
		}
	}
}
