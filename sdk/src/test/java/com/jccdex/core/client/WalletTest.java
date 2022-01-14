package com.jccdex.core.client;

import org.junit.Assert;
import org.junit.Test;

import com.utils.JsonUtils;

public class WalletTest {
	String VALID_SECRET = "sszWqvtbDzzMQEVWqGDSA5DbMYDBN";
	String VALID_ADDRESS = "jahbmVT3T9yf5D4Ykw8x6nRUtUfAAMzBRV";
	String VAILD_PUBLIC_KEY = "023EDC5180579FB3E282A7F6B49EBFDACD447B1CBB7C8A7BC7FAB51D3076188E39";
	String VAILD_PRIVATE_KEY = 	"00D239DA526E2C662AC8EB2AACD8145020D3B0D5F870DCA23A21EF90928F27AD79";

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

			// assert wallet == null : wallet.getAddress();
			System.out.println("---------generate jingtum wallet----------");
			Wallet wallet = Wallet.generate();
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
			System.out.println("publicKey=" + wallet.getPublicKey());
			System.out.println(JsonUtils.toJsonString(wallet));
			Assert.assertTrue(true);

			System.out.println("---------generate bizain wallet----------");
			Wallet walletBiz = Wallet.generate("bpshnaf39wBUDNEGHJKLM4PQRST7VWXYZ2jcdeCg65rkm8oFqi1tuvAxyz");
			System.out.println("secret=" + walletBiz.getSecret());
			System.out.println("address=" + walletBiz.getAddress());
			System.out.println("publicKey=" + walletBiz.getPublicKey());
			System.out.println(JsonUtils.toJsonString(walletBiz));
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("创建钱包出错 ：" + e.getMessage());
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
			Wallet wallet = Wallet.fromSecret(VALID_SECRET);
			String addresss = wallet.getAddress();
			Assert.assertEquals(VALID_ADDRESS, wallet.getAddress());
			Assert.assertEquals(VAILD_PUBLIC_KEY, wallet.getPublicKey());
		} catch (Exception e) {
			System.out.println("INVALID_SECRET1  为 NULL时 ：" + e.getMessage());
		}

		try {
			System.out.println("---------fromSecret----------");
			Wallet wallet = Wallet.fromSecret(INVALID_SECRET1);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			System.out.println("INVALID_SECRET1  为 NULL时 ：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			Wallet wallet = Wallet.fromSecret(INVALID_SECRET3);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			System.out.println("INVALID_SECRET1  为 空时 ：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			Wallet wallet = Wallet.fromSecret(INVALID_SECRET4);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			System.out.println("INVALID_SECRET1  为 过短时 异常：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			Wallet wallet = Wallet.fromSecret(INVALID_SECRET5);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("INVALID_SECRET5  为 过长时 异常：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			Wallet wallet = Wallet.fromSecret(INVALID_SECRET6);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			System.out.println("should fail when tail string 异常：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			Wallet wallet = Wallet.fromSecret(INVALID_SECRET7);
			System.out.println("secret=" + wallet.getSecret());
			System.out.println("address=" + wallet.getAddress());
		} catch (Exception e) {
			System.out.println("should fail when not start with s 异常：" + e.getMessage());
		}
		try {
			System.out.println("---------fromSecret----------");
			Wallet wallet = Wallet.fromSecret(INVALID_SECRET8);
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
			System.out.println("是否有效=" + Wallet.isValidSecret(VALID_SECRET));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥 异常：" + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + Wallet.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when null secret" + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + Wallet.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when undefined secret" + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + Wallet.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when empty secret" + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + Wallet.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when too short secret" + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + Wallet.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when too long secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + Wallet.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when tail string " + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + Wallet.isValidSecret(INVALID_SECRET1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when not start with s " + e.getMessage());
		}
		try {
			System.out.println("---------isValidSecret----------");
			System.out.println("是否有效=" + Wallet.isValidSecret(INVALID_SECRET1));
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
			System.out.println("是否有效=" + Wallet.isValidAddress(VALID_ADDRESS));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + Wallet.isValidAddress(INVALID_ADDRESS1));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + Wallet.isValidAddress(INVALID_ADDRESS2));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + Wallet.isValidAddress(INVALID_ADDRESS3));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + Wallet.isValidAddress(INVALID_ADDRESS4));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + Wallet.isValidAddress(INVALID_ADDRESS5));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + Wallet.isValidAddress(INVALID_ADDRESS6));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + Wallet.isValidAddress(INVALID_ADDRESS7));
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("校验密钥  异常：should fail when double secret " + e.getMessage());
		}
		try {
			System.out.println("---------isValidAddress----------");
			System.out.println("是否有效=" + Wallet.isValidAddress(INVALID_ADDRESS8));
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
			Wallet wallet = new Wallet(VALID_SECRET);
			System.out.println(wallet.getSecret() + "  " + VALID_SECRET);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			Wallet wallet = new Wallet(INVALID_SECRET1);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			Wallet wallet = new Wallet(INVALID_SECRET2);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			Wallet wallet = new Wallet(INVALID_SECRET3);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			Wallet wallet = new Wallet(INVALID_SECRET4);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			Wallet wallet = new Wallet(INVALID_SECRET5);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			Wallet wallet = new Wallet(INVALID_SECRET6);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			Wallet wallet = new Wallet(INVALID_SECRET7);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
		try {
			Wallet wallet = new Wallet(INVALID_SECRET8);
		} catch (Exception e) {
			System.out.println("创建钱包  异常：" + e.getMessage());
		}
	}

	/**
	 *
	 */
	@Test
	public void structureInit() {
		Wallet wallet = new Wallet(VALID_SECRET);
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

	/**
	 * 签名与验签
	 */
	@Test
	public void signAndVerify() {
		System.out.println("---------fromSecret----------");
		String message = "1qazxsw2";
		Wallet wallet = Wallet.fromSecret(VALID_SECRET);
		String addresss = wallet.getAddress();
		String pubKey = wallet.getPublicKey();
		String sign = wallet.sign(message);
		Boolean f1 = Wallet.verify(message,sign,pubKey);
		Assert.assertTrue(f1);
		Boolean f2 = Wallet.verify("1qazxsw3",sign,pubKey);
		Assert.assertFalse(f2);
	}

	/**
	 * 获取公钥和钱包地址
	 */
	@Test
	public void getPublicKeyAndAdress() {
		System.out.println("---------fromSecret----------");
		Wallet wallet = Wallet.fromSecret(VALID_SECRET);
		String address1 = wallet.getAddress();
		String pubKey = wallet.getPublicKey();
		String address2 = Wallet.getAddress(pubKey);
		System.out.println("address1:"+address1);
		System.out.println("pubKey:"+pubKey);
		System.out.println("address2:"+address2);
	}
}
