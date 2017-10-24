package com.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.model.video.Video;
import com.until.errorcode.MAGICCODE;
import com.until.queryterm.QueryTerm;

public class VideoDBManager
{
	protected DBManager dbManager = null;

	public int createVideo(Video video, String keyID)
	{
		dbManager = DBManager.getInstance();
		Connection connection = null;
		String sql = "INSERT INTO video (userId,name,uuid,path,authority,category,keyID,pic,introduce,create_time) VALUES (?,?,?,?,?,?,?,?,?,?)";

		try
		{
			connection = dbManager.getConection();
			connection.setAutoCommit(true);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, video.getUserId());
			statement.setString(2, video.getName());
			statement.setString(3, video.getUuid());
			statement.setString(4, video.getPath());
			statement.setString(5, video.getAuthority());
			statement.setString(6, video.getCategory());
			statement.setString(7, keyID);
			statement.setString(8, video.getPic());
			statement.setString(9, video.getDescribe());
	        statement.setTimestamp(10, new java.sql.Timestamp(new java.util.Date().getTime()));
			statement.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
			return MAGICCODE.DB_ERROR;
		} finally
		{
			try
			{
				if (null != connection)
				{
					connection.close();
				}

			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int dealRollback(Connection connection)
	{
		int result = 0;
		try
		{
			connection.rollback();

		} catch (SQLException e1)
		{
			result = MAGICCODE.DB_ERROR;
			e1.printStackTrace();
		}
		return result;

	}

    public int isYourVideo(String uuid, String userID,String keyID)
    {
        dbManager = DBManager.getInstance();
        Connection connection = dbManager.getConection();
        String sql = "select userId from video where uuid= ? and keyID = ?";
        try
        {
            connection.setAutoCommit(true);

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setString(2, keyID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                String id = resultSet.getString("userId");
                if(userID.equals(id))
                {
                    return MAGICCODE.OK;
                }
            }
            connection.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
            try
            {
                connection.close();
            } catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            return MAGICCODE.DB_ERROR;
        }
        
        
        return MAGICCODE.ERROR;
    }

    public int deleteVideo(String uuid, String userID, String keyID)
    {
        dbManager = DBManager.getInstance();
        Connection connection = dbManager.getConection();
        String sql = "delete from  video where uuid = ? and keyID = ?";
        String sql2 = "INSERT INTO video_delete (user_id, video_uuid,keyID,delete_time) VALUES (?,?,?,?)";
        try
        {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setString(2, keyID);
            statement.executeUpdate();
            
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, Integer.parseInt(userID));
            statement2.setString(2, uuid);
            statement2.setString(3, keyID);
            statement2.setTimestamp(4,
                    new java.sql.Timestamp(new java.util.Date().getTime()));
            statement2.executeUpdate();
            connection.commit();
            connection.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
            try
            {
                connection.close();
            } catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            return RollBackManager.dealRollback(connection);
        }
        return MAGICCODE.OK;

    }
    
    public int updateVideo(Video video, String keyID)
    {
        dbManager = DBManager.getInstance();
        Connection connection = dbManager.getConection();
        String sql = "update video set name = ? and authority = ? and category = ? and introduce = ? where keyID = ?";
        try
        {
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, video.getName());
            statement.setString(2, video.getAuthority());
            statement.setString(3, video.getCategory());
            statement.setString(4, video.getDescribe());
            statement.setString(5, keyID);
            statement.executeUpdate();
            connection.close();
            
        }catch (SQLException e)
        {
            e.printStackTrace();
            try
            {
                connection.close();
            }catch (SQLException e1)
            {
                e1.printStackTrace();
            }
        }
        
        return 0;
    }

    public int updateVideoPic(String videoPic, String uuid, String keyID)
    {
        dbManager = DBManager.getInstance();
        Connection connection = dbManager.getConection();
        String sql = "update video set pic = ? where keyID = ? and uuid = ?";
        try
        {
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, videoPic);
            statement.setString(2, keyID);
            statement.setString(3, uuid);
            statement.executeUpdate();
            connection.close();
            
        }catch (SQLException e)
        {
            e.printStackTrace();
            try
            {
                connection.close();
            }catch (SQLException e1)
            {
                e1.printStackTrace();
            }
        }
        
        return MAGICCODE.OK;
    }
    
    public int queryAllVideo(List<Video> videoList, QueryTerm queryTerm, String keyID)
    {
        dbManager = DBManager.getInstance();
        
        Connection connection = dbManager.getConection();
        String sql = "select * from video where authority = ? and keyID = ? order by create_time desc limit ? ,?";
        try
        {
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, "public");
            statement.setString(2, keyID);
            statement.setInt(3, queryTerm.queryBegin);
            statement.setInt(4, queryTerm.queryNum);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                Video video = DBUntil.getVideoFromResultSet(resultSet);
                videoList.add(video);
            }
            connection.close();
            
        }catch (SQLException e)
        {
            e.printStackTrace();
            try
            {
                connection.close();
            }catch (SQLException e1)
            {
                e1.printStackTrace();
            }
        }
        return MAGICCODE.OK;
    }

    public int queryMyVideo(QueryTerm queryTerm,String userID, List<Video> videoList, String keyID)
    {
        dbManager  = DBManager.getInstance();
        
        Connection connection = dbManager.getConection();
        String sql = "select * from video where userId = ? and keyID = ? order by create_time limit ? ,?";
        try
        {
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userID);
            statement.setString(2,keyID);
            statement.setInt(3, queryTerm.queryBegin);
            statement.setInt(4, queryTerm.queryNum);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                Video video = DBUntil.getVideoFromResultSet(resultSet);
                videoList.add(video);
            }
            
            connection.close();
            
        }catch(SQLException e)
        {
            e.printStackTrace();
            try
            {
                connection.close();
            }catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            return MAGICCODE.DB_ERROR;
        }
        return MAGICCODE.OK;
    }

    public Video queryVideo(String uuid, String keyID)
    {
        dbManager = DBManager.getInstance();
        Connection connection = dbManager.getConection();
        String sql = "select * from video where uuid = ? and keyID = ?";
        Video video = null;
        try
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setString(2, keyID);
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next())
            {
                video = DBUntil.getVideoFromResultSet(resultSet);
            }
            connection.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
            try
            {
                connection.close();
            } catch (SQLException e1)
            {
                e1.printStackTrace();
            }
        }
        return video;
    }

    public int queryCategory(QueryTerm queryTerm,List<Video> videoList, String category, String keyID)
    {
        dbManager = DBManager.getInstance();
        
        Connection connection = dbManager.getConection();
        String sql = "select * from video where category = ? and keyID = ? order by create_time limit ? ,?";
        try
        {
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, category);
            statement.setString(2, keyID);
            statement.setInt(3, queryTerm.queryBegin);
            statement.setInt(4, queryTerm.queryNum);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                Video video = DBUntil.getVideoFromResultSet(resultSet);
                videoList.add(video);
            }
            
            connection.close();
            
        }catch(SQLException e)
        {
            e.printStackTrace();
            try
            {
                connection.close();
            }catch (SQLException e1)
            {
                e1.printStackTrace();
            }
            return MAGICCODE.DB_ERROR;
        }
        return MAGICCODE.OK;
    }


}
