package top.xp18.crm.workbench.dao;

import top.xp18.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity at);

    int getTotalByCondition(Map<String, Object> map);

    List<Activity> getActivityByCondition(Map<String, Object> map);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity at);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, Object> map);

    List<Activity> getActivityListByName(String aname);
}
