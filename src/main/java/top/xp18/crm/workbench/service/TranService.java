package top.xp18.crm.workbench.service;

import top.xp18.crm.workbench.domain.Tran;
import top.xp18.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranService {
    boolean save(Tran t, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String TranId);

    boolean changeStage(Tran t);
}
