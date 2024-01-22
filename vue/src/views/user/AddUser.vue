<template>
  <div style="width: 80%">

    <!-- 标题 -->
    <div style="margin-bottom: 30px">新增用户</div>

    <!-- 表单 -->
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="200px">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio v-model="form.sex" label="男">男</el-radio>
        <el-radio v-model="form.sex" label="女">女</el-radio>
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" placeholder="请输入地址"></el-input>
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
  name: 'AddUser',
  data() {
    const checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('年龄不能为空'));
      }
      setTimeout(() => {
        if (!/^[0-9]\d*$/.test(value)) {
          callback(new Error('请输入数字值'));
        } else {
          if (value < 18) {
            callback(new Error('必须年满18岁'));
          } else {
            if (parseInt(value) > 120 || parseInt(value) <= 0) {
              callback(new Error('请输入合理年龄！'));
            } else {
              callback();
            }
          }
        }
      }, 1000);
    };

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

    return {
       form: { sex: '男' },
       rules: {
         name: [
           {required: true, message: '请输入姓名', trigger: 'blur'}
         ],
         age: [
           {validator: checkAge, trigger: 'blur'}
         ],
         phone: [
           {validator: checkPhone, trigger: 'blur'}
         ]
       }
    }
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/user/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('新增成功')
              this.form =  { sex: '男' }
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
