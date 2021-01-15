let testData = {
  compareResultWord: [
    {
      pred: ["i", "love", "you"],
      id: "230",
      error_location: [1],
      sen_id: 0
    }
  ]
};

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
  let paragraphList = text.split("\n");
  let currentSentence = 0; // 记录句子ID
  let result = []; // 存放结果
  for (let paraId = 0; paraId < paragraphList.length; paraId++) {
    // 匹配分隔符
    let splitTokens = paragraphList[paraId].match(/[.?!]/g);
    // 按分隔符分句和格式化
    let sentenceList = paragraphList[paraId]
      .split(/[.?!]/)
      .filter(item => item)
      .map(
        (sentence, index) => `${sentence}`.trim() + splitTokens[index] + " "
      );
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
          splitedSentence[
            index
          ] = `<span color="red">${splitedSentence[index]}</span>${correctResult[0].pred[index]}`;
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
    result += "<p>" + paragraph + "</p>";
  }
  // 返回结果
  return result;
}

// parseGrammerError(
//   `With the development of the social, there are widespread habits of college students. In their mind, the college students's daily life is so boring that need exciting something. So they always go to bed very late to watch TVs or play games. In addition, it takes students too much money to buy famous products.Apart of students use class time to play game or watch TV so that they couldn't focus attention on studying courses. They are staying up too late to lack energy frequently. Also they consume excessively,which gives their parents too much borden.In fact, we are all do the best to go to college, therefore, it is advisable for us to study hard as soon as possible. We should overcome these unhealth habits. As far as I am concerned that I think only strong will can we make a success.`,
//   testData.compareResultGrammar
// );

let ok = parseWordError(`i lov you.`, testData.compareResultWord);
console.log(ok);
