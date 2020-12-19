<template>
  <div id="review-comment-page">
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
      <a slot="comment" slot-scope="text, record">
        <a-popover title="评论内容">
          <template slot="content">
            {{ text }}
          </template>
          <div>{{ record.brief }}</div>
        </a-popover>
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
        dataIndex: "commentId",
        key: "commentId",
        scopedSlots: { customRender: "id" },
        width: 80
      },
      {
        title: "评论内容",
        dataIndex: "commentBody",
        key: "commentBody",
        scopedSlots: { customRender: "comment" }
      },
      {
        title: "发布者",
        dataIndex: "username",
        key: "username"
      },
      {
        title: "修改时间",
        dataIndex: "time",
        key: "time",
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
    const dataSource = [
      {
        commentId: "10000",
        commentBody: "我难道不是最强的吗？",
        time: "2020-11-09 20:08:30",
        username: "tuenity",
        valid: 0
      },
      {
        commentId: "10001",
        commentBody:
          "我是光芒耳机绝对天龙人，之所以叫光芒是因为我每一个见到我的人都会被我亮瞎眼，之所以戴耳机是因为我有钱到可以用钱砸死你全家，之所以叫绝对是因为我无人能敌，之所以叫天龙人是因为你们都不配。",
        time: "2020-11-09 20:08:30",
        username: "tuenity",
        valid: 0
      },
      {
        commentId: "10002",
        commentBody: "哈哈哈，你是真的菜！",
        time: "2020-11-09 20:08:30",
        username: "tuenity",
        valid: 0
      }
    ];
    dataSource.forEach(item => (item.brief = this.textBrief(item.commentBody)));
    return {
      screenWidth: document.body.clientWidth,
      primaryKey: "commentId",
      tableColumns,
      // 在此编辑测试数据
      dataSource,
      rowSelection,
      rowSelected: []
    };
  },
  mounted() {
    window.onresize = () => {
      return (() => {
        this.screenWidth = document.body.clientWidth;
      })();
    };
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
    textBrief(text) {
      if (this.screenWidth < 768) {
        if (text.length >= 5) return text.substring(0, 5) + "...";
      } else if (this.screenWidth < 992) {
        if (text.length >= 11) return text.substring(0, 11) + "...";
      } else if (this.screenWidth < 1200) {
        if (text.length >= 13) return text.substring(0, 13) + "...";
      } else if (this.screenWidth < 1408) {
        if (text.length >= 30) return text.substring(0, 30) + "...";
      } else {
        if (text.length >= 60) return text.substring(0, 60) + "...";
      }
      return text;
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
  },
  watch: {
    screenWidth() {
      this.dataSource.forEach(
        item => (item.brief = this.textBrief(item.commentBody))
      );
    }
  }
};
</script>
<style lang="scss" scoped>
.action-button {
  margin: 1rem;
}
</style>
