package com.db.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.video.Video;

public class DBUntil
{
	public static Video getVideoFromResultSet(ResultSet resultSet)
	{
	    Video video = new Video();
		try
		{
		    video.setId(resultSet.getString("id"));
		    video.setName(resultSet.getString("name"));
		    video.setAuthority(resultSet.getString("authority"));
		    video.setCategory(resultSet.getString("category"));
		    video.setDescribe(resultSet.getString("introduce"));
		    video.setLikeNum(resultSet.getLong("like_num"));
		    video.setPlayNum(resultSet.getLong("play_num"));
		    video.setUuid(resultSet.getString("uuid"));
		    video.setPath(resultSet.getString("path"));
		    video.setUserId(resultSet.getString("userId"));
		    video.setPic(resultSet.getString("pic"));
		    video.setTime(resultSet.getString("create_time"));
	
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return video;
	}
	
}
