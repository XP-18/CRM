package top.xp18.crm.workbench.web.controller;

import top.xp18.crm.settings.domain.User;
import top.xp18.crm.settings.service.UserService;
import top.xp18.crm.utils.DateTimeUtil;
import top.xp18.crm.utils.UUIDUtil;
import top.xp18.crm.vo.PaginationVo;
import top.xp18.crm.workbench.domain.Activity;
import top.xp18.crm.workbench.domain.ActivityRemark;
import top.xp18.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityService as;
    @Autowired
    private UserService us;


    @RequestMapping("/getUserList.do")
    @ResponseBody
private List<User> getUserList(){
    List<User> user=us.getUserList();
    return user;
    }
    @RequestMapping("/save.do")
    @ResponseBody
private boolean save(Activity at, HttpServletRequest req){
        String id = UUIDUtil.getUUID();
//        创建时间：当前系统时间
        String createTime= DateTimeUtil.getSysTime();
//    创建人，当前登录用户
        String createBy= ((User) req.getSession().getAttribute("user")).getName();
        at.setId(id);
        at.setCreateTime(createTime);
        at.setCreateBy(createBy);
        boolean flag=as.save(at);
        return flag;
}
    @RequestMapping("/pageList.do")
    @ResponseBody
    private PaginationVo<Activity> pageList(String name, String owner, String startDate,
                                            String endDate, @RequestParam("pageNo") String pageNoStr,
                                            @RequestParam("pageSize") String pageSizeStr){
        int pageNo=Integer.valueOf(pageNoStr);
        int pageSize=Integer.valueOf(pageSizeStr);
//        计算出略过的数量
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        PaginationVo<Activity> vo=as.pageList(map);

       return vo;
    }

    @RequestMapping("/delete.do")
    @ResponseBody
private boolean delete(@RequestParam("id") String ids[]){
        boolean flag=as.delete(ids);
        return flag;
    }


    @RequestMapping("/getUserListAndActivity.do")
    @ResponseBody
    private Map<String, Object> getUserListAndActivity(String id){
        Map<String,Object> map=as.getUserListAndActivity(id);
        return map;
    }
    @RequestMapping("/update.do")
    @ResponseBody
    private boolean update(Activity at,HttpServletRequest request){
        //       修改时间：当前系统时间
        String editTime= DateTimeUtil.getSysTime();
//        修改人：当前登录的账户
        String editBy=((User) request.getSession().getAttribute("user")).getName();
        at.setCreateTime(editTime);
        at.setCreateBy(editBy);
        boolean flag=as.update(at);
        return flag;
    }
    @RequestMapping("/detail.do")
    @ResponseBody
    private ModelAndView detail(String id){
        ModelAndView mv=new ModelAndView();
        Activity a=as.detail(id);
        mv.addObject("a",a);
        mv.setViewName("/activity/detail");
        return mv;
    }

    @RequestMapping("/getRemarkListByAid.do")
    @ResponseBody
    private List<ActivityRemark> getRemarkListByAid(@RequestParam("activityId") String id){
        List<ActivityRemark> arList=as.getRemarkListByAid(id);
        return arList;
    }

    @RequestMapping("/deleteRemark.do")
    @ResponseBody
    private Boolean deleteRemark(String id){
        boolean flag=as.deleteRemark(id);
        return flag;
    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    private Map<String ,Object> saveRemark(ActivityRemark ar,HttpServletRequest request){
        String id =UUIDUtil.getUUID();
        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="0";
        ar.setId(id);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setEditFlag(editFlag);
        boolean flag=as.saveRemark(ar);
        Map<String ,Object> map=new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);
        return map;
    }
    @RequestMapping("/updateRemark.do")
    @ResponseBody
    private Map<String ,Object> updateRemark(ActivityRemark ar,HttpServletRequest request){
        String editTime= DateTimeUtil.getSysTime();
        String editBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="1";
        ar.setEditFlag(editFlag);
        ar.setEditTime(editTime);
        ar.setEditBy(editBy);
        boolean flag=as.updateRemark(ar);
        Map<String ,Object> map=new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);
        return map;
    }
}
