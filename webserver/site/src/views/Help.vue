<template>
  <div id="help-page">
    <a-modal v-model="editorVisible" title="编辑" okText="提交" @ok="onUpdate">
      <!-- 在此插入编辑用的表单 -->
      <a-form-model-item label="帮助问题">
        <a-input
          v-model="editorForm.helpTitle"
          placeholder="请输入问题"
          allowClear
        />
      </a-form-model-item>
      <a-form-model-item label="帮助信息">
        <a-textarea
          v-model="editorForm.helpBody"
          placeholder="请输入帮助信息"
          allowClear
          autoSize
        />
      </a-form-model-item>
    </a-modal>
    <a-modal
      v-model="viewVisible"
      title="快速查看"
      okText="确认"
      @ok="onConfirm"
    >
      <a-descriptions v-if="viewVisible">
        <a-descriptions-item label="帮助ID">
          {{ viewTarget.helpId }}
        </a-descriptions-item>
        <a-descriptions-item label="帮助问题" :span="2">
          {{ viewTarget.helpTitle }}
        </a-descriptions-item>
        <a-descriptions-item label="编辑者">
          {{ viewTarget.adminName }}
        </a-descriptions-item>
        <a-descriptions-item label="编辑时间" :span="2">
          {{ translateTime(viewTarget.time) }}
        </a-descriptions-item>
        <a-descriptions-item label="帮助信息" :span="3">
          {{ viewTarget.helpBody }}
        </a-descriptions-item>
      </a-descriptions>
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
      <span slot="helpTitle" slot-scope="text">
        {{ text.length > 8 ? text.substring(0, 8) + "..." : text }}
      </span>
      <span slot="body" slot-scope="text">
        {{ text.length > 28 ? text.substring(0, 28) + "..." : text }}
      </span>
      <span slot="time" slot-scope="text">
        {{ translateTime(text) }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="onView(record)">查看</a>
        <a-divider type="vertical" />
        <a @click="onEdit(record)">编辑</a>
        <a-divider type="vertical" />
        <a-popconfirm title="Sure to delete?" @confirm="onDelete(record)">
          <a>删除</a>
        </a-popconfirm>
      </span>
    </a-table>
  </div>
</template>

<script>
import moment from "moment";
import { mapState } from "vuex";
export default {
  data() {
    // 在此定义表结构
    const tableColumns = [
      {
        title: "帮助ID",
        dataIndex: "helpId",
        key: "helpId",
        width: 80
      },
      {
        title: "标题",
        dataIndex: "helpTitle",
        key: "helpTitle",
        scopedSlots: { customRender: "helpTitle" },
        width: 160
      },
      {
        title: "帮助信息",
        dataIndex: "helpBody",
        scopedSlots: { customRender: "body" },
        key: "helpBody"
      },
      {
        title: "添加时间",
        dataIndex: "time",
        key: "time",
        scopedSlots: { customRender: "time" },
        width: 180
      },
      {
        title: "添加人",
        dataIndex: "adminName",
        key: "adminName",
        width: 80
      },
      {
        title: "操作",
        key: "action",
        width: 160,
        scopedSlots: { customRender: "action" }
      }
    ];
    return {
      primaryKey: "helpId",
      tableColumns,
      // 在此编辑测试数据
      dataSource: [],
      editorForm: {},
      editorVisible: false,
      editingTarget: null,
      editingStatus: 0,
      viewVisible: false,
      viewTarget: null
    };
  },
  computed: {
    ...mapState(["admin"])
  },
  created() {
    this.axios
      .get("/help")
      .then(res => {
        console.log(res.data);
        this.dataSource = res.data.data.helpEntityList;
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    onDelete(record) {
      this.axios
        .delete(`/help/${record.helpId}`)
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
    onView(record) {
      this.viewTarget = record;
      this.viewVisible = true;
    },
    onCreate() {
      this.editorForm = {
        adminName: this.admin.adminName,
        helpBody: "",
        helpId: "",
        helpTitle: "",
        time: ""
      };
      this.editingStatus = 0;
      this.editorVisible = true;
    },
    onConfirm() {
      this.viewVisible = false;
    },
    onUpdate() {
      this.editorForm.time = new Date().getTime();
      if (this.editingStatus == 0) {
        this.axios
          .post(`/help`, this.editorForm)
          .then(res => {
            console.log(res.data);
            this.editorForm.helpId = res.data.data.feedbackId;
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
          .put(`/help/${this.editingTarget.helpId}`, this.editorForm)
          .then(res => {
            console.log(res.data);
            this.editingTarget.helpTitle = this.editorForm.helpTitle;
            this.editingTarget.helpBody = this.editorForm.helpBody;
            this.editingTarget.time = this.editorForm.time;
            this.editingTarget.adminName = this.admin.adminName;
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
