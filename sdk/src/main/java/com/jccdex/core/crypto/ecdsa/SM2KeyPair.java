package com.jccdex.core.crypto.ecdsa;

import com.jccdex.core.encoding.common.B16;
import com.jccdex.core.utils.SM3;
import com.jccdex.core.utils.SM3HashUtils;
import com.jccdex.core.utils.Utils;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.crypto.signers.SM2Signer;

import java.security.SecureRandom;
import java.math.BigInteger;

public class SM2KeyPair implements IKeyPair {
    public static  String DefaultUserId = "1234567812345678";
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
        try {
            SM2Signer signer =  new SM2Signer();
            ECPoint pubPoint = SM2P256V1.curve().decodePoint(pub.toByteArray());
            ECPublicKeyParameters params = new ECPublicKeyParameters(pubPoint, SM2P256V1.params());
            signer.init(false,params);
            signer.update(data,0,data.length);
            return(signer.verifySignature(sigBytes));
        }catch (Exception ex)
        {
            System.out.println("error:"+ex.getMessage());
            return(false);
        }
    }

    public static boolean verify(byte[] data, byte[] sigBytes, String publicKey) {
        byte[] hash = SM3HashUtils.Digest(data);
        BigInteger pub =  Utils.uBigInt(B16.decode(publicKey));
        return(verify(hash,sigBytes,pub));
    }
    public static byte[] signHash(byte[] bytes, BigInteger secret) {
        byte[] der = createECDSASignature(bytes, secret);
        return der;
    }

    private static  byte[] createECDSASignature(byte[] hash, BigInteger secret) {
        byte[] sigs= new byte[10];
        try {
            SM2Signer signer =  new SM2Signer();
            ECPrivateKeyParameters privKey = new ECPrivateKeyParameters(secret, SM2P256V1.params());
            ParametersWithRandom paramRandom = new ParametersWithRandom(privKey, SecureRandom.getInstance("SHA1PRNG"));
            signer.init(true,paramRandom);
            signer.update(hash,0,hash.length);
            sigs = signer.generateSignature();
            return sigs;
        }catch (Exception ex)
        {
            System.out.println("error:"+ex.getMessage());
        }
        return sigs;
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
        byte[] hash = SM3HashUtils.Digest(message);
        return verifyHash(hash, sigBytes);
    }

    @Override
    public byte[] signMessage(byte[] message) {
        byte[] hash = SM3HashUtils.Digest(message);
        return signHash(hash);
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
