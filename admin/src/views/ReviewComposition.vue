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
      dataSource: [
        {
          compositionId: "10000",
          title: "test",
          compositionBody:
            "It is widely acknowledged that unhealthy habits of college students has become an increasingly serious problem. Taking a look around, one can find numerous examples with ease, staying up all night,being addicted to games. Why such a phenomenon emerges?The reason, which is obvious to all, can be listed as follows.Firstly, college life is different from the time during senior high school.Secondly,people always do what others doing, college students are easily form a bad habit if one person have it.It will give rise to a host of severe problems if we leave the situation as it is, first and foremost,the bad habit is harmful to health. In light of the seriousness of the problem, due attention has to be paid to this issue, in the first place,college students should be aware of the harm of the unhealthy habits and develop the realization of cultivation of good habits.With these measures taken,it is reasonable for us to believe that the problem will be solved in the near future.",
          description:
            "赶在周五的发布会之前，GTK 开发博客已经抢先宣布了 GTK 4.0 开源工具包的正式到来。虽然没有与 GNOME 保持同步，但这至少可让开发团队在年底前腾出更多时间，为明年春季的 GNOME 40 的发布和移植做好准备。",
          visibility: 1,
          releaseTime: "2020-11-09 20:08:30",
          username: "tuenity",
          valid: 0
        },
        {
          compositionId: "10001",
          title: "我是强者",
          compositionBody:
            "Different places have different customs .The Spring Festival in My Hometown,a few days before it,many will go out to purchase,New Year's eve that day we'll stick in your own door cowplet,each major market will hang a lantern.In the New Year's eve that night the family get together to eat hot pot,watching the Spring Festival evening party,the next day morning, to eat glutinous rice balls to represent one family together.then,back to ShangFen.",
          description:
            "首先是外观方面，GTK 4.0 引入了新的小部件元素，并对现有元素进行了重新制作。同时集成了对媒体播放的支持，GPU 加速改进（Vulkan 渲染器）和针对 macOS 的更好支持。此外还有数据传输方面的改进、大幅修整的着色器、GPU 加速的滚动，易于制作的自定义小部件、Vulkan 之外的 OpenGL 渲染改进，HTML5 Broadway、以及更好的 Windows 支持等。",
          visibility: 2,
          releaseTime: "2020-11-09 20:08:30",
          username: "tuenity",
          valid: 1
        },
        {
          compositionId: "10002",
          title: "性感荷官在线发牌",
          compositionBody:
            "The bar chart shows the China's growing percentage of computer users in youth from 2% in 1965 to 50% in 2005, simultaneously, the graph of China's percentage of short-sighted persons who use computers in youth sharply went up from 5% to 60% during the same period. We all know that as the improvement of China people's living standards, the computer be used by more and more people, especially in youth.However, when they use a computer, some bad habit would result that short-sighted of eyes.For example, youth like playing computer games for a long time, eyes be forced to expose on the computer radiation, the youth vision will decline because the unhealthy behaviors. As for me, when we play the computer, we need a bright condition.Otherwise, we should have a rest when we use computer for a long time.Furthermore, we could rub the eyes after use computer.",
          description:
            "ACM 杰出科学家（Distinguished Member）评选从 2006 年开始，意在表彰至少有 15 年的专业经验、至少具有5年的ACM专业会员资格、并在计算机领域做出了杰出贡献或产生巨大影响的 ACM 会员。ACM 杰出科学家的评选比例不超过 ACM 会员的前 10%。",
          visibility: 3,
          releaseTime: "2020-11-09 20:08:30",
          username: "tuenity",
          valid: 2
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
