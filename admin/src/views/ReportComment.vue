<template>
  <div id="review-composition-page">
    <a-modal
      v-model="viewVisiable"
      title="快速查看"
      okText="确认"
      @ok="onConfirm"
    >
      <!-- 在此插入编辑用的表单 -->
      <a-descriptions v-if="viewModel == 1">
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
      <a-descriptions v-if="viewModel == 2">
        <a-descriptions-item label="发布者">
          {{ viewTarget.username }}
        </a-descriptions-item>
        <a-descriptions-item label="发布时间" :span="2">
          {{ viewTarget.time }}
        </a-descriptions-item>
        <a-descriptions-item label="内容" :span="3">
          {{ viewTarget.commentBody }}
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
      <span slot="report" slot-scope="text, record">
        <a @click="onQuickView(record)">{{ text }}</a>
      </span>
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
        dataIndex: "reportId",
        key: "reportId",
        width: 80
      },
      {
        title: "内容类型",
        dataIndex: "type",
        key: "type",
        scopedSlots: { customRender: "report" },
        width: 100
      },
      {
        title: "被举报用户",
        dataIndex: "username",
        key: "username",
        width: 200
      },
      {
        title: "举报理由",
        dataIndex: "reportBody",
        key: "reportBody"
      },
      {
        title: "举报时间",
        dataIndex: "time",
        key: "time"
      },
      {
        title: "状态",
        dataIndex: "valid",
        key: "valid",
        scopedSlots: { customRender: "status" },
        width: 100
      }
    ];
    const rowSelection = {
      onChange: (selectedRowKeys, selectedRows) => {
        this.rowSelected = selectedRows;
      }
    };
    return {
      viewVisiable: false,
      viewModel: 0,
      primaryKey: "reportId",
      tableColumns,
      // 在此编辑测试数据
      dataSource: [
        {
          reportId: "10001",
          type: "评论",
          commentBody: "哈哈哈，你是真的菜！",
          time: "2020-11-09 20:08:30",
          username: "tuenity",
          reportBody: "嘲笑和玩弄弱者",
          valid: 0
        },
        {
          reportId: "10002",
          type: "作文",
          title: "性感荷官在线发牌",
          compositionBody:
            "The bar chart shows the China's growing percentage of computer users in youth from 2% in 1965 to 50% in 2005, simultaneously, the graph of China's percentage of short-sighted persons who use computers in youth sharply went up from 5% to 60% during the same period. We all know that as the improvement of China people's living standards, the computer be used by more and more people, especially in youth.However, when they use a computer, some bad habit would result that short-sighted of eyes.For example, youth like playing computer games for a long time, eyes be forced to expose on the computer radiation, the youth vision will decline because the unhealthy behaviors. As for me, when we play the computer, we need a bright condition.Otherwise, we should have a rest when we use computer for a long time.Furthermore, we could rub the eyes after use computer.",
          description:
            "ACM 杰出科学家（Distinguished Member）评选从 2006 年开始，意在表彰至少有 15 年的专业经验、至少具有5年的ACM专业会员资格、并在计算机领域做出了杰出贡献或产生巨大影响的 ACM 会员。ACM 杰出科学家的评选比例不超过 ACM 会员的前 10%。",
          visibility: 3,
          time: "2020-11-09 20:08:30",
          username: "tuenity",
          reportBody: "涉及黄色信息",
          valid: 0
        }
      ],
      rowSelection,
      rowSelected: [],
      viewTarget: null
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
      this.viewVisiable = true;
      this.viewTarget = record;
      if (record.type == "作文") {
        this.viewModel = 1;
      } else {
        this.viewModel = 2;
      }
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
