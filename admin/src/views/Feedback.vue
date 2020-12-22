<template>
  <div id="review-composition-page">
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
      <a slot="feedback" slot-scope="text">
        <a-popover title="评论内容">
          <template slot="content">
            {{ text }}
          </template>
          <div>{{ text }}</div>
        </a-popover>
      </a>
      <span
        slot="status"
        slot-scope="text"
        :style="{
          color: text == 0 ? '#f5222d' : '#000'
        }"
      >
        {{ translateStatus(text) }}
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
        width: 100
      },
      {
        title: "提交者",
        dataIndex: "username",
        key: "username",
        width: 100
      },
      {
        title: "反馈时间",
        dataIndex: "time",
        key: "time",
        width: 180
      },
      {
        title: "状态",
        dataIndex: "status",
        key: "status",
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
        feedbackId: "10000",
        feedbackBody: "这软件太垃圾了，我遇到的每一个人都比我弱",
        time: "2020-11-09 20:08:30",
        username: "tuenity",
        status: 0,
        feedbackType: "使用建议"
      },
      {
        feedbackId: "10001",
        feedbackBody:
          "我就问，还有谁比我光芒耳机绝对冰天龙人还要强的人，如果有的话，那一定是我的小号-光芒耳机绝对天龙人",
        time: "2020-11-09 20:08:30",
        username: "tuenity",
        status: 0,
        feedbackType: "BUG反馈"
      }
    ];
    return {
      screenWidth: document.body.clientWidth,
      primaryKey: "feedbackId",
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
  created() {
    this.axios
      .get("/feedback")
      .then(res => {
        console.log(res.data);
        // this.dataSource = res.data.data.pushArticleEntityList;
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    translateStatus(status) {
      if (status == 0) {
        return "待处理";
      } else if (status == 1) {
        return "已处理";
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
    onHandle() {
      this.rowSelected.forEach(row => {
        row.status = 1;
      });
      this.$message.success(`更新记录状态`, 1);
    }
  },
  watch: {
    screenWidth() {
      this.dataSource.forEach(
        item => (item.brief = this.textBrief(item.feedbackBody))
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
