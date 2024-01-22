<template>
  <div style="width: 80%">

    <!-- 标题 -->
    <div style="margin-bottom: 30px">新增借书记录</div>

    <!-- 表单 -->
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="150px">
      <el-form-item label="图书码" prop="bookNo">
        <el-select v-model="form.bookNo" filterable clearable placeholder="请选择图书码" @change="selBook">
          <!-- 延时显示 -->

          <el-option
              v-for="item in books"
              :key="item.id"
              :label="item.bookNo"
              :value="item.bookNo">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图书名称" prop="bookName">
        <el-input v-model="form.bookName" disabled/>
      </el-form-item>
      <el-form-item label="所需积分" prop="score">
        <el-input v-model="form.score" disabled/>
      </el-form-item>
      <el-form-item label="图书数量" prop="nums">
        <el-input v-model="form.nums" disabled/>
      </el-form-item>
      <br/>
      <el-form-item label="用户会员码" prop="userNo">
        <el-select v-model="form.userNo" filterable clearable placeholder="请选择用户会员码" @change="selUser">
          <el-option
            v-for="item in users"
             :key="item.id"
             :label="item.username"
             :value="item.username">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户名称" prop="userName">
        <el-input v-model="form.userName" disabled/>
      </el-form-item>
      <el-form-item label="用户联系方式" prop="userPhone">
        <el-input v-model="form.userPhone" disabled/>
      </el-form-item>
      <el-form-item label="用户当前积分" prop="account">
        <el-input v-model="form.account" disabled/>
      </el-form-item>
      <el-form-item label="借出天数" prop="days">
        <el-input-number :min="1" :max="30" v-model="form.days"/>
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
  name: 'AddBorrow',
  data() {
    return {
       form: { days: 1 },
      books: [],
      users: [],
      rules: {
         bookNo: [
           { required: true, message: '请输入图书号', trigger: 'blur' }
         ],
        userNo: [
          { required: true, message: '请输入用户会员码', trigger: 'blur' }
        ],
        days: [
          { required: true, message: '请输入借出天数', trigger: 'blur'}
        ]
       }
    }
  },
  created() {
    request.get('/book/list').then(res => {
      this.books = res.data
    })

    request.get('/user/list').then(res => {
      this.users = res.data.filter(v => v.status)
    })
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/borrow/save', this.form).then(res => {
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
    },
    selBook() {
      const book = this.books.find(v => v.bookNo === this.form.bookNo)
      request.get('/book/' + book.id).then(res => {
        this.$set(this.form, 'bookName', res.data.name)
        this.$set(this.form, 'score', res.data.score)
        this.$set(this.form, 'nums', res.data.nums)
      })
    },
    selUser() {
      const user = this.users.find(v => v.username === this.form.userNo)
      request.get('/user/' + user.id).then(res => {
        this.$set(this.form, 'userName', res.data.name)
        this.$set(this.form, 'userPhone', res.data.phone)
        this.$set(this.form, 'account', res.data.account)
      })
    }
  }
}
</script>

<style scoped>

</style>
