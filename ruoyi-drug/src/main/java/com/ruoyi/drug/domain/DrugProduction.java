package com.ruoyi.drug.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class DrugProduction extends BaseEntity {
    /**
     * 编号
     */
    @Excel(name = "批准文号")
    private String number;
    /**
     * 药品名字
     */
    @Excel(name = "产品名称")
    private String name;
    /**
     * 类型
     */
    @Excel(name = "剂型")
    private String form;
    /**
     * 成分
     */
    @Excel(name = "规格")
    private String specification;
    /**
     * 厂家
     */
    @Excel(name = "生产单位")
    private String production;
    /**
     * 编码
     */
    @Excel(name = "药品编码")
    private String encoded;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }

    @Override
    public String toString() {
        return "DrugProduction{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", form='" + form + '\'' +
                ", specification='" + specification + '\'' +
                ", production='" + production + '\'' +
                ", encoded='" + encoded + '\'' +
                '}';
    }
}
