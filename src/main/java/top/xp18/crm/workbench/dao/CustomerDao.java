package top.xp18.crm.workbench.dao;

import top.xp18.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer cus);

    List<String> getCustomerName(String name);

    List<Customer> forPage(Map<String, Object> map);

    int getNums(Map<String, Object> map);
}
