<template>
  <div id="review-composition-page">
    <a-modal
      v-model="viewVisiable"
      title="快速查看"
      okText="确认"
      @ok="onConfirm"
    >
      <!-- 在此插入编辑用的表单 -->
      <a-descriptions v-if="viewVisiable">
        <a-descriptions-item label="举报ID">
          {{ viewTarget.ccompositionReportId }}
        </a-descriptions-item>
        <a-descriptions-item label="举报时间" :span="2">
          {{ translateTime(viewTarget.time) }}
        </a-descriptions-item>
        <a-descriptions-item label="被举报用户" :span="3">
          {{ viewTarget.username }}
        </a-descriptions-item>
        <a-descriptions-item label="举报理由" :span="3">
          {{ viewTarget.compositionReportBody }}
        </a-descriptions-item>
      </a-descriptions>
    </a-modal>
    <a-row type="flex">
      <a-col class="action-button">
        <a-button
          type="primary"
          :disabled="rowSelected.length == 0"
          @click="onSetValid"
        >
          标记为忽略
        </a-button>
      </a-col>
      <a-col class="action-button">
        <a-button
          type="danger"
          :disabled="rowSelected.length == 0"
          @click="onSetInvalid"
        >
          标记为违规
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
      <span slot="report" slot-scope="text">
        {{ text.length > 28 ? text.substring(0, 28) + "..." : text }}
      </span>
      <span
        slot="status"
        slot-scope="text"
        :style="{
          color: text == 0 ? '#000' : text == 1 ? '#1890ff' : '#f5222d'
        }"
      >
        {{ translateStatus(text) }}
      </span>
      <span slot="time" slot-scope="text">
        {{ translateTime(text) }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="onQuickView(record)">查看</a>
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
        dataIndex: "compositionReportId",
        key: "compositionReportId",
        width: 80
      },
      {
        title: "被举报用户",
        dataIndex: "username",
        key: "username",
        width: 150
      },
      {
        title: "举报理由",
        dataIndex: "compositionReportBody",
        key: "compositionReportBody",
        scopedSlots: { customRender: "report" }
      },
      {
        title: "举报时间",
        dataIndex: "time",
        key: "time",
        scopedSlots: { customRender: "time" },
        width: 180
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
      viewVisiable: false,
      viewTarget: null,
      primaryKey: "compositionReportId",
      tableColumns,
      // 在此编辑测试数据
      dataSource: [],
      rowSelection,
      rowSelected: []
    };
  },
  created() {
    this.axios
      .get(`/report/composition`)
      .then(res => {
        console.log(res.data);
        this.dataSource = res.data.data.compositionReportEntityList;
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    translateStatus(status) {
      if (status == 0) {
        return "待审核";
      } else if (status == 1) {
        return "忽略";
      } else if (status == 2) {
        return "违规";
      }
    },
    onSetValid() {
      this.rowSelected.forEach(row => {
        row.status = 1;
        this.axios
          .put(`/report/composition/status/${row.compositionReportId}`, row)
          .then(res => {
            console.log(res);
          })
          .catch(err => console.error(err.response.data));
      });
      this.$message.success(`更新记录状态`, 1);
    },
    onSetInvalid() {
      this.rowSelected.forEach(row => {
        row.status = 2;
        this.axios
          .put(`/report/composition/status/${row.compositionReportId}`, row)
          .then(res => {
            console.log(res.data);
          })
          .catch(err => console.error(err.response.data));
      });
      this.$message.success(`更新记录状态`, 1);
    },
    onQuickView(record) {
      this.viewVisiable = true;
      this.viewTarget = record;
    },
    onConfirm() {
      this.viewVisiable = false;
    }
  }
};
</script>
<style lang="scss" scoped>
.action-button {
  margin: 1rem;
}
</style>
