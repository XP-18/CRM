package top.xp18.crm.workbench.web.controller;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.xp18.crm.settings.domain.User;
import top.xp18.crm.settings.service.UserService;
import top.xp18.crm.utils.DateTimeUtil;
import top.xp18.crm.utils.UUIDUtil;
import top.xp18.crm.workbench.domain.Activity;
import top.xp18.crm.workbench.domain.Clue;
import top.xp18.crm.workbench.domain.Tran;
import top.xp18.crm.workbench.service.ActivityService;
import top.xp18.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clue")
public class ClueController {
    @Autowired
    private UserService us;
    @Autowired
    private ClueService cs;
    @Autowired
    private ActivityService as;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    private List<User> getUserList() {
        List<User> uList = us.getUserList();
        return uList;
    }

    @RequestMapping("/save.do")
    @ResponseBody
    private boolean save(Clue clue, HttpServletRequest request) {
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        clue.setId(id);
        clue.setCreateTime(createTime);
        clue.setCreateBy(createBy);
        boolean flag = cs.save(clue);
        return flag;
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    private ModelAndView detail(String id) {
        ModelAndView mv = new ModelAndView();
        Clue c = cs.detail(id);
        mv.addObject("c", c);
        mv.setViewName("/clue/detail");
        return mv;
    }

    @RequestMapping("/getActivityListByClueId.do")
    @ResponseBody
    private List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> aList = as.getActivityListByClueId(clueId);
        return aList;
    }

    @RequestMapping("/unbund.do")
    @ResponseBody
    private boolean unbund(String id) {
        boolean flag = cs.unbund(id);
        return flag;
    }

    @RequestMapping("/getActivityListByNameAndNotByClueId.do")
    @ResponseBody
    private List<Activity> getActivityListByNameAndNotByClueId(String aname, String clueId) {
        Map<String, Object> map = new HashMap<>();
        map.put("aname", aname);
        map.put("clueId", clueId);
        List<Activity> aList = as.getActivityListByNameAndNotByClueId(map);
        return aList;

    }

    @RequestMapping("/bund.do")
    @ResponseBody
    private boolean bund(String aid[], String cid) {
        boolean flag = cs.bund(aid, cid);
        return flag;
    }


    @RequestMapping("/getActivityListByName.do")
    @ResponseBody
    private List<Activity> getActivityListByName(String aname) {
        List<Activity> aList = as.getActivityListByName(aname);
        return aList;
    }


    @RequestMapping("/convert.do")
    private ModelAndView convert(String clueId, String flag, HttpServletRequest request) {
        Tran t = null;
        ModelAndView mv = new ModelAndView();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        if ("a".equals(flag)) {
//            ??????????????????????????????
            t = new Tran();
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();
            t.setId(id);
            t.setCreateTime(createTime);
            t.setCreateBy(createBy);
            t.setMoney(money);
            t.setName(name);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setActivityId(activityId);
        }
        /*????????????????????????
         * 1.?????????????????????clueId???????????????????????????????????????????????????
         * 2.??????????????????t??????????????????????????????????????????????????????????????????????????????????????????t???????????????null???*/
        boolean flag1 = cs.convert(clueId, t, createBy);
        if (flag1) {
            mv.setViewName("redirect:/workbench/clue/index.jsp");
        }
        mv.addObject(flag1);
        return mv;
    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    public Map<String, Object> pageForClue(@RequestParam Map map) {
        int pageNo = Integer.parseInt((String) map.get("pageNo"));
        int pageSize =  Integer.parseInt((String) map.get("pageSize"));
//        ????????????????????????
        int skipCount = (pageNo - 1) * pageSize;
        map.put("skipCount",skipCount);
        map.replace("pageSize",pageSize);

        return cs.queryForPage(map);
    }
    @RequestMapping(value = "/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteClue(@RequestParam("idList[]") List<String> idList){
        return cs.deleteClu(idList);
    }

}
