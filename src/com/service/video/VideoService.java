package com.service.video;

import java.util.ArrayList;
import java.util.List;

import com.db.manager.VideoDBManager;
import com.model.video.Video;
import com.service.interfaces.VideoServiceInterface;
import com.until.errorcode.MAGICCODE;
import com.until.queryterm.QueryTerm;

public class VideoService implements VideoServiceInterface
{
    private VideoDBManager videoDBManager = null;
    
    public VideoService()
    {
        videoDBManager = new VideoDBManager();
    }

    @Override
    public int createVideo(Video video, String keyID)
    {
        int result = MAGICCODE.ERROR;
        
        if(null == video)
        {
            return result;
        }
        
        result = videoDBManager.createVideo(video,keyID);
        return 0;
    }
    
    @Override
    public int deleteVideo(String uuid, String userID, String keyID)
    {
        int result = MAGICCODE.OK;
        if(null == uuid || "".equals(uuid))
        {
            return MAGICCODE.ERROR;
        }
        
        if(null == userID || "".equals(userID))
        {
            return MAGICCODE.ERROR;
        }
        
        result = isYourVideo(uuid, userID,keyID);
        if(MAGICCODE.OK != result)
        {
            System.out.println("NOT YOUR VIDEO!");
            return result;
        }
        
        result = videoDBManager.deleteVideo(uuid, userID,keyID);
        if (MAGICCODE.OK != result)
        {
            
        }
        return result;

    }

    public int isYourVideo(String uuid, String userID,String keyID)
    {
        if(null == uuid || "".equals(uuid))
        {
            return MAGICCODE.ERROR;
        }
        
        if(null == userID || "".equals(userID))
        {
            return MAGICCODE.ERROR;
        }
        
        return videoDBManager.isYourVideo(uuid, userID,keyID);
        
        
    }

    @Override
    public int updateVideo(Video video,  String userID, String keyID)
    {
        int result = 0;
        if(null == video)
        {
            return MAGICCODE.ERROR;
        }
        
        if(null == userID || "".equals(userID))
        {
            return MAGICCODE.ERROR;
        }
        
        result = isYourVideo(video.getUuid(), userID, keyID);
        if(MAGICCODE.OK != result)
        {
            System.out.println("NOT YOUR VIDEO!");
            return result;
        }
        
        result = videoDBManager.updateVideo(video, keyID);
        return 0;
    }

    @Override
    public List<Video> queryByCategory(QueryTerm queryTerm,String category, String keyID)
    {
        if(null == category || "".equals(category))
        {
            return null;
        }
        if(null == queryTerm)
        {
            queryTerm = new QueryTerm();
        }
        List<Video> videoList = new ArrayList<Video>();
        
        int result = videoDBManager.queryCategory(queryTerm,videoList, category,keyID);
        if(MAGICCODE.OK != result)
        {
            
        }
        return videoList;
    }

    @Override
    public Video queryvideo(String uuid, String keyID)
    {
        if(null == uuid)
        {
            return null;
        }
        Video video = videoDBManager.queryVideo(uuid,keyID);
        return video;
    }

    @Override
    public List<Video> queryMyVideo(QueryTerm queryTerm,String userID, String keyID)
    {
        if(null == userID || "".equals(userID))
        {
            return null;
        }
        if(null == queryTerm)
        {
            queryTerm = new QueryTerm();
        }
        List<Video> videoList = new ArrayList<Video>();
        int result = videoDBManager.queryMyVideo(queryTerm,userID, videoList,keyID);
        if(MAGICCODE.OK != result)
        {
            
        }
        return videoList;
    }

    @Override
    public List<Video> queryAllVideo(QueryTerm queryTerm, String keyID)
    {
        if(null == queryTerm)
        {
            queryTerm = new QueryTerm();
        }
        
        List<Video> videoList = new ArrayList<Video>();
        int result = videoDBManager.queryAllVideo(videoList, queryTerm,keyID);
        
        if(MAGICCODE.OK != result)
        {
            
        }
        
        return videoList;
    }

    @Override
    public int updateVideoPic(String videoPic, String uuid, String userID,String keyID)
    {
        int result = MAGICCODE.OK;
        result = isYourVideo(uuid,userID,keyID);
        result = videoDBManager.updateVideoPic(videoPic, uuid, keyID);
        if(result != MAGICCODE.OK)
        {
            
        }
        
        return 0;
    }

 
}
