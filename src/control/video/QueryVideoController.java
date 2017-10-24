package control.video;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.video.Video;
import com.platform.dispatch.RtmpServerDispatch;
import com.service.interfaces.VideoServiceInterface;
import com.service.video.VideoService;
import com.until.errorcode.MAGICCODE;
import com.until.replace.ReplaceSrvToHttp;
import com.until.replace.ReplaceSrvToStmp;

public class QueryVideoController implements Controller 
{
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        
        Map<String, String> map = new HashMap<String, String>();
        JSONObject json = null;
        OutputStream stream = response.getOutputStream();
        
        String keyID = request.getParameter("keyID");
        if(null == keyID)
        {
            map.put("code", MAGICCODE.MAGIC_KEY_NULL);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
        }
        String uuid = request.getParameter("uuid");
        VideoServiceInterface videoService = new VideoService();
        
        Video video = videoService.queryvideo(uuid, keyID);
        if(null == video)
        {

            map.put("code", MAGICCODE.MAGIC_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
        } else
        {
            video.setPic(ReplaceSrvToHttp.replace(video.getPic()));
            RtmpServerDispatch dis = RtmpServerDispatch.getInstance();
            video.setServerAdd(dis.getRtmpServer());
            video.setPath(ReplaceSrvToHttp.replace(video.getPath()));
//            map.put("code", MAGICCODE.MAGIC_OK);
            json = JSONObject.fromObject(video);
            stream.write(json.toString().getBytes("UTF-8"));
        }

        return null;
    }

}
