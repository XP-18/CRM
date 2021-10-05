package top.xp18.crm.workbench.dao;

import top.xp18.crm.workbench.domain.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsDao {

    int save(Contacts con);

    List<Contacts> forPage(Map map);

    int getNums(Map map);
}
