package com.until.replace;

import java.util.Random;

import com.until.control.base.BaseString;

public class ReplaceSrvToStmp
{
    public static String replace(String base)
    {
        if (null == base)
        {
            return "";
        }
//        Random random = new Random();
//        int i = random.nextInt(100);
//        String result = null;
//        if (0 == i % 2)
//        {
//            result = base.replace("/srv/www/htdocs/", BaseString.baseStmpURL);
//        } else
//        {
//            result = base.replace("/srv/www/htdocs/", BaseString.baseStmpURL2);
//        }
        String result = base.replace("/home/red5/red5-server/webapps/oflaDemo/streams/", "");;
        return result;
    }

    public static void main(String arts[])
    {
        String srv = "/srv/www/htdocs/image/i.png";
        for (int i = 0; i < 100; i++)
        {
            String result = ReplaceSrvToStmp.replace(srv);
            System.out.println(result);
        }

    }

}
