<template>
  <div style="width: 80%">

    <!-- 标题 -->
    <div style="margin-bottom: 30px">新增管理员</div>

    <!-- 表单 -->
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="200px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱地址"></el-input>
      </el-form-item>
    </el-form>

    <!-- 提交按钮 -->
    <div style="text-align: center; margin-top: 30px">
      <el-button type="primary" @click="save" size="medium">提交</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: 'AddAdmin',
  data() {

    const checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('联系方式不能为空'));
      }
      setTimeout(() => {
          if (!/^1[3456789]\d{9}$/.test(value)) {
            callback(new Error('请输入正确的手机号'));
          }
          callback();
        }, 1000);
    };

    const checkEmail = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('邮箱不能为空'));
      }
      setTimeout(() => {
          if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(value)) {
            callback(new Error('请输入正确的邮箱'));
          }
          callback();
      }, 1000);
    };

    return {
       form: {},
       rules: {
         username: [
           {required: true, message: '请输入用户名', trigger: 'blur'},
           {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
         ],
         phone: [
           {validator: checkPhone, trigger: 'blur'}
         ],
         email: [
           {validator: checkEmail, trigger: 'blur'}
         ]
       }
    }
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/admin/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('新增成功')
              this.form =  {}
            } else {
              this.$notify.error(res.msg)
            }
          })
        } else {
          this.$notify.error('请完善信息')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
