package com.test.video;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.model.video.Video;
import com.service.interfaces.VideoServiceInterface;
import com.service.video.VideoService;
import com.until.errorcode.MAGICCODE;
import com.until.random.RandomString;

public class VideoServiceTest
{

    public VideoServiceInterface service = null;
    public String keyID;
    @Before
    public void setUp() throws Exception
    {
        service = new VideoService();
        keyID = UUID.randomUUID().toString();
    }

    @Test
    public void testCreateVideo()
    {
        
        Video video = new Video();
        video.setName("magiczz");
        video.setAuthority("public");
        video.setCategory("magiczz");
        video.setDescribe("This is just a test");
        video.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video.setUserId("1");
        int result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
    }

    @Test
    public void testDeleteVideo()
    {
        Video video = new Video();
        video.setName("magiczz");
        video.setAuthority("public");
        video.setCategory("magiczz");
        video.setDescribe("This is just a test");
        video.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video.setUserId("1");
        String uuid = UUID.randomUUID().toString();
        video.setUuid(uuid);
        int result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Video newVideo = service.queryvideo(uuid,keyID);
        assertEquals(newVideo.getName(),"magiczz");
        assertEquals("public",newVideo.getAuthority());
        assertEquals("magiczz",newVideo.getCategory());
        assertEquals("This is just a test",video.getDescribe());
        assertEquals("1",video.getUserId());
        assertEquals(uuid,video.getUuid());
        assertEquals("/srv/www/htdoc/video/2017/video.mp4",video.getPath());
        assertEquals(0,video.getLikeNum());
        assertEquals(0,video.getPlayNum());
        
        result = service.deleteVideo(uuid, "2",keyID);
        assertNotEquals(MAGICCODE.OK,result);
        result = service.deleteVideo(uuid, "1","111");
        assertNotEquals(MAGICCODE.OK,result);
        Video delFailedVideo = service.queryvideo(uuid, keyID);
        assertNotEquals(null,delFailedVideo);
        
        result = service.deleteVideo(uuid, "1",keyID);
        assertEquals(MAGICCODE.OK,result);
        Video delVideo = service.queryvideo(uuid, keyID);
        assertEquals(null,delVideo);
        
    }

    @Test
    public void testUpdateVideo()
    {
    }

    @Test
    public void testQueryCategory()
    {
        String userID = RandomString.getRandomString(10);
        String catagory = RandomString.getRandomString(10);
        Video video = new Video();
        video.setName("magiczz");
        video.setAuthority("public");
        video.setCategory(catagory);
        video.setDescribe("This is just a test");
        video.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video.setUserId(userID);
        int result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        List<Video> list1 = service.queryMyVideo(null,userID, keyID);
        assertEquals(1,list1.size());
        
        Video video2 = new Video();
        video2.setName("magiczz");
        video2.setAuthority("public");
        video2.setCategory(catagory);
        video2.setDescribe("This is just a test");
        video2.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video2.setUserId(userID);
        result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        List<Video> list2 = service.queryMyVideo(null,userID, keyID);
        assertEquals(2,list2.size());
        
        Video video3 = new Video();
        video3.setName("magiczz");
        video3.setAuthority("public");
        video3.setCategory(catagory);
        video3.setDescribe("This is just a test");
        video3.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video3.setUserId(userID);
        result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        List<Video> list3 = service.queryMyVideo(null,userID, keyID);
        assertEquals(3,list3.size());
    }

    @Test
    public void testQueryvideo()
    {
        Video video = new Video();
        video.setName("magiczz");
        video.setAuthority("public");
        video.setCategory("magiczz");
        video.setDescribe("This is just a test");
        video.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video.setUserId("1");
        String uuid = UUID.randomUUID().toString();
        video.setUuid(uuid);
        int result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Video newVideo = service.queryvideo(uuid,keyID);
        assertEquals(newVideo.getName(),"magiczz");
        assertEquals("public",newVideo.getAuthority());
        assertEquals("magiczz",newVideo.getCategory());
        assertEquals("This is just a test",video.getDescribe());
        assertEquals("1",video.getUserId());
        assertEquals(uuid,video.getUuid());
        assertEquals("/srv/www/htdoc/video/2017/video.mp4",video.getPath());
        assertEquals(0,video.getLikeNum());
        assertEquals(0,video.getPlayNum());
    }

    @Test
    public void testQueryMyVideo()
    {
        String userID = RandomString.getRandomString(10);
        Video video = new Video();
        video.setName("magiczz");
        video.setAuthority("public");
        video.setCategory("magiczz");
        video.setDescribe("This is just a test");
        video.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video.setUserId(userID);
        int result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        List<Video> list1 = service.queryMyVideo(null,userID, keyID);
        assertEquals(1,list1.size());
        
        Video video2 = new Video();
        video2.setName("magiczz");
        video2.setAuthority("public");
        video2.setCategory("magiczz");
        video2.setDescribe("This is just a test");
        video2.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video2.setUserId(userID);
        result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        List<Video> list2 = service.queryMyVideo(null,userID, keyID);
        assertEquals(2,list2.size());
        
        Video video3 = new Video();
        video3.setName("magiczz");
        video3.setAuthority("public");
        video3.setCategory("magiczz");
        video3.setDescribe("This is just a test");
        video3.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video3.setUserId(userID);
        result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        List<Video> list3 = service.queryMyVideo(null,userID, keyID);
        assertEquals(3,list3.size());
    }

    @Test
    public void testQueryAllVideo()
    {
        
        String userID = RandomString.getRandomString(10);
        Video video = new Video();
        video.setName("magiczz");
        video.setAuthority("public");
        video.setCategory("magiczz");
        video.setDescribe("This is just a test");
        video.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video.setUserId(userID);
        int result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Video video2 = new Video();
        video2.setName("magiczz");
        video2.setAuthority("public");
        video2.setCategory("magiczz");
        video2.setDescribe("This is just a test");
        video2.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video2.setUserId(userID);
        result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Video video3 = new Video();
        video3.setName("magiczz");
        video3.setAuthority("public");
        video3.setCategory("magiczz");
        video3.setDescribe("This is just a test");
        video3.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video3.setUserId(userID);
        result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        List<Video> list3 = service.queryAllVideo(null,keyID);
        assertNotEquals(0,list3.size());
    }
    
    @Test
    public void testUploadVideoPic()
    {
        String userID = RandomString.getRandomString(10);
        Video video = new Video();
        video.setName("magiczz");
        video.setAuthority("public");
        video.setCategory("magiczz");
        video.setDescribe("This is just a test");
        video.setPath("/srv/www/htdoc/video/2017/video.mp4");
        video.setUserId(userID);
        int result = service.createVideo(video,keyID);
        assertEquals(MAGICCODE.OK,result);
        
        result = service.updateVideoPic("/srv/www/htdocs/video/pic/pic.png", video.getUuid(), userID,keyID);
        assertEquals(MAGICCODE.OK,result);
        
        Video newVideo = service.queryvideo(video.getUuid(), keyID);
        assertEquals("/srv/www/htdocs/video/pic/pic.png",newVideo.getPic());
        
    }

}
