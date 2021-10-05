package top.xp18.crm.workbench.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xp18.crm.workbench.dao.ContactsDao;
import top.xp18.crm.workbench.dao.CustomerDao;
import top.xp18.crm.workbench.dao.TranDao;
import top.xp18.crm.workbench.service.OtherService;

import java.util.HashMap;
import java.util.Map;
@Service
public class OtherServiceImpl implements OtherService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private TranDao tranDao;
    @Override
    public Map<String, Object> forPage(Map<String, Object> map) {
        Map<String, Object> map1=new HashMap<>();
        map1.put("dataList",customerDao.forPage(map));
        map1.put("toltal",customerDao.getNums(map));
        return map1;
    }

    @Override
    public Map<String, Object> forContactPage(Map map) {
        Map<String, Object> map1=new HashMap<>();
        map1.put("dataList",contactsDao.forPage(map));
        map1.put("total",contactsDao.getNums(map));
        return map1;
    }

    @Override
    public Map<String, Object> forEchart() {
        Map map=new HashMap();
        map.put("dataList",tranDao.getEarts());
        map.put("total",tranDao.getTranNums());

        return map;
    }
}
