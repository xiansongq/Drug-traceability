<template>
  <div class="register">
    <el-form
      ref="registerForm"
      :model="registerForm"
      :rules="registerRules"
      class="register-form"
    >
      <h3 class="title">药品溯源平台</h3>
      <el-form-item>
        <el-row>
          <el-col :span="12">
            生产单位：
            <el-switch
              v-model="isproduction"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="isproductionchange()"
            >
            </el-switch>
          </el-col>
          <el-col :span="12">
            零售商：
            <el-switch
              v-model="isretail"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="isretailchange()"
            >
            </el-switch>
          </el-col>
        </el-row>
      </el-form-item>

      <!-- 生产单位注册表单 -->
      <div v-if="isproduction">
        <el-form-item prop="userName">
          <el-input
            v-model="registerForm.userName"
            type="text"
            auto-complete="off"
            placeholder="单位名称"
          >
            <svg-icon
              slot="prefix"
              icon-class="user"
              class="el-input__icon input-icon"
            />
          </el-input>
        </el-form-item>
        <el-form-item prop="phoneNumber">
          <el-input
            v-model="registerForm.phoneNumber"
            type="text"
            auto-complete="off"
            placeholder="手机号"
          >
            <svg-icon
              slot="prefix"
              icon-class="phone"
              class="el-input__icon input-icon"
            />
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="registerForm.password"
            type="password"
            auto-complete="off"
            placeholder="密码"
            @keyup.enter.native="handleRegister"
          >
            <svg-icon
              slot="prefix"
              icon-class="password"
              class="el-input__icon input-icon"
            />
          </el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input
            v-model="registerForm.confirmPassword"
            type="password"
            auto-complete="off"
            placeholder="确认密码"
            @keyup.enter.native="handleRegister"
          >
            <svg-icon
              slot="prefix"
              icon-class="password"
              class="el-input__icon input-icon"
            />
          </el-input>
        </el-form-item>
        <el-row>
          <el-col :span="16">
            <el-form-item>
              <el-input
                v-model="registerForm.email"
                type="mail"
                auto-complete="off"
                placeholder="邮件地址"
              >
                <svg-icon
                  slot="prefix"
                  icon-class="user"
                  class="el-input__icon input-icon"
                />
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-button
              :loading="emailcodeloading"
              size="medium"
              type="primary"
              style="width: 100%"
              @click.native.prevent="handeleemail"
            >
              <span v-if="!emailcodeloading">发送验证码</span>
              <span v-else>验证码发送中...</span>
            </el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-form-item>
              <el-input
                v-model="registerForm.code"
                type="code"
                auto-complete="off"
                placeholder="验证码"
              >
                <svg-icon
                  slot="prefix"
                  icon-class="user"
                  class="el-input__icon input-icon"
                />
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-button
              :loading="checkcodeloading"
              size="medium"
              type="primary"
              style="width: 100%"
              @click.native.prevent="handelcheckcode"
            >
              <span v-if="!checkcodeloading">验证</span>
              <span v-else>验证中</span>
            </el-button>
          </el-col>
        </el-row>
        <!-- <el-form-item>
          <el-row>


            <el-col :span="8">
              <el-button
                size="small"
                type="primary"
                class="el-icon-upload"
                round
                @click="uploadmyfile(1)"
                >《上传营业执照》</el-button
              >
            </el-col>
            <el-col :span="9">
              <el-button
                size="small"
                type="primary"
                class="el-icon-upload"
                round
                @click="uploadmyfile(2)"
                >《上传药品经营许可证》</el-button
              >
            </el-col>
            <el-col :span="4">
              <el-button
                size="small"
                type="primary"
                class="el-icon-upload"
                round
                @click="uploadmyfile(3)"
                >《上传GSP认证证书》</el-button
              >
            </el-col>
          </el-row>
        </el-form-item> -->
      </div>

      <!-- 零售商注册表单 -->
      <div v-if="isretail">
        <el-form-item>
          <el-input
            v-model="registerForm.username"
            type="text"
            auto-complete="off"
            placeholder="账号"
          >
            <svg-icon
              slot="prefix"
              icon-class="user"
              class="el-input__icon input-icon"
            />
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="registerForm.nickName"
            type="text"
            auto-complete="off"
            placeholder="姓名"
          >
            <svg-icon
              slot="prefix"
              icon-class="user"
              class="el-input__icon input-icon"
            />
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="registerForm.phonenumber"
            type="text"
            auto-complete="off"
            placeholder="手机号"
          >
            <svg-icon
              slot="prefix"
              icon-class="user"
              class="el-input__icon input-icon"
            />
          </el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item>
              <el-input
                v-model="registerForm.email"
                type="text"
                auto-complete="off"
                placeholder="邮件地址"
              >
                <svg-icon
                  slot="prefix"
                  icon-class="user"
                  class="el-input__icon input-icon"
                />
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-button
              :loading="loading"
              size="medium"
              type="primary"
              style="width: 100%"
              @click.native.prevent="handleRegister"
            >
              <span v-if="!loading">校 验</span>
              <span v-else>验证码发送中...</span>
            </el-button>
          </el-col>
        </el-row>
      </div>

      <!-- <el-form-item prop="username">
        <el-input v-model="registerForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="registerForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleRegister"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input
          v-model="registerForm.confirmPassword"
          type="password"
          auto-complete="off"
          placeholder="确认密码"
          @keyup.enter.native="handleRegister"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input
          v-model="registerForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleRegister"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="register-code">
          <img :src="codeUrl" @click="getCode" class="register-code-img"/>
        </div>
      </el-form-item> -->

      <el-form-item style="width: 100%">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width: 100%"
          @click.native.prevent="handleRegister"
        >
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>
        <div style="float: right">
          <router-link class="link-type" :to="'/login'"
            >使用已有账户登录</router-link
          >
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-register-footer">
      <span>Copyright © 2018-2023 ruoyi.vip All Rights Reserved.</span>
    </div>

    <!-- 证书上传对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".jpg,.png,.jpeg,.bmp,.svg"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入图片文件。</span>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getCodeImg,
  register,
  sendEmailCode,
  checkCode,
  register1,
} from "@/api/login";
import identity from "lodash/identity";

export default {
  name: "Register",
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      codeUrl: "",
      isproduction: true,
      isretail: false,
      // 用户导入参数
      upload: {
        // 弹出层标题（用户导入）
        title: "",
        // 是否打开弹出层
        open: false,
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        //headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/drug/production/importData",
      },
      registerForm: {
        nickName: "",
        username: "",
        password: "",
        confirmPassword: "",
        email: "",
        phoneNumber: " ",
        code: "",
        uuid: "",
      },
      registerRules: {
        userName: [
          { required: true, trigger: "blur", message: "请输入单位名称" },
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" },
          {
            min: 5,
            max: 20,
            message: "用户密码长度必须介于 5 和 20 之间",
            trigger: "blur",
          },
        ],
        confirmPassword: [
          { required: true, trigger: "blur", message: "请再次输入您的密码" },
          { required: true, validator: equalToPassword, trigger: "blur" },
        ],
        code: [{ required: true, trigger: "blur", message: "请输入验证码" }],
        email: [{ required: true, trigger: "blur", message: "请输入邮件地址" }],
      },
      loading: false,
      emailcodeloading: false,
      checkcodeloading: false,
      captchaEnabled: true,
    };
  },
  created() {
    this.getCode();
  },
  methods: {
    isproductionchange() {
      if (this.isproduction == true) this.isretail = false;
    },
    isretailchange() {
      if (this.isretail == true) this.isproduction = false;
    },
    getCode() {
      getCodeImg().then((res) => {
        this.captchaEnabled =
          res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.registerForm.uuid = res.uuid;
        }
      });
    },
    handleRegister() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          register1(this.registerForm)
            .then((res) => {
              // const username = this.registerForm.username;
              // this.$alert(
              //   "<font color='red'>恭喜你，您的账号 " +
              //     username +
              //     " 注册成功！</font>",
              //   "系统提示",
              //   {
              //     dangerouslyUseHTMLString: true,
              //     type: "success",
              //   }
              // )
              //   .then(() => {
              //     this.$router.push("/login");
              //   })
              //   .catch(() => {});
              this.$confirm("注册成功，请提交证明材料以激活用户。", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
              })
                .then(() => {
                  this.$router.push("/uploadfile");
                })
                .catch(() => {
                  //this.$router.push("/uploadfile");
                });
            })
            .catch(() => {
              this.loading = false;
              if (this.captchaEnabled) {
                this.getCode();
              }
            });
        }
      });
    },
    handeleemail() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          this.emailcodeloading = true;
          sendEmailCode({ email: this.registerForm.email }).then((res) => {
            this.$message(res.msg);
            this.registerForm.uuid = res.uuid;
            this.emailcodeloading = false;
          });
        }
      });
    },
    handelcheckcode() {
      this.checkcodeloading = true;
      checkCode({
        uuid: this.registerForm.uuid,
        code: this.registerForm.code,
      }).then((res) => {
        this.$message(res.msg);
        this.checkcodeloading = false;
      });
    },
    uploadmyfile(p) {
      var str = "";
      if (p == 1) {
        str = "营业执照上传";
      } else if (p == 2) {
        str = "药品经营许可证上传";
      } else if (p == 3) {
        str = "GSP认证证书上传";
      }
      this.upload.title = str;
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
          response.msg +
          "</div>",
        "导入结果",
        { dangerouslyUseHTMLString: true }
      );
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.register-form {
  border-radius: 6px;
  background: #ffffff;
  width: 600px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.register-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.register-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-register-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.register-code-img {
  height: 38px;
}
</style>
