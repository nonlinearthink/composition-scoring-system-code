<template>
  <div id="review-composition-page">
    <a-row type="flex">
      <a-col class="action-button">
        <a-button
          type="primary"
          :disabled="rowSelected.length == 0"
          @click="onSetValid"
        >
          标记为通过
        </a-button>
      </a-col>
      <a-col class="action-button">
        <a-button
          type="danger"
          :disabled="rowSelected.length == 0"
          @click="onSetInvalid"
        >
          标记为驳回
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
      <span
        slot="status"
        slot-scope="text"
        :style="{
          color: text == 0 ? '#000' : text == 1 ? '#1890ff' : '#f5222d'
        }"
      >
        {{ translateValid(text) }}
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
        title: "申诉者",
        dataIndex: "username",
        key: "username",
        width: 100
      },
      {
        title: "申诉时间",
        dataIndex: "time",
        key: "time",
        width: 180
      },
      {
        title: "申诉理由",
        dataIndex: "reason",
        key: "reason"
      },
      {
        title: "状态",
        dataIndex: "valid",
        key: "valid",
        scopedSlots: { customRender: "status" },
        width: 80
      }
    ];
    const rowSelection = {
      onChange: (selectedRowKeys, selectedRows) => {
        this.rowSelected = selectedRows;
      }
    };
    return {
      primaryKey: "defenseId",
      tableColumns,
      // 在此编辑测试数据
      dataSource: [
        {
          defenseId: "10000",
          reason: "我没有做任何违法的事情",
          time: "2020-11-09 20:08:30",
          username: "tuenity",
          valid: 0
        },
        {
          defenseId: "10001",
          reason: "好吧，我认了",
          time: "2020-11-09 20:08:30",
          username: "tuenity",
          valid: 0
        }
      ],
      rowSelection,
      rowSelected: []
    };
  },
  methods: {
    translateValid(valid) {
      if (valid == 0) {
        return "待审核";
      } else if (valid == 1) {
        return "通过";
      } else if (valid == 2) {
        return "驳回";
      }
    },
    onSetValid() {
      this.rowSelected.forEach(row => {
        row.valid = 1;
      });
      this.$message.success(`更新记录状态`, 1);
    },
    onSetInvalid() {
      this.rowSelected.forEach(row => {
        row.valid = 2;
      });
      this.$message.success(`更新记录状态`, 1);
    }
  }
};
</script>
<style lang="scss" scoped>
.action-button {
  margin: 1rem;
}
</style>
