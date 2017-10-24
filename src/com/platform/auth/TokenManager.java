package com.platform.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.until.errorcode.MAGICCODE;
import net.sf.json.JSONObject;

public class TokenManager
{
    private String mainAuthServer = null;
    private String salveAuthServer = null;
    private String server = null;

    public TokenManager()
    {
        // 阿里云内权限验证
        // mainAuthServer = "10.30.193.90";
        mainAuthServer = "101.37.81.165";
//        salveAuthServer = "122.4.241.3";
        salveAuthServer = "magic.puckart.com";
        // server = mainAuthServer;
        server = salveAuthServer;
    }

    public String createToken(String userID, String keyID)
    {
        String result = null;
        try
        {
            String strURL = "https://" + server + "/auth/create_token.action?userID=" + userID + "&keyID=" + keyID;
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

        } catch (Exception e)
        {
            server = salveAuthServer;
            return createToken(userID, keyID);
        } finally
        {
            // 阿里云内主要还是这个鉴权
            server = mainAuthServer;
        }

        return result;
    }

    public String updateToken(String userID, String keyID)
    {
        String result = null;
        try
        {
            String strURL = "http://" + server + "/auth/update_token.action?userID=" + userID + "&keyID=" + keyID;
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

        } catch (Exception e)
        {
            server = salveAuthServer;
            return updateToken(userID, keyID);
        } finally
        {
            // 阿里云内主要还是这个鉴权
            server = mainAuthServer;
        }

        return result;
    }

    public String queryUser(String token, String keyID)
    {
        String result = null;
        try
        {
            String strURL = "http://" + server + "/auth/query_user.action?token=" + token + "&keyID=" + keyID;
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

        } catch (Exception e)
        {
            server = salveAuthServer;
            return queryUser(token, keyID);
        } finally
        {
            // 阿里云内主要还是这个鉴权
            server = mainAuthServer;
        }

        return result;
    }

    public static void main(String args[])
    {
        TokenManager authManager = new TokenManager();
        String userID = "magiczz";
        String keyID = "magiczz";
        String result = null;
        JSONObject jsonObject = null;
        String code = null;
        String token = null;
        result = authManager.createToken(userID, keyID);
        jsonObject = JSONObject.fromObject(result);
        code = (String) jsonObject.get("code");
        token = (String) jsonObject.get("token");
        if (MAGICCODE.MAGIC_ERROR == code || MAGICCODE.MAGIC_PARAMETER_ERROR == code)
        {

        }
        System.out.println(token);
        
//         result = authManager.updateToken(userID, keyID);
//         jsonObject = JSONObject.fromObject(result);
//         code = (String) jsonObject.get("code");
//         token = (String) jsonObject.get("token");
//         if (MAGICCODE.MAGIC_ERROR == code || MAGICCODE.MAGIC_PARAMETER_ERROR == code)
//         {
//        
//         }
//         System.out.println(token);
//        
//         result = authManager.queryUser(token, keyID);
//         jsonObject = JSONObject.fromObject(result);
//         code = (String) jsonObject.get("code");
//         String queryID = (String) jsonObject.get("userID");
//         if (MAGICCODE.MAGIC_ERROR == code || MAGICCODE.MAGIC_PARAMETER_ERROR
//         == code)
//         {
//        
//         }
//         System.out.println(queryID);
    }
}
