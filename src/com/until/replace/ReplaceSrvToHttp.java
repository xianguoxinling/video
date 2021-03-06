package com.until.replace;

import com.platform.dispatch.HttpServerDispatch;
import com.platform.storage.StorageManager;

public class ReplaceSrvToHttp
{
	public static String replace(String base)
	{
		if(null == base)
		{
			return "";
		}
		String result  = base.replace(StorageManager.getStorage(), HttpServerDispatch.getInstance().gethttpServer());
		return result;
	}
	
	public static void main(String arts[])
	{
		String srv = "www.puckart.com/image/i.png";
		String result = ReplaceSrvToHttp.replace(srv);
		System.out.println(result);
	}
	
}
