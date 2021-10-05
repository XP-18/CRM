package top.xp18.crm.workbench.service;

import top.xp18.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface OtherService {
    Map<String, Object> forPage(Map<String, Object> map);

    Map<String, Object> forContactPage(Map map);

    Map<String, Object> forEchart();

}
