package top.xp18.crm.workbench.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.xp18.crm.workbench.service.OtherService;

import java.util.Map;

@Controller
@RequestMapping("/other")
public class Other {
    @Autowired
    private OtherService customService;
    @RequestMapping("/pageList1.do")
    @ResponseBody
    public Map<String, Object> pageForCus(@RequestParam Map<String, Object> map){
        int pageNo = Integer.parseInt((String) map.get("pageNo"));
        int pageSize =  Integer.parseInt((String) map.get("pageSize"));
//        计算出略过的数量
        int skipCount = (pageNo - 1) * pageSize;
        map.put("skipCount",skipCount);
        map.replace("pageSize",pageSize);
        return customService.forPage(map);
    }

    @RequestMapping("/pageList2.do")
    @ResponseBody
    public Map<String, Object> pageForCon(@RequestParam Map<String, Object> map) {
        int pageNo = Integer.parseInt((String) map.get("pageNo"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
//        计算出略过的数量
        int skipCount = (pageNo - 1) * pageSize;
        map.put("skipCount", skipCount);
        map.replace("pageSize", pageSize);
        return customService.forContactPage(map);
    }
    @RequestMapping("/echart.do")
    @ResponseBody
    public Map<String, Object> forEachart(){
        return customService.forEchart();
    }
}
