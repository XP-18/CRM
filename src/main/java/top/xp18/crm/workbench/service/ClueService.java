package top.xp18.crm.workbench.service;

import top.xp18.crm.workbench.domain.Clue;
import top.xp18.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {
    boolean save(Clue clue);

    Map queryForPage(Map map);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String[] aid, String cid);

    boolean convert(String clueId, Tran t, String createBy);

    boolean deleteClu(List<String> id);
}
