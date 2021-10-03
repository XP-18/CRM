package top.xp18.crm.workbench.dao;

import top.xp18.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> getHistoryListByTranId(String TranId);
}
