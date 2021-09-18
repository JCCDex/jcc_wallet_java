package com.jccdex.core.utils;

import org.bouncycastle.crypto.digests.SM3Digest;
import java.security.NoSuchAlgorithmException;

public class SM3 {
	SM3Digest messageDigest;

	public SM3() {
		try {
			messageDigest = new SM3Digest();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public SM3(byte[] start) {
		this();
		add(start);
	}

	public SM3 add(byte[] bytes) {
		messageDigest.update(bytes,0,bytes.length);
		return this;
	}

	public SM3 addU32(int i) {
		messageDigest.update((byte) ((i >>> 24) & 0xFF));
		messageDigest.update((byte) ((i >>> 16) & 0xFF));
		messageDigest.update((byte) ((i >>> 8) & 0xFF));
		messageDigest.update((byte) ((i) & 0xFF));
		return this;
	}

	public byte[] finish() {
		byte[] hash = new byte[messageDigest.getDigestSize()];
		messageDigest.doFinal(hash,0);
		return hash;
	}
}
