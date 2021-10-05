package top.xp18.crm.workbench.web.controller;

import org.springframework.web.bind.annotation.RequestParam;
import top.xp18.crm.settings.domain.User;
import top.xp18.crm.settings.service.UserService;
import top.xp18.crm.utils.DateTimeUtil;
import top.xp18.crm.utils.UUIDUtil;
import top.xp18.crm.workbench.domain.Tran;
import top.xp18.crm.workbench.domain.TranHistory;
import top.xp18.crm.workbench.service.CustomerService;
import top.xp18.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/transaction")
public class TranController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TranService ts;

    @RequestMapping("/add.do")
    @ResponseBody
    private ModelAndView add(){
        System.out.println("进入到跳转交易添加页的操作");
        ModelAndView mv=new ModelAndView();
        List<User> uList=userService.getUserList();
        mv.addObject("uList",uList);
        mv.setViewName("/transaction/save");
        return mv;
    }

    @RequestMapping("/getCustomerName.do")
    @ResponseBody
    private List<String> getCustomerName(String name){
        System.out.println("根据客户名称 取得列表（模糊查询）");
        List<String> sList = customerService.getCustomerName(name);
        return sList;

    }

    @RequestMapping("/save.do")
    private String save(Tran t, HttpServletRequest request){
        System.out.println("执行添加交易的操作");
        t.setId(UUIDUtil.getUUID());
        String customerName = request.getParameter("customerName");  //此处我们暂时只有客户的名称，还没有id
        String createTime = DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        t.setCreateBy(createBy);
        t.setCreateTime(createTime);

        boolean flag = ts.save(t,customerName);

        if(flag){
//            如果添加交易成功，跳转到列表页

            return "redirect:/workbench/transaction/index.jsp";
        }
        return "redirect:/workbench/transaction/save.jsp";

    }

    @RequestMapping("/detail.do")
    private ModelAndView detail(String id,HttpServletRequest request){

        System.out.println("跳转到相信信息页");

        Tran t = ts.detail(id);

//        处理可能性 阶段----可能性之间的对应关系  取pMap
        String stage = t.getStage();

        ServletContext application = request.getSession().getServletContext();

        Map<String ,String> pMap = (Map<String, String>) application.getAttribute("pMap");

        String possilibity = pMap.get(stage);
        t.setPossibility(possilibity);

        ModelAndView mv = new ModelAndView();

        mv.addObject("t",t);

        mv.setViewName("/transaction/detail");

        return mv;
     }

     @RequestMapping("/getHistoryListByTranId.do")
    @ResponseBody
    private List<TranHistory> getHistoryListByTranId(String TranId, HttpServletRequest request){

         System.out.println("根据交易id取得相应的历史记录");

         List<TranHistory> thList = ts.getHistoryListByTranId(TranId);

         ServletContext application = request.getSession().getServletContext();

         Map<String ,String> pMap = (Map<String, String>) application.getAttribute("pMap");

//         将交易列表遍历 取得阶段
         for(TranHistory th : thList){
             String stage = th.getStage();
             String possibility = pMap.get(stage);
             th.setPossibility(possibility);
         }

        return thList;
     }

     @RequestMapping("/changeStage.do")
    @ResponseBody
    private Map changeStage(HttpServletRequest request,Tran t){
         System.out.println("执行改变阶段的操作");
         String editTime = DateTimeUtil.getSysTime();
         String editBy = ((User)request.getSession().getAttribute("user")).getName();
         t.setEditBy(editBy);
         t.setEditTime(editTime);
         ServletContext application = request.getSession().getServletContext();
         Map<String ,String> pMap = (Map<String, String>) application.getAttribute("pMap");
         String possibility = pMap.get(t.getStage());
         t.setPossibility(possibility);
         boolean flag = ts.changeStage(t);
         Map<String ,Object> map=new HashMap<>();
         map.put("success",flag);
         map.put("t",t);
         return map;

     }
    @RequestMapping("/pageList.do")
    @ResponseBody
    public Map<String, Object> pageForCon(@RequestParam Map<String, Object> map) {
        int pageNo = Integer.parseInt((String) map.get("pageNo"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
//        计算出略过的数量
        int skipCount = (pageNo - 1) * pageSize;
        map.put("skipCount", skipCount);
        map.replace("pageSize", pageSize);
        return ts.forTranPage(map);
    }


}
