<template>
  <div id="system-message-page">
    <a-modal v-model="editorVisible" title="编辑" okText="提交" @ok="onUpdate">
      <!-- 在此插入编辑用的表单 -->
      <a-form-model-item label="消息内容">
        <a-textarea
          v-model="editorForm.sMessageBody"
          placeholder="请输入消息内容"
          allowClear
          autoSize
        />
      </a-form-model-item>
    </a-modal>
    <a-table
      :columns="tableColumns"
      :data-source="dataSource"
      :rowKey="primaryKey"
    >
      <!-- 如果有需求，在此自定义每个单元格的样式 -->
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
export default {
  data() {
    // 在此定义表结构
    const tableColumns = [
      {
        title: "ID",
        dataIndex: "sMessageId",
        key: "sMessageId",
        width: 80
      },
      {
        title: "消息内容",
        dataIndex: "sMessageBody",
        key: "sMessageBody"
      },
      {
        title: "修改时间",
        dataIndex: "time",
        key: "time",
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
      primaryKey: "sMessageId",
      tableColumns,
      // 在此编辑测试数据
      dataSource: [
        {
          sMessageId: "10000",
          sMessageBody: "hello",
          time: "2020-11-09 20:08:30",
          adminName: "root"
        }
      ],
      editorForm: {},
      editingTarget: null
    };
  },
  methods: {
    onDelete(record) {
      this.dataSource = this.dataSource.filter(
        item => item[this.primaryKey] != record[this.primaryKey]
      );
      this.$message.success(`删除记录${record[this.primaryKey]}成功`, 1);
    },
    onEdit(record) {
      this.editingTarget = record;
      this.editorForm = Object.assign({}, record);
      this.editorVisible = true;
    },
    onUpdate() {
      // 在此插入需要更新的内容
      this.editingTarget.sMessageBody = this.editorForm.sMessageBody;
      this.editingTarget.time = new Date().getTime();
      // 插入结束
      this.$message.success(
        `修改记录${this.editingTarget[this.primaryKey]}成功`,
        1
      );
      this.editorVisible = false;
    }
  }
};
</script>
<style lang="scss" scoped></style>
