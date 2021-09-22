package com.jccdex.core.config;

import com.jccdex.core.encoding.B58IdentiferCodecs;
import com.jccdex.core.encoding.base58.B58;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

// Somewhat of a global registry, dependency injection ala guice would be nicer, but trying to KISS
public class BaseConfig {
	public static final String DEFAULT_ALPHABET = "jpshnaf39wBUDNEGHJKLM4PQRST7VWXYZ2bcdeCg65rkm8oFqi1tuvAxyz";
	public static B58IdentiferCodecs b58IdentiferCodecs;
	public static double feeCushion;
	public static B58 b58;
	public static int SEED_PREFIX = 33;
	public static String CURRENCY = "SWT";
	public static String ACCOUNT_ZERO = "jjjjjjjjjjjjjjjjjjjjjhoLvTp";
	public static String ACCOUNT_ONE = "jjjjjjjjjjjjjjjjjjjjBZbvri";
	public static Integer FEE = 100;
	public static Boolean guomi = false;
}
