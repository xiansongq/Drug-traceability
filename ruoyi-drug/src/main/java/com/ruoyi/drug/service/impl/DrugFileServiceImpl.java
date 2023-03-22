package com.ruoyi.drug.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.drug.mapper.DrugFileMapper;
import com.ruoyi.system.domain.DrugFile;
import com.ruoyi.drug.service.IDrugFileService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-19
 */
@Service
public class DrugFileServiceImpl implements IDrugFileService 
{
    @Autowired
    private DrugFileMapper drugFileMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param fid 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public DrugFile selectDrugFileByFid(Long fid)
    {
        return drugFileMapper.selectDrugFileByFid(fid);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param drugFile 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DrugFile> selectDrugFileList(DrugFile drugFile)
    {
        return drugFileMapper.selectDrugFileList(drugFile);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param drugFile 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDrugFile(DrugFile drugFile)
    {
        drugFile.setCreateTime(DateUtils.getNowDate());
        return drugFileMapper.insertDrugFile(drugFile);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param drugFile 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDrugFile(DrugFile drugFile)
    {
        drugFile.setUpdateTime(DateUtils.getNowDate());
        return drugFileMapper.updateDrugFile(drugFile);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param fids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDrugFileByFids(Long[] fids)
    {
        return drugFileMapper.deleteDrugFileByFids(fids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param fid 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDrugFileByFid(Long fid)
    {
        return drugFileMapper.deleteDrugFileByFid(fid);
    }
}
