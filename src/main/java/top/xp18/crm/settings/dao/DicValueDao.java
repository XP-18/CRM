package top.xp18.crm.settings.dao;

import top.xp18.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
