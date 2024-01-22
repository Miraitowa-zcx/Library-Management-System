<template>
  <div style="width: 80%">

    <!-- 标题 -->
    <div style="margin-bottom: 30px">编辑借书记录</div>

    <!-- 表单 -->
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="150px">
      <el-form-item label="图书码" prop="bookNo">
        <el-select v-model="form.bookNo" filterable clearable placeholder="请选择图书码" @change="selBook">
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
  name: 'EditBorrow',
  data() {
    return {
       form: {},
      books: [],
      users: [],
      rules: {
         bookNo: [
           { required: true, message: '请输入图书号', trigger: 'blur' }
         ],
        userNo: [
          { required: true, message: '请输入用户会员码', trigger: 'blur' }
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

    const id = this.$route.query.id
    request.get("/borrow/" + id).then(res => {
      this.form = res.data
    })
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.put('/borrow/update', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('更新成功')
              this.$router.push("/borrowList")
            } else {
              this.$notify.error(res.msg)
            }
          })
        } else {
          this.$notify.error('请输入完整信息')
        }
      })
    },
    selBook() {
      const book = this.books.find(v => v.bookNo === this.form.bookNo)
      this.form.bookName = book.name
      this.form.score = book.score
      this.form.nums = book.nums
    },
    selUser() {
      const user = this.users.find(v => v.username === this.form.userNo)
      this.form.userName = user.name
      this.form.userPhone = user.phone
    }
  }
}
</script>

<style scoped>

</style>
