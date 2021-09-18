package com.jccdex.core.config;

import com.jccdex.core.encoding.B58IdentiferCodecs;
import com.jccdex.core.encoding.base58.B58;

// Somewhat of a global registry, dependency injection ala guice would be nicer, but trying to KISS
public class ConfigSM extends Config {

	public static void setAlphabet(String alphabet) {
		b58 = new B58(alphabet,true);
		b58IdentiferCodecs = new B58IdentiferCodecs(b58);
	}
}
