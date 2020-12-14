import Vue from "vue";

import {
  ActionSheet,
  Button,
  Cell,
  CellGroup,
  Checkbox,
  CheckboxGroup,
  Col,
  Dialog,
  Divider,
  Field,
  Form,
  Icon,
  Image as VanImage,
  NavBar,
  Popup,
  Row,
  Sticky,
  Tabbar,
  TabbarItem,
  Tag,
  Toast,
  Uploader
} from "vant";

Vue.use(ActionSheet)
  .use(Button)
  .use(Cell)
  .use(CellGroup)
  .use(Checkbox)
  .use(CheckboxGroup)
  .use(Col)
  .use(Dialog)
  .use(Divider)
  .use(Field)
  .use(Form)
  .use(Icon)
  .use(NavBar)
  .use(Popup)
  .use(Row)
  .use(Sticky)
  .use(Tabbar)
  .use(TabbarItem)
  .use(Tag)
  .use(Toast)
  .use(VanImage)
  .use(Uploader);
