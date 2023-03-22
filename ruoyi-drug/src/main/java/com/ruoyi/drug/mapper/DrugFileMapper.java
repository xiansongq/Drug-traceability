package com.ruoyi.drug.mapper;

import java.util.List;
import com.ruoyi.system.domain.DrugFile;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-19
 */
public interface DrugFileMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param fid 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public DrugFile selectDrugFileByFid(Long fid);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param drugFile 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DrugFile> selectDrugFileList(DrugFile drugFile);

    /**
     * 新增【请填写功能名称】
     * 
     * @param drugFile 【请填写功能名称】
     * @return 结果
     */
    public int insertDrugFile(DrugFile drugFile);

    /**
     * 修改【请填写功能名称】
     * 
     * @param drugFile 【请填写功能名称】
     * @return 结果
     */
    public int updateDrugFile(DrugFile drugFile);

    /**
     * 删除【请填写功能名称】
     * 
     * @param fid 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDrugFileByFid(Long fid);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param fids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDrugFileByFids(Long[] fids);
}
