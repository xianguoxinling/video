package com.platform.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.until.errorcode.MAGICCODE;

public class HttpServerDispatch
{
    private List<String> httpServerList = null;
    private static HttpServerDispatch serverDispatch = null;
    
    private HttpServerDispatch()
    {
        init();
    }
    
    public void init()
    {
        httpServerList = new ArrayList<String>();
        //后期节点多了可以从配置文件或者数据库读取。重写init方法
        httpServerList.add("http://video.puckart.com/");
        httpServerList.add("http://video2.puckart.com/");
    }
    
    public static HttpServerDispatch getInstance()
    {
        if(null == serverDispatch)
        {
            serverDispatch = new HttpServerDispatch();
        }
        
        return serverDispatch;
    }
    
    public String gethttpServer()
    {
        if(null == httpServerList)
        {
            return MAGICCODE.MAGIC_ERROR;
        }
        int size = httpServerList.size();
        Random random = new Random();
        int index = random.nextInt(size);
        String server = httpServerList.get(index);
        return server;
    }

    public List<String> gethttpServerList()
    {
        return httpServerList;
    }

    public void sethttpServerList(List<String> httpServerList)
    {
        this.httpServerList = httpServerList;
    }

    public static void main(String args[])
    {
        HttpServerDispatch dis = HttpServerDispatch.getInstance();
        String server = null;
        for(int i=0;i<100;i++)
        {
            server = dis.gethttpServer();
            System.out.println(server);
        }
    }
}
