<template>
  <div style="width: 80%">

    <!-- 标题 -->
    <div style="margin-bottom: 30px">添加分类</div>

    <!-- 表单 -->
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="200px">
      <el-form-item label="图书名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入图书名称"/>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input style="width: 400px" type="textarea" v-model="form.description" placeholder="请输入描述"/>
      </el-form-item>
      <el-form-item label="发行日期" prop="publishDate">
        <el-date-picker
          v-model="form.publishDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择出版日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model="form.author" placeholder="请输入作者名"/>
      </el-form-item>
      <el-form-item label="出版社" prop="publisher">
        <el-input v-model="form.publisher" placeholder="请输入出版社"/>
      </el-form-item>
      <el-form-item label="分类" prop="category">
        <el-cascader
          style="width: 220px"
          v-model="form.categories"
          :options="categories"
          :props="{ value: 'name', label: 'name' }"
          placeholder="请选择分类">
        </el-cascader>
      </el-form-item>
      <el-form-item label="书号" prop="bookNo">
        <el-input v-model="form.bookNo" placeholder="请输入书号"/>
      </el-form-item>
      <el-form-item label="借书积分" prop="score">
        <el-input-number v-model="form.score" :min="10" :max="30" label="所需积分"/>
      </el-form-item>
      <el-form-item label="图书数量" prop="nums">
        <el-input v-model="form.nums" :min="10" :max="30" label="请输入图书数量"/>
      </el-form-item>
      <br/>
      <el-form-item label="封面" prop="cover">
        <el-upload
           class="avatar-uploader"
           :action="'http://localhost:13761/api/book/file/upload?token=' + this.admin.token"
           :show-file-list="false"
           :on-success="handleCoverSuccess">
          <img v-if="form.cover" :src="form.cover" class="avatar" alt="">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
    </el-form>

    <!-- 提交按钮 -->
    <div style="text-align: center; margin-top: 30px">
      <el-button type="primary" @click="save" size="medium">提交</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request"
import Cookies from "js-cookie"

export default {
  name: 'AddBook',
  data() {
    const checkNums =  (rule, value, callback) => {
      value = parseInt(value)
      if (value === null || value === undefined) {
        return callback(new Error('请输入图书数量'));
      }
      if (!Number.isInteger(value) || value < 0 || value >= 1000) {
        callback(new Error('请输入大于0小于等于1000的数字值'));
      }
      callback();
    };

    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
       form: { score: 10, cover: '' },
      categories: [],
      rules: {
         name: [
           { required: true, message: '请输入图书名称', trigger: 'blur' },
           { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
         ],
         publishDate: [
           { trigger: 'blur' }
         ],
         author: [
          { message: '请输入图书作者', trigger: 'blur' }
        ],
        publisher: [
          { message: '请输入图书出版社', trigger: 'blur' }
        ],
        bookNo: [
          { required: true, message: '请输入图书号', trigger: 'blur' }
        ],
        nums: [
          { validator: checkNums, trigger: 'blur' }
        ],
        score: [
          { required: true, message: '请输入借书积分', trigger: 'blur' }
        ],
         cover: [
          { trigger: 'blur' }
         ]
       }
    }
  },
  created() {
    request.get('/category/tree').then(res => {
      this.categories = res.data
    })
  },
  methods: {
    handleCoverSuccess(res) {
      if (res.code === '200') {
        this.form.cover = res.data
      }
    },
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/book/save', this.form).then(res => {
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

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
