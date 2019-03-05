package cn.com.eship.service.impl;

import cn.com.eship.dao.UserDao;
import cn.com.eship.service.SystemService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SystemServiceImpl implements SystemService {
    private final UserDao userDao;

    @Autowired
    public SystemServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Map<String, Object> checkUserIdentity(String userID, String passWd) {
        return userDao.checkUserIdentity(userID,passWd);
    }

    @Override
    public String findUserList() throws IOException {
        List<Map<String,Object>> list = userDao.findUserList();
        if (list!=null&&list.size()>0){
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(list);
        }else {
            return "";
        }
    }

    @Override
    public String updateUserInfo(String id, String userName,String passWd,String authority,String email,String department) throws IOException {
        Map<String,Object> map = userDao.updateUserInfo(id,userName,passWd,authority,email,department);
        if (map!=null&&map.size()>0){
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(map);
        }else {
            return "";
        }
    }

    @Override
    public String deleteUser(String id) throws IOException {
        Map<String,Object> map = userDao.deleteUser(id);
        if (map!=null&&map.size()>0){
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(map);
        }else {
            return "";
        }
    }
}
