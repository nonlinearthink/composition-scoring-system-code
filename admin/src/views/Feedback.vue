<template>
  <div id="review-composition-page">
    <a-modal
      v-model="viewVisible"
      title="快速查看"
      okText="确认"
      @ok="onConfirm"
    >
      <a-descriptions v-if="viewVisible">
        <a-descriptions-item label="反馈ID">
          {{ viewTarget.feedbackId }}
        </a-descriptions-item>
        <a-descriptions-item label="反馈时间" :span="2">
          {{ translateTime(viewTarget.time) }}
        </a-descriptions-item>
        <a-descriptions-item label="反馈类型">
          {{ translateType(viewTarget.feedbackType) }}
        </a-descriptions-item>
        <a-descriptions-item label="反馈者" :span="2">
          {{ viewTarget.username }}
        </a-descriptions-item>
        <a-descriptions-item label="反馈内容" :span="3">
          {{ viewTarget.feedbackBody }}
        </a-descriptions-item>
      </a-descriptions>
    </a-modal>
    <a-row type="flex">
      <a-col class="action-button">
        <a-button
          type="primary"
          :disabled="rowSelected.length == 0"
          @click="onHandle"
        >
          标记为已处理
        </a-button>
      </a-col>
    </a-row>
    <a-table
      :columns="tableColumns"
      :data-source="dataSource"
      :rowKey="primaryKey"
      :row-selection="rowSelection"
    >
      <!-- 如果有需求，在此自定义每个单元格的样式 -->
      <span slot="feedback" slot-scope="text">
        {{ text.length > 28 ? text.substring(0, 28) + "..." : text }}
      </span>
      <span slot="time" slot-scope="text">
        {{ translateTime(text) }}
      </span>
      <span slot="feedbackType" slot-scope="text">
        {{ translateType(text) }}
      </span>
      <span
        slot="status"
        slot-scope="text"
        :style="{
          color: text == 0 ? '#f5222d' : '#000'
        }"
      >
        {{ translateStatus(text) }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="onView(record)">查看</a>
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
        dataIndex: "feedbackId",
        key: "feedbackId",
        width: 80
      },
      {
        title: "反馈内容",
        dataIndex: "feedbackBody",
        key: "feedbackBody",
        scopedSlots: { customRender: "feedback" }
      },
      {
        title: "反馈类型",
        dataIndex: "feedbackType",
        key: "feedbackType",
        scopedSlots: { customRender: "feedbackType" },
        width: 100
      },
      {
        title: "提交者",
        dataIndex: "username",
        key: "username",
        width: 130
      },
      {
        title: "反馈时间",
        dataIndex: "time",
        key: "time",
        scopedSlots: { customRender: "time" },
        width: 170
      },
      {
        title: "状态",
        dataIndex: "status",
        key: "status",
        scopedSlots: { customRender: "status" },
        width: 80
      },
      {
        title: "操作",
        key: "action",
        width: 80,
        scopedSlots: { customRender: "action" }
      }
    ];
    const rowSelection = {
      onChange: (selectedRowKeys, selectedRows) => {
        this.rowSelected = selectedRows;
      }
    };
    return {
      primaryKey: "feedbackId",
      tableColumns,
      // 在此编辑测试数据
      dataSource: [],
      rowSelection,
      rowSelected: [],
      viewVisible: false,
      viewTarget: null
    };
  },
  created() {
    this.axios
      .get("/feedback")
      .then(res => {
        console.log(res.data);
        this.dataSource = res.data.data.feedbackEntityList;
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    translateType(type) {
      if (type == 0) {
        return "BUG反馈";
      } else {
        return "改进建议";
      }
    },
    translateStatus(status) {
      if (status == 0) {
        return "待处理";
      } else if (status == 1) {
        return "已处理";
      }
    },
    onHandle() {
      this.rowSelected.forEach(row => {
        row.status = 1;
        this.axios
          .put(`/feedback/${row.feedbackId}`, row)
          .then(res => {
            console.log(res);
          })
          .catch(err => console.error(err.response.data));
      });
      this.$message.success(`更新记录状态`, 1);
    },
    onView(record) {
      this.viewTarget = record;
      this.viewVisible = true;
    },
    onConfirm() {
      this.viewVisible = false;
    }
  }
};
</script>
<style lang="scss" scoped>
.action-button {
  margin: 1rem;
}
</style>
