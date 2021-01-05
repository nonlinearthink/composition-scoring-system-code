<template>
  <div id="system-message-page">
    <a-modal v-model="editorVisible" title="编辑" okText="提交" @ok="onUpdate">
      <!-- 在此插入编辑用的表单 -->
      <a-form-model-item label="消息内容">
        <a-textarea
          v-model="editorForm.systemMessageBody"
          placeholder="请输入消息内容"
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
      <a-descriptions v-if="viewVisible" :column="4">
        <a-descriptions-item label="系统消息ID">
          {{ viewTarget.systemMessageId }}
        </a-descriptions-item>
        <a-descriptions-item label="编辑者">
          {{ viewTarget.adminName }}
        </a-descriptions-item>
        <a-descriptions-item label="编辑时间" :span="2">
          {{ translateTime(viewTarget.time) }}
        </a-descriptions-item>
        <a-descriptions-item label="系统消息" :span="4">
          {{ viewTarget.systemMessageBody }}
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
      <span slot="time" slot-scope="text">
        {{ translateTime(text) }}
      </span>
      <span slot="body" slot-scope="text">
        {{ text.length > 28 ? text.substring(0, 28) + "..." : text }}
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
        title: "ID",
        dataIndex: "systemMessageId",
        key: "systemMessageId",
        width: 80
      },
      {
        title: "消息内容",
        dataIndex: "systemMessageBody",
        key: "systemMessageBody",
        scopedSlots: { customRender: "body" }
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
        width: 160,
        scopedSlots: { customRender: "action" }
      }
    ];
    return {
      editorVisible: false,
      primaryKey: "systemMessageId",
      tableColumns,
      // 在此编辑测试数据
      dataSource: [],
      editorForm: {},
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
      .get("/system-message")
      .then(res => {
        console.log(res.data);
        this.dataSource = res.data.data.systemMessageEntityList;
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    onDelete(record) {
      this.axios
        .delete(`/system-message/${record.smessageId}`)
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
    onConfirm() {
      this.viewVisible = false;
    },
    onUpdate() {
      // 在此插入需要更新的内容
      this.editorForm.time = new Date().getTime();
      if (this.editingStatus == 0) {
        this.axios
          .post("/system-message", this.editorForm)
          .then(res => {
            console.log(res.data);
            this.editorForm.smessageId = res.data.data.sMessageId;
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
          .put(
            `/system-message/${this.editingTarget.smessageId}`,
            this.editorForm
          )
          .then(res => {
            console.log(res.data);
            this.editingTarget.smessageBody = this.editorForm.smessageBody;
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
    },
    onView(record) {
      this.viewTarget = record;
      this.viewVisible = true;
    },
    onCreate() {
      this.editorForm = {
        adminName: this.admin.adminName,
        smessageBody: "",
        smessageId: "",
        time: ""
      };
      this.editingStatus = 0;
      this.editorVisible = true;
    }
  }
};
</script>

<style lang="scss" scoped>
.action-button {
  margin: 1rem;
}
</style>
