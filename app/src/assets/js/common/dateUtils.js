/**
 * 计算时间间隔的返回字符串
 *
 * @param {Long} timestamp 时间戳
 * @return 字符串
 */
function timeIntervalString(timestamp) {
  let diff = new Date().getTime() - timestamp;
  let diffText = "";
  if (diff <= 60 * 1000) {
    diffText = "刚刚";
  } else if (diff <= 60 * 60 * 1000) {
    diffText = `${Math.floor(diff / 60000)}分钟前`;
  } else if (diff <= 24 * 60 * 60 * 1000) {
    diffText = `${Math.floor(diff / 3600000)}小时前`;
  } else if (diff <= 7 * 24 * 60 * 60 * 1000) {
    diffText = `${Math.floor(diff / 86400000)}天前`;
  } else {
    let date = new Date(timestamp);
    diffText = `${date.getMonth() + 1}月${date.getDate()}日`;
  }
  return diffText;
}

export default { timeIntervalString };
