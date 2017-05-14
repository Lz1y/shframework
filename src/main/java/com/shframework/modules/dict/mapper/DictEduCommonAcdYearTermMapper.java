package com.shframework.modules.dict.mapper;

import com.shframework.modules.dict.entity.DictEduCommonAcdYearTerm;
import com.shframework.modules.dict.entity.DictEduCommonAcdYearTermExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;

@MyBatisRepository
public interface DictEduCommonAcdYearTermMapper {
    int countByExample(DictEduCommonAcdYearTermExample example);

    int deleteByExample(DictEduCommonAcdYearTermExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictEduCommonAcdYearTerm record);

    int insertSelective(DictEduCommonAcdYearTerm record);

    List<DictEduCommonAcdYearTerm> selectByExample(DictEduCommonAcdYearTermExample example);
    List<DictEduCommonAcdYearTerm> selectByExample1(DictEduCommonAcdYearTermExample example);
    DictEduCommonAcdYearTerm getYearTermByCode(@Param("code") String code);
    
    // 学年+学期名称
    List<DictEduCommonAcdYearTerm> getYearTermTitle();

    DictEduCommonAcdYearTerm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DictEduCommonAcdYearTerm record, @Param("example") DictEduCommonAcdYearTermExample example);

    int updateByExample(@Param("record") DictEduCommonAcdYearTerm record, @Param("example") DictEduCommonAcdYearTermExample example);

    int updateByPrimaryKeySelective(DictEduCommonAcdYearTerm record);

    int updateByPrimaryKey(DictEduCommonAcdYearTerm record);
    
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
    List<DictEduCommonAcdYearTerm> selectByMap(Map<String, Object> parMap);
    
    /**
     * 根据表名，获取状态为选用，未删除，且根据priority字段排序 的 记录数据
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> getAllUseable();
    
    /**
     * 根据年-学期的code取得对应的title
     * @param
     * @return		
     * @memo			 					
     * @author 	wangkang
     * @date    2015年3月27日 下午6:17:58
     */
    public Map<String, Object> getTitleByYearTermCode(String code);
    
    /**
     * 根据 code 取得对象
     * @param
     * @return		
     * @memo			 					
     * @author 	wangkang
     * @date    2015年5月15日 上午10:33:13
     */
    public List<DictEduCommonAcdYearTerm> getAcdYearTermByYearTermCode(String code);
    
    /**
     * 根据 yearTermCode 取得 DictEduCommonAcdYearTerm ，包括 yearTitle，termTitle，yearCode，termCode
     * @param
     * @return		
     * @memo			 					
     * @author 	wangkang
     * @date    2015年5月20日 下午6:57:54
     */
    public List<DictEduCommonAcdYearTerm> selectAcdYearTermByYearTermCode(String yearTermCode);
    
    /**
     * 根据 yearTermId 取得 DictEduCommonAcdYearTerm ，包括 yearTitle，termTitle，yearCode，termCode
     * @param
     * @return		
     * @memo			 					
     * @author 	wangkang
     * @date    2015年5月20日 下午6:57:54
     */
    public List<DictEduCommonAcdYearTerm> selectAcdYearTermByYearTermId(Integer yearTermId);
    
    
    /**
	 * 取得大于当前学期的学年学期id集合
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年6月3日 下午5:21:18
	 */
    public List<Integer> getYearTermIdListOfGreaterCurrTerm(String currYearTermCode);
    
    Integer selectIdByCode(String code);
    
}