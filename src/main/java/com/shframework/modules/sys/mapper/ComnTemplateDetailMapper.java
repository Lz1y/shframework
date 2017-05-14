package com.shframework.modules.sys.mapper;

import com.shframework.modules.sys.entity.ColumnComment;
import com.shframework.modules.sys.entity.ComnTemplateDetail;
import com.shframework.modules.sys.entity.ComnTemplateDetailExample;
import com.shframework.modules.sys.entity.ComnTemplateDetailKey;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.annotations.Param;
import org.mybatis.pagination.extra.MyBatisRepository;
/**
 * <p>模板设置</p>
 * @author zhangjk
 */
@MyBatisRepository
public interface ComnTemplateDetailMapper {
    int countByExample(ComnTemplateDetailExample example);

    int deleteByExample(ComnTemplateDetailExample example);

    int deleteByPrimaryKey(ComnTemplateDetailKey key);

    int insert(ComnTemplateDetail record);

    int insertSelective(ComnTemplateDetail record);

    List<ComnTemplateDetail> selectByExample(ComnTemplateDetailExample example);

    ComnTemplateDetail selectByPrimaryKey(ComnTemplateDetailKey key);

    int updateByExampleSelective(@Param("record") ComnTemplateDetail record, @Param("example") ComnTemplateDetailExample example);

    int updateByExample(@Param("record") ComnTemplateDetail record, @Param("example") ComnTemplateDetailExample example);

    int updateByPrimaryKeySelective(ComnTemplateDetail record);

    int updateByPrimaryKey(ComnTemplateDetail record);
    
    public List<ComnTemplateDetail> getComnTemplateDetailListByTemplateId(Integer templateId);
    
    
    /**
     * 参数类型为map，查询出map
     * @param mapParam
     * @return
     * @author zhangjk
     */
//	Map<String, String> getMap(Map<String,Object> mapParam);
	
//	Map<String, String> getColdbColFileName(Map<String,Object> mapParam);

	/**
	 * 模板设置时，用到了此方法。
	 * @param templateId
	 * @return
	 */
	int deleteByTemplateId(Integer templateId);

	/**
     * 根据表名和表别名，查询出对应的<Column Name,Comment>
     * @param mapParam
     * @return
     * @author zhangjk
     */
//	List<LinkedHashMap<Object, Object>> getColumnCommentByList(
//			List<LinkedHashMap<String, String>> moduleList);
	
	/**
	 * 获取 给定“数据库”下，给定“表名”的信息：列名（表别名,列名），列注释，表注释
	 * @param moduleList
	 * @return
	 */
	List<ColumnComment> getComment(
			List<LinkedHashMap<String, String>> moduleList);
	
	List<Map<String, Object>> saveExportExcelByIdList(Map<String, Object> map);

	List<ConcurrentHashMap<String, Object>> exportExcelPaymentData(
			Map<String, Object> map);

}