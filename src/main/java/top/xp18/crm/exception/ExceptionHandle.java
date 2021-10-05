package top.xp18.crm.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public Map<String, Object> loginHandle(LoginException e){
        Map map=new HashMap();
        String msg=e.getMessage();
        map.put("success",false);
        map.put("msg",msg);
        return map;
    }
}
