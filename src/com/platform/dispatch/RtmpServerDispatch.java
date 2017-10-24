package com.platform.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.until.errorcode.MAGICCODE;

public class RtmpServerDispatch
{
    private List<String> rtmpServerList = null;
    private static RtmpServerDispatch serverDispatch = null;
    
    private RtmpServerDispatch()
    {
        init();
    }
    
    public void init()
    {
        rtmpServerList = new ArrayList<String>();
        //后期节点多了可以从配置文件或者数据库读取。重写init方法
        rtmpServerList.add("rtmp://123.133.78.91/oflaDemo");
        rtmpServerList.add("rtmp://122.4.241.3/oflaDemo");
    }
    
    public static RtmpServerDispatch getInstance()
    {
        if(null == serverDispatch)
        {
            serverDispatch = new RtmpServerDispatch();
        }
        
        return serverDispatch;
    }
    
    public String getRtmpServer()
    {
        if(null == rtmpServerList)
        {
            return MAGICCODE.MAGIC_ERROR;
        }
        int size = rtmpServerList.size();
        Random random = new Random();
        int index = random.nextInt(size);
        String server = rtmpServerList.get(index);
        return server;
    }

    public List<String> getRtmpServerList()
    {
        return rtmpServerList;
    }

    public void setRtmpServerList(List<String> rtmpServerList)
    {
        this.rtmpServerList = rtmpServerList;
    }

    public static void main(String args[])
    {
        RtmpServerDispatch dis = RtmpServerDispatch.getInstance();
        String server = null;
        for(int i=0;i<100;i++)
        {
            server = dis.getRtmpServer();
            System.out.println(server);
        }
    }
}
