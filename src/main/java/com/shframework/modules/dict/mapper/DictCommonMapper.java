package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictCommon;
import com.shframework.modules.dict.entity.DictCommonExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictCommonMapper {
	 //以下部分，是对参数进行了修改的， 增加了参数 tableName
    //By RanWeizheng
	int countByExample(@Param("tableName") String tableName,  @Param("example")DictCommonExample example);

    int deleteByExample(@Param("tableName") String tableName,  @Param("example")DictCommonExample example);

    List<DictCommon> selectByExample(@Param("tableName") String tableName,  @Param("example") DictCommonExample example);
    
    DictCommon selectByPrimaryKey(@Param("tableName")String tableName, @Param("id")Integer id);//changed

    int updateByExampleSelective(@Param("tableName") String tableName, @Param("record") DictCommon record, @Param("example") DictCommonExample example);

    int insertSelective(@Param("tableName")String tableName, @Param("record")DictCommon record);
    
    int deleteByPrimaryKey(@Param("tableName")String tableName, @Param("id")Integer id);
    
    int updateByPrimaryKeySelective(@Param("tableName")String tableName, @Param("record")DictCommon record);

    /**
     * 根据查询条件map，查找符合条件的记录数量
     * @param parMap 
     * {
     * String tableName;
     * String searchCondition;
     * int limitStart;
     * int limitEnd;
     * }
     * @return
     * @author RanWeizheng
     */
    int countByMap(Map<String, Object> parMap);
    
    /**
     * 根据查询条件map，查找符合条件的记录
     * @param parMap 
     * {
     * String tableName;
     * String searchCondition;
     * int limitStart;
     * int limitEnd;
     * }
     * @return
     * @author RanWeizheng
     */
    List<DictCommon> selectByMap(Map<String, Object> parMap);
    
    /**
     * 获取所有字典表的名字和字典表的描述文字
     * @author RanWeizheng
     * @return
     */
    public List<Map<String, String>> getAllDictInfo(String dbName);
    
    /**
      * <P>判断动态表，数据库表中 是否含有"code"列，根据isContainCode为"YES"或"NO"判定页面是否展示"编码"</p>
      * @author zhangjinkui
     */
    public List<Map<String, String>> getDictInfoByTableName(Map<String,Object> dbName);
    
    /**
     * 根据表名，获取状态为选用，未删除，且根据priority字段排序 的 记录数据
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> getAllUseableByTableName(@Param("tableName")String tableName);
    
    int queryIdByTitle(@Param("title")String title, @Param("tbname")String tbname);
}