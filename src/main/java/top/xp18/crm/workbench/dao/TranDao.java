package top.xp18.crm.workbench.dao;

import top.xp18.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran t);

    Tran detail(String id);

    int changeStage(Tran t);

    List<Tran> forPage(Map map);

    int getNums(Map map);

    List<Map> getEarts();

    int getTranNums();
}
