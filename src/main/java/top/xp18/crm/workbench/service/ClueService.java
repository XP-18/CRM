package top.xp18.crm.workbench.service;

import top.xp18.crm.workbench.domain.Clue;
import top.xp18.crm.workbench.domain.Tran;

public interface ClueService {
    boolean save(Clue clue);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String[] aid, String cid);

    boolean convert(String clueId, Tran t, String createBy);
}
