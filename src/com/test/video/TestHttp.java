package com.test.video;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

public class TestHttp
{

    public static void main(String[] args)
    {
        String result = null;
        try
        {
            String strURL = "http://" + "magic.puckart.com/video/query_all.action?keyID=nhatrgog4yqq4b";
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }

            reader.close();
            httpConn.disconnect();

            result = buffer.toString();
            System.out.println("RESULT："+result);

        } catch (Exception e)
        {
            System.out.println(e);
        }finally
        {
        }

    }

}
