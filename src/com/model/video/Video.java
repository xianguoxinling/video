package com.model.video;

import java.util.UUID;

public class Video
{
    private String id = null;
    private String userId = null;
    private String name = null;
    private String describe = null;
    private String uuid = null;
    private String path = null;
    private String authority = null;
    private String time = null;
    private String category = null;
    private String pic = null;
    private String serverAdd = null;
    private long likeNum = 0;
    private long playNum = 0;
    
    public Video()
    {
        uuid = UUID.randomUUID().toString();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescribe()
    {
        return describe;
    }

    public void setDescribe(String describe)
    {
        this.describe = describe;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getAuthority()
    {
        return authority;
    }

    public void setAuthority(String authority)
    {
        this.authority = authority;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public long getLikeNum()
    {
        return likeNum;
    }

    public void setLikeNum(long likeNum)
    {
        this.likeNum = likeNum;
    }

    public long getPlayNum()
    {
        return playNum;
    }

    public void setPlayNum(long playNum)
    {
        this.playNum = playNum;
    }

    public String getPic()
    {
        return pic;
    }

    public void setPic(String pic)
    {
        this.pic = pic;
    }

    public String getServerAdd()
    {
        return serverAdd;
    }

    public void setServerAdd(String serverAdd)
    {
        this.serverAdd = serverAdd;
    }
    
    
}
