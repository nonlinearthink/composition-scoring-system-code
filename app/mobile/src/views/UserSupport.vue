<template>
  <div id="user-support-page">
    hello
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  created() {
    this.axios
      .get("support")
      .then(res => {
        console.log(res.data);
      })
      .catch(err => {
        console.error(err.response.data);
      });
  }
};
</script>

<style lang="scss" scoped></style>
