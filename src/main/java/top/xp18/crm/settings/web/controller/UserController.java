package top.xp18.crm.settings.web.controller;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import top.xp18.crm.exception.LoginException;
import top.xp18.crm.settings.domain.User;
import top.xp18.crm.settings.service.UserService;
import top.xp18.crm.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.xp18.crm.utils.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String, Object> login (String loginAct, String loginPwd, HttpServletRequest request) throws LoginException {
        loginPwd= MD5Util.getMD5(loginPwd);
        String ip =request.getRemoteAddr();
        Map<String ,Object> map=new HashMap<>();
            User user=userService.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            map.put("success",true);
        return  map;
    }
    @RequestMapping("/add.do")
    @ResponseBody
    public Map<String, Object> addUser(User user,HttpServletRequest request) throws LoginException {
        if (user.getLoginAct()==null||"".equals(user.getLoginAct())){
            throw new LoginException("用户名不能为空");
        }
        if(user.getLoginPwd()==null||"".equals(user.getLoginPwd())){
            throw new LoginException("密码不能为空");
        }
        String loginPwd=user.getLoginPwd();
        String dataNow=DateUtil.now();
        Date date=DateUtil.parse(dataNow);
        Date endDate=DateUtil.endOfDay(date);
        user.setLoginPwd(MD5Util.getMD5(loginPwd));
        user.setCreateTime(dataNow);
        user.setExpireTime(DateUtil.formatDateTime(endDate));
        user.setId(UUIDUtil.getUUID());
        user.setLockState("1");
        user.setAllowIps(request.getRemoteAddr());
        return userService.addUser(user);
    }

}
