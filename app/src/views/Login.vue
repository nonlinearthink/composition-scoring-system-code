<template>
  <div id="login-page">
    <van-image
      :src="require('../assets/images/icon.svg')"
      fit="scale-down"
      width="6rem"
    />
    <div class="app-name">
      批多多
    </div>
    <van-form @submit="onSubmit">
      <van-field
        class="field"
        v-for="field in form.fields"
        :key="field.name"
        v-model="field.value"
        :name="field.name"
        :type="field.type"
        :placeholder="field.placeholder"
      >
        <template #left-icon>
          <van-icon
            :name="field.icon"
            @click="onClickLeftIcon(field)"
            class="icon"
            :size="form.iconSize"
          />
        </template>
      </van-field>
      <div style="margin: 16px;">
        <van-button round block type="info" native-type="submit">
          提交
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      icon: {},
      form: {
        iconSize: "1.2rem",
        fields: [
          {
            name: "username",
            value: "",
            type: "text",
            placeholder: "用户名",
            icon: "user-circle-o"
          },
          {
            name: "password",
            value: "",
            type: "password",
            placeholder: "密码",
            icon: "closed-eye",
            iconToggle: "eye-o"
          }
        ]
      }
    };
  },
  methods: {
    onClickLeftIcon(field) {
      if (field.name == "password") {
        [field.icon, field.iconToggle] = [field.iconToggle, field.icon];
        field.type = field.type == "text" ? "password" : "text";
      }
    }
  }
};
</script>

<style lang="scss" scoped>
#login-page {
  padding: 0 1rem;
}

.field {
  font-size: 1rem;
  padding: 1rem;
  .icon {
    margin-right: 1rem;
  }
}
</style>
