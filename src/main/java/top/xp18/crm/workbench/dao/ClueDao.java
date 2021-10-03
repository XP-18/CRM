package top.xp18.crm.workbench.dao;

import top.xp18.crm.workbench.domain.Clue;

public interface ClueDao {


    int save(Clue clue);

    Clue detail(String id);

    Clue getByid(String clueId);

    int delete(String clueId);
}
