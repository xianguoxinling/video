package control.video;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model.video.Video;
import com.platform.auth.TokenManager;
import com.platform.base.UserCookieManager;
import com.platform.storage.StorageManager;
import com.service.interfaces.VideoServiceInterface;
import com.service.video.VideoPicServiceThread;
import com.service.video.VideoService;
import com.until.errorcode.MAGICCODE;

public class UpdateVideoController implements Controller 
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
    {
        
        String token = UserCookieManager.getUserID(request, response);
        
        String userID = null;
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
        if(null == token)
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
        
        TokenManager authManager = new TokenManager();
        String jsonResult = authManager.queryUser(token, keyID);
        JSONObject jsonObject = JSONObject.fromObject(jsonResult);
        String code = (String) jsonObject.get("code");
        if(!MAGICCODE.MAGIC_OK.equals(code))
        {
//            System.out.println("AUTH ERROR!");
            map.put("code", code);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        userID = (String) jsonObject.get("userID");
        
        String authority = request.getParameter("authority");
//        System.out.println("authority:" + authority);
        if(null == authority || "".equals(authority))
        {
            authority = "public";
        }
        
        String name = request.getParameter("name");
        String introduce = request.getParameter("introduce");
        String category = request.getParameter("category");
        String uuid = request.getParameter("uuid");
        Video video = new Video();
        video.setUuid(uuid);
        video.setName(name);
        video.setCategory(category);
        video.setDescribe(introduce);
        
        //解释一下 下面的？
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        if (null != multipartFile)
        {
            String originalFileName = multipartFile.getOriginalFilename();
            if(null == originalFileName || "".equals(originalFileName))
            {
                map.put("code", MAGICCODE.MAGIC_ERROR);
                json = JSONObject.fromObject(map);
                stream.write(json.toString().getBytes("UTF-8"));
                return null;
            }
            String filePath = StorageManager.getStorage();
            filePath += "/";
            filePath += keyID;
            filePath += "_";
            filePath += video.getUuid();
            filePath += "/";
            
            File folder = new File(filePath);
            if (!folder.exists())
            {
                folder.mkdirs();
            }
            
            String pic = filePath + "image.jpeg";
            
            File source = new File(pic);
            multipartFile.transferTo(source);
            video.setPic(pic);
        }
        
        VideoServiceInterface videoService = new VideoService();
        int result = videoService.updateVideo(video, userID, keyID);
        if(MAGICCODE.OK != result)
        {
            map.put("code", MAGICCODE.MAGIC_ERROR);
        }
        else
        {
            map.put("code", MAGICCODE.MAGIC_OK);
        }
        
        VideoPicServiceThread videoThread = new VideoPicServiceThread(video);
        Thread thread = new Thread(videoThread);
        thread.start();
        
        json = JSONObject.fromObject(map);
        stream.write(json.toString().getBytes("UTF-8"));
        
        return null;
    }

}

