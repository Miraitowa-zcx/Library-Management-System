<template>
  <div>

    <!-- 搜索表单 -->
    <div style="margin-bottom: 20px;">
      <el-input style="width: 240px;" placeholder="请输入用户名" v-model="params.username"></el-input>
      <el-input style="width: 240px; margin-left: 5px;" placeholder="请输入联系方式" v-model="params.phone"></el-input>
      <el-input style="width: 240px; margin-left: 5px;" placeholder="请输入电子邮件" v-model="params.email"></el-input>
      <el-button type="primary" icon="el-icon-search" style="margin-left: 5px;" @click="load">搜索</el-button>
      <el-button type="warning" icon="el-icon-refresh" style="margin-left: 5px;" @click="reset">重置</el-button>
    </div>

    <!-- 数据表格 -->
    <el-table :data="tableData"  border stripe>
      <el-table-column prop="id" label="编号" width="80"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="phone" label="联系方式"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间"></el-table-column>

      <el-table-column label="状态" width="280">
        <template slot-scope="scope" >
          <el-switch
            v-model="scope.row.status"
            @change="changeStatus(scope.row)"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="280">
        <template slot-scope="scope" >
          <el-button type="primary" icon="el-icon-edit" @click="$router.push('/editAdmin?id=' + scope.row.id)">编辑</el-button>
          <el-popconfirm
              style = "margin-left: 5px;"
            title="您确定要删除这行数据吗？"
            @confirm="del(scope.row.id)">
            <el-button type="danger" icon="el-icon-delete" slot="reference">删除</el-button>
          </el-popconfirm>
<!--          <el-popconfirm-->
<!--              style = "margin-left: 5px;"-->
<!--            title="您确定要修改密码为123456吗？"-->
<!--            @confirm="del(scope.row.id)">-->
<!--            <el-button type="danger" icon="el-icon-delete" slot="reference">修改密码</el-button>-->
<!--          </el-popconfirm>-->
          <el-button style = "margin-left: 5px;" type="warning" @click="handleChangePass(scope.row)">修改密码</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="margin-top: 20px; text-align: center;">
      <el-pagination
        background
        :current-page="params.pageNum"
        :page-size="params.pageSize"
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :total="total">
      </el-pagination>
    </div>

    <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px" ref="formRef" :rules="rules">
        <el-form-item label="原密码" prop="oldPass">
          <el-input v-model="form.oldPass" autocomplete="off" show-password type="password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPass">
          <el-input v-model="form.newPass" autocomplete="off" show-password type="password"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savePass">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request';
import Cookies from 'js-cookie'

export default {
  name: 'AdminList',
  data() {

    const checkOldPass = (rule, value, callback) => {
      setTimeout(() => {
        if (value === '') {
          callback(new Error('请输入原密码'));
        } else {
          if (value !== Cookies.get('admin').password) {
            callback(new Error('原密码错误'));
          } else {
            callback();
          }
        }
      }, 1000)
    };

    const checkNewPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入新密码'));
      } else {
        callback();
      }
    };

    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      tableData: [],
      total: 0,
      form: {},
      dialogFormVisible: false,
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: '',
        email: ''
      },
      rules: {
        newPass: [
          { required: true, validator: checkNewPass, trigger: 'blur' }
        ],
        oldPass: [
          { required: true, validator: checkOldPass, trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    changeStatus(row) {
      if (this.admin.id === row.id && !row.status) {
        row.status = true
        this.$notify.warning('不能修改自己')
        return
      }
      request.put('/admin/update', row).then(res => {
        if (res.code === '200') {
          if (row.status === true) {
            this.$notify.success('启用成功')
          } else {
            this.$notify.success('禁用成功')
          }
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    handleChangePass(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    savePass() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          request.put('admin/password', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success("修改成功")
              if (this.form.id === this.admin.id) {
                setTimeout(() => {
                  Cookies.remove('admin')
                  this.$router.push('/login')
                }, 1000)
              } else {
                this.dialogFormVisible = false
                this.load()
              }
            } else {
              this.$notify.error("修改失败")
            }
          })
        } else {
          this.$notify.error("请填写完整")
        }
      })
    },
    load() {
      request.get('/admin/page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: '',
        email: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      // 点击分页按钮触发分页
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
      request.delete("/admin/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('删除成功')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
