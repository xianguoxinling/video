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
import com.platform.dispatch.RtmpServerDispatch;
import com.service.interfaces.VideoServiceInterface;
import com.service.video.VideoService;
import com.until.errorcode.MAGICCODE;
import com.until.queryterm.QueryTerm;
import com.until.replace.ReplaceSrvToHttp;

public class QueryAllVideoController implements Controller 
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        
        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        JSONArray videoJsonList = null;
        OutputStream stream = response.getOutputStream();
        String begin = request.getParameter("begin");
        String num = request.getParameter("num");

        QueryTerm queryTerm = new QueryTerm();
        if (null != begin)
        {
            queryTerm.queryNum = Integer.parseInt(num);
            queryTerm.queryBegin = (Integer.parseInt(begin) - 1) * queryTerm.queryNum;
        }
        
        String keyID = request.getParameter("keyID");
        if(null == keyID)
        {
            map.put("code", MAGICCODE.MAGIC_KEY_NULL);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
        }
        VideoServiceInterface videoService = new VideoService();
        
        List<Video> videoList = videoService.queryAllVideo(queryTerm,keyID);
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

