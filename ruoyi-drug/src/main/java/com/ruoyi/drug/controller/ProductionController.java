package com.ruoyi.drug.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.drug.domain.DrugProduction;
import org.apache.commons.lang3.ArrayUtils;
import org.hyperledger.fabric.client.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drug/production")
public class ProductionController extends BaseController {
    @Resource
    private Contract contract;


    @GetMapping("/list")
    public TableDataInfo list() {

        TableDataInfo tableDataInfo = new TableDataInfo();
        byte[] res = new byte[0];
        try {
            res = contract.evaluateTransaction("QueryAllInfo");
        } catch (GatewayException e) {
            tableDataInfo.setCode(500);
            tableDataInfo.setMsg("区块链查询失败！");
            return tableDataInfo;
        }

        List<DrugProduction> list = new ArrayList<>();
        String data = new String(res, StandardCharsets.UTF_8);
        JSONArray jsonArray = JSON.parseArray(data);
        for (Object object : jsonArray) {
            JSONObject jsonObject1 = (JSONObject) JSON.toJSON(object);
            JSONObject jsonObject2 = jsonObject1.getJSONObject("Record");
            DrugProduction dp = JSON.parseObject(String.valueOf(jsonObject2), DrugProduction.class);
            list.add(dp);
        }

        return getDataTable(startPage(list), list.size());
    }

    @Log(title = "药品管理", businessType = BusinessType.IMPORT)
    // @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<DrugProduction> util = new ExcelUtil<DrugProduction>(DrugProduction.class);
        List<DrugProduction> drugProductionList = util.importExcel(file.getInputStream());
        //System.out.println(drugProductionList.toString());
        //System.out.println(drugProductionList.size());
        String operName = getUsername();
        String message = importUser(drugProductionList, updateSupport, operName);
        return success(message);
    }

    @Log(title = "药品管理", businessType = BusinessType.EXPORT)
    //@PreAuthorize("@ss.hasPermi('system:user:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, DrugProduction drugProduction) {
        byte[] res = new byte[0];
        try {
            res = contract.evaluateTransaction("QueryAllInfo");
        } catch (GatewayException e) {
            System.out.println(e.getMessage());
        }

        List<DrugProduction> list = new ArrayList<>();
        String data = new String(res, StandardCharsets.UTF_8);
        JSONArray jsonArray = JSON.parseArray(data);
        for (Object object : jsonArray) {
            JSONObject jsonObject1 = (JSONObject) JSON.toJSON(object);
            JSONObject jsonObject2 = jsonObject1.getJSONObject("Record");
            DrugProduction dp = JSON.parseObject(String.valueOf(jsonObject2), DrugProduction.class);
            list.add(dp);
        }
        ExcelUtil<DrugProduction> util = new ExcelUtil<DrugProduction>(DrugProduction.class);
        util.exportExcel(response, list, "药品数据");
    }


    /**
     * 国药编码目录
     *
     * @param drugProductionList 用户数据列表
     * @param isUpdateSupport    是否更新支持，如果已存在，则进行更新数据
     * @param operName           操作用户
     * @return 结果
     */
    // 当前链码还不支持 数据更新操作
    public String importUser(List<DrugProduction> drugProductionList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(drugProductionList) || drugProductionList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (DrugProduction drugProduction : drugProductionList) {
            try {
                // 直接调用链码 上链信息
                try {
                    contract.submitTransaction("AddInfo", drugProduction.getNumber(), drugProduction.getName(), drugProduction.getForm(), drugProduction.getSpecification(), drugProduction.getProduction(), drugProduction.getEncoded());
                } catch (Error error) {
                    failureNum++;
                    failureMsg.append("<br/>" + error.getMessage());
                }
                successNum++;
                successMsg.append("<br/>" + successNum + "、账号 " + drugProduction.getNumber() + " 上传成功");

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + drugProduction.getNumber() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());

            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<DrugProduction> util = new ExcelUtil<DrugProduction>(DrugProduction.class);
        util.importTemplateExcel(response, "药品数据");
    }

    /**
     * 新增药品信息
     */
    //@PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "药品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DrugProduction drugProduction) {
        byte[] res = new byte[0];
        try {
            res = contract.submitTransaction("AddInfo", drugProduction.getNumber(), drugProduction.getName(), drugProduction.getForm(), drugProduction.getSpecification(), drugProduction.getProduction(), drugProduction.getEncoded());
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();

    }
    /**
     * 删除药品数据
     */
    //@PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "药品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{numbers}")
    public AjaxResult remove(@PathVariable String[] numbers)
    {
        byte []res=new byte[0];
        for (String number:numbers){
            try {
                res=contract.submitTransaction("DeleteInfo", number);
            } catch (Exception e) {
              AjaxResult.error(e.getMessage());
            }
        }
        return AjaxResult.success();
    }
    /**
     * 根据药品批准文号 获取药品的详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = { "/", "/{number}" })
    public AjaxResult getInfo(@PathVariable(value = "number", required = false) String number)
    {

        AjaxResult ajax = AjaxResult.success();
        byte[] res=new byte[0];
        try {
            res= contract.submitTransaction("QueryInfo", number);
        } catch (Exception e) {
           return AjaxResult.error(e.getMessage());
        }
        String data=new String(res,StandardCharsets.UTF_8);
        DrugProduction drugProduction=JSONObject.parseObject(data,DrugProduction.class);
        ajax.put(AjaxResult.DATA_TAG, drugProduction);
        return ajax;
    }

}
