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

public class CreateVideoController implements Controller 
{

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, 
            HttpServletResponse response) throws Exception
    {
        
        String token = UserCookieManager.getUserID(request, response);
//        String keyID = "nhatrgog4yqq4b";
        

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
        if(null == authority || "".equals(authority))
        {
            authority = "public";
        }
        
        String name = request.getParameter("name");
        String introduce = request.getParameter("introduce");
        String category = request.getParameter("category");
        Video video = new Video();
        video.setName(name);
        video.setCategory(category);
        video.setDescribe(introduce);
        video.setUserId(userID);
        video.setAuthority(authority);
        
        //解释一下 下面的？
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        if (null == multipartFile)
        {
//            return new ModelAndView("redirect:" + BaseString.baseURL + "XXXXXXXXXX");
            System.out.println("FILE NULL!");
            
            map.put("code", MAGICCODE.MAGIC_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        
        String originalFileName = multipartFile.getOriginalFilename();
        if(null == originalFileName || "".equals(originalFileName))
        {
            System.out.println("FILE NAME NULL!");
            map.put("code", MAGICCODE.MAGIC_ERROR);
            json = JSONObject.fromObject(map);
            stream.write(json.toString().getBytes("UTF-8"));
            return null;
        }
        
        System.out.println("FILE NAME:"+originalFileName);
        String originalArray[] = originalFileName.split("\\.");
//        System.out.println(originalArray.length);
//        System.out.println(originalArray[0]);
//        System.out.println(originalArray[1]);
        String videoType = originalArray[originalArray.length - 1];
        System.out.println("FILE TYPE:"+videoType);
        String filePath = StorageManager.getStorage();
        filePath += "video/";
        filePath += keyID;
        filePath += "/";
        filePath += video.getUuid();
        filePath += "/";
        
        File folder = new File(filePath);
        if (!folder.exists())
        {
            folder.mkdirs();
        }
        
        String pic = filePath + "image.jpeg";
        String attacheFile = filePath + "video";
        attacheFile += ".";
        attacheFile += videoType;
        
        File source = new File(attacheFile);
        multipartFile.transferTo(source);
        
        video.setPath(attacheFile);
        video.setPic(pic);
        VideoServiceInterface videoService = new VideoService();
        int result = videoService.createVideo(video, keyID);
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
