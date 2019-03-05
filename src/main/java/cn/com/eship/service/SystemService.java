package cn.com.eship.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by guoji on 2016/12/5 0005.
 */
@Service
public interface SystemService {
    Map<String,Object> checkUserIdentity(String userID, String passWd);
    String findUserList() throws IOException;
    String updateUserInfo(String id, String userName,String passWd,String authority,String email,String department) throws IOException;
    String deleteUser(String id)throws IOException;
}
