package control.video;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.video.Video;
import com.platform.auth.TokenManager;
import com.platform.base.UserCookieManager;
import com.platform.dispatch.RtmpServerDispatch;
import com.service.interfaces.VideoServiceInterface;
import com.service.video.VideoService;
import com.until.errorcode.MAGICCODE;
import com.until.queryterm.QueryTerm;
import com.until.replace.ReplaceSrvToHttp;

public class QueryMyVideoController implements Controller 
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        
        String token = UserCookieManager.getUserID(request, response);
        String userID = null;
        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        JSONArray videoJsonList = null;
        OutputStream stream = response.getOutputStream();
        if(null == token)
        {
            map.put("code", MAGICCODE.MAGIC_NOT_LOGIN);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        String keyID = request.getParameter("keyID");
        if(null == keyID)
        {
            map.put("code", MAGICCODE.MAGIC_KEY_NULL);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
        }
        TokenManager authManager = new TokenManager();
        String jsonResult = authManager.queryUser(token, keyID);
        JSONObject jsonObject = JSONObject.fromObject(jsonResult);
        String code = (String) jsonObject.get("code");
        if(!MAGICCODE.MAGIC_OK.equals(code))
        {
            token = request.getParameter("token");
            if(null == token)
            {
                map.put("code", MAGICCODE.MAGIC_NOT_LOGIN);
                json = JSONObject.fromObject(map);
                stream.write(json.toString().getBytes("UTF-8"));
                return null;
            }
        }
        userID = (String) jsonObject.get("userID");
        
        String begin = request.getParameter("begin");
        String num = request.getParameter("num");

        QueryTerm queryTerm = new QueryTerm();
        if (null != begin)
        {
            queryTerm.queryNum = Integer.parseInt(num);
            queryTerm.queryBegin = (Integer.parseInt(begin) - 1) * queryTerm.queryNum;
        }
        VideoServiceInterface videoService = new VideoService();
        
        List<Video> videoList = videoService.queryMyVideo(queryTerm,userID,keyID);
        if(null == videoList)
        {
            map.put("code", MAGICCODE.MAGIC_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
        } else
        {
            Iterator<Video> it = videoList.iterator();
            while(it.hasNext())
                
            {
                Video video = it.next();
                video.setPic(ReplaceSrvToHttp.replace(video.getPic()));
                RtmpServerDispatch dis = RtmpServerDispatch.getInstance();
                video.setServerAdd(dis.getRtmpServer());
                video.setPath(ReplaceSrvToHttp.replace(video.getPath()));
            }
            
            videoJsonList = JSONArray.fromObject(videoList);
            stream.write(videoJsonList.toString().getBytes("UTF-8"));
        }
        
//        stream.write(json.toString().getBytes("UTF-8"));
        return null;
    }

}

