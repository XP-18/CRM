package top.xp18.crm.workbench.service.impl;

import top.xp18.crm.workbench.dao.CustomerDao;
import top.xp18.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<String> getCustomerName(String name) {
        List<String> sList = customerDao.getCustomerName(name);
        return sList;
    }
}
