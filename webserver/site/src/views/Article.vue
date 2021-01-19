<template>
  <div id="article-page">
    <a-modal v-model="editorVisible" title="编辑" okText="提交" @ok="onUpdate">
      <!-- 在此插入编辑用的表单 -->
      <a-upload
        name="avatar"
        list-type="picture-card"
        class="avatar-uploader"
        :show-upload-list="false"
        :customRequest="onUpload"
        @change="handleChange"
      >
        <img
          v-if="editorForm.avatarUrl"
          :src="editorForm.avatarUrl"
          alt="avatar"
          :style="{ width: '100%', objectFit: 'cover' }"
        />
        <div v-else>
          <a-icon :type="loadingAvatar ? 'loading' : 'plus'" />
          <div class="ant-upload-text">
            Upload
          </div>
        </div>
      </a-upload>
      <a-form-model-item label="推送标题">
        <a-input
          v-model="editorForm.articleTitle"
          placeholder="请输入标题"
          allowClear
        />
      </a-form-model-item>
      <a-form-model-item label="消息内容">
        <a-textarea
          v-model="editorForm.articleBody"
          placeholder="请输入消息内容"
          allowClear
          autoSize
        />
      </a-form-model-item>
    </a-modal>
    <a-modal
      v-model="viewVisible"
      title="快速查看"
      okText="确认"
      @ok="onConfirm"
    >
      <a-descriptions v-if="viewVisible">
        <a-descriptions-item label="推送ID">
          {{ viewTarget.articleId }}
        </a-descriptions-item>
        <a-descriptions-item label="推送标题" :span="2">
          {{ viewTarget.articleTitle }}
        </a-descriptions-item>
        <a-descriptions-item label="编辑者">
          {{ viewTarget.adminName }}
        </a-descriptions-item>
        <a-descriptions-item label="编辑时间" :span="2">
          {{ translateTime(viewTarget.time) }}
        </a-descriptions-item>
        <a-descriptions-item label="推送内容" :span="3">
          {{ viewTarget.articleBody }}
        </a-descriptions-item>
        <a-descriptions-item
          v-if="viewTarget.avatarUrl"
          label="推送图片"
          :span="3"
        >
          <img
            :src="viewTarget.avatarUrl"
            alt="avatar"
            :style="{ width: '100%', objectFit: 'cover' }"
          />
        </a-descriptions-item>
      </a-descriptions>
    </a-modal>
    <a-row type="flex">
      <a-col class="action-button">
        <a-button type="primary" @click="onCreate">
          新建
        </a-button>
      </a-col>
    </a-row>
    <a-table
      :columns="tableColumns"
      :data-source="dataSource"
      :rowKey="primaryKey"
    >
      <!-- 如果有需求，在此自定义每个单元格的样式 -->
      <span slot="articleTitle" slot-scope="text">
        {{ text.length > 8 ? text.substring(0, 8) + "..." : text }}
      </span>
      <span slot="body" slot-scope="text">
        {{ text.length > 28 ? text.substring(0, 28) + "..." : text }}
      </span>
      <span slot="time" slot-scope="text">
        {{ translateTime(text) }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="onView(record)">查看</a>
        <a-divider type="vertical" />
        <a @click="onEdit(record)">编辑</a>
        <a-divider type="vertical" />
        <a-popconfirm title="Sure to delete?" @confirm="onDelete(record)">
          <a>删除</a>
        </a-popconfirm>
      </span>
    </a-table>
  </div>
</template>

<script>
import moment from "moment";
import { mapState } from "vuex";
function getBase64(img, callback) {
  const reader = new FileReader();
  reader.addEventListener("load", () => callback(reader.result));
  reader.readAsDataURL(img);
}
export default {
  data() {
    // 在此定义表结构
    const tableColumns = [
      {
        title: "ID",
        dataIndex: "articleId",
        key: "articleId",
        width: 80
      },
      {
        title: "推送标题",
        dataIndex: "articleTitle",
        key: "articleTitle",
        scopedSlots: { customRender: "articleTitle" },
        width: 160
      },
      {
        title: "推送内容",
        dataIndex: "articleBody",
        scopedSlots: { customRender: "body" },
        key: "articleBody"
      },
      {
        title: "修改时间",
        dataIndex: "time",
        key: "time",
        scopedSlots: { customRender: "time" },
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
        width: 160,
        scopedSlots: { customRender: "action" }
      }
    ];
    return {
      primaryKey: "articleId",
      tableColumns,
      // 在此编辑测试数据
      dataSource: [],
      editorForm: {},
      editorVisible: false,
      editingTarget: null,
      editingStatus: 0,
      viewVisible: false,
      viewTarget: null,
      loadingAvatar: false
    };
  },
  computed: {
    ...mapState(["admin"])
  },
  created() {
    this.axios
      .get("/article")
      .then(res => {
        console.log(res.data);
        this.dataSource = res.data.data.pushArticleEntityList;
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    onDelete(record) {
      this.axios
        .delete(`/article/${record.articleId}`)
        .then(res => {
          console.log(res.data);
          this.dataSource = this.dataSource.filter(
            item => item[this.primaryKey] != record[this.primaryKey]
          );
          this.$message.success(`删除记录${record[this.primaryKey]}成功`, 1);
        })
        .catch(err => console.error(err.response.data));
    },
    onEdit(record) {
      this.editingTarget = record;
      this.editorForm = Object.assign({}, record);
      this.editingStatus = 1;
      this.editorVisible = true;
    },
    onView(record) {
      this.viewTarget = record;
      this.viewVisible = true;
    },
    onCreate() {
      this.editorForm = {
        adminName: this.admin.adminName,
        articleBody: "",
        articleId: "",
        articleTitle: "",
        time: "",
        avatarUrl: null
      };
      this.editingStatus = 0;
      this.editorVisible = true;
    },
    onConfirm() {
      this.viewVisible = false;
    },
    onUpdate() {
      this.editorForm.time = new Date().getTime();
      if (this.editingStatus == 0) {
        this.axios
          .post(`/article`, this.editorForm)
          .then(res => {
            console.log(res.data);
            this.editorForm.articleId = res.data.data.pushArticleId;
            this.dataSource.push(this.editorForm);
            this.$message.success(
              `创建记录${this.editorForm[this.primaryKey]}成功`,
              1
            );
            this.editorVisible = false;
          })
          .catch(err => console.error(err.response.data));
      } else {
        this.axios
          .put(`/article/${this.editingTarget.articleId}`, this.editorForm)
          .then(res => {
            console.log(res.data);
            this.editingTarget.articleTitle = this.editorForm.articleTitle;
            this.editingTarget.articleBody = this.editorForm.articleBody;
            this.editingTarget.time = this.editorForm.time;
            this.editingTarget.adminName = this.admin.adminName;
            this.$message.success(
              `修改记录${this.editingTarget[this.primaryKey]}成功`,
              1
            );
            this.editorVisible = false;
          })
          .catch(err => console.error(err.response.data));
      }
    },
    handleChange(info) {
      if (info.file.status === "uploading") {
        this.loadingAvatar = true;
        return;
      }
      if (info.file.status === "done") {
        getBase64(info.file.originFileObj, imageUrl => {
          this.imageUrl = imageUrl;
          this.loadingAvatar = false;
        });
      }
    },
    onUpload(file) {
      console.log(file);
      let renameFile = new File([file.file], `admin-${Date.now()}`, {
        type: file.type
      });
      file.file = renameFile;
      this.loadingAvatar = true;
      let data = new FormData();
      data.append("img", file.file);
      console.log(data);
      this.axios
        .post("/img", data, {
          headers: { "Content-Type": "multipart/form-data" }
        })
        .then(res => {
          console.log(res);
          this.loadingAvatar = false;
          this.editorForm.avatarUrl = `http://${res.data.data.imgModel.url}`;
        })
        .catch(err => {
          console.error(err);
          this.loadingAvatar = false;
        });
    }
  }
};
</script>

<style lang="scss" scoped>
.action-button {
  margin: 1rem;
}
</style>
