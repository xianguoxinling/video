package com.service.interfaces;

import java.util.List;

import com.model.video.Video;
import com.until.queryterm.QueryTerm;

public interface VideoServiceInterface
{
    public int createVideo(Video video, String keyID);
    
    public int deleteVideo(String uuid, String userID, String keyID);
    
    public int updateVideo(Video video,  String userID, String keyID);
    
    public List<Video> queryByCategory(QueryTerm queryTerm,String category, String keyID);
    
    public Video queryvideo(String uuid, String keyID);

    public List<Video> queryMyVideo(QueryTerm queryTerm,String userID, String keyID);
    
    public List<Video> queryAllVideo(QueryTerm queryTerm, String keyID);
    
    public int updateVideoPic(String videoPic, String uuid, String userID,String keyID);

}
