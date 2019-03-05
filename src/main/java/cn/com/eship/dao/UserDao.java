package cn.com.eship.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by simon on 16/7/14.
 */
public interface UserDao {
    Map<String,Object> checkUserIdentity(String userID, String passWd);
    List<Map<String,Object>> findUserList();
    Map<String,Object> updateUserInfo(String id, String userName,String passWd,String authority,String email,String department);
    Map<String,Object> deleteUser(String id);

}
