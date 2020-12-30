# 语法纠错

## 有关整个语法纠错模型解析流程

首先，作文会被分词和分句，分句默认按照**结束标点号**来分隔,比方说 

```bash
. ？ !
```

e.g. 输入文本：

```bash
Last year my father lost his job. At that time my parents felt a bit sad. I encouraged my father and said I was old enough and could do something to help. In order to help my parents, I took a part time job on weekends in the KFC near my home.Luckily, it didnt take long time for my father to find a new job in a company. With the money I earned through working I bought a pair of new shoes for my father to celebrate the good news. My parents were deeply moved.
```

被分句之后，变成了7个句子，每个句子之间以换行符分隔：

```bash
Last year my father lost his job.
At that time my parents felt a bit sad.
I encouraged my father and said I was old enough and could do something to help.
In order to help my parents, I took a part time job on weekends in the KFC near my home.
Luckily, it didnt take long time for my father to find a new job in a company.
With the money I earned through working I bought a pair of new shoes for my father to celebrate the good news.
My parents were deeply moved.
```

之后这样的文本会被输入给模型预测，模型是按照一行行读入预测，所以最后模型的输出格式应该是和输入一样的，也就是还是7行：

```bash
Last year my father lost his job .
At that time , my parents felt a bit sad .
I encouraged my father and said I was old enough and could do something to help .
In order to help my parents , I took a part - time job on weekends at the KFC near my home .
Luckily , it did nt take long for my father to find a new job in a company .
With the money I earned through working , I bought a pair of new shoes for my father to celebrate the good news .
My parents were deeply moved .
```

之后上述两段文本，也就是原文本（ori）和预测文本(pred)会被**逐行比对**，由于时间复杂度问题这里不用很精确的子串匹配算法，用的是最简单的整行匹配，也就是**如果这一行和模型预测不一致，那么返回的就是整行的纠错信息**，包括标点符号！

## 有关返回内容解释

返回的json是一个python list，listl里面每个元素是一个python dict，每个元素有如下属性：

```
{"id": "006", "sen_id": 1, "token_id": 0, "error_len": 9, "pred": ["at", "that", "time", ",", "my", "parents", "felt", "a", "bit", "sad", "."]}
```

- **id** : 作文的id号，从redis里面取出来的
- **sen_id**: 正如之前所说，纠错是按照逐行来匹配的，所以sen_id意味着这篇文章被分句之后**所在的句子的序号**
- **token_id** :这行句子里，出现错误的token的位置。注意，这里的位置指的是**字符的位置**，而且句子中间的空格符、标点号也算。
-  **error_len**：错误部分的长度，这里指的是token的个数，不包括空格符，包括标点符号。
- **pred**: 针对该部分错误，模型的纠错结果，是一个python list，里面每个元素是小写的token

e.g.

原始句：

```bash
In order to help my parents, I took a part time job on weekends in the KFC near my home.
```

预测句：

```bash
In order to help my parents , I took a part - time job on weekends at the KFC near my home .
```

可以看到，这句话模型纠错的部分是在part和time之间补上了一个"-"，所以对应的这个dict的内容如下：

```bash
 {"id": "006", 
 "sen_id": 3, 
 "token_id": 29, 
 "error_len": 14, 
 "pred": ["i", "took", "a", "part", "-", "time", "job", "on", "weekends", "at", "the", "kfc", "near", "my", "home"]
```

id,sen_id就不多说了，token_id为什么是29呢？从原始句的**第一个字符**开始数,0,12,......数到29,刚好数到 “I” ，这就是错误开始的地方，然后error_len是14，那就从**第一个word** “I”开始数，0,1,2,....数到14，刚好就是整个句子“I took a part time job on weekends in the KFC near my home.”结束，而pred就是这段错误的纠错结果，为了方便前端更灵活展示，所以我就以token为分隔了。

我说了因为难度太大，我就以整句句子为单位来返回纠错信息，所以这里其实就少了个中横线，就返回整个句子的报错，现在时间有限先这么做，这个等到后面有时间再去优化。前端的话可以做的柔和一些，因为那么一点点错就报红之类的不好，那就把纠错信息做成类似于**标注**的形式，比方说上面这句句子的右上角来个小标签，点开是显示的纠错内容。



# 单词纠错

## 单词纠错流程

单词纠错发生在**语法纠错分词分句之后**，即分词分句之后返回的processed_context会传给java后端，交由单词纠错匹配处理，匹配的时候，先逐个token遍历，判断这个token是否是字符（要排除空格之类还有数字，标点的影响），如果是的话就去数据库匹配。

单词纠错返回信息格式类似于上述语法纠错，即一个python list,list中还是存放dict

每个dict元素属性如下：

- id :和语法纠错一致
- sen_id:和语法纠错一致
- token_id：和语法纠错一致

e.g.

```bash
I love yhis cat .
```

这句话返回的纠错信息就是：

```bash
{"id":"018",
"sen_id":0,
"token_id":7}
```

还是和语法纠错一样，token_id指的是**字符计数的下标**，这里的话“yhis”这个单词是错的，那么从第7个字符的位置开始算，字符也是包括空格类和标点号的。由于单词本来就是以一个单词为单元，所以就没有error_len一说。



# 通篇评分/词汇丰富/篇幅得分

**通篇评分**指的是用一个模型直接去预估这篇文章分数有多少，可以把他理解成一个黑盒映射，返回的信息很简单：

- id：文章id
- score: 预估分数

模型预测得分之后，直接把这个score写到数据库里就行。

此外，**词汇丰富/篇幅得分**这两个也是一样，直接用函数去预测获得分数，这两个分数也和上面一样，存到数据库。



# 总结

所以前端总共要拿出五个信息：

1. **语法纠错**
2. **单词纠错**
3. **通篇评分**
4. **词汇评分**
5. **篇幅评分**

直接从数据库里，把对应文章id的这几个字段内容取出来。其中3-5都是一个分数（可能是float，要看后端），1和2如上述取出来是一个数据结构，里面包含不止一条纠错信息，依次显示就好。

***需要注意***：

- 因为是按照. ? ! 分句之后来纠错的，所以前端也要以这些标号分隔句子，这样才能对应上sen_id。
- token_id按照字符计数，而且所有空格类字符（比方说“ ”，“\n”）理论上都是只能算一个字符的，所以前端在显示的时候也应该这么做才能对应上正确的token_id。
- error_len是按照word来计数的，而且**包括标点号**，所以前端也应该把sentence分词之后,才能对应上error_len,显示有错误的span。