<template>
  <div id="review-composition-page">
    <a-modal
      v-model="editorVisible"
      title="快速查看"
      okText="确认"
      @ok="onConfirm"
    >
      <!-- 在此插入编辑用的表单 -->
      <a-form-model-item label="冻结账号至">
        <a-date-picker placeholder="选择解冻时间" @change="onChange" />
      </a-form-model-item>
    </a-modal>
    <a-table
      :columns="tableColumns"
      :data-source="dataSource"
      :rowKey="primaryKey"
    >
      <!-- 如果有需求，在此自定义每个单元格的样式 -->
      <span
        slot="status"
        slot-scope="text"
        :style="{
          color: text ? '#f5222d' : '#1890ff'
        }"
      >
        {{ translateStatus(text) }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a-popconfirm
          title="Sure to freeze?"
          @confirm="onFreeze(record)"
          v-if="!record.frozen"
        >
          <a>Freeze</a>
        </a-popconfirm>
        <a-popconfirm
          title="Sure to unfreeze?"
          @confirm="onUnfreeze(record)"
          v-else
        >
          <a>Unfreeze</a>
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
        title: "用户",
        dataIndex: "username",
        key: "username",
        width: 100
      },
      {
        title: "违规次数",
        dataIndex: "count",
        key: "count",
        width: 200
      },
      {
        title: "状态",
        dataIndex: "frozen",
        key: "frozen",
        scopedSlots: { customRender: "status" },
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
      primaryKey: "username",
      tableColumns,
      // 在此编辑测试数据
      dataSource: [
        {
          username: "tuenity's small account",
          count: 3,
          frozen: false
        },
        {
          username: "tuenity",
          count: 10,
          frozen: true
        }
      ],
      editingTarget: null
    };
  },
  methods: {
    translateStatus(frozen) {
      if (frozen) {
        return "冻结中";
      } else {
        return "启用中";
      }
    },
    onFreeze(record) {
      this.onEditing(record);
    },
    onUnfreeze(record) {
      record.frozen = false;
      this.$message.success(`账号${record.username}被解冻`, 1);
    },
    onChange(value) {
      this.editingTarget.defrostingTime = value.valueOf();
    },
    onEditing(record) {
      this.editorVisible = true;
      this.editingTarget = record;
    },
    onConfirm() {
      if (this.editingTarget.defrostingTime <= moment().valueOf()) {
        this.$message.error("解冻日期必须在未来");
        return;
      }
      this.$message.success(
        `账号${this.editingTarget.username}被冻结至${moment(
          this.editingTarget.defrostingTime
        ).format("YYYY-MM-DD")}`,
        1
      );
      this.editingTarget.frozen = true;
      this.editorVisible = false;
    }
  }
};
</script>
<style lang="scss" scoped>
.action-button {
  margin: 1rem;
}
</style>
