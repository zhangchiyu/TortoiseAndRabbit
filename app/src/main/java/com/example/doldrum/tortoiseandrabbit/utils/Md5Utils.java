package com.example.doldrum.tortoiseandrabbit.utils;

import java.security.MessageDigest;

public class Md5Utils {
	private static String seed = "sevalo!@#$%^&*()";

	public static String getMd5String(String s1) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(s1.getBytes("utf-8"));
			return toHex(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String toHex(byte[] bytes) {

		final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString().toLowerCase();
	}
}
/*
	}
*/
/**
	 * 给指定的字符串进行MD5加密
	 *
	 * @param plainText
	 *            需要加密的串
	 * @return 返回加密后的串
	 * @throws UnsupportedEncodingException
	 *//*

	*/
/*public static String getMd5String(String plainText)
			throws UnsupportedEncodingException {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update((plainText+seed).getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result.toUpperCase();
	}*//*




	*/
/*private static  String getMd5String(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(s.getBytes("utf-8"));
			//return toHex(bytes);
			return new String(bytes);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String toHex(byte[] bytes) {

		final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i=0; i<bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}
}
*/
