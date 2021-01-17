let testData = {
  compareResultGrammar: [
    {
      pred: [
        "When",
        "it",
        "comes",
        "to",
        "college",
        "life",
        ",",
        "we",
        "ca",
        "n't",
        "ignore",
        "this",
        "factory",
        "."
      ],
      id: "254",
      sen_id: 0
    },
    {
      pred: [
        "That",
        "on",
        "caps",
        "we",
        "can",
        "see",
        "some",
        "not",
        "good",
        "phenomenon",
        "widespread",
        "which",
        "do",
        "harm",
        "to",
        "their",
        "health",
        "."
      ],
      id: "254",
      sen_id: 1
    },
    {
      pred: [
        "Typical",
        "examples",
        "are",
        "followed",
        "Wednesday",
        ".",
        "Some",
        "of",
        "them",
        "stay",
        "up",
        "too",
        "late",
        ",",
        "which",
        "easily",
        "results",
        "in",
        "waking",
        "up",
        "late",
        ",",
        "even",
        "skipping",
        "classes",
        "."
      ],
      id: "254",
      sen_id: 2
    },
    {
      pred: [
        "Others",
        "consume",
        "too",
        "much",
        "money",
        "in",
        "an",
        "inappropriate",
        "way",
        "."
      ],
      id: "254",
      sen_id: 3
    },
    {
      pred: [
        "Besides",
        ",",
        "many",
        "tend",
        "to",
        "be",
        "addicted",
        "to",
        "computer",
        "games",
        "and",
        "software",
        "."
      ],
      id: "254",
      sen_id: 4
    },
    {
      pred: [
        "It",
        "can",
        "be",
        "noticed",
        "that",
        "the",
        "majority",
        "have",
        "good",
        "learning",
        "of",
        "the",
        "negative",
        "impactments",
        "."
      ],
      id: "254",
      sen_id: 5
    },
    {
      pred: [
        "In",
        "the",
        "first",
        "place",
        ",",
        "they",
        "have",
        "difficulty",
        "focusing",
        "on",
        "lessons",
        "about",
        "what",
        "teachers",
        "say",
        "in",
        "the",
        "daytime",
        "."
      ],
      id: "254",
      sen_id: 6
    },
    {
      pred: [
        "In",
        "addition",
        ",",
        "they",
        "are",
        "likely",
        "to",
        "neglect",
        "their",
        "studies",
        "and",
        "drop",
        "out",
        "."
      ],
      id: "254",
      sen_id: 7
    },
    {
      pred: [
        "They",
        "gradually",
        "become",
        "physically",
        "or",
        "mentally",
        "ill",
        "."
      ],
      id: "254",
      sen_id: 8
    },
    {
      pred: [
        "Even",
        "worse",
        ",",
        "they",
        "may",
        "embark",
        "on",
        "a",
        "crime",
        "."
      ],
      id: "254",
      sen_id: 9
    },
    {
      pred: [
        "Therefore",
        ",",
        "we",
        "just",
        "can",
        "not",
        "leave",
        "youngsters",
        "alone",
        ",",
        "or",
        "they",
        "are",
        "likely",
        "to",
        "be",
        "influenced",
        "by",
        "unhealthy",
        "tendencies",
        "."
      ],
      id: "254",
      sen_id: 10
    },
    {
      pred: [
        "It",
        "'s",
        "high",
        "time",
        "that",
        "college",
        "students",
        "should",
        "take",
        "effective",
        "methods",
        "to",
        "get",
        "rid",
        "of",
        "unhealthy",
        "habits",
        "and",
        "live",
        "a",
        "colorful",
        "life",
        "."
      ],
      id: "254",
      sen_id: 11
    },
    {
      pred: [
        "It",
        "is",
        "advisable",
        "that",
        "college",
        "students",
        "themselves",
        "ought",
        "to",
        "strengthen",
        "awareness",
        "to",
        "lead",
        "a",
        "healthy",
        "life",
        "."
      ],
      id: "254",
      sen_id: 12
    },
    {
      pred: [
        "Furthermore",
        ",",
        "they",
        "ought",
        "to",
        "gather",
        "the",
        "courage",
        "to",
        "participate",
        "in",
        "outdoor",
        "activities",
        "and",
        "go",
        "to",
        "interesting",
        "and",
        "meaningful",
        "clubs",
        "."
      ],
      id: "254",
      sen_id: 13
    },
    {
      pred: [
        "Last",
        "but",
        "not",
        "least",
        ",",
        "students",
        "themselves",
        "should",
        "control",
        "their",
        "lives",
        "in",
        "an",
        "appropriate",
        "way",
        "to",
        "maintain",
        "a",
        "balance",
        "between",
        "playing",
        "and",
        "studying",
        "instead",
        "of",
        "yielding",
        "to",
        "temptation",
        "."
      ],
      id: "254",
      sen_id: 14
    },
    {
      pred: [
        "Only",
        "in",
        "this",
        "way",
        "can",
        "they",
        "make",
        "a",
        "change",
        "and",
        "lead",
        "a",
        "meaningful",
        "and",
        "wonderful",
        "life",
        "on",
        "campus",
        "."
      ],
      id: "254",
      sen_id: 15
    }
  ]
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
  text = text
    .replace("？", "?")
    .replace("。", ".")
    .replace("！", "!");
  // 文章分段
  let paragraphList = text.split("\n").filter(item => item != "");
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
  console.log("语法错误");
  // 返回结果
  console.log(result);
  return result;
}

// parseGrammerError(
//   `With the development of the social, there are widespread habits of college students. In their mind, the college students's daily life is so boring that need exciting something. So they always go to bed very late to watch TVs or play games. In addition, it takes students too much money to buy famous products.Apart of students use class time to play game or watch TV so that they couldn't focus attention on studying courses. They are staying up too late to lack energy frequently. Also they consume excessively,which gives their parents too much borden.In fact, we are all do the best to go to college, therefore, it is advisable for us to study hard as soon as possible. We should overcome these unhealth habits. As far as I am concerned that I think only strong will can we make a success.`,
//   testData.compareResultGrammar
// );

let ok = parseGrammerError(
  "When it comes to collee life,we can't ignore this fact.That on capus we can see some not good phnomenon widspread wich do harm to their health.Typical examples are followed,some of them stay up too late which easily resulting in waking up late even skippig classes.Others consume too much money in an inappopiate way.Besides many are tended to be addicted to coputer games and so.\nIt can be noticed that the masjority has a good learning of the negative impacts.In the first place,they have diffiulty in focusing on lessons about what teachers said in the daytime.In addition,they are likely to negsect their studies and drop out.They gradually become physical or mental ill-being.Even worse,they may embark on the crimes.\nTherefore,we just can not leave youngsters alone,or they are likely to be influenced by unheadlthy tendensdcies.It's high time that the college studients should take effective methods to get rid of unhealthy habits and live a colorful life.It is advisable that college students themselves ought to strengthen the awaeness to lead a healthy life.Furthermore,they ought to gather the courage to participate in outdoor activities and go to intresting and meaningful clubs.La st but not lest,students themselves should control their lives in an appropriate way to maintain a baalance between plaiying and studkkying instead of yielding to the tesmptation.Only in this way,can they make a change and lead a meaningful and wonderful life on campus.\n",
  testData.compareResultGrammar
);
console.log(ok);
