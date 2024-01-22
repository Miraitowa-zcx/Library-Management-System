<template>
  <div style="width: 80%">

    <!-- 标题 -->
    <div style="margin-bottom: 30px">添加分类</div>

    <!-- 表单 -->
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="200px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" placeholder="请输入备注"></el-input>
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
  name: 'AddCategory',
  data() {
    return {
       form: {},
       rules: {
         name: [
           {required: true, message: '请输入名称', trigger: 'blur'},
           {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
         ],
         remark: [
           {message: '请输入备注', trigger: 'blur'}
         ]
       }
    }
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/category/save', this.form).then(res => {
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
