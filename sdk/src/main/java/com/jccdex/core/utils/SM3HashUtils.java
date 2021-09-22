package com.jccdex.core.utils;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SM3HashUtils {
	/**
	 * See {@link SM3HashUtils#doubleDigest(byte[], int, int)}.
	 */
	public static byte[] doubleDigest(byte[] input) {
		return doubleDigest(input, 0, input.length);
	}

	/**
	 * Calculates the SHA-256 hash of the given byte range, and then hashes the resulting hash again. This is
	 * standard procedure in Bitcoin. The resulting hash is in big endian form.
	 */
	public static byte[] doubleDigest(byte[] input, int offset, int length) {
		SM3 sm3=new SM3();
		sm3.add(input);
		byte[] sm3Hash = sm3.finish();
		sm3.add(sm3Hash);
		return(sm3.finish());
	}

	public static byte[] Digest(byte[] bytes) {
		SM3 sm3=new SM3();
		sm3.add(bytes);
		byte[] sm3Hash = sm3.finish();
		return(sm3Hash);
	}

	public static byte[] SM3_RIPEMD160(byte[] input) {
		try {
			SM3 sm3=new SM3();
			sm3.add(input);
			byte[] sm3Hash = sm3.finish();
			RIPEMD160Digest digest = new RIPEMD160Digest();
			digest.update(sm3Hash, 0, sm3Hash.length);
			byte[] out = new byte[20];
			digest.doFinal(out, 0);
			return out;
		} catch (Exception e) {
			throw new RuntimeException(e); // Cannot happen.
		}
	}
}
