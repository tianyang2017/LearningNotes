# 《JavaScript 高级程序设计》读书笔记

## 第三章 基本语法

1. 由于保存浮点数值需要的内存空间是保存整数值的两倍，因此 ECMAScript 会不失时机地将浮点数值转换为整数值。显然，如果小数点后面没有跟任何数字，那么这个数值就可以作为整数值来保存。同样地，如果浮点数值本身表示的就是一个整数（如 1.0），那么该值也会被转换为整数，如下面的例子所示：

   ```javascript
   var floatNum1 = 1.; // 小数点后面没有数字——解析为 1
   var floatNum2 = 10.0; // 整数——解析为 10 
   ```

2. 为了消除在使用parseInt() 函数时可能导致的上述困惑，可以为这个函数提供第二个参数：转换时使用的基数（即多少进制）。如果知道要解析的值是十六进制格式的字符串，那么指定基数 16 作为第二个参数，可以保证得到正确的结果，例如：var num = parseInt("0xAF", 16); //175实际上，如果指定了 16 作为第二个参数，字符串可以不带前面的"0x"，如下所示：

   ```javascript
   var num1 = parseInt("AF", 16); //175
   var num2 = parseInt("AF"); //NaN	
   ```

3. 多数情况下，调用 toString()方法不必传递参数。但是，在调用数值的 toString()方法时，可以传递一个参数：输出数值的基数。默认情况下， toString()方法以十进制格式返回数值的字符串表示。而通过传递基数，toString()可以输出以二进制、八进制、十六进制，乃至其他任意有效进制格式表示的字符串值。下面给出几个例子：

   ```javascript
   var num = 10;
   alert(num.toString()); // "10"
   alert(num.toString(2)); // "1010"
   alert(num.toString(8)); // "12"
   alert(num.toString(10)); // "10"
   alert(num.toString(16)); // "a" 
   ```


4. 在不知道要转换的值是不是 null 或 undefined 的情况下，还可以使用转型函数 String()，这个函数能够将任何类型的值转换为字符串。 String()函数遵循下列转换规则：

   + 如果值有 toString()方法，则调用该方法（没有参数）并返回相应的结果；

   - 如果值是 null，则返回"null"；
   -  如果值是 undefined，则返回"undefined"。
       下面再看几个例子： 

   ```javascript
   var value1 = 10;
   var value2 = true;
   var value3 = null;
   var value4;
   alert(String(value1)); // "10"
   alert(String(value2)); // "true"
   alert(String(value3)); // "null"
   alert(String(value4)); // "undefined" 	
   ```

5. Object 的每个实例都具有下列属性和方法。

   + **constructor**：保存着用于创建当前对象的函数。对于前面的例子而言，构造函数（constructor）就是 Object()。

   + **hasOwnProperty(propertyName)**：用于检查给定的属性在当前对象实例中（而不是在实例的原型中）是否存在。其中，作为参数的属性名（propertyName）必须以字符串形式指定（例如： o.hasOwnProperty("name")）。

   + **isPrototypeOf(object)**：用于检查传入的对象是否是传入对象的原型（第 5 章将讨论原型）。

   + **propertyIsEnumerable(propertyName)**：用于检查给定的属性是否能够使用 for-in 语句（本章后面将会讨论）来枚举。与 hasOwnProperty()方法一样，作为参数的属性名必须以字符
        串形式指定。

   + **toLocaleString()**：返回对象的字符串表示，该字符串与执行环境的地区对应。

   + **toString()**：返回对象的字符串表示。

   + **valueOf()**：返回对象的字符串、数值或布尔值表示。通常与 toString()方法的返回值相同。 

   ![js 对象属性](https://github.com/heibaiying/LearningNotes/blob/master/picture/js%20%E5%AF%B9%E8%B1%A1%E5%B1%9E%E6%80%A7.png)


## 第四章 变量、作用域和内存问题

1. **ECMAScript 中所有函数的参数都是按值传递的。** 

   ```javascript
   function setName(obj) {
   obj.name = "Nicholas";
   }
   var person = new Object();
   setName(person);
   alert(person.name); //"Nicholas"
   ```

   ```javascript
   function setName(obj) {
   obj.name = "Nicholas";
   obj = new Object();
   obj.name = "Greg";
   }
   var person = new Object();
   setName(person);
   alert(person.name); //"Nicholas"
   ```



## 第五章 应用类型

### 1. Array 类型

1. 数组的 length 属性很有特点——它不是只读的。因此，通过设置这个属性，可以从数组的末尾移除项或向数组中添加新项。请看下面的例子： 

   ```javascript
   var colors = ["red", "blue", "green"]; // 创建一个包含 3 个字符串的数组
   colors.length = 2;
   alert(colors[2]); //undefined 
   ```

   这个例子中的数组 colors 一开始有 3 个值。将其 length 属性设置为 2 会移除最后一项（位置为2 的那一项），结果再访问 colors[2]就会显示 undefined 了。如果将其 length 属性设置为大于数组项数的值，则新增的每一项都会取得 undefined 值，如下所示：

   ```javascript
    var colors = ["red", "blue", "green"]; // 创建一个包含 3 个字符串的数组
   colors.length = 4;
   alert(colors[3]); //undefined 
   ```

2. 数组常用方法

   ```javascript
   var colors = ["red", "blue", "green"]; 
   
   alert(colors.toString()); // red,blue,green
   alert(colors.valueOf()); // red,blue,green
   alert(colors); // red,blue,green
   
   alert(colors.join(",")); //red,green,blue
   alert(colors.join("||")); //red||green||blue
   
   
   var colors = new Array(); // 创建一个数组
   var count = colors.push("red", "green"); // 推入两项
   alert(count); //2
   count = colors.push("black"); // 推入另一项
   alert(count); //3
   var item = colors.pop(); // 取得最后一项
   alert(item); //"black"
   alert(colors.length); //2
   
   var item = colors.shift(); //取得第一项
   alert(item); //"red"
   alert(colors.length); //2
   
   
   var colors = new Array(); //创建一个数组
   var count = colors.unshift("red", "green"); //推入两项
   alert(count); //2
   count = colors.unshift("black"); //推入另一项
   alert(count); //3
   var item = colors.pop(); //取得最后一项
   alert(item); //"green"
   alert(colors.length); //2
   ```



3. 数组重排序

   在默认情况下， sort()方法按升序排列数组项——即最小的值位于最前面，最大的值排在最后面。为了实现排序， sort()方法会调用每个数组项的 toString()转型方法，然后比较得到的字符串，以确定如何排序。即使数组中的每一项都是数值， sort()方法比较的也是字符串，如下所示。 

   ```javascript
   var values = [1, 2, 3, 4, 5];
   values.reverse();
   alert(values); //5,4,3,2,1
   
   var values = [0, 1, 5, 10, 15];
   values.sort();
   alert(values); //0,1,10,15,5
   
   //传入比较函数
   function compare(value1, value2) {
   	if (value1 < value2) {
   	return -1;
   	} else if (value1 > value2) {
   	return 1;
   } else {
   return 0;
   	}
   }
   ```



4. 数组操作方法

   splice()的主要用途是向数组的中部插入项，但使用这种方法的方式则有如下 3 种。

   - **删除**：可以删除任意数量的项，只需指定 2 个参数：要删除的第一项的位置和要删除的项数。例如， splice(0,2)会删除数组中的前两项。
   - **插入**：可以向指定位置插入任意数量的项，只需提供 3 个参数：起始位置、 0（要删除的项数）和要插入的项。如果要插入多个项，可以再传入第四、第五，以至任意多个项。例如，splice(2,0,"red","green")会从当前数组的位置 2 开始插入字符串"red"和"green"。
   - **替换**：可以向指定位置插入任意数量的项，且同时删除任意数量的项，只需指定 3 个参数：起始位置、要删除的项数和要插入的任意数量的项。插入的项数不必与删除的项数相等。例如，splice (2,1,"red","green")会删除当前数组位置 2 的项，然后再从位置 2 开始插入字符串"red"和"green"。 

   splice()方法始终都会返回一个数组，该数组中包含从原始数组中删除的项（如果没有删除任何项，则返回一个空数组）。下面的代码展示了上述 3 种使用 splice()方法的方式。 

   ```javascript
   //基于当前数据创建新的数组
   var colors = ["red", "green", "blue"];
   var colors2 = colors.concat("yellow", ["black", "brown"]);
   alert(colors); //red,green,blue
   alert(colors2); //red,green,blue,yellow,black,brown
   
   //slice
   var colors = ["red", "green", "blue", "yellow", "purple"];
   var colors2 = colors.slice(1);
   var colors3 = colors.slice(1,4);
   alert(colors2); //green,blue,yellow,purple
   alert(colors3); //green,blue,yellow
   
   
   var colors = ["red", "green", "blue"];
   var removed = colors.splice(0,1); // 删除第一项
   alert(colors); // green,blue
   alert(removed); // red，返回的数组中只包含一项
   removed = colors.splice(1, 0, "yellow", "orange"); // 从位置 1 开始插入两项
   alert(colors); // green,yellow,orange,blue
   alert(removed); // 返回的是一个空数组
   removed = colors.splice(1, 1, "red", "purple"); // 插入两项，删除一项
   alert(colors); // green,red,purple,orange,blue
   alert(removed); // yellow，返回的数组中只包含一项
   ```



5. 数组迭代方法

   ECMAScript 5 为数组定义了 5 个迭代方法。每个方法都接收两个参数：要在每一项上运行的函数和（可选的）运行该函数的作用域对象——影响 this 的值。传入这些方法中的函数会接收三个参数：数组项的值、该项在数组中的位置和数组对象本身。根据使用的方法不同，这个函数执行后的返回值可能会也可能不会影响方法的返回值。以下是这 5 个迭代方法的作用。

   + **every()**：对数组中的每一项运行给定函数，如果该函数对每一项都返回 true，则返回 true。
   + **filter()**：对数组中的每一项运行给定函数，返回该函数会返回 true 的项组成的数组。
   + **forEach()**：对数组中的每一项运行给定函数。这个方法没有返回值。
   +  **map()**：对数组中的每一项运行给定函数，返回每次函数调用的结果组成的数组。
   +  **some()**：对数组中的每一项运行给定函数，如果该函数对任一项返回 true，则返回 true。
       以上方法都不会修改数组中的包含的值。 

   ```javascript
   var numbers = [1,2,3,4,5,4,3,2,1];
   
   var everyResult = numbers.every(function(item, index, array){
   return (item > 2);
   });
   alert(everyResult); //false
   
   var someResult = numbers.some(function(item, index, array){
   return (item > 2);
   });
   alert(someResult); //true
   
   var filterResult = numbers.filter(function(item, index, array){
   return (item > 2);
   });
   alert(filterResult); //[3,4,5,4,3]
   
   var mapResult = numbers.map(function(item, index, array){
   return item * 2;
   });
   alert(mapResult); //[2,4,6,8,10,8,6,4,2]
   
   numbers.forEach(function(item, index, array){
   //执行某些操作
   });
   ```



6. 归并方法 

   ECMAScript 5 还新增了两个归并数组的方法： reduce()和 reduceRight()。这两个方法都会迭代数组的所有项，然后构建一个最终返回的值。其中， reduce()方法从数组的第一项开始，逐个遍历到最后。而 reduceRight()则从数组的最后一项开始，向前遍历到第一项。这两个方法都接收两个参数：一个在每一项上调用的函数和（可选的）作为归并基础的初始值。传给 reduce()和 reduceRight()的函数接收 4 个参数：前一个值、当前值、项的索引和数组对象。这个函数返回的任何值都会作为第一个参数自动传给下一项。第一次迭代发生在数组的第二项上，因此第一个参数是数组的第一项，第二个参数就是数组的第二项。  

   ```javascript
   var values = [1,2,3,4,5];
   var sum = values.reduce(function(prev, cur, index, array){
   return prev + cur;
   });
   alert(sum); //15
   
   var sum = values.reduceRight(function(prev, cur, index, array){
   return prev + cur;
   });
   alert(sum); //15
   ```



### 2. RegExp 类型 	

1. ```
   var expression = / pattern / flags ;
   ```

   其中的模式（pattern）部分可以是任何简单或复杂的正则表达式，可以包含字符类、限定符、分组、向前查找以及反向引用。每个正则表达式都可带有一或多个标志（flags），用以标明正则表达式的行为。
   正则表达式的匹配模式支持下列 3 个标志。

   + **g**：表示全局（global）模式，即模式将被应用于所有字符串，而非在发现第一个匹配项时立即停止；
   +  **i**：表示不区分大小写（case-insensitive）模式，即在确定匹配项时忽略模式与字符串的大小写；
   +  **m**：表示多行（multiline）模式，即在到达一行文本末尾时还会继续查找下一行中是否存在与模式匹配的项。 

   模式中使用的所有元字符都必须转义。正则表达式中的元字符包括： ( [ { \ ^ $ | ) ? * + .]} 

   ```javascript
   /*
   * 匹配字符串中所有"at"的实例
   */
   var pattern1 = /at/g;
   /*
   * 匹配第一个"bat"或"cat"，不区分大小写
   */
   var pattern2 = /[bc]at/i;
   /*
   * 匹配所有以"at"结尾的 3 个字符的组合，不区分大小写
   */
   var pattern3 = /.at/gi;
   /*
   * 匹配第一个"bat"或"cat"，不区分大小写
   */
   var pattern1 = /[bc]at/i;
   /*
   * 匹配第一个" [bc]at"，不区分大小写
   */
   var pattern2 = /\[bc\]at/i;
   /*
   * 匹配所有以"at"结尾的 3 个字符的组合，不区分大小写
   */
   var pattern3 = /.at/gi;
   /*
   * 匹配所有".at"，不区分大小写
   */
   var pattern4 = /\.at/gi;
   /*
   * 匹配第一个"bat"或"cat"，不区分大小写
   */
   var pattern1 = /[bc]at/i;
   /*
   * 与 pattern1 相同，只不过是使用构造函数创建的
   */
   var pattern2 = new RegExp("[bc]at", "i");
   ```


2. RegExp 对象的主要方法是 exec()，该方法是专门为捕获组而设计的。 exec()接受一个参数，即要应用模式的字符串，然后返回包含第一个匹配项信息的数组；或者在没有匹配项的情况下返回 null。返回的数组虽然是 Array 的实例，但包含两个额外的属性： index 和 input。其中， index 表示匹配项在字符串中的位置，而 input 表示应用正则表达式的字符串。在数组中，第一项是与整个模式匹配的字符串，其他项是与模式中的捕获组匹配的字符串（如果模式中没有捕获组，则该数组只包含一项）。

   ```javascript
   var text = "mom and dad and baby";
   var pattern = /mom( and dad( and baby)?)?/gi;
   var matches = pattern.exec(text);
   alert(matches.index); // 0
   alert(matches.input); // "mom and dad and baby"
   alert(matches[0]); // "mom and dad and baby"
   alert(matches[1]); // " and dad and baby"
   alert(matches[2]); // " and baby"
   ```

   对于 exec()方法而言，即使在模式中设置了全局标志（g），它每次也只会返回一个匹配项。在不设置全局标志的情况下，在同一个字符串上多次调用 exec()将始终返回第一个匹配项的信息。而在设置全局标志的情况下，每次调用 exec()则都会在字符串中继续查找新匹配项，如下面的例子所示。 

   ```javascript
   var text = "cat, bat, sat, fat";
   
   var pattern1 = /.at/;
   var matches = pattern1.exec(text);
   alert(matches.index); //0
   alert(matches[0]); //cat
   alert(pattern1.lastIndex); //0
   matches = pattern1.exec(text);
   alert(matches.index); //0
   alert(matches[0]); //cat
   alert(pattern1.lastIndex); //0
   
   var pattern2 = /.at/g;
   var matches = pattern2.exec(text);
   alert(matches.index); //0
   alert(matches[0]); //cat
   alert(pattern2.lastIndex); //3
   matches = pattern2.exec(text);
   alert(matches.index); //5
   alert(matches[0]); //bat
   alert(pattern2.lastIndex); //8
   
   
   var text = "000-00-0000";
   var pattern = /\d{3}-\d{2}-\d{4}/;
   if (pattern.test(text)){
   alert("The pattern was matched.");
   }
   ```



3. RegExp构造函数属性 

   |   长属性名   | 短属性名 |                            说 明                             |
   | :----------: | :------: | :----------------------------------------------------------: |
   |    input     |    $_    |          最近一次要匹配的字符串。 Opera未实现此属性          |
   |  lastMatch   |    $&    |             最近一次的匹配项。 Opera未实现此属性             |
   |  lastParen   |    $+    |           最近一次匹配的捕获组。 Opera未实现此属性           |
   | leftContext  |    $`    |               input字符串中lastMatch之前的文本               |
   |  multiline   |    $*    | 布尔值，表示是否所有表达式都使用多行模式。 IE和Opera未实现此属性 |
   | rightContext |    $'    |               Input字符串中lastMatch之后的文本               |

   ```javascript
   var text = "this has been a short summer";
   var pattern = /(.)hort/g;
   /*
   * 注意： Opera 不支持 input、 lastMatch、 lastParen 和 multiline 属性
   * Internet Explorer 不支持 multiline 属性
   */
   if (pattern.test(text)){
   alert(RegExp.input); // this has been a short summer
   alert(RegExp.leftContext); // this has been a
   alert(RegExp.rightContext); // summer
   alert(RegExp.lastMatch); // short
   alert(RegExp.lastParen); // s
   alert(RegExp.multiline); // false
   }
   
   if (pattern.test(text)){
   alert(RegExp.$_); // this has been a short summer
   alert(RegExp["$`"]); // this has been a
   alert(RegExp["$'"]); // summer
   alert(RegExp["$&"]); // short
   alert(RegExp["$+"]); // s
   alert(RegExp["$*"]); // false
   }
   
   //RegExp.$1、 RegExp.$2…RegExp.$9，分别用于存储第一、第二……第九个匹配的捕获组。
   var text = "this has been a short summer";
   var pattern = /(..)or(.)/g;
   if (pattern.test(text)){
   alert(RegExp.$1); //sh
   alert(RegExp.$2); //t
   }
   ```


### 3. Function 类型

1. 每个函数都包含两个非继承而来的方法： apply()和 call()。这两个方法的用途都是在特定的作用域中调用函数，实际上等于设置函数体内 this 对象的值。首先， apply()方法接收两个参数：一个是在其中运行函数的作用域，另一个是参数数组。其中，第二个参数可以是 Array 的实例，也可以是arguments 对象。例如： 

   ```javascript
   function sum(num1, num2){
   return num1 + num2;
   }
   function callSum1(num1, num2){
   return sum.apply(this, arguments); // 传入 arguments 对象
   }
   function callSum2(num1, num2){
   return sum.apply(this, [num1, num2]); // 传入数组
   }
   alert(callSum1(10,10)); //20
   alert(callSum2(10,10)); //20
   
   /*call()方法与 apply()方法的作用相同，它们的区别仅在于接收参数的方式不同。对于 call() 方法而言，第一个参数是 this 值没有变化，变化的是其余参数都直接传递给函数。换句话说，在使用call()方法时，传递给函数的参数必须逐个列举出来，如下面的例子所示。
    */
   function sum(num1, num2){
   return num1 + num2;
   }
   function callSum(num1, num2){
   return sum.call(this, num1, num2);
   }
   alert(callSum(10,10)); //20
   
   
   /*传递参数并非 apply()和 call()真正的用武之地；它们真正强大的地方是能够扩充函数赖以运行的作用域。下面来看一个例子。*/
   window.color = "red";
   var o = { color: "blue" };
   function sayColor(){
   alert(this.color);
   }
   sayColor(); //red
   sayColor.call(this); //red
   sayColor.call(window); //red
   sayColor.call(o); //blue
   ```

2. ECMAScript 5 还定义了一个方法： bind()。这个方法会创建一个函数的实例，其 this 值会被绑定到传给 bind()函数的值。例如： 

   ```javascript
   window.color = "red";
   var o = { color: "blue" };
   function sayColor(){
   alert(this.color);
   }
   var objectSayColor = sayColor.bind(o);
   objectSayColor(); //blue
   ```


### 4. String 类型

1. ECMAScript还提供了三个基于子字符串创建新字符串的方法： slice()、 substr()和 substring()。这三个方法都会返回被操作字符串的一个子字符串，而且也都接受一或两个参数。第一个参数指定子字符串的开始位置，第二个参数（在指定的情况下）表示子字符串到哪里结束。具体来说， slice()和substring()的第二个参数指定的是子字符串最后一个字符后面的位置。而 substr()的第二个参数指定的则是返回的字符个数。如果没有给这些方法传递第二个参数，则将字符串的长度作为结束位置。与concat()方法一样， slice()、 substr()和 substring()也不会修改字符串本身的值——它们只是返回一个基本类型的字符串值，对原始字符串没有任何影响。请看下面的例子。  

   ```javascript
   var stringValue = "hello world";
   alert(stringValue.slice(3)); //"lo world"
   alert(stringValue.substring(3)); //"lo world"
   alert(stringValue.substr(3)); //"lo world"
   alert(stringValue.slice(3, 7)); //"lo w"
   alert(stringValue.substring(3,7)); //"lo w"
   alert(stringValue.substr(3, 7)); //"lo worl"
   ```


2. 字符串的模式匹配

   ```javascript
   //match
   var text = "cat, bat, sat, fat";
   var pattern = /.at/;
   //与 pattern.exec(text)相同
   var matches = text.match(pattern);
   alert(matches.index); //0
   alert(matches[0]); //"cat"
   alert(pattern.lastIndex); //0
   
   //search
   var text = "cat, bat, sat, fat";
   var pos = text.search(/at/);
   alert(pos); //1
   
   
   //replace
   ar text = "cat, bat, sat, fat";
   var result = text.replace("at", "ond");
   alert(result); //"cond, bat, sat, fat"
   result = text.replace(/at/g, "ond");
   alert(result); //"cond, bond, sond, fond"
   
   var text = "cat, bat, sat, fat";
   result = text.replace(/(.at)/g, "word ($1)");
   alert(result); //word (cat), word (bat), word (sat), word (fat)
   
   function htmlEscape(text){
   return text.replace(/[<>"&]/g, function(match, pos, originalText){
       switch(match){
           case "<":
           	return "&lt;";
           case ">":
           	return "&gt;";
           case "&":
           	return "&amp;";
           case "\"":
           	return "&quot;";
           }
       });
   }
   alert(htmlEscape("<p class=\"greeting\">Hello world!</p>"));
   //&lt;p class=&quot;greeting&quot;&gt;Hello world!&lt;/p&gt;
   
   
   /*split()，这个方法可以基于指定的分隔符将一个字符串分割成多个子字符串，并将结果放在一个数组中。分隔符可以是字符串，也可以是一个 RegExp 对象（这个方法不会将字符串看成正则表达式）。 split()方法可以接受可选的第二个参数，用于指定数组的大小，以便确保返回的数组不会超过既定大小。请看下面的例子
   */
   var colorText = "red,blue,green,yellow";
   var colors1 = colorText.split(","); //["red", "blue", "green", "yellow"]
   var colors2 = colorText.split(",", 2); //["red", "blue"]
   var colors3 = colorText.split(/[^\,]+/); //["", ",", ",", ",", ""]
   ```

## 第六章 面向对象的程序设计

### 6.1 理解对象

####  1. 属性类型

1. ECMAScript 中有两种属性：数据属性和访问器属性。
   1. **数据属性**
       数据属性包含一个数据值的位置。在这个位置可以读取和写入值。数据属性有 4 个描述其行为的特性。
         **[[Configurable]]**：表示能否通过 delete 删除属性从而重新定义属性，能否修改属性的特性，或者能否把属性修改为访问器属性。像前面例子中那样直接在对象上定义的属性，它们的这个特性默认值为 true。
         **[[Enumerable]]**：表示能否通过 for-in 循环返回属性。像前面例子中那样直接在对象上定义的属性，它们的这个特性默认值为 true。
         **[[Writable]]**：表示能否修改属性的值。像前面例子中那样直接在对象上定义的属性，它们的这个特性默认值为 true。
         **[[Value]]**：包含这个属性的数据值。读取属性值的时候，从这个位置读；写入属性值的时候，把新值保存在这个位置。这个特性的默认值为 undefined。 

     ```javascript
     var person = {};
     Object.defineProperty(person, "name", {
     writable: false,
     value: "Nicholas"
     });
     alert(person.name); //"Nicholas"
     person.name = "Greg";
     alert(person.name); //"Nicholas"
     
     var person = {};
     Object.defineProperty(person, "name", {
     configurable: false,
     value: "Nicholas"
     });
     alert(person.name); //"Nicholas"
     delete person.name;
     alert(person.name); //"Nicholas"
     
     //一旦把属性定义为不可配置的，就不能再把它变回可配置了
     var person = {};
     Object.defineProperty(person, "name", {
     configurable: false,
     value: "Nicholas"
     });
     //抛出错误
     Object.defineProperty(person, "name", {
     configurable: true,
     value: "Nicholas"
     });
     ```

   2. **访问器属性**
      访问器属性不包含数据值；它们包含一对儿 getter 和 setter 函数（不过，这两个函数都不是必需的） 

       **[[Configurable]]**：表示能否通过 delete 删除属性从而重新定义属性，能否修改属性的特性，或者能否把属性修改为数据属性。对于直接在对象上定义的属性，这个特性的默认值为true。
       **[[Enumerable]]**：表示能否通过 for-in 循环返回属性。对于直接在对象上定义的属性，这个特性的默认值为 true。
       **[[Get]]**：在读取属性时调用的函数。默认值为 undefined。
       **[[Set]]**：在写入属性时调用的函数。默认值为 undefined。 

      **访问器属性不能直接定义，必须使用 Object.defineProperty()来定义。** 

      ```javascript
      var book = {
          _year: 2004,
          edition: 1
      	};
      Object.defineProperty(book, "year", {
          get: function(){
          	return this._year;
      	},
          set: function(newValue){
              if (newValue > 2004) {
                  this._year = newValue;
                  this.edition += newValue - 2004;
              }
          }
      });
      book.year = 2005;
      alert(book.edition); //2
      
      
      // 定义多个属性
      var book = {};
      Object.defineProperties(book, {
          _year: {
         		value: 2004
      	},
      	edition: {
      		value: 1
      	},
      	year: {
              get: function(){
                  return this._year;
              },
              set: function(newValue){
                  if (newValue > 2004) {
                      this._year = newValue;
                      this.edition += newValue - 2004;
                  }
              }
      	}
      });
      ```


#### 2. 读取属性

1. 使用 ECMAScript 5 的 Object.getOwnPropertyDescriptor()方法，可以取得给定属性的描述符。这个方法接收两个参数：属性所在的对象和要读取其描述符的属性名称。

```javascript
var book = {};
Object.defineProperties(book, {
    _year: {
    	value: 2004
    },
    edition: {
    	value: 1
    },
    year: {
        get: function(){
       		return this._year;
        },
    set: function(newValue){
            if (newValue > 2004) {
                this._year = newValue;
                this.edition += newValue - 2004;
            }
    	}
    }
});


var descriptor = Object.getOwnPropertyDescriptor(book, "_year");
alert(descriptor.value); //2004
alert(descriptor.configurable); //false

alert(typeof descriptor.get); //"undefined"
var descriptor = Object.getOwnPropertyDescriptor(book, "year");
alert(descriptor.value); //undefined
alert(descriptor.enumerable); //false
alert(typeof descriptor.get); //"function"
```



### 6.2  创建对象

#### 1.  构造函数模式

```javascript
function Person(name, age, job){
    this.name = name;
    this.age = age;
    this.job = job;
    this.sayName = function(){
        alert(this.name);
    };
}
var person1 = new Person("Nicholas", 29, "Software Engineer");
var person2 = new Person("Greg", 27, "Doctor");

alert(person1.constructor == Person); //true
alert(person2.constructor == Person); //true
alert(person1 instanceof Object); //true
alert(person1 instanceof Person); //true
alert(person2 instanceof Object); //true
alert(person2 instanceof Person); //true


//构造函数模式的缺陷
alert(person1.sayName == person2.sayName); //false

//解决办法
function Person(name, age, job){
    this.name = name;
    this.age = age;
    this.job = job;
    this.sayName = sayName;
}
function sayName(){
	alert(this.name);
}
var person1 = new Person("Nicholas", 29, "Software Engineer");
var person2 = new Person("Greg", 27, "Doctor");
```



#### 2.  原型模式

```javascript
function Person(){
}
Person.prototype.name = "Nicholas";
Person.prototype.age = 29;
Person.prototype.job = "Software Engineer";
Person.prototype.sayName = function(){
alert(this.name);
};
var person1 = new Person();
person1.sayName(); //"Nicholas"
var person2 = new Person();
person2.sayName(); //"Nicholas"
alert(person1.sayName == person2.sayName); //true
alert(Person.prototype.constructor=== Person);  //true
alert(Person.prototype == person1.__proto__);  //true
```

![js 原型1](https://github.com/heibaiying/LearningNotes/blob/master/picture/js%20%E5%8E%9F%E5%9E%8B1.png)

![js 原型2](https://github.com/heibaiying/LearningNotes/blob/master/picture/js%20%E5%8E%9F%E5%9E%8B2.png)

```javascript
//覆盖原型中的属性
function Person(){
}
Person.prototype.name = "Nicholas";
Person.prototype.age = 29;
Person.prototype.job = "Software Engineer";
Person.prototype.sayName = function(){
	alert(this.name);
};
var person1 = new Person();
var person2 = new Person();
person1.name = "Greg";
alert(person1.name); //"Greg"—— 来自实例
alert(person2.name); //"Nicholas"—— 来自原型
```

```javascript
//属性恢复
function Person(){
}
Person.prototype.name = "Nicholas";
Person.prototype.age = 29;
Person.prototype.job = "Software Engineer";
Person.prototype.sayName = function(){
	alert(this.name);
};
var person1 = new Person();
var person2 = new Person();
person1.name = "Greg";
alert(person1.name); //"Greg"—— 来自实例
alert(person2.name); //"Nicholas"—— 来自原型
delete person1.name;
alert(person1.name); //"Nicholas"—— 来自原型
```

```javascript
//使用 hasOwnProperty()方法可以检测一个属性是存在于实例中，还是存在于原型中。
function Person(){
}
Person.prototype.name = "Nicholas";
Person.prototype.age = 29;
Person.prototype.job = "Software Engineer";
Person.prototype.sayName = function(){alert(this.name);
};
var person1 = new Person();
var person2 = new Person();
alert(person1.hasOwnProperty("name")); //false
person1.name = "Greg";
alert(person1.name); //"Greg"—— 来自实例
alert(person1.hasOwnProperty("name")); //true
alert(person2.name); //"Nicholas"—— 来自原型
alert(person2.hasOwnProperty("name")); //false
delete person1.name;
alert(person1.name); //"Nicholas"—— 来自原型
alert(person1.hasOwnProperty("name")); //false
```

```javascript
// in 操作符只要通过对象能够访问到属性就返回 true
function Person(){
}
Person.prototype.name = "Nicholas";
Person.prototype.age = 29;
Person.prototype.job = "Software Engineer";
Person.prototype.sayName = function(){
alert(this.name);
};
var person1 = new Person();
var person2 = new Person();
alert(person1.hasOwnProperty("name")); //false
alert("name" in person1); //true
person1.name = "Greg";
alert(person1.name); //"Greg" —— 来自实例
alert(person1.hasOwnProperty("name")); //true
alert("name" in person1); //true
alert(person2.name); //"Nicholas" —— 来自原型
alert(person2.hasOwnProperty("name")); //false
alert("name" in person2); //true
delete person1.name;
alert(person1.name); //"Nicholas" —— 来自原型
alert(person1.hasOwnProperty("name")); //false
alert("name" in person1); //true
```

​	在使用 for-in 循环时，返回的是所有能够通过对象访问的、可枚举的（enumerated）属性，其中既包括存在于实例中的属性，也包括存在于原型中的属性。屏蔽了原型中不可枚举属性（即将[[Enumerable]]标记为 false 的属性）的实例属性也会在 for-in 循环中返回，因为根据规定，所有开发人员定义的属性都是可枚举的。

```javascript
//获取所有可枚举的属性
function Person(){
}
Person.prototype.name = "Nicholas";
Person.prototype.age = 29;
Person.prototype.job = "Software Engineer";
Person.prototype.sayName = function(){
alert(this.name);
};
var keys = Object.keys(Person.prototype);
alert(keys); //"name,age,job,sayName"
var p1 = new Person();
p1.name = "Rob";
p1.age = 31;
var p1keys = Object.keys(p1);
alert(p1keys); //"name,age"

// 获取所有属性 不论是否可以枚举
var keys = Object.getOwnPropertyNames(Person.prototype);
alert(keys); //"constructor,name,age,job,sayName"
```

```javascript
// 更简单的原型语法
function Person(){
}
Person.prototype = {
    name : "Nicholas",
    age : 29,
    job: "Software Engineer",
    sayName : function () {
    alert(this.name);
	}
};
var friend = new Person();
alert(friend instanceof Object); //true
alert(friend instanceof Person); //true
alert(friend.constructor == Person); //false
alert(friend.constructor == Object); //true


// 弊端
function Person(){
}
//这种赋值方式 相当于把Person.prototype指向一个用字面量创建的对象,相当于下面的创建结果
Person.prototype = {
    name : "Nicholas",
    age : 29,
    job: "Software Engineer",
    sayName : function () {
        alert(this.name);
    }
};
console.log(Person.prototype.constructor); //[Function: Object]
var obj = {
    name : "Nicholas",
    age : 29,
    job: "Software Engineer",
    sayName : function () {
        alert(this.name);
    }
};
console.log(obj.constructor);  //[Function: Object]


//更好的创建方式 以这种方式重设 constructor 属性会导致它的[[Enumerable]]特性被设置为 true。默认情况下，原生的 constructor 属性是不可枚举的，
function Person(){
}
Person.prototype = {
    constructor : Person,
    name : "Nicholas",
    age : 29,
    job: "Software Engineer",
    sayName : function () {
    alert(this.name);
	}
};


//最好的创建方式
function Person(){
}
Person.prototype = {
    name : "Nicholas",
    age : 29,
    job : "Software Engineer",
    sayName : function () {
    alert(this.name);
    }
};
//重设构造函数，只适用于 ECMAScript 5 兼容的浏览器
Object.defineProperty(Person.prototype, "constructor", {
    enumerable: false,
    value: Person
});
```

```javascript
// 扩展原生对象的原型方式
alert(typeof Array.prototype.sort); //"function"
alert(typeof String.prototype.substring); //"function"

String.prototype.startsWith = function (text) {
return this.indexOf(text) == 0;
};
var msg = "Hello world!";
alert(msg.startsWith("Hello")); //true
```

```javascript
// 原生模式的弊端
function Person(){
}
Person.prototype = {
    constructor: Person,
    name : "Nicholas",
    age : 29,
    job : "Software Engineer",
    friends : ["Shelby", "Court"],
    sayName : function () {
        alert(this.name);
        }
};
var person1 = new Person();
var person2 = new Person();
person1.friends.push("Van");
alert(person1.friends); //"Shelby,Court,Van"
alert(person2.friends); //"Shelby,Court,Van"
alert(person1.friends === person2.friends); //true
```



#### 3.组合使用构造函数和原型模式（主要使用方式）

```javascript
function Person(name, age, job){
this.name = name;
this.age = age;
this.job = job;
this.friends = ["Shelby", "Court"];
}
Person.prototype = {
constructor : Person,
sayName : function(){
alert(this.name);
}
}
var person1 = new Person("Nicholas", 29, "Software Engineer");
var person2 = new Person("Greg", 27, "Doctor");
person1.friends.push("Van");
alert(person1.friends); //"Shelby,Count,Van"
alert(person2.friends); //"Shelby,Count"
alert(person1.friends === person2.friends); //false
alert(person1.sayName === person2.sayName); //true
```



### 6.3 继承

#### 1. 原型链

```javascript
function Parent(){
    this.property = "parent";
    this.value="common value";
}
Parent.prototype.getParentValue= function(){
    return this.property;
};
Parent.prototype.getValue= function(){
    return this.value;
};
function Son(){
    this.subproperty = "son";
}

Son.prototype = new Parent();
Son.prototype.getSonValue = function (){
    return this.subproperty;
};
var instance = new Son();
console.log(instance.getParentValue());     //parent
console.log(instance.getSonValue());        //son
console.log(instance.getValue());           //common value

//重写原型中的方法
Son.prototype.getValue=function(){
  return this.value+" modified by son"    //common value modified by son
};

console.log(instance.getValue());

// 确定原型和实例的关系
console.log(instance instanceof Object);    //true
console.log(instance instanceof Parent);    //true
console.log(instance instanceof Son);       //true

// 确定原型和实例的关系
console.log(Object.prototype.isPrototypeOf(instance));  //true
console.log(Parent.prototype.isPrototypeOf(instance));  //true
console.log(Son.prototype.isPrototypeOf(instance));     //true
```



#### 2. 组合继承(主要使用方式)

```javascript
function Parent(name) {
    this.name = name;
    this.colors = ["red", "blue", "green"];
}

Parent.prototype.sayName = function () {
    console.log(this.name);
};

function Son(name, age) {
//继承属性
    Parent.call(this, name);
    this.age = age;
}

//继承方法
Son.prototype = new Parent();
Son.prototype.constructor = Son;
Son.prototype.sayAge = function () {
    console.log(this.age);
};
var instance1 = new Son("Nicholas", 29);
instance1.colors.push("black");
console.log(instance1.colors);          //"red,blue,green,black"
instance1.sayName();                    //"Nicholas";
instance1.sayAge();                     //29

var instance2 = new Son("Greg", 27);
console.log(instance2.colors);          //"red,blue,green"
instance2.sayName();                    //"Greg";
instance2.sayAge();                     //27

```



#### 3. 原型链继承

```javascript
var person = {
    name: "Nicholas",
    friends: ["Shelby", "Court", "Van"]
};
var anotherPerson = Object.create(person);
anotherPerson.name = "Greg";
anotherPerson.friends.push("Rob");
var yetAnotherPerson = Object.create(person);
yetAnotherPerson.name = "Linda";
yetAnotherPerson.friends.push("Barbie");

console.log(person.name);               // Nicholas
console.log(yetAnotherPerson.name);     // Linda
console.log(anotherPerson.name);        // Greg
//包含引用类型值的属性始终都会共享相应的值
console.log(person.friends);            // [ 'Shelby', 'Court', 'Van', 'Rob', 'Barbie' ]
console.log(yetAnotherPerson.friends);  // [ 'Shelby', 'Court', 'Van', 'Rob', 'Barbie' ]
console.log(anotherPerson.friends);     // [ 'Shelby', 'Court', 'Van', 'Rob', 'Barbie' ]


var anotherPerson2 = Object.create(person, {
    name: {
        value: "Greg2"
    }
});
console.log(anotherPerson2.name);             //"Greg2"
```



## 第七章 函数表达式

1. 递归的隐藏问题

   ```javascript
   function factorial(num){
       if (num <= 1){
           return 1;
       } else {
           return num * factorial(num-1);
       }
   }
   var anotherFactorial = factorial;
   factorial=function(){
       return 0
   };
   console.log(anotherFactorial(4)); //0
   factorial = null;
   console.log(anotherFactorial(4)); //出错
   
   //稳妥实现方式1
   function factorial(num){
       if (num <= 1){
           return 1;
       } else {
           return num * arguments.callee(num-1);
       }
   }
   
   // 稳妥实现方式2
   var factorial = (function f(num){
       if (num <= 1){
           return 1;
           } else {
           return num * f(num-1);
       }
   });
   ```


2. #### *闭包是指有权访问另一个函数作用域中的变量的函数。*

   ```javascript
   //闭包
   function f(param) {
       // 内部函数持有了外部函数的变量
       return function () {
           return param+10
       }
   }
   let f1 = f(2);
   console.log(f1());   //12
   ```

   ```javascript
   //回调函数和this关键字
   function f(param, fun) {
       for (let i = 0; i < 1000; i++) {
           param++;
       }
       fun(param)
   }
   
   var a = {};
   a.say = f1;
   
   function f1() {
       this.name = 1000;
       var that = this;
       f(222, function (param) {
           console.log("this " + this.name);           // undefined
           console.log("global " + global.name);       // undefined
           console.log("that name " + that.name);      // 1000
           console.log("结果 " + param)                 // 1222
       });
   }
   
   a.say();
   console.log(a.name); //1000
   
   ```



## 第十章 DOM

### 1. Node 类型

![Node节点](D:\学习笔记\picture\Node节点.png)

![Node节点](https://github.com/heibaiying/LearningNotes/blob/master/picture/Node%E8%8A%82%E7%82%B9.png)

```JavaScript
//新增节点
var returnedNode = someNode.appendChild(newNode);

//someNode 有多个子节点
var returnedNode = someNode.appendChild(someNode.firstChild);
alert(returnedNode == someNode.firstChild); //false
alert(returnedNode == someNode.lastChild); //true

//插入后成为最后一个子节点
returnedNode = someNode.insertBefore(newNode, null);
alert(newNode == someNode.lastChild); //true
//插入后成为第一个子节点
var returnedNode = someNode.insertBefore(newNode, someNode.firstChild);
alert(returnedNode == newNode); //true
alert(newNode == someNode.firstChild); //true
//插入到最后一个子节点前面
returnedNode = someNode.insertBefore(newNode, someNode.lastChild);
alert(newNode == someNode.childNodes[someNode.childNodes.length-2]); //true

//替换第一个子节点
var returnedNode = someNode.replaceChild(newNode, someNode.firstChild);
//替换最后一个子节点
returnedNode = someNode.replaceChild(newNode, someNode.lastChild);

//移除第一个子节点
var formerFirstChild = someNode.removeChild(someNode.firstChild);
//移除最后一个子节点
var formerLastChild = someNode.removeChild(someNode.lastChild);

// 深拷贝
var deepList = myList.cloneNode(true); 
alert(deepList.childNodes.length); //3（ IE < 9）或 7（其他浏览器）
// 浅拷贝
var shallowList = myList.cloneNode(false);
alert(shallowList.childNodes.length);
```



### 2.Document类型

2.1 JavaScript 通过 Document 类型表示文档。在浏览器中， document 对象是 HTMLDocument（继承自 Document 类型）的一个实例，表示整个 HTML 页面。而且， document 对象是 window 对象的一个属性，因此可以将其作为全局对象来访问。 

```javascript
var html = document.documentElement; //取得对<html>的引用
alert(html === document.childNodes[0]); //true
alert(html === document.firstChild); //true

var body = document.body; //取得对<body>的引用

//取得文档标题
var originalTitle = document.title;
//设置文档标题
document.title = "New page title";
//取得完整的 URL
var url = document.URL;
//取得域名
var domain = document.domain;
//取得来源页面的 URL
var referrer = document.referrer;

//如果 URL 中包含一个子域名，例如 p2p.wrox.com，那么就只能将 domain 设置为"wrox.com"（URL 中包含"www"，如 www.wrox.com 时，也是如此）。不能将这个属性设置为 URL 中不包含的域，
document.domain = "wrox.com"; // 成功
document.domain = "nczonline.net"; // 出错

//假设页面来自于 p2p.wrox.com 域  允许由紧绷到松散 不允许松散到紧绷
document.domain = "wrox.com"; //松散的（成功）
document.domain = "p2p.wrox.com"; //紧绷的（出错！）！
```

2.2 查找元素

```javascript
//getElementById
var div = document.getElementById("mydiv");

//getElementsByTagName
var images = document.getElementsByTagName("img");
alert(images.length); //输出图像的数量
alert(images[0].src); //输出第一个图像元素的 src 特性
alert(images.item(0).src); //输出第一个图像元素的 src 特性

//对 HTMLCollection 而言，我们可以向方括号中传入数值或字符串形式的索引值。在后台，对数值索引就会调用 item()，而对字符串索引就会调用 namedItem()。
<img src="myimage.gif" name="myImage">
var myImage = images.namedItem("myImage");
var myImage = images["myImage"];


<fieldset>
    <legend>Which color do you prefer?</legend>
        <ul>
        <li><input type="radio" value="red" name="color" id="colorRed">
        <label for="colorRed">Red</label></li>
        <li><input type="radio" value="green" name="color" id="colorGreen">
        <label for="colorGreen">Green</label></li>
        <li><input type="radio" value="blue" name="color" id="colorBlue">
        <label for="colorBlue">Blue</label></li>
    </ul>
</fieldset>
//getElementsByName
var radios = document.getElementsByName("color");
```



### 3.Element类型

```javascript
<div id="myDiv"></div>
var div = document.getElementById("myDiv");
alert(div.tagName); //"DIV"
alert(div.tagName == div.nodeName); //true

if (element.tagName == "div"){ //不能这样比较，很容易出错！
//在此执行某些操作
}
if (element.tagName.toLowerCase() == "div"){ //这样最好（适用于任何文档）
//在此执行某些操作
}

<div id="myDiv" class="bd" title="Body text" lang="en" dir="ltr"></div>
var div = document.getElementById("myDiv");
// 取值
alert(div.id); //"myDiv""
alert(div.className); //"bd"
alert(div.title); //"Body text"
alert(div.lang); //"en"
alert(div.dir); //"ltr"
//赋值
div.id = "someOtherId";
div.className = "ft";
div.title = "Some other text";
div.lang = "fr";
div.dir ="rtl";
//取值
alert(div.getAttribute("id")); //"myDiv"
alert(div.getAttribute("class")); //"bd"
alert(div.getAttribute("title")); //"Body text"
alert(div.getAttribute("lang")); //"en"
alert(div.getAttribute("dir")); //"ltr"
//赋值
div.setAttribute("id", "someOtherId");
div.setAttribute("class", "ft");
div.setAttribute("title", "Some other text");
div.setAttribute("lang","fr");
div.setAttribute("dir", "rtl");
//删除属性
div.removeAttribute("class");

// 创建元素方式1
var div = document.createElement("div");
div.id = "myNewDiv";
div.className = "box";
document.body.appendChild(div);

//创建元素方式2
var div = document.createElement("<div id=\"myNewDiv\" class=\"box\"></div >");

//查找子元素
var ul = document.getElementById("myList");
//选择所有后代元素中标签为li，不论是否是直接子元素还是间接子元素
var items = ul.getElementsByTagName("li"); 
```



### 4.Text类型

文本节点由 Text 类型表示，包含的是可以照字面解释的纯文本内容。 

- appendData(text)：将 text 添加到节点的末尾。
- deleteData(offset, count)：从 offset 指定的位置开始删除 count 个字符。
-  insertData(offset, text)：在 offset 指定的位置插入 text。
-  replaceData(offset, count, text)：用 text 替换从 offset 指定的位置开始到 offset+count 为止处的文本。
- splitText(offset)：从 offset 指定的位置将当前文本节点分成两个文本节点。
-  substringData(offset, count)：提取从 offset 指定的位置开始到 offset+count 为止处的字符串。 

```javascript
//创建文本节点
var textNode = document.createTextNode("<strong>Hello</strong> world!");

//创建文本节点
var element = document.createElement("div");
element.className = "message";
var textNode = document.createTextNode("Hello world!");
element.appendChild(textNode);
var anotherTextNode = document.createTextNode("Yippee!");
element.appendChild(anotherTextNode);
document.body.appendChild(element);

//规范化文本节点 DOM 文档中存在相邻的同胞文本节点很容易导致混乱，因为分不清哪个文本节点表示哪个字符串
var element = document.createElement("div");
element.className = "message";
var textNode = document.createTextNode("Hello world!");
element.appendChild(textNode);
var anotherTextNode = document.createTextNode("Yippee!");
element.appendChild(anotherTextNode);
document.body.appendChild(element);
alert(element.childNodes.length); //2
element.normalize();
alert(element.childNodes.length); //1
alert(element.firstChild.nodeValue); // "Hello world!Yippee!"

//分割文本节点
var element = document.createElement("div");
element.className = "message";
var textNode = document.createTextNode("Hello world!");
element.appendChild(textNode);
document.body.appendChild(element);
var newNode = element.firstChild.splitText(5);
alert(element.firstChild.nodeValue); //"Hello"
alert(newNode.nodeValue); //" world!"
alert(element.childNodes.length); //2
```



### 5.动态创建表格

**为<table>元素添加的属性和方法如下：**

- caption：保存着对<caption>元素（如果有）的指针。
- tBodies：是一个<tbody>元素的 HTMLCollection。
- tFoot：保存着对<tfoot>元素（如果有）的指针。
- tHead：保存着对<thead>元素（如果有）的指针。
- rows：是一个表格中所有行的 HTMLCollection。
- createTHead()：创建<thead>元素，将其放到表格中，返回引用。
- createTFoot()：创建<tfoot>元素，将其放到表格中，返回引用。
- createCaption()：创建<caption>元素，将其放到表格中，返回引用。
- deleteTHead()：删除<thead>元素。
- deleteTFoot()：删除<tfoot>元素。
- deleteCaption()：删除<caption>元素。
- deleteRow(pos)：删除指定位置的行。
-  insertRow(pos)：向 rows 集合中的指定位置插入一行。

**为<tbody>元素添加的属性和方法如下：**

- rows：保存着<tbody>元素中行的 HTMLCollection。
- deleteRow(pos)：删除指定位置的行。
- insertRow(pos)：向 rows 集合中的指定位置插入一行，返回对新插入行的引用。

**为<tr>元素添加的属性和方法如下：**

- cells：保存着<tr>元素中单元格的 HTMLCollection。
- deleteCell(pos)：删除指定位置的单元格。
- insertCell(pos)：向 cells 集合中的指定位置插入一个单元格，返回对新插入单元格的引用。  

```javascript
//创建 table
var table = document.createElement("table");
table.border = 1;
table.width = "100%";
//创建 tbody
var tbody = document.createElement("tbody");
table.appendChild(tbody);
//创建第一行
tbody.insertRow(0);
tbody.rows[0].insertCell(0);
tbody.rows[0].cells[0].appendChild(document.createTextNode("Cell 1,1"));
tbody.rows[0].insertCell(1);
tbody.rows[0].cells[1].appendChild(document.createTextNode("Cell 2,1"));
//创建第二行
tbody.insertRow(1);
tbody.rows[1].insertCell(0);
tbody.rows[1].cells[0].appendChild(document.createTextNode("Cell 1,2"));
tbody.rows[1].insertCell(1);
tbody.rows[1].cells[1].appendChild(document.createTextNode("Cell 2,2"));
//将表格添加到文档主体中
document.body.appendChild(table);
```



## 第十一章 DOM扩展

**1. querySelector()方法** 

querySelector()方法接收一个 CSS 选择符，返回与该模式匹配的第一个元素，如果没有找到匹配的元素，返回 null。 	

```javascript
//取得 body 元素
var body = document.querySelector("body");
//取得 ID 为"myDiv"的元素
var myDiv = document.querySelector("#myDiv");
//取得类为"selected"的第一个元素
var selected = document.querySelector(".selected");
//取得类为"button"的第一个图像元素
var img = document.body.querySelector("img.button");
```

**2.querySelectorAll()方法** 

querySelectorAll()方法接收的参数与 querySelector()方法一样，都是一个 CSS 选择符，但返回的是所有匹配的元素而不仅仅是一个元素。这个方法返回的是一个 NodeList 的实例。 如果没有找到匹配的元素， NodeList 就是空的。 

```javascript
//取得某<div>中的所有<em>元素（类似于 getElementsByTagName("em")）
var ems = document.getElementById("myDiv").querySelectorAll("em");
//取得类为"selected"的所有元素
var selecteds = document.querySelectorAll(".selected");
//取得所有<p>元素中的所有<strong>元素
var strongs = document.querySelectorAll("p strong");

//要取得返回的 NodeList 中的每一个元素，可以使用 item()方法，也可以使用方括号语法，比如：
var i, len, strong;
for (i=0, len=strongs.length; i < len; i++){
    strong = strongs[i]; //或者 strongs.item(i)
    strong.className = "important";
}
```

**3.元素遍历**

Element Traversal API 为 DOM 元素添加了以下 5 个属性。

-  childElementCount：返回子元素（不包括文本节点和注释）的个数。
-  firstElementChild：指向第一个子元素； firstChild 的元素版。
-  lastElementChild：指向最后一个子元素； lastChild 的元素版。
-  previousElementSibling：指向前一个同辈元素； previousSibling 的元素版。
-  nextElementSibling：指向后一个同辈元素； nextSibling 的元素版。

**支持的浏览器为 DOM 元素添加了这些属性，利用这些元素不必担心空白文本节点。**



**4.getElementsByClassName()方法** 

```javascript
//取得所有类中包含"username"和"current"的元素，类名的先后顺序无所谓
var allCurrentUsernames = document.getElementsByClassName("username current");
//取得 ID 为"myDiv"的元素中带有类名"selected"的所有元素
var selected = document.getElementById("myDiv").getElementsByClassName("selected");
```



**5.classList 属性**

classList 属性是新集合类型 DOMTokenList 的实例。与其他 DOM 集合类似，OMTokenList 有一个表示自己包含多少元素的 length 属性，而要取得每个元素可以使用 item()方法，也可以使用方括号语法。此外，这个新类型还定义如下方法。

- add(value)：将给定的字符串值添加到列表中。如果值已经存在，就不添加了。
- contains(value)：表示列表中是否存在给定的值，如果存在则返回 true，否则返回 false。
- remove(value)：从列表中删除给定的字符串。
- toggle(value)：如果列表中已经存在给定的值，删除它；如果列表中没有给定的值，添加它。  

```javascript
<div class="bd user disabled">...</div>

//删除"disabled"类
div.classList.remove("disabled");
//添加"current"类
div.classList.add("current");
//切换"user"类
div.classList.toggle("user");
//确定元素中是否包含既定的类名
if (div.classList.contains("bd") && !div.classList.contains("disabled")){
//执行操作
)
//迭代类名
for (var i=0, len=div.classList.length; i < len; i++){
doSomething(div.classList[i]);
}
```

**6.焦点管理**

```javascript
var button = document.getElementById("myButton");
button.focus();
alert(document.activeElement === button); //true

var button = document.getElementById("myButton");
button.focus();
alert(document.hasFocus()); //true
```

