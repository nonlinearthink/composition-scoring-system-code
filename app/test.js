let testData = {
  compareResultWord: [
    {
      id: "1",
      sen_id: 0,
      pred: [
        "With",
        "the",
        "development",
        "of",
        "the",
        "social,",
        "there",
        "are",
        "widespread",
        "habits",
        "of",
        "college",
        "students"
      ]
    },
    {
      id: "1",
      sen_id: 1,
      pred: [
        "In",
        "their",
        "mind,",
        "the",
        "college",
        "students's",
        "daily",
        "life",
        "is",
        "so",
        "boring",
        "that",
        "need",
        "exciting",
        "something"
      ]
    },
    {
      id: "1",
      sen_id: 2,
      pred: [
        "So",
        "they",
        "always",
        "go",
        "to",
        "bed",
        "very",
        "late",
        "to",
        "watch",
        "vs",
        "or",
        "play",
        "games"
      ]
    },
    {
      id: "1",
      sen_id: 3,
      pred: [
        "In",
        "addition,",
        "it",
        "takes",
        "students",
        "too",
        "much",
        "money",
        "to",
        "buy",
        "famous",
        "products"
      ]
    },
    {
      id: "1",
      sen_id: 4,
      pred: [
        "Apart",
        "of",
        "students",
        "use",
        "class",
        "time",
        "to",
        "play",
        "game",
        "or",
        "watch",
        "TO",
        "so",
        "that",
        "they",
        "couldn't",
        "focus",
        "attention",
        "on",
        "studying",
        "courses"
      ]
    },
    {
      id: "1",
      sen_id: 5,
      pred: [
        "They",
        "are",
        "staying",
        "up",
        "too",
        "late",
        "to",
        "lack",
        "energy",
        "frequently"
      ]
    },
    {
      id: "1",
      sen_id: 6,
      pred: [
        "Also",
        "they",
        "consume",
        "excessively,which",
        "gives",
        "their",
        "parents",
        "too",
        "much",
        "border"
      ]
    },
    {
      id: "1",
      sen_id: 7,
      pred: [
        "In",
        "fact,",
        "we",
        "are",
        "all",
        "do",
        "the",
        "best",
        "to",
        "go",
        "to",
        "college,",
        "therefore,",
        "it",
        "is",
        "advisable",
        "for",
        "us",
        "to",
        "study",
        "hard",
        "as",
        "soon",
        "as",
        "possible"
      ]
    },
    {
      id: "1",
      sen_id: 8,
      pred: ["We", "should", "overcome", "these", "unhealthy", "habits"]
    },
    {
      id: "1",
      sen_id: 9,
      pred: [
        "As",
        "far",
        "as",
        "I",
        "am",
        "concerned",
        "that",
        "I",
        "think",
        "only",
        "strong",
        "will",
        "can",
        "we",
        "make",
        "a",
        "success"
      ]
    }
  ],
  compareResultGrammar: [
    {
      id: "1",
      sen_id: 0,
      pred: [
        "With",
        "the",
        "development",
        "of",
        "society",
        ",",
        "there",
        "are",
        "widespread",
        "habits",
        "of",
        "college",
        "students",
        "."
      ]
    },
    {
      id: "1",
      sen_id: 1,
      pred: [
        "In",
        "their",
        "minds",
        ",",
        "the",
        "college",
        "student",
        "'s",
        "daily",
        "life",
        "is",
        "so",
        "boring",
        "that",
        "they",
        "need",
        "exciting",
        "something",
        "."
      ]
    },
    {
      id: "1",
      sen_id: 4,
      pred: [
        "Apart",
        "from",
        "students",
        "using",
        "class",
        "time",
        "to",
        "play",
        "games",
        "or",
        "watch",
        "TV",
        ",",
        "they",
        "could",
        "n't",
        "focus",
        "attention",
        "on",
        "studying",
        "course",
        "."
      ]
    },
    {
      id: "1",
      sen_id: 6,
      pred: [
        "Also",
        ",",
        "they",
        "consume",
        "excessively",
        ",",
        "which",
        "gives",
        "their",
        "parents",
        "too",
        "much",
        "boundaries",
        "."
      ]
    },
    {
      id: "1",
      sen_id: 7,
      pred: [
        "In",
        "fact",
        ",",
        "we",
        "all",
        "do",
        "our",
        "best",
        "to",
        "go",
        "to",
        "college",
        ".",
        "Therefore",
        ",",
        "it",
        "is",
        "advisable",
        "for",
        "us",
        "to",
        "study",
        "hard",
        "as",
        "soon",
        "as",
        "possible",
        "."
      ]
    },
    {
      id: "1",
      sen_id: 8,
      pred: ["We", "should", "overcome", "these", "unhealthy", "habits", "."]
    },
    {
      id: "1",
      sen_id: 9,
      pred: [
        "As",
        "far",
        "as",
        "I",
        "am",
        "concerned",
        ",",
        "I",
        "think",
        "only",
        "strong",
        "will",
        "we",
        "make",
        "a",
        "success",
        "."
      ]
    }
  ],
  wordScore: 0.9614285714285715,
  grammarScore: 0.30000000000000004,
  sentenceFluencyScore: 0.8382288018862406,
  lengthScore: 0.7066929149017771,
  richnessScore: 0.5383325022485517
};

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
      .map(
        (sentence, index) => `${sentence}${splitTokens[index]}`.trim() + " "
      );
    let paragraph = []; // 存放段落
    for (let senId = 0; senId < sentenceList.length - 1; senId++) {
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
        } else {
          // 相同则直接记录
          paragraph.push({
            senId: currentSentence,
            origin: sentenceList[senId]
          });
        }
        // 删除一条纠错记录
        correctResult.splice(0, 1);
      } else {
        // 直接记录
        paragraph.push({
          senId: currentSentence,
          origin: sentenceList[senId]
        });
      }
      // 更新当前的句子ID
      currentSentence++;
    }
    // 添加段落
    result.push({ paraId, paragraph });
  }
  // 返回结果
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
  let paragraphList = text.split("\n");
  let currentSentence = 0; // 记录句子ID
  let result = []; // 存放结果
  for (let paraId = 0; paraId < paragraphList.length; paraId++) {
    // 匹配分隔符
    let splitTokens = paragraphList[paraId].match(/[.?!]/g);
    // 按分隔符分句和格式化
    let sentenceList = paragraphList[paraId]
      .split(/[.?!]/)
      .map((sentence, index) => `${sentence}`.trim() + " ");
    let paragraph = []; // 存放段落
    for (let senId = 0; senId < sentenceList.length - 1; senId++) {
      // 如果句子ID和纠错句子ID匹配上，则进入处理
      if (
        correctResult.length > 0 &&
        currentSentence == correctResult[0].sen_id
      ) {
        // 合并句子
        let advice = mergeWord(correctResult[0].pred);
        // 判断是否相同
        if (advice != sentenceList[senId]) {
          // 不同则添加错误信息
          paragraph.push({
            senId: currentSentence,
            origin: sentenceList[senId],
            error: true,
            advice
          });
        } else {
          // 相同则直接记录
          paragraph.push({
            senId: currentSentence,
            origin: sentenceList[senId]
          });
        }
        // 删除一条纠错记录
        correctResult.splice(0, 1);
      } else {
        // 直接记录
        paragraph.push({
          senId: currentSentence,
          origin: sentenceList[senId]
        });
      }
      // 更新当前的句子ID
      currentSentence++;
    }
    // 添加段落
    result.push({ paraId, paragraph });
  }
  // 返回结果
  return result;
}

// parseGrammerError(
//   `With the development of the social, there are widespread habits of college students. In their mind, the college students's daily life is so boring that need exciting something. So they always go to bed very late to watch TVs or play games. In addition, it takes students too much money to buy famous products.Apart of students use class time to play game or watch TV so that they couldn't focus attention on studying courses. They are staying up too late to lack energy frequently. Also they consume excessively,which gives their parents too much borden.In fact, we are all do the best to go to college, therefore, it is advisable for us to study hard as soon as possible. We should overcome these unhealth habits. As far as I am concerned that I think only strong will can we make a success.`,
//   testData.compareResultGrammar
// );

parseWordError(
  `With the development of the social, there are widespread habits of college students. In their mind, the college students's daily life is so boring that need exciting something. So they always go to bed very late to watch TVs or play games. In addition, it takes students too much money to buy famous products.Apart of students use class time to play game or watch TV so that they couldn't focus attention on studying courses. They are staying up too late to lack energy frequently. Also they consume excessively,which gives their parents too much borden.In fact, we are all do the best to go to college, therefore, it is advisable for us to study hard as soon as possible. We should overcome these unhealth habits. As far as I am concerned that I think only strong will can we make a success.`,
  testData.compareResultWord
);
