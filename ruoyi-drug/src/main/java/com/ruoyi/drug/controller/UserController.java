package com.ruoyi.drug.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.drug.util.MailUtil;
import com.ruoyi.system.service.ISysUserService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.checkerframework.checker.units.qual.A;
import org.hyperledger.fabric.client.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/drug/user")
public class UserController extends BaseController {
    @Resource
    private Contract contract;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MailUtil mailUtil;
    /**
     * 发送邮件验证码
     * @param sysUser
     */
    @GetMapping("/sendemail")
   public AjaxResult test1(SysUser sysUser){
       AjaxResult ajax=AjaxResult.success();
        //首先校验 邮件地址是否已经注册
        System.out.println("======"+userService.checkEmailUnique(sysUser));
       if(!userService.checkEmailUnique(sysUser)){
           ajax.put("msg","该邮箱地址已被注册！");
           return AjaxResult.error("该邮箱地址已被注册！");
       }
       //创建验证码
       String code=getCheckCode();
       //获取uuid
       String uuid = IdUtils.simpleUUID();
       String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
       // 缓存验证码
       redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
       //发送邮件
       // 注册成功 讲用户的 username 发送到用户邮箱
       String title="药品溯源平台";
       String context= "您好!</br>&nbsp;&nbsp;&nbsp;请输入：<span style='color:red;font-size: 23px;'>"+code+"</span>,验证你的邮件地址，有效时间10分钟，请勿回复。";
       try {
           mailUtil.sendMail(sysUser.getEmail(),context,title);
       } catch (Exception e) {
           return AjaxResult.error("邮件发送失败!");
       }
       ajax.put("uuid",uuid);
       ajax.put("msg","邮件发送成功");
       return ajax;
   }
    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            // 产生0到size-1的随机值
            int index = r.nextInt(size);
            // 在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            // 将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }


    /**
     * 验证用户邮箱 验证码
     * @param loginBody
     * @return
     */

    @PostMapping("/checkcode")
    public AjaxResult test2(@RequestBody LoginBody loginBody){
        String uuid=loginBody.getUuid();
        String code=loginBody.getCode();
        System.out.println(uuid+"====="+code);
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException();
        }
        return AjaxResult.success("验证通过");
    }



    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public AjaxResult register(@RequestBody SysUser user){
        return toAjax(userService.insertUser(user));
    }



}
