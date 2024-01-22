<template>
  <div>

    <!-- 搜索表单 -->
    <div style="margin-bottom: 20px;">
      <el-input style="width: 240px;" placeholder="请输入图书名称" v-model="params.bookName"></el-input>
      <el-input style="width: 240px; margin-left: 5px;" placeholder="请输入图书码" v-model="params.bookNo"></el-input>
      <el-input style="width: 240px; margin-left: 5px;" placeholder="请输入用户名称" v-model="params.userName"></el-input>
      <el-button type="primary" icon="el-icon-search" style="margin-left: 5px;" @click="load">搜索</el-button>
      <el-button type="warning" icon="el-icon-refresh" style="margin-left: 5px;" @click="reset">重置</el-button>
    </div>

    <!-- 数据表格 -->
    <el-table :data="tableData" stripe
              row-key="id"
              default-expand-all
              border>
      <el-table-column prop="id" label="编号" width="80"></el-table-column>
      <el-table-column prop="bookName" label="图书名称"></el-table-column>
      <el-table-column prop="bookNo" label="标准码"></el-table-column>
      <el-table-column prop="userNo" label="用户会员码"></el-table-column>
      <el-table-column prop="userName" label="用户名称"></el-table-column>
      <el-table-column prop="userPhone" label="用户联系方式"></el-table-column>
      <el-table-column prop="score" label="所用积分"></el-table-column>
      <el-table-column prop="createtime" label="借出日期"></el-table-column>
      <el-table-column prop="status" label="借出状态"></el-table-column>
      <el-table-column prop="days" label="借出时间"></el-table-column>
      <el-table-column prop="returnDate" label="归还日期"></el-table-column>
      <el-table-column prop="note" label="过期提醒">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.note === '正常'">{{ scope.row.note }}</el-tag>
          <el-tag type="primary" v-else-if="scope.row.note === '即将到期'">{{ scope.row.note }}</el-tag>
          <el-tag type="warning" v-else-if="scope.row.note === '已到期'">{{ scope.row.note }}</el-tag>
          <el-tag type="danger" v-else>{{ scope.row.note }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="管理">
        <template v-slot="scope">
           <el-popconfirm
            style = "margin-left: 5px;"
            title="您确定要归还书籍吗？"
            @confirm="returnBooks(scope.row)">
            <el-button type="primary" icon="el-icon-upload2" v-if="scope.row.status === '已借出'" slot="reference">还书</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
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
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'BorrowList',
  data() {
    return {
      note: '',
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        bookName: '',
        bookNo: '',
        userName: ''
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get('/borrow/page', {
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
        BookName: '',
        bookNo: '',
        userName: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      // 点击分页按钮触发分页
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
      request.delete("/borrow/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('删除成功')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    returnBooks(row) {
      request.post("/return/save/", row).then(res => {
        if (res.code === '200') {
          this.$notify.success('还书成功')
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
