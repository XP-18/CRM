package top.xp18.crm.settings.service;


import top.xp18.crm.exception.LoginException;
import top.xp18.crm.settings.domain.User;

import java.util.List;

public interface UserService  {
    User login(String loginAct, String loginPwd,String ip)throws LoginException;

    List<User> getUserList();
}
