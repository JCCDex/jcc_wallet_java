package com.jccdex.core.crypto.ecdsa;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import com.jccdex.core.config.ConfigSM;
import com.jccdex.core.encoding.B58IdentiferCodecs;
import com.jccdex.core.encoding.base58.B58;
import com.jccdex.core.utils.SM3;
import com.jccdex.core.utils.Utils;

public class SeedSM {
	public static byte[] VER_K256 = new byte[] { (byte) B58IdentiferCodecs.VER_FAMILY_SEED };
	public static byte[] VER_ED25519 = new byte[] { (byte) 0x1, (byte) 0xe1, (byte) 0x4b };
	final byte[] seedBytes;
	byte[] version;

	public SeedSM(byte[] seedBytes) {
		this(VER_K256, seedBytes);
	}

	public SeedSM(byte[] version, byte[] seedBytes) {
		this.seedBytes = seedBytes;
		this.version = version;
	}

	@Override
	public String toString() {
		return ConfigSM.getB58().encodeToStringChecked(seedBytes, version);
	}

	public byte[] bytes() {
		return seedBytes;
	}

	public byte[] version() {
		return version;
	}

	public SeedSM setEd25519() {
		this.version = VER_ED25519;
		return this;
	}

	public IKeyPair keyPair() {
		return keyPair(0);
	}

	public IKeyPair rootKeyPair() {
		return keyPair(-1);
	}

	public IKeyPair keyPair(int account) {
		if (Arrays.equals(version, VER_ED25519)) {
			if (account != 0) {
				throw new AssertionError();
			}
			return EDKeyPair.from128Seed(seedBytes);
		} else {
			return createKeyPair(seedBytes, account);
		}
	}

	public static SeedSM fromBase58(String b58) {
		B58.Decoded decoded = ConfigSM.getB58().decodeMulti(b58, 16, VER_K256, VER_ED25519);
		return new SeedSM(decoded.version, decoded.payload);
	}

	public static IKeyPair createKeyPair(byte[] seedBytes) {
		return createKeyPair(seedBytes, 0);
	}

	public static IKeyPair createKeyPair(byte[] seedBytes, int accountNumber) {// accountNumber=0
		BigInteger secret, pub, privateGen;
		// The private generator (aka root private key, master private key)
		privateGen = SM2KeyPair.computePrivateGen(seedBytes);// li-ok
		byte[] publicGenBytes = SM2KeyPair.computePublicGenerator(privateGen);// li-ok
		if (accountNumber == -1) {
			// The root keyPair
			return new SM2KeyPair(privateGen, Utils.uBigInt(publicGenBytes));
		} else {
			secret = SM2KeyPair.computeSecretKey(privateGen, publicGenBytes, accountNumber);// li-ok
			pub = SM2KeyPair.computePublicKey(secret);
			return new SM2KeyPair(secret, pub);
		}
	}

	public static IKeyPair getKeyPair(byte[] seedBytes) {
		return createKeyPair(seedBytes, 0);
	}

	public static IKeyPair getKeyPair(String b58) {
		return getKeyPair(ConfigSM.getB58IdentiferCodecs().decodeFamilySeed(b58));
	}

	public static String random() {
		byte[] randBytes = new byte[16];
		Random random = new Random();
		random.nextBytes(randBytes);
		byte[] bytes = new byte[randBytes.length + 1];
		bytes[0] = (byte) ConfigSM.SEED_PREFIX;
		System.arraycopy(randBytes, 0, bytes, 1, randBytes.length);
		byte[] hashValue = null;
		try {
			SM3 sm3 = new SM3();
			sm3.add(bytes);
			byte[] hashValue2 = sm3.finish();
			sm3.add(hashValue2);
			hashValue = sm3.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] checksum = Arrays.copyOf(hashValue, 4);
		byte[] ret = new byte[bytes.length + checksum.length];
		System.arraycopy(bytes, 0, ret, 0, bytes.length);
		System.arraycopy(checksum, 0, ret, bytes.length, checksum.length);
		return ConfigSM.getB58().encodeToString(ret);
	}
}
