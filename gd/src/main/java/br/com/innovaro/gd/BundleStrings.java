package br.com.innovaro.gd;

import java.util.Locale;
import java.util.ResourceBundle;

public class BundleStrings {
	
	public static String getString(String key) {
		final Locale locale = Locale.getDefault();
		final ResourceBundle bundle = ResourceBundle.getBundle("strings",locale);
		
		return bundle.getString(key);
	}
}
