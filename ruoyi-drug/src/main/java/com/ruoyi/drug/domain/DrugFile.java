package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 drug_file
 * 
 * @author ruoyi
 * @date 2023-03-19
 */
public class DrugFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long fid;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fname;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String fpath;

    /** 文件类型（1营业执照 2药品经营许可证 3GSP认证证书） */
    @Excel(name = "文件类型", readConverterExp = "1=营业执照,2=药品经营许可证,3=GSP认证证书")
    private String ftype;

    /** 用户id */
    @Excel(name = "用户id")
    private String fuserid;

    public void setFid(Long fid) 
    {
        this.fid = fid;
    }

    public Long getFid() 
    {
        return fid;
    }
    public void setFname(String fname) 
    {
        this.fname = fname;
    }

    public String getFname() 
    {
        return fname;
    }
    public void setFpath(String fpath) 
    {
        this.fpath = fpath;
    }

    public String getFpath() 
    {
        return fpath;
    }
    public void setFtype(String ftype) 
    {
        this.ftype = ftype;
    }

    public String getFtype() 
    {
        return ftype;
    }
    public void setFuserid(String fuserid) 
    {
        this.fuserid = fuserid;
    }

    public String getFuserid() 
    {
        return fuserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fid", getFid())
            .append("fname", getFname())
            .append("fpath", getFpath())
            .append("ftype", getFtype())
            .append("fuserid", getFuserid())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
