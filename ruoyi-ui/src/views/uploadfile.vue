<template>
  <div class="login">
    <div class="login-form">
      <h3 class="title">证明材料上传</h3>
      <div class="step">
        <el-steps :active="pages">
          <el-step title="上传《营业执照》" icon="el-icon-upload"></el-step>
          <el-step
            title="上传《药品经营许可证》"
            icon="el-icon-upload"
          ></el-step>
          <el-step title="上传《GSP认证证书》" icon="el-icon-upload"></el-step>
        </el-steps>
      </div>
      

      <div class="filechoise" v-show="pages == 1">
        <el-upload
          ref="upload"
        
          accept=".jpg,.png,.jpeg,.svg,.dpm"
          :headers="upload.headers"
          :action="
            upload.url + '?type=' + upload.type + '&userName=' + upload.userName
          "
          :disabled="upload.isUploading"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess1"
          :auto-upload="false"
          :file-list="upload.fileList"
          :on-change="handlChange"
          drag
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip text-center" slot="tip">
            <span style="color: red">仅允许导入图片。</span>
          </div>
        </el-upload>
      </div>
      <div class="filechoise" v-show="pages == 2">
        <el-upload
          ref="upload2"
         
          accept=".jpg,.png,.jpeg,.svg,.dpm"
          :headers="upload.headers"
          :action="
            upload.url +
            '?type=' +
            upload2.type +
            '&userName=' +
            upload.userName
          "
          :disabled="upload.isUploading"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess2"
          :auto-upload="false"
          drag
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip text-center" slot="tip">
            <span style="color: red">仅允许导入图片。</span>
          </div>
        </el-upload>
      </div>
      <div class="filechoise" v-show="pages == 3">
        <el-upload
          ref="upload3"
          
          accept=".jpg,.png,.jpeg,.svg,.dpm"
          :headers="upload.headers"
          :action="
            upload.url +
            '?type=' +
            upload3.type +
            '&userName=' +
            upload.userName
          "
          :disabled="upload.isUploading"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess3"
          :auto-upload="false"
          :on-exceed="handleExceed"
          drag
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip text-center" slot="tip">
            <span style="color: red">仅允许导入图片。</span>
          </div>
        </el-upload>
      </div>
      <!-- 底部按钮 -->
      <div class="mybut">
        <el-row>
          <el-col :span="8">
            <el-button type="primary" round @click="prestep()" :disabled="but1"
              >上一步</el-button
            >
          </el-col>
          <el-col :span="8">
            <el-button
              type="primary"
              round
              @click="submitFileForm()"
              :disabled="but2"
              >提交</el-button
            >
          </el-col>
          <el-col :span="8">
            <el-button type="primary" round :disabled="but3" @click="nexstep()"
              >下一步</el-button
            >
          </el-col>
        </el-row>
      </div>
    </div>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2018-2023 ruoyi.vip All Rights Reserved.</span>
    </div>
  </div>
</template>
  
  <script>
export default {
  name: "Uploadfile",
  data() {
    return {
      // 用户导入参数
      upload: {
        // 弹出层标题（用户导入）
        title: "",
        // 是否打开弹出层
        open: false,
        // 是否禁用上传
        isUploading: false,
        // 文件类型
        type: 1,
        //用户名
        userName: "Admin",
        // 设置上传的请求头部
        //headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/drug/file/uploadfile",
        //文件列表
        fileList: [],
      },
      // 用户导入参数
      upload2: {
        // 弹出层标题（用户导入）
        title: "",
        // 是否打开弹出层
        open: false,
        // 是否禁用上传
        isUploading: false,
        // 文件类型
        type: 2,
        //用户名
        userName: "Admin",
        // 设置上传的请求头部
        //headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/drug/file/uploadfile",
      },
      // 用户导入参数
      upload3: {
        // 弹出层标题（用户导入）
        title: "",
        // 是否打开弹出层
        open: false,
        // 是否禁用上传
        isUploading: false,
        // 文件类型
        type: 3,
        //用户名
        userName: "Admin",
        // 设置上传的请求头部
        //headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/drug/file/uploadfile",
      },

      pages: 1,
      but1: true,
      but3: false,
      but2: false,
    };
  },
  watch: {},
  created() {},
  methods: {
    handlChange(file, fileList) {
      // 不会改变fileList
      const newFileList = fileList.slice(-1);
      // fileList直接赋值this.fileList不会改变的原因是因为他们两个的指向是一致的
      this.upload.fileList = newFileList;
    },
    prestep() {
      this.pages--;
      if (this.pages == 1) this.but1 = true;
      this.but3 = false;
    },
    nexstep() {
      this.pages++;
      if (this.pages == 3) this.but3 = true;
      this.but1 = false;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess1(response, file, fileList) {
      console.log(response);
      this.$message({
        message: response.msg,
        type: "success",
      });
      
    },
        // 文件上传成功处理
        handleFileSuccess2(response, file, fileList) {
      console.log(response);
      this.$message({
        message: response.msg,
        type: "success",
      });
    
    },
        // 文件上传成功处理
        handleFileSuccess3(response, file, fileList) {
      console.log(response);
      this.$message({
        message: response.msg,
        type: "success",
      });
      // if (this.pages == 1) this.$refs.upload.clearFiles();
      // else if (this.pages == 2) {
      //   this.$refs.upload2.clearFiles();
      // } else if (this.pages == 3) {
      //   this.$refs.upload3.clearFiles();
      // }
    },
    // 提交上传文件
    submitFileForm() {
      if (this.pages == 1) {
        this.$refs.upload.submit();
      } else if (this.pages == 2) {
        this.$refs.upload2.submit();
      } else if (this.pages == 3) {
        this.$refs.upload3.submit();
      }
    },
  },
};
</script>
  
  <style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  widows: 800px;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
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
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
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
.login-code-img {
  height: 38px;
}
.step {
  width: 100%;
}
.filechoise {
  width: 100%;
  align-content: center;
}
.mybut {
  padding-top: 10px;
}
el-upload-dragger {
  background-color: #fff;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  width: 100%px;
  height: 3000px;
  text-align: center;
  cursor: pointer;
  overflow: hidden;
}
</style>
  