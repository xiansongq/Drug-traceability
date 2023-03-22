package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.DrugFile;
import com.ruoyi.drug.service.IDrugFileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 【证明文件】Controller
 * 
 * @author ruoyi
 * @date 2023-03-19
 */
@RestController
@RequestMapping("/drug/file")
public class DrugFileController extends BaseController
{
    @Autowired
    private IDrugFileService drugFileService;

    /**
     * 查询【证明文件】列表
     */
   // @PreAuthorize("@ss.hasPermi('system:file:list')")
    @GetMapping("/list")
    public TableDataInfo list(DrugFile drugFile)
    {
        startPage();
        List<DrugFile> list = drugFileService.selectDrugFileList(drugFile);
        return getDataTable(list);
    }

    /**
     * 导出【证明文件】列表
     */
   // @PreAuthorize("@ss.hasPermi('system:file:export')")
    @Log(title = "导出【证明文件】列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DrugFile drugFile)
    {
        List<DrugFile> list = drugFileService.selectDrugFileList(drugFile);
        ExcelUtil<DrugFile> util = new ExcelUtil<DrugFile>(DrugFile.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【证明文件】详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:file:query')")
    @GetMapping(value = "/{fid}")
    public AjaxResult getInfo(@PathVariable("fid") Long fid)
    {
        return success(drugFileService.selectDrugFileByFid(fid));
    }

    /**
     * 新增【证明文件】
     */
   // @PreAuthorize("@ss.hasPermi('system:file:add')")

    @PostMapping("/uploadfile")
    public AjaxResult add( MultipartFile file, String type,String userName ) throws Exception
    {
        System.out.println(type+"==="+userName+"===="+file.getOriginalFilename());
        //首先校验文件是否为空 以及type参数是否上传
        if(!file.isEmpty() && StringUtils.isNotNull(type) && StringUtils.isNotNull(userName)){
            //获取文件保存路径
            String path= FileUploadUtils.upload(RuoYiConfig.getProfile(), file, MimeTypeUtils.IMAGE_EXTENSION);
            DrugFile drugFile=new DrugFile();
            drugFile.setFname(file.getOriginalFilename());
            drugFile.setFpath(path);
            drugFile.setFtype(type);
            drugFile.setFuserid(userName);
            if(drugFileService.insertDrugFile(drugFile)==1){
                return AjaxResult.success("文件上传成功");
            }
        }
        return AjaxResult.error("文件上传失败，请重试！");
    }

    /**
     * 修改【证明文件信息】
     */
   // @PreAuthorize("@ss.hasPermi('system:file:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)

    @PutMapping
    public AjaxResult edit(@RequestBody DrugFile drugFile)
    {
        return toAjax(drugFileService.updateDrugFile(drugFile));
    }

    /**
     * 删除【证明文件记录】
     */
   // @PreAuthorize("@ss.hasPermi('system:file:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fids}")
    public AjaxResult remove(@PathVariable Long[] fids)
    {
        return toAjax(drugFileService.deleteDrugFileByFids(fids));
    }
}
