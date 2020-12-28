import { shallowMount } from "@vue/test-utils";
import FloatCard from "@/components/FloatCard.vue";
// import "../../src/plugins/vant";

describe("AppLogo.vue", () => {
  it("renders props.msg when passed", () => {
    // const msg = "new message";
    const wrapper = shallowMount(FloatCard, { slots: {} });
    wrapper.find();
    expect(wrapper.find("piduoduo-float-card").exists()).toEqual(false);
  });
});
