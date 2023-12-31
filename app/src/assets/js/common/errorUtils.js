function mergeGrammer(splitedText) {
  // 合并预测结果，注意标点符号和普通单词的区别
  let mergedText = splitedText.reduce((total, item) => {
    return item.charAt(item.length - 1).match(/[,.!?]/) == null
      ? total + " " + item
      : total + item;
  });
  // 处理最后一个单词或标点后无空格的问题
  mergedText += " ";
  // 首字母替换成大写
  return mergedText.replace(mergedText[0], mergedText[0].toUpperCase());
}
function parseGrammerError(text, errorInfo) {
  console.log("语法解析");
  console.log(errorInfo);
  // 对数组执行深拷贝
  let correctResult = JSON.parse(JSON.stringify(errorInfo));
  // 对数组进行排序
  correctResult.sort((item1, item2) => item1.sen_id - item2.sen_id);
  // 文章分段
  let paragraphList = text
    .split("\n")
    .filter(item => item && item != "")
    .map(item => {
      item = item.trim();
      if (!item.match(/[.?!]$/)) {
        item += ".";
      }
      return item;
    });
  console.log(paragraphList);
  let currentSentence = 0; // 记录句子ID
  let result = []; // 存放结果
  for (let paraId = 0; paraId < paragraphList.length; paraId++) {
    // 匹配分隔符
    let splitTokens = paragraphList[paraId].match(/[.?!]/g);
    console.log("匹配分割符");
    console.log(splitTokens);
    // 按分隔符分句和格式化
    let sentenceList = paragraphList[paraId]
      .split(/[.?!]/)
      .filter(item => item && item != "")
      .map(
        (sentence, index) => `${sentence}${splitTokens[index]}`.trim() + " "
      );
    console.log("分割后的句子集合");
    console.log(sentenceList);
    let paragraph = []; // 存放段落
    for (let senId = 0; senId <= sentenceList.length - 1; senId++) {
      // 如果句子ID和纠错句子ID匹配上，则进入处理
      if (
        correctResult.length > 0 &&
        currentSentence == correctResult[0].sen_id
      ) {
        // 合并句子
        let advice = mergeGrammer(correctResult[0].pred);
        // 判断是否相同
        if (advice != sentenceList[senId]) {
          // 不同则添加错误信息
          paragraph.push({
            senId: currentSentence,
            origin: sentenceList[senId],
            error: true,
            advice
          });
        }
        // 删除一条纠错记录
        correctResult.splice(0, 1);
      }
      // 更新当前的句子ID
      currentSentence++;
    }
    if (paragraph.length > 0) {
      // 添加段落
      result.push({ paraId, paragraph });
    }
  }
  // 返回结果
  console.log(result);
  return result;
}
function mergeWord(splitedText) {
  // 合并预测结果，注意标点符号和普通单词的区别
  let mergedText = splitedText.reduce((total, item) => total + " " + item);
  // 处理最后一个单词或标点后无空格的问题
  mergedText += " ";
  // 首字母替换成大写
  return mergedText.replace(mergedText[0], mergedText[0].toUpperCase());
}

function parseWordError(text, errorInfo) {
  // 对数组执行深拷贝
  let correctResult = JSON.parse(JSON.stringify(errorInfo));
  // 对数组进行排序
  correctResult.sort((item1, item2) => item1.sen_id - item2.sen_id);
  // 文章分段
  let paragraphList = text
    .split("\n")
    .filter(item => item && item != "")
    .map(item => {
      item = item.trim();
      if (!item.match(/[.?!]$/)) {
        item += ".";
      }
      return item;
    });
  // console.log(paragraphList);
  let currentSentence = 0; // 记录句子ID
  let result = ""; // 存放结果
  for (let paraId = 0; paraId < paragraphList.length; paraId++) {
    // 匹配分隔符
    let splitTokens = paragraphList[paraId].match(/[.?!]/g);
    // console.log(splitTokens);
    // 按分隔符分句和格式化
    let sentenceList = paragraphList[paraId]
      .split(/[.?!]/)
      .filter(item => item)
      .map(
        (sentence, index) => `${sentence}`.trim() + splitTokens[index] + " "
      );
    // console.log(sentenceList);
    let paragraph = ""; // 存放段落
    for (let senId = 0; senId < sentenceList.length; senId++) {
      // 如果句子ID和纠错句子ID匹配上，则进入处理
      if (
        correctResult.length > 0 &&
        currentSentence == correctResult[0].sen_id
      ) {
        let splitedSentence = sentenceList[senId]
          .split(" ")
          .filter(item => item != "");
        correctResult[0].error_location.forEach(index => {
          if (correctResult[0].pred[index] == "-unk-") {
            splitedSentence[
              index
            ] = `&nbsp;<span style="position: relative;"><span style="text-decoration: underline; text-decoration-color: orange;">&nbsp;&nbsp;&nbsp;</span><span style="position: absolute; left: 0.2rem; color: orange">${"?"}</span></span>&nbsp;`;
          } else {
            splitedSentence[
              index
            ] = `&nbsp;&nbsp;<span style="position: relative;"><span style="text-decoration: underline; text-decoration-color: orange;">${splitedSentence[index]}</span><span style="position: absolute; top: -1.2rem; left: 0rem; color: orange">${correctResult[0].pred[index]}</span></span>&nbsp;&nbsp;`;
          }
        });
        // 合并句子
        let advice = mergeWord(splitedSentence);
        paragraph += advice;
        // 删除一条纠错记录
        correctResult.splice(0, 1);
      } else {
        paragraph += sentenceList[senId];
      }
      // 更新当前的句子ID
      currentSentence++;
    }
    // 添加段落
    result += "<p style='line-height: 2rem;'>" + paragraph + "</p>";
  }
  // 返回结果
  return result;
}

// parseWordError();

export default { parseGrammerError, parseWordError };
