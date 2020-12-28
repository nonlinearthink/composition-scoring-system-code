let testData = [
  {
    pred: [
      "with",
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
    ],
    id: "1",
    sen_id: 0
  },
  {
    pred: [
      "in",
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
    ],
    id: "1",
    sen_id: 1
  },
  {
    pred: [
      "so",
      "they",
      "always",
      "go",
      "to",
      "bed",
      "very",
      "late",
      "to",
      "watch",
      "tvs",
      "or",
      "play",
      "games",
      "."
    ],
    id: "1",
    sen_id: 2
  },
  {
    pred: [
      "in",
      "addition",
      ",",
      "it",
      "takes",
      "students",
      "too",
      "much",
      "money",
      "to",
      "buy",
      "famous",
      "products",
      "."
    ],
    id: "1",
    sen_id: 3
  },
  {
    pred: [
      "apart",
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
      "tv",
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
    ],
    id: "1",
    sen_id: 4
  },
  {
    pred: [
      "they",
      "are",
      "staying",
      "up",
      "too",
      "late",
      "to",
      "lack",
      "energy",
      "frequently",
      "."
    ],
    id: "1",
    sen_id: 5
  },
  {
    pred: [
      "also",
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
    ],
    id: "1",
    sen_id: 6
  },
  {
    pred: [
      "in",
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
      "therefore",
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
    ],
    id: "1",
    sen_id: 7
  },
  {
    pred: ["we", "should", "overcome", "these", "unhealthy", "habits", "."],
    id: "1",
    sen_id: 8
  },
  {
    pred: [
      "as",
      "far",
      "as",
      "i",
      "am",
      "concerned",
      ",",
      "i",
      "think",
      "only",
      "strong",
      "will",
      "we",
      "make",
      "a",
      "success",
      "."
    ],
    id: "1",
    sen_id: 9
  }
];

function mergeSentence(splitedText) {
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

function renderRightText(origin) {
  // 对数组执行深拷贝
  let correctResult = JSON.parse(JSON.stringify(testData));
  // 对数组进行排序
  correctResult.sort((item1, item2) => item1.sen_id - item2.sen_id);
  //   // 文章分段
  let paragraphList = origin.split("\n");
  let currentSentence = 0;
  let result = "";
  for (let paraId = 0; paraId < paragraphList.length; paraId++) {
    let sentenceList = paragraphList[paraId]
      .split(".")
      .map(sentence => `${sentence}.`.trim());
    let paragraph = "";
    for (let senId = 0; senId < sentenceList.length; senId++) {
      if (currentSentence == correctResult[0].sen_id) {
        paragraph +=
          "<span style='red'>" +
          mergeSentence(correctResult[0].pred) +
          "</span>";
      } else {
        paragraph += sentenceList[senId];
      }
      currentSentence++;
    }
    result += "<p>" + paragraph + "</p>";
  }
  return result;
}

// function showText(str) {
//   // 对数组执行深拷贝
//   let correctResult = JSON.parse(JSON.stringify(testData));
//   // 对数组进行排序
//   correctResult.sort((item1, item2) => {
//     if (item1.sen_id == item2.sen_id) {
//       return item1.token_id - item2.token_id;
//     }
//     return item1.sen_id - item2.sen_id;
//   });
//   // 文章分段
//   let paragraphList = str.split("\n");
//   // 保存当前的句子ID
//   let currentSentence = 0;
//   let renderText = "";
//   let lastEffect = false;
//   for (let paraId = 0; paraId < paragraphList.length; paraId++) {
//     let sentenceList = paragraphList[paraId]
//       .split(".")
//       .map(sentence => `${sentence}.`.trim());
//     let paragraph = "";
//     for (let senId = 0; senId < sentenceList.length; senId++) {
//       if (lastEffect) {
//         senId--;
//         //   currentSentence--;
//         lastEffect = false;
//       }
//       if (senId == correctResult[0].sen_id) {
//         let originString = sentenceList[senId].substring(
//           0,
//           correctResult[0].token_id
//         );
//         let replaceString = sentenceList[senId].substring(
//           correctResult[0].token_id,
//           sentenceList[senId].length
//         );
//         let replaceWords = replaceString.split(" ");
//         replaceWords.splice(0, correctResult[0].error_len);
//         correctResult[0].pred.push(...replaceWords);
//         let finalResult =
//           originString +
//           "<span color='red'>" +
//           mergeSentence(correctResult[0].pred) +
//           "</span>";
//         paragraph += finalResult;
//         correctResult.splice(0, 1);
//         lastEffect = true;
//         currentSentence++;
//       } else {
//         if (currentSentence < senId) {
//           paragraph += sentenceList[senId];
//           currentSentence++;
//         }
//       }
//     }
//     renderText += `<p>${paragraph}</p>`;
//   }
//   return renderText;
// }

// function splitWith(str) {
//     strList = str.split(/\b/);
//     result = []
//     strList.forEach(element => {
//         for (let i = 0; i < str.length; i++){
//             if (str.charAt(i).match(",")) {
//                 str.split()
//             }
//         }
//     });
//     let wordCache = "";
//     for (let i = 0; i < str.length; i++) {
//         if (str[i].match(/[\b]/))
//   }
// }

renderRightText(
  `With the development of the social, there are widespread habits of college students. In their mind, the college students's daily life is so boring that need exciting something. So they always go to bed very late to watch TVs or play games. In addition, it takes students too much money to buy famous products.
Apart of students use class time to play game or watch TV so that they couldn't focus attention on studying courses. They are staying up too late to lack energy frequently. Also they consume excessively,which gives their parents too much borden.
In fact, we are all do the best to go to college, therefore, it is advisable for us to study hard as soon as possible. We should overcome these unhealth habits. As far as I am concerned that I think only strong will can we make a success.`
);
