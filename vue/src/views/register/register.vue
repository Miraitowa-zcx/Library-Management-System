<template>
  <div style="height: 100vh; overflow: hidden; position: relative;">
    <el-card class="cover" v-if="registerAdmin.username">
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
    <div style="width: 500px; height: 450px; background-color: white; border-radius: 10px;
            margin: 250px auto; padding: 50px;">
      <div style="margin: 30px; text-align: center; font-size: 30px; font-weight: bold; color: dodgerblue;">注 册</div>
      <el-form :model="admin" :rules="rules" ref="registerForm" label-width="80px">
        <el-form-item prop="username" label="账号">
          <el-input placeholder="请输入账号" prefix-icon="el-icon-user" size="medium" v-model="admin.username"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input placeholder="请输入密码" show-password prefix-icon="el-icon-lock" size="medium" v-model="admin.password" type="password"></el-input>
        </el-form-item>
        <el-form-item prop="repeatPassword" label="确认密码">
          <el-input placeholder="请输入确认密码" show-password prefix-icon="el-icon-lock" size="medium" v-model="admin.repeatPassword" type="password"></el-input>
        </el-form-item>
        <el-form-item style="margin-top: 30px">
          <el-button style="width: 100%; " size="medium" type="primary" @click="register">注册</el-button>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-link :underline="false" href="/login" target="_self">已有账号，去登录</el-link>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'Register',
  data() {
    const checkPass = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入密码'));
      }
      if (value.length < 6) {
        callback(new Error('密码长度不能小于6位'));
      } else {
        callback();
      }
    };
    const repeatPass = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请再次输入密码'));
      } else {
        if (value !== this.admin.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      }
    }
    return {
      registerAdmin: {},
      checkPass: {},
      admin: {},
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { validator: checkPass, required: true, trigger: 'blur' }
        ],
        repeatPassword: [
            { validator: repeatPass, required: true, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    register() {
      this.$refs['registerForm'].validate((valid) => {
        if (valid) {
          request.post('/admin/register', this.admin).then(res => {
            if (res.code === '200') {
              this.registerAdmin = res.data
            } else {
              this.$notify.error(res.msg)
            }
          })
        } else {
          if (this.admin.password !== this.admin.repeatPassword && this.admin.repeatPassword !== '' && this.admin.password !== '' && this.admin.username !== '') {
            this.$notify.error('密码不一致')
          } else {
            this.$notify.error('请输入账号或密码')
          }
        }
      })
    },
    onSuccess() {
      this.$notify.success("注册成功")
      setTimeout(() => {
        this.$router.push('/login')
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
