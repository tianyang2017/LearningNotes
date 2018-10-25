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
    如果值有 toString()方法，则调用该方法（没有参数）并返回相应的结果；
    如果值是 null，则返回"null"；
    如果值是 undefined，则返回"undefined"。
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
    constructor：保存着用于创建当前对象的函数。对于前面的例子而言，构造函数（constructor）就是 Object()。
    hasOwnProperty(propertyName)：用于检查给定的属性在当前对象实例中（而不是在实例的原型中）是否存在。其中，作为参数的属性名（propertyName）必须以字符串形式指定（例如： o.hasOwnProperty("name")）。
    isPrototypeOf(object)：用于检查传入的对象是否是传入对象的原型（第 5 章将讨论原型）。
    propertyIsEnumerable(propertyName)：用于检查给定的属性是否能够使用 for-in 语句（本章后面将会讨论）来枚举。与 hasOwnProperty()方法一样，作为参数的属性名必须以字符
   串形式指定。
    toLocaleString()：返回对象的字符串表示，该字符串与执行环境的地区对应。
    toString()：返回对象的字符串表示。
    valueOf()：返回对象的字符串、数值或布尔值表示。通常与 toString()方法的返回值相同。 

![js 对象属性](D:\学习笔记\picture\js 对象属性.png)



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

### Array 类型

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
    删除：可以删除任意数量的项，只需指定 2 个参数：要删除的第一项的位置和要删除的项数。例如， splice(0,2)会删除数组中的前两项。
    插入：可以向指定位置插入任意数量的项，只需提供 3 个参数：起始位置、 0（要删除的项数）和要插入的项。如果要插入多个项，可以再传入第四、第五，以至任意多个项。例如，splice(2,0,"red","green")会从当前数组的位置 2 开始插入字符串"red"和"green"。
    替换：可以向指定位置插入任意数量的项，且同时删除任意数量的项，只需指定 3 个参数：起始位置、要删除的项数和要插入的任意数量的项。插入的项数不必与删除的项数相等。例如，splice (2,1,"red","green")会删除当前数组位置 2 的项，然后再从位置 2 开始插入字符串"red"和"green"。 

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
    every()：对数组中的每一项运行给定函数，如果该函数对每一项都返回 true，则返回 true。
    filter()：对数组中的每一项运行给定函数，返回该函数会返回 true 的项组成的数组。
    forEach()：对数组中的每一项运行给定函数。这个方法没有返回值。
    map()：对数组中的每一项运行给定函数，返回每次函数调用的结果组成的数组。
    some()：对数组中的每一项运行给定函数，如果该函数对任一项返回 true，则返回 true。
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



### RegExp 类型 	

1. ```
   var expression = / pattern / flags ;
   ```

   其中的模式（pattern）部分可以是任何简单或复杂的正则表达式，可以包含字符类、限定符、分组、向前查找以及反向引用。每个正则表达式都可带有一或多个标志（flags），用以标明正则表达式的行为。
   正则表达式的匹配模式支持下列 3 个标志。
    g：表示全局（global）模式，即模式将被应用于所有字符串，而非在发现第一个匹配项时立即停止；
    i：表示不区分大小写（case-insensitive）模式，即在确定匹配项时忽略模式与字符串的大小写；
    m：表示多行（multiline）模式，即在到达一行文本末尾时还会继续查找下一行中是否存在与模式匹配的项。 

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


### Function 类型

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


### String 类型

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

