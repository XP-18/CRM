package top.xp18.crm.workbench.dao;

import top.xp18.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int save(Clue clue);

    Clue detail(String id);

    Clue getByid(String clueId);

    int delete(String clueId);

    List<Clue> selectForPage(Map map);

    int queryNums(Map map);
}
