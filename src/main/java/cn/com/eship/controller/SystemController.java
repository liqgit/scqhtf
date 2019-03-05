package cn.com.eship.controller;

import cn.com.eship.service.SystemService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by simon on 16/7/14.
 */
@Controller
@RequestMapping("system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping("login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("systemMenu")
    public String systemMenuPage() {
        return "systemMenu";
    }

    @RequestMapping("loginSubmit")
    public void loginAction(String userId, String passWd, HttpServletRequest req, HttpServletResponse response) throws Exception{
        Map<String,Object> map = systemService.checkUserIdentity(userId,passWd);
        if(map.get("result")!=null&&(boolean)map.get("result")){
            HttpSession session = req.getSession();
            String sessionId = session.getId();
            session.setAttribute("authority",map.get("authority"));
            Cookie c1 = new Cookie("userSID",sessionId) ;
            c1.setMaxAge(3600);
            c1.setPath("/");
            response.addCookie(c1);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        response.getOutputStream().write(objectMapper.writeValueAsString(map).getBytes("utf-8"));
    }

    @RequestMapping("signOut")
    public void loginAction(HttpServletRequest req, HttpServletResponse response) throws Exception{
        HttpSession session = req.getSession();
        session.setAttribute("authority","");
        Cookie c1 = new Cookie("userSID","") ;
        c1.setMaxAge(3600);
        c1.setPath("/");
        response.addCookie(c1);
    }

}
