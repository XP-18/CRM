package top.xp18.crm.workbench.dao;

import top.xp18.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int bund(ClueActivityRelation car);

    int unbund(String id);

    List<ClueActivityRelation> getListByClueId(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
