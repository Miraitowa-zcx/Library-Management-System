<template>
  <div>

    <!-- 搜索表单 -->
    <div style="margin-bottom: 20px;">
      <el-input style="width: 240px;" placeholder="请输入分类名称" v-model="params.name"></el-input>
      <el-button type="primary" icon="el-icon-search" style="margin-left: 5px;" @click="load">搜索</el-button>
      <el-button type="warning" icon="el-icon-refresh" style="margin-left: 5px;" @click="reset">重置</el-button>
    </div>

    <!-- 数据表格 -->
    <el-table :data="tableData" stripe
              row-key="id"
              default-expand-all
              border>
      <el-table-column prop="id" label="编号" width="80"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间"></el-table-column>

      <el-table-column label="操作" width="320">
        <template slot-scope="scope" >
          <el-button type="success" icon="el-icon-circle-plus-outline"  v-if="!scope.row.pid" @click="handleAdd(scope.row)">添加下级分类</el-button>
          <el-button type="primary" icon="el-icon-edit" @click="$router.push('/editCategory?id=' + scope.row.id)">编辑</el-button>
          <el-popconfirm
              style = "margin-left: 5px;"
            title="您确定要删除这行数据吗？"
            @confirm="del(scope.row.id)">
            <el-button type="danger" icon="el-icon-delete" slot="reference">删除</el-button>
          </el-popconfirm>
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

    <el-dialog title="添加下级分类" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px" ref="ruleForm" :rules="rules">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类备注" prop="remark">
          <el-input v-model="form.remark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'CategoryList',
  data() {
    return {
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      form: {},
      pid: null,
      params: {
        pageNum: 1,
        pageSize: 10,
        name: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入分类名称', trigger: 'blur' }
        ],
        remark: [
          { message: '请输入分类备注', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get('/category/page', {
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
        pageSize: 10
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      // 点击分页按钮触发分页
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
      request.delete("/category/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('删除成功')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    handleAdd(row) {
      this.pid = row.id
      this.dialogFormVisible = true

    },
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.form.pid = this.pid
          request.post('/category/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('新增成功')
              this.dialogFormVisible = false
              this.load()
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
