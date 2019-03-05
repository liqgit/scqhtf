package cn.com.eship.controller;

import cn.com.eship.service.SystemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("userManager")
public class UserManageController {
    @Autowired
    private SystemService systemService;

    @RequestMapping("toUserManagePage")
    public String loginPage() {
        return "usersManage";
    }

    @RequestMapping("findUserList")
    public void findUserList(HttpServletRequest req,HttpServletResponse response) throws IOException {
        HttpSession session = req.getSession();
        String authority = (String) session.getAttribute("authority");
        if(StringUtils.isNotEmpty(authority)&&authority.equals("all")){
            String str = systemService.findUserList();
            if (StringUtils.isNotEmpty(str)){
                response.getOutputStream().write(str.getBytes("utf-8"));
            }
        }

    }

    @RequestMapping("updateUserInfo")
    public void updateUserInfo(String id,String userName,String passWd,String authority,
                               String email,String department,HttpServletRequest req,HttpServletResponse response) throws IOException {
        HttpSession session = req.getSession();
        String sa = (String) session.getAttribute("authority");
        if(StringUtils.isNotEmpty(sa)&&sa.equals("all")){
            String str = systemService.updateUserInfo(id,userName,passWd,authority,email,department);
            if (StringUtils.isNotEmpty(str)){
                response.getOutputStream().write(str.getBytes("utf-8"));
            }
        }
    }

    @RequestMapping("deleteUser")
    public void deleteUser(String id,HttpServletRequest req,HttpServletResponse response) throws IOException {
        HttpSession session = req.getSession();
        String authority = (String) session.getAttribute("authority");
        if(StringUtils.isNotEmpty(authority)&&authority.equals("all")){
            String str = systemService.deleteUser(id);
            if (StringUtils.isNotEmpty(str)){
                response.getOutputStream().write(str.getBytes("utf-8"));
            }
        }

    }

}
