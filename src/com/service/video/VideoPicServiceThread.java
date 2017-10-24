package com.service.video;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.model.video.Video;
import com.until.errorcode.MAGICCODE;

public class VideoPicServiceThread implements Runnable
{

    private Video video = null;
    
    public VideoPicServiceThread(Video video)
    {
        this.video = video;
    }
    
    @Override
    public void run()
    {
        int result = createVideoPic();
        if(result != MAGICCODE.OK)
        {
            createVideoPic();
        }
        
    }
    
    public int createVideoPic()
    {
        if(null == video)
        {
            return MAGICCODE.OK;
        }
        
        String cmd = "ffmpeg -i";
        cmd += " ";
        cmd += video.getPath();
        cmd += " ";
        cmd += "-y -f image2 -ss 8 -t 0.001 -s 300*200";
        cmd += " ";
        cmd += video.getPic();
        System.out.println("CMD:"+cmd);
        
        Runtime run = Runtime.getRuntime();
        try
        {
            Process p = run.exec(cmd);
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null)
                System.out.println(lineStr);
            // 检查命令是否执行失败。
            if (p.waitFor() != 0)
            {
                if (p.exitValue() != 0)
                {

                    // p.exitValue()==0表示正常结束，1：非正常结束
                    // System.err.println("命令执行失败!");
                    return MAGICCODE.ERROR;
                }
            }
            inBr.close();
            in.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return MAGICCODE.OK;
    }

}
