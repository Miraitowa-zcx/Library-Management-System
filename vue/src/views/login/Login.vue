<template>
  <div style="height: 100vh; overflow: hidden; position: relative;">
    <el-card class="cover" v-if="loginAdmin.id">
      <slide-verify :l="42"
            :r="10"
            :w="310"
            :h="155"
            slider-text="向右滑动"
            @success="onSuccess"
            @fail="onFail"
            @refresh="onRefresh">
      </slide-verify>
      <div>{{msg}}</div>
    </el-card>
    <div style="width: 500px; height: 400px; background-color: white; border-radius: 10px;
            margin: 250px auto; padding: 50px;">
      <div style="margin: 30px; text-align: center; font-size: 30px; font-weight: bold; color: dodgerblue;">登 录</div>
      <el-form :model="admin" :rules="rules" ref="loginForm" label-width="60px">
        <el-form-item prop="username" label="账号">
          <el-input placeholder="请输入账号" prefix-icon="el-icon-user" size="medium" v-model="admin.username"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input placeholder="请输入密码" show-password prefix-icon="el-icon-lock" size="medium" v-model="admin.password" type="password"></el-input>
        </el-form-item>
        <el-form-item style="margin-top: 30px">
          <el-button style="width: 100%; " size="medium" type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-link :underline="false" href="/register" target="_self">还有没账号，去注册</el-link>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: 'Login',
  data() {
    return {
      loginAdmin: {},
      admin: {},
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs['loginForm'].validate((valid) => {
        if (valid) {
          request.post('/admin/login', this.admin).then(res => {
            if (res.code === '200') {
              this.loginAdmin = res.data
            } else {
              this.$notify.error(res.msg)
            }
          })
        } else {
          this.$notify.error('请输入账号密码')
        }
      })
    },
    onSuccess() {
      this.$notify.success("登陆成功")
      setTimeout(() => {
        Cookies.set('admin', JSON.stringify(this.loginAdmin))
        this.$router.push('/')
      }, 1000)
    },
    onFail() {
      this.$notify.error("验证失败")
    },
    onRefresh() {
      this.$notify.success("刷新成功")
    }
  }
}
</script>

<style scoped>
.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}
</style>
