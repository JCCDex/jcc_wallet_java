package com.jccdex.core.utils;

import com.jccdex.core.serialized.BytesSink;
import org.bouncycastle.crypto.digests.SM3Digest;

/**
 * @author xdjiang
 */
public class SM3 implements BytesSink {
	public SM3Digest messageDigest;

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

	public static SM3 prefixed256(byte[] bytes) {
		SM3 sm3 = new SM3();
		sm3.add(bytes);
		return sm3;
	}

	@Override
	public void add(byte aByte) {
		messageDigest.update(aByte);
	}

	@Override
	public void add(byte[] bytes) {
		messageDigest.update(bytes,0,bytes.length);
	}

//	public Hash256 finishHash256() {
//		byte[] half = finish();
//		return new Hash256(half);
//	}
}
