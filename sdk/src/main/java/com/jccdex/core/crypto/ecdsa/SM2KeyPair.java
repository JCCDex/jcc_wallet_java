package com.jccdex.core.crypto.ecdsa;

import com.jccdex.core.config.Config;
import com.jccdex.core.utils.SM3HashUtils;
import com.jccdex.core.utils.SM3;
import com.jccdex.core.utils.Utils;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

public class SM2KeyPair implements IKeyPair {
	BigInteger priv, pub, secretKey;
	byte[] pubBytes;

	// See https://wiki.ripple.com/Account_Family
	/**
	 *
	 * @param secretKey secret point on the curve as BigInteger
	 * @return corresponding public point
	 */
	public static byte[] getPublic(BigInteger secretKey) {
		return SM2P256V1.basePointMultipliedBy(secretKey);
	}

	/**
	 *
	 * @param privateGen secret point on the curve as BigInteger
	 * @return the corresponding public key is the public generator
	 *         (aka public root key, master public key).
	 *         return as byte[] for convenience.
	 */
	public static byte[] computePublicGenerator(BigInteger privateGen) {
		return getPublic(privateGen);
	}

	public static BigInteger computePublicKey(BigInteger secret) {
		return Utils.uBigInt(getPublic(secret));
	}

	public static BigInteger computePrivateGen(byte[] seedBytes) {
		return generateKey(seedBytes, null);
	}

	public static byte[] computePublicKey(byte[] publicGenBytes, int accountNumber) {
		ECPoint rootPubPoint = SM2P256V1.curve().decodePoint(publicGenBytes);
		BigInteger scalar = generateKey(publicGenBytes, accountNumber);
		ECPoint point = SM2P256V1.basePoint().multiply(scalar);
		ECPoint offset = rootPubPoint.add(point);
		return offset.getEncoded(true);
	}

	public static BigInteger computeSecretKey(BigInteger privateGen, byte[] publicGenBytes, int accountNumber) {
		return generateKey(publicGenBytes, accountNumber).add(privateGen).mod(SM2P256V1.order());
	}

	/**
	 * @param seedBytes - a bytes sequence of arbitrary length which will be hashed
	 * @param discriminator - nullable optional uint32 to hash
	 * @return a number between [1, order -1] suitable as a private key
	 *
	 */
	public static BigInteger generateKey(byte[] seedBytes, Integer discriminator) {
		BigInteger key = null;
		for (long i = 0; i <= 0xFFFFFFFFL; i++) {
			SM3 sm3 = new SM3();
			sm3.add(seedBytes);
			if (discriminator != null) {
				sm3.addU32(discriminator);
			}
			sm3.addU32((int) i);
			byte[] keyBytes = sm3.finish();
			key = Utils.uBigInt(keyBytes);
			if (key.compareTo(BigInteger.ZERO) == 1 && key.compareTo(SM2P256V1.order()) == -1) {
				break;
			}
		}
		return key;
	}

	public static boolean verify(byte[] data, byte[] sigBytes, BigInteger pub) {
		ECDSASignature signature = ECDSASignature.decodeFromDER(sigBytes);
		if (signature == null) {
			return false;
		}
		ECDSASigner signer = new ECDSASigner();
		ECPoint pubPoint = SECP256K1.curve().decodePoint(pub.toByteArray());
		ECPublicKeyParameters params = new ECPublicKeyParameters(pubPoint, SECP256K1.params());
		signer.init(false, params);
		return signer.verifySignature(data, signature.r, signature.s);
	}

	public static byte[] signHash(byte[] bytes, BigInteger secret) {
		ECDSASignature sig = createECDSASignature(bytes, secret);
		byte[] der = sig.encodeToDER();
		if (!ECDSASignature.isStrictlyCanonical(der)) {
			throw new IllegalStateException("Signature is not strictly canonical");
		}
		return der;
	}

	private static ECDSASignature createECDSASignature(byte[] hash, BigInteger secret) {
		return(null);//暂时为实现
	}

	public boolean verifyHash(byte[] hash, byte[] sigBytes) {
		return verify(hash, sigBytes, pub);
	}

	public byte[] signHash(byte[] bytes) {
		return signHash(bytes, priv);
	}

	@Override
	public BigInteger pub() {
		return pub;
	}

	@Override
	public byte[] canonicalPubBytes() {
		return pubBytes;
	}

	public SM2KeyPair(BigInteger priv, BigInteger pub) {
		this.priv = priv;
		this.pub = pub;
		this.pubBytes = pub.toByteArray();
	}

	@Override
	public BigInteger priv() {
		return priv;
	}
	
	@Override
	public boolean verifySignature(byte[] message, byte[] sigBytes) {
		return(false);//暂时未实现
	}

	@Override
	public byte[] signMessage(byte[] message) {
		return(null);//暂时未实现
	}

	@Override
	public byte[] pub160Hash() {
		return SM3HashUtils.SM3_RIPEMD160(pubBytes);
	}

	@Override
	public String canonicalPubHex() {
		return Utils.bigHex(pub);
	}

	@Override
	public String privHex() {
		return Utils.bigHex(priv);
	}

}
