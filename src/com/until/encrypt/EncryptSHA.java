package com.until.encrypt;

import org.apache.commons.codec.digest.DigestUtils;

import com.until.errorcode.MAGICCODE;

/**
 * 使用SHA256进行不可逆加解密
 * @author xbayonet
 *
 */
public class EncryptSHA implements Encrypt
{
	
	private static EncryptSHA encryptSha = null;
	
	private EncryptSHA()
	{
	}
	
	public static EncryptSHA getInstances()
	{
		if(null == encryptSha)
		{
			encryptSha = new EncryptSHA();
		}
		
		return encryptSha;
	}
	
	public String encrypt(String encrypt)
	{
		System.out.println("encrypt:"+encrypt);
		String result = DigestUtils.sha512Hex(encrypt);
		return result;
	}
	
	public int checkPassword(String password, String encryptPassword)
	{
		if(null == password|| null == encryptPassword)
		{
			return MAGICCODE.ERROR;
		}
		
		if (password.equals(DigestUtils.sha512Hex(encryptPassword)))
		{
			return MAGICCODE.OK;
		}
		else
		{
			return MAGICCODE.USER_PASSWORD_ERROR;
		}
	}
	
	public static void main(String args[])
	{
		EncryptSHA test = new EncryptSHA();
		String testPastword = "This is just a test!";
		String encryptPassword = test.encrypt(testPastword);
		System.out.println(encryptPassword);
		
		System.out.println(test.checkPassword(testPastword, encryptPassword));
		
		System.out.println(test.checkPassword("This is just a tet!", encryptPassword));
	}
}
