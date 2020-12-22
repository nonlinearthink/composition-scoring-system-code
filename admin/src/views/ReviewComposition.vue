<template>
  <div id="review-composition-page">
    <a-modal
      v-model="viewVisible"
      title="快速查看"
      okText="确认"
      @ok="onConfirm"
    >
      <!-- 在此插入编辑用的表单 -->
      <a-descriptions v-if="viewVisible">
        <a-descriptions-item label="标题" :span="2">
          {{ viewTarget.title }}
        </a-descriptions-item>
        <a-descriptions-item label="发布者">
          {{ viewTarget.username }}
        </a-descriptions-item>
        <a-descriptions-item label="可见性">
          {{ translateVisibility(viewTarget.visibility) }}
        </a-descriptions-item>
        <a-descriptions-item label="更新时间" :span="2">
          {{ viewTarget.releaseTime }}
        </a-descriptions-item>
        <a-descriptions-item label="内容" :span="3">
          {{ viewTarget.compositionBody }}
        </a-descriptions-item>
        <a-descriptions-item label="描述" :span="3">
          {{ viewTarget.description }}
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
          标记为通过
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
      <a
        slot="composition"
        slot-scope="text, record"
        @click="onQuickView(record)"
      >
        {{ text }}
      </a>
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
        title: "ID",
        dataIndex: "compositionId",
        key: "compositionId",
        width: 80
      },
      {
        title: "作文标题",
        dataIndex: "title",
        key: "title",
        scopedSlots: { customRender: "composition" },
        width: 200
      },
      {
        title: "发布者",
        dataIndex: "username",
        key: "username",
        width: 100
      },
      {
        title: "修改时间",
        dataIndex: "releaseTime",
        key: "releaseTime",
        width: 180
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
      viewVisible: false,
      primaryKey: "compositionId",
      tableColumns,
      // 在此编辑测试数据
      dataSource: [],
      rowSelection,
      rowSelected: [],
      viewTarget: null
    };
  },
  created() {
    this.axios
      .get("/composition/all")
      .then(res => {
        console.log(res.data);
        this.dataSource = res.data.data.compositionList;
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    translateValid(valid) {
      if (valid == 0) {
        return "待审核";
      } else if (valid == 1) {
        return "通过";
      } else if (valid == 2) {
        return "违规";
      }
    },
    translateVisibility(value) {
      let visibilityList = [
        { value: 1, name: "私密" },
        { value: 2, name: "仅粉丝可见" },
        { value: 3, name: "公开" }
      ];
      if (value) {
        let visibility = visibilityList.find(
          visibility => visibility.value == value
        );
        return visibility.name;
      }
      return this.visibilityList[0].name;
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
    },
    onQuickView(record) {
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
