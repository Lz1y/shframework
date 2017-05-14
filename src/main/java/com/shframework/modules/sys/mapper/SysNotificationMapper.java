package com.shframework.modules.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.pagination.extra.MyBatisRepository;

import com.shframework.modules.sys.entity.SysNotification;
import com.shframework.modules.sys.entity.SysNotificationExample;

@MyBatisRepository
public interface SysNotificationMapper {
    int countByExample(SysNotificationExample example);

    int deleteByExample(SysNotificationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysNotification record);

    int insertSelective(SysNotification record);

    List<SysNotification> selectByExample(SysNotificationExample example);

    SysNotification selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysNotification record, @Param("example") SysNotificationExample example);

    int updateByExample(@Param("record") SysNotification record, @Param("example") SysNotificationExample example);

    int updateByPrimaryKeySelective(SysNotification record);

    int updateByPrimaryKey(SysNotification record);

	int read(int id);

	int readAll(int receiveUserUd);
	int deleteAll(int receiveUserUd);
	
	List<SysNotification> list(Map<String, Object> map);
	int count(Map<String, Object> map);

	int batchread(List<Integer> list);
	int batchdelete(List<Integer> list);

	@Select("select count(*) from sys_notification where type = #{0} and receive_user_id = #{1} and status = 0 and logic_delete = 0")
	int unreadCount(Integer flag, Integer userId);
	
	List<SysNotification> readByUserId(String userId);
	
	Integer[] unReadCount(String userId);
	
}