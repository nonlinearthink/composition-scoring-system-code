<template>
  <div id="article-page">
    <a-modal v-model="editorVisible" title="编辑" okText="提交" @ok="onUpdate">
      <!-- 在此插入编辑用的表单 -->
      <a-form-model-item label="推送标题">
        <a-input
          v-model="editorForm.articleTitle"
          placeholder="请输入标题"
          allowClear
        />
      </a-form-model-item>
      <a-form-model-item label="消息内容">
        <a-textarea
          v-model="editorForm.articleBody"
          placeholder="请输入消息内容"
          allowClear
          autoSize
        />
      </a-form-model-item>
    </a-modal>
    <a-row type="flex">
      <a-col class="action-button">
        <a-button type="primary" @click="onCreate">
          新建
        </a-button>
      </a-col>
    </a-row>
    <a-table
      :columns="tableColumns"
      :data-source="dataSource"
      :rowKey="primaryKey"
    >
      <!-- 如果有需求，在此自定义每个单元格的样式 -->
      <a slot="articleTitle" slot-scope="text">
        <a-popover title="推送标题">
          <template slot="content">
            {{ text }}
          </template>
          <div>{{ text.length > 8 ? text.substring(0, 8) + "..." : text }}</div>
        </a-popover>
      </a>
      <a slot="body" slot-scope="text">
        <a-popover title="推送内容">
          <template slot="content">
            {{ text }}
          </template>
          <div>
            {{ text.length > 28 ? text.substring(0, 28) + "..." : text }}
          </div>
        </a-popover>
      </a>
      <span slot="time" slot-scope="text">
        {{ translateTime(text) }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="onEdit(record)">Edit</a>
        <a-divider type="vertical" />
        <a-popconfirm title="Sure to delete?" @confirm="onDelete(record)">
          <a>Delete</a>
        </a-popconfirm>
      </span>
    </a-table>
  </div>
</template>

<script>
import moment from "moment";
export default {
  data() {
    // 在此定义表结构
    const tableColumns = [
      {
        title: "ID",
        dataIndex: "articleId",
        key: "articleId",
        width: 80
      },
      {
        title: "推送标题",
        dataIndex: "articleTitle",
        key: "articleTitle",
        scopedSlots: { customRender: "articleTitle" },
        width: 180
      },
      {
        title: "推送内容",
        dataIndex: "articleBody",
        scopedSlots: { customRender: "body" },
        key: "articleBody"
      },
      {
        title: "修改时间",
        dataIndex: "time",
        key: "time",
        scopedSlots: { customRender: "time" },
        width: 180
      },
      {
        title: "发布人",
        dataIndex: "adminName",
        key: "adminName",
        width: 80
      },
      {
        title: "操作",
        key: "action",
        width: 120,
        scopedSlots: { customRender: "action" }
      }
    ];
    return {
      editorVisible: false,
      primaryKey: "articleId",
      tableColumns,
      // 在此编辑测试数据
      dataSource: [],
      editorForm: {},
      editingTarget: null,
      editingStatus: 0
    };
  },
  created() {
    this.axios
      .get("/article")
      .then(res => {
        console.log(res.data);
        this.dataSource = res.data.data.pushArticleEntityList;
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    onDelete(record) {
      this.axios
        .delete(`/article/${record.articleId}`)
        .then(res => {
          console.log(res.data);
          this.dataSource = this.dataSource.filter(
            item => item[this.primaryKey] != record[this.primaryKey]
          );
          this.$message.success(`删除记录${record[this.primaryKey]}成功`, 1);
        })
        .catch(err => console.error(err.response.data));
    },
    onEdit(record) {
      this.editingTarget = record;
      this.editorForm = Object.assign({}, record);
      this.editingStatus = 1;
      this.editorVisible = true;
    },
    onCreate() {
      this.editorForm = {
        adminName: "",
        articleBody: "",
        articleId: "",
        articleTitle: "",
        time: ""
      };
      this.editingStatus = 0;
      this.editorVisible = true;
    },
    onUpdate() {
      this.editorForm.time = new Date().getTime();
      if (this.editingStatus == 0) {
        this.axios
          .post(`/article`, this.editorForm)
          .then(res => {
            console.log(res.data);
            this.editorForm.articleId = res.data.data.pushArticleId;
            this.dataSource.push(this.editorForm);
            this.$message.success(
              `创建记录${this.editorForm[this.primaryKey]}成功`,
              1
            );
            this.editorVisible = false;
          })
          .catch(err => console.error(err.response.data));
      } else {
        this.axios
          .put(`/article/${this.editingTarget.articleId}`, this.editorForm)
          .then(res => {
            console.log(res.data);
            this.editingTarget.articleTitle = this.editorForm.articleTitle;
            this.editingTarget.articleBody = this.editorForm.articleBody;
            this.editingTarget.time = this.editorForm.time;
            this.$message.success(
              `修改记录${this.editingTarget[this.primaryKey]}成功`,
              1
            );
            this.editorVisible = false;
          })
          .catch(err => console.error(err.response.data));
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.action-button {
  margin: 1rem;
}
</style>
