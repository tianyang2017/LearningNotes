# 《深入理解ES6》《ECMAScript 6 入门》读书笔记

## 第一章 块级绑定

### 1. var 声明与变量提升

```javascript
function getValue(condition) {
    if (condition) {
    var value = "blue";
    	// 其他代码
    	return value;
    } else {
    	// value 在此处可访问，值为 undefined
    	return null;
    } 
    	// value 在此处可访问，值为 undefined
}

//提升后 相当于这段代码
function getValue(condition) {
    var value;
    if (condition) {
        value = "blue";
        // 其他代码
        return value;
    } else {
    	return null;
    }
}
```

### 2. let 与 var 的区别

```javascript
//使用var
var funcs = [];
for (var i = 0; i < 10; i++) {
	funcs.push(function() { console.log(i); });
} 
funcs.forEach(function(func) {
	func(); // 输出数值 "10" 十次
})

// 使用let
var funcs = [];
for (let i = 0; i < 10; i++) {
    funcs.push(function() {
    	console.log(i);
    });
} 
funcs.forEach(function(func) {
	func(); // 从 0 到 9 依次输出
})
```

## 第二章 字符串与正则表达式

### 1.字符串扩展

#### 1.1 includes()、startsWith() 、endsWith() 方法

ES6 包含了以下三个方法来满足这类需求：

- `includes()` 方法，在给定文本存在于字符串中的任意位置时会返回 true ，否则返回false ；

- `startsWith()` 方法，在给定文本出现在字符串起始处时返回 true ，否则返回 false ；

- `endsWith()` 方法，在给定文本出现在字符串结尾处时返回 true ，否则返回 false 。 

  每个方法都接受两个参数：需要搜索的文本，以及可选的搜索起始**位置索引**。

  ```javascript
  var msg = "Hello world!";
  console.log(msg.startsWith("Hello")); // true
  console.log(msg.endsWith("!")); // true
  console.log(msg.includes("o")); // true
  console.log(msg.startsWith("o")); // false
  console.log(msg.endsWith("world!")); // true
  console.log(msg.includes("x")); // false
  console.log(msg.startsWith("o", 4)); // true
  console.log(msg.endsWith("o", 8)); // true
  console.log(msg.includes("o", 8)); // false
  ```

#### 1.2 repeat() 方法 

```javascript
console.log("x".repeat(3)); // "xxx"
console.log("hello".repeat(2)); // "hellohello"
console.log("abc".repeat(4)); // "abcabcabcabc"
```

#### 1.3 padStart()，padEnd()方法

ES2017 引入了字符串补全长度的功能。如果某个字符串不够指定长度，会在头部或尾部补全。`padStart()`用于头部补全，`padEnd()`用于尾部补全。

```javascript
'x'.padStart(5, 'ab') // 'ababx'
'x'.padStart(4, 'ab') // 'abax'

'x'.padEnd(5, 'ab') // 'xabab'
'x'.padEnd(4, 'ab') // 'xaba'
```

上面代码中，`padStart`和`padEnd`一共接受两个参数，第一个参数用来指定字符串的最小长度，第二个参数是用来补全的字符串。

如果原字符串的长度，等于或大于指定的最小长度，则返回原字符串。

```javascript
'xxx'.padStart(2, 'ab') // 'xxx'
'xxx'.padEnd(2, 'ab') // 'xxx'
```

如果用来补全的字符串与原字符串，两者的长度之和超过了指定的最小长度，则会截去超出位数的补全字符串。

```javascript
'abc'.padStart(10, '0123456789')
// '0123456abc'
```

如果省略第二个参数，默认使用空格补全长度。

```javascript
'x'.padStart(4) // '   x'
'x'.padEnd(4) // 'x   '
```

### 2.正则表达式扩展

#### 2.1 正则表达式 y 标志 

在 Firefox 实现了对正则表达式 y 标志的专有扩展之后，ES6 将该实现标准化。 y 标志影响正则表达式搜索时的粘连（ sticky ） 属性，它表示从正则表达式的 lastIndex 属性值的位置开始检索字符串中的匹配字符。如果在该位置没有匹配成功，那么正则表达式将停止检索。 

```javascript
var text = "hello1 hello2 hello3",
    pattern = /hello\d\s?/,
    result = pattern.exec(text),
    globalPattern = /hello\d\s?/g,
    globalResult = globalPattern.exec(text),
    stickyPattern = /hello\d\s?/y,
    stickyResult = stickyPattern.exec(text);
console.log(result[0]); // "hello1 "
console.log(globalResult[0]); // "hello1 "
console.log(stickyResult[0]); // "hello1 "
pattern.lastIndex = 1;
globalPattern.lastIndex = 1;
stickyPattern.lastIndex = 1;
result = pattern.exec(text);
globalResult = globalPattern.exec(text);
stickyResult = stickyPattern.exec(text);
console.log(result[0]); // "hello1 "
console.log(globalResult[0]); // "hello2 "
console.log(stickyResult[0]); // Error! stickyResult is null

var text = "hello1 hello2 hello3",
    pattern = /hello\d\s?/,
    result = pattern.exec(text),
    globalPattern = /hello\d\s?/g,
    globalResult = globalPattern.exec(text),
    stickyPattern = /hello\d\s?/y,
    stickyResult = stickyPattern.exec(text);
console.log(result[0]); // "hello1 "
console.log(globalResult[0]); // "hello1 "
console.log(stickyResult[0]); // "hello1 "
console.log(pattern.lastIndex); // 0
console.log(globalPattern.lastIndex); // 7
console.log(stickyPattern.lastIndex); // 7
result = pattern.exec(text);
globalResult = globalPattern.exec(text);
stickyResult = stickyPattern.exec(text);
console.log(result[0]); // "hello1 "
console.log(globalResult[0]); // "hello2 "
console.log(stickyResult[0]); // "hello2 "
console.log(pattern.lastIndex); // 0
console.log(globalPattern.lastIndex); // 14
console.log(stickyPattern.lastIndex); // 14
```

1. 只有调用正则表达式对象上的方法（例如 exec() 与 test() 方法） ， lastIndex 属性才会生效。而将正则表达式作为参数传递给字符串上的方法（例如 match() ） ，并不会体现粘连特性。
2. 当使用 ^ 字符来匹配字符串的起始处时，粘连的正则表达式只会匹配字符串的起始处（或者在多行模式下匹配行首） 。当 lastIndex 为 0 时， ^ 不会让粘连的正则表达式与非粘连的有任何区别；而当 lastIndex 在单行模式下不对应整个字符串起始处，或者当它在多行模式下不对应行首时，粘连的正则表达式永远不会匹配成功。

```javascript
// 判断粘连标志
var pattern = /hello\d/y;
console.log(pattern.sticky); // true

//检测是否支持粘连标志
function hasRegExpY() {
try {
    var pattern = new RegExp(".", "y");
    	return true;
    } catch (ex) {
    	return false;
    }
}
```

#### 2.2 复制正则表达式

```JavaScript
var re1 = /ab/i,
re2 = new RegExp(re1);

//新增标志
var re1 = /ab/i,
// ES5 中会抛出错误, ES6 中可用
re2 = new RegExp(re1, "g");
console.log(re1.toString()); // "/ab/i"
console.log(re2.toString()); // "/ab/g"
console.log(re1.test("ab")); // true
console.log(re2.test("ab")); // true
console.log(re1.test("AB")); // true
console.log(re2.test("AB")); // false          
```

#### 2.3 flags属性 

```javascript
var re = /ab/g;
console.log(re.source); // "ab"
console.log(re.flags); // "g"
```



### 3.模板字符串

替换位允许你将**任何有效的 JS 表达式**嵌入到模板字面量中。 

```javascript
let name = "Nicholas",
message = `Hello, ${name}.`;
console.log(message); // "Hello, Nicholas."

//有效js表达式
let count = 10,
price = 0.25,
message = `${count} items cost $${(count * price).toFixed(2)}.`;
console.log(message); // "10 items cost $2.50."

//嵌套
let name = "Nicholas",
message = `Hello, ${
`my name is ${ name }`
}.`;
console.log(message); // "Hello, my name is Nicholas."
```



## 第三章 函数

### 1.带参数默认值的函数

不传或者传递undefined 都会调用默认值，**传递null是有效的**，不会调用默认值。

```javascript
function makeRequest(url, timeout = 2000, callback) {
// 函数的剩余部分
}

// 使用默认的 timeout
makeRequest("/foo", undefined, function(body) {
doSomething(body);
});
// 使用默认的 timeout
makeRequest("/foo");
// 不使用默认值
makeRequest("/foo", null, function(body) {
doSomething(body);
});
```

**始终依据 arguments 对象来反映初始调用状态。** 

```javascript
function mixArgs(first, second = "b") {
    console.log(arguments.length);  //1
    console.log(first === arguments[0]);  // true
    console.log(second === arguments[1]); // false
    first = "c";
    second = "d";
    console.log(first === arguments[0]);  // false
    console.log(second === arguments[1]); //false
} 
mixArgs("a"); //arguments 反映的是初始化调用状态

function mixArgs(first, second = "b") {
    console.log(arguments.length);  //1
    console.log(first === arguments[0]);  // true
    console.log(second === arguments[1]); // true
    first = "c";
    second = "d";
    console.log(first === arguments[0]);  // false
    console.log(second === arguments[1]); //false
}
mixArgs("a","k"); //arguments 反映的是初始化调用状态
```

**参数默认值最有意思的特性或许就是默认值并不要求一定是基本类型的值**。例如，你可以执行一个函数来产生参数的默认值，就像这样： 

```javascript
function getValue() {
	return 5;
} 
function add(first, second = getValue()) {
	return first + second;
} 
console.log(add(1, 1)); // 2
console.log(add(1)); // 6
```

可以将前面的参数作为后面参数的默认值 ,**反之不行。**

```javascript
function getValue(value) {
	return value + 5;
} 
function add(first, second = getValue(first)) {
	return first + second;
} 
console.log(add(1, 1)); // 2
console.log(add(1)); // 7
```

### 2.使用不具名参数

#### 2.1 剩余参数

剩余参数（rest parameter ） 由三个点（ ... ） 与一个紧跟着的具名参数指定 。

- 剩余参数受到两点限制。一是函数只能有一个剩余参数，并且它必须被放在最后。

- 第二个限制是剩余参数不能在对象字面量的 setter 属性中使用，这意味着如下代码同样会导致语法错误。存在此限制的原因是：对象字面量的 setter 被限定只能使用单个参数；而剩余参数按照定义是不限制参数数量的，因此它在此处不被许可。 

```javascript
let object = {
    // 语法错误：不能在 setter 中使用剩余参数
    set name(...value) {
    // 一些操作
    }
};

function checkArgs(...args) {
console.log(args.length);   //2
console.log(arguments.length); //2
console.log(args[0], arguments[0]);//a  a
console.log(args[1], arguments[1]);//b  b
} 
checkArgs("a", "b");
```

### 3.扩展运算符

```javascript
// ES5解决方案
let values = [25, 50, 75, 100]
console.log(Math.max.apply(Math, values)); // 100

//ES6解决方案
let values = [-25, -50, -75, -100]
console.log(Math.max(...values, 0)); 
```

### 4.ES6的名称属性

```javascript
var doSomething = function doSomethingElse() {
// ...
};
var person = {
    get firstName() {
    	return "Nicholas"
	},
	sayName: function() {
		console.log(this.name);
	}
} 
console.log(doSomething.name); // "doSomethingElse"
console.log(person.sayName.name); // "sayName"
var descriptor = Object.getOwnPropertyDescriptor(person, "firstName");
console.log(descriptor.get.name); // "get firstName"
//person.firstName 实际是个 getter 函数，因此它的名称是 "get firstName" 

var doSomething = function() {
// ...
};
console.log(doSomething.bind().name); // "bound doSomething"
console.log((new Function()).name); // "anonymous"
```

函数名称还有另外两个特殊情况。使用 bind() 创建的函数会在名称属性值之前带有"bound" 前缀；而使用 Function 构造器创建的函数，其名称属性则会有 "anonymous" 前缀。 

### 5.明确函数的双重用途

#### 5.1 在 ES5 中判断函数如何被调用

```javascript
function Person(name) {
if (this instanceof Person) {
	this.name = name; // 使用 new
	} else {
	throw new Error("You must use new with Person.")
	}
} 
var person = new Person("Nicholas");
var notAPerson = Person("Nicholas"); // 抛出错误

//不能避免
function Person(name) {
if (this instanceof Person) {
	this.name = name; // 使用 new
} else {
	throw new Error("You must use new with Person.")
}
} 
var person = new Person("Nicholas");
var notAPerson = Person.call(person, "Michael"); // 奏效了！
```

ES6 引入了 **new.target 元属性**。元属性指的是“非对象”（例如 new ）上的一个属性，并提供关联到它的目标的附加信息。当函数的 [[Construct]] 方法被调用时， new.target 会被填入 new 运算符的作用目标，该目标通常是新创建的对象实例的构造器，并且会成为函数体内部的 this 值。而若 [[Call]] 被执行， new.target 的值则会是undefined 。 

```javascript
function Person(name) {
if (typeof new.target !== "undefined") {
	this.name = name; // 使用 new
} else {
	throw new Error("You must use new with Person.")
}
} 
var person = new Person("Nicholas");
var notAPerson = Person.call(person, "Michael"); // 出错！

//也可以检查 new.target 是否被使用特定构造器进行了调用
function Person(name) {
if (new.target === Person) {
	this.name = name; // 使用 new
} else {
	throw new Error("You must use new with Person.")
}
} 
function AnotherPerson(name) {
Person.call(this, name);
} 
var person = new Person("Nicholas");
var anotherPerson = new AnotherPerson("Nicholas"); // 出错！
```

### 6. 箭头函数

ES6 最有意思的一个新部分就是箭头函数（arrow function ） 。箭头函数正如名称所示那样使用一个“箭头”（ => ） 来定义，但它的行为在很多重要方面与传统的 JS 函数不同：

- 没有 this 、 super 、 arguments ，也没有 new.target 绑定： this 、 super 、arguments 、以及函数内部的 new.target 的值由所在的、最靠近的非箭头函数来决定。
- 不能被使用 new 调用： 箭头函数没有 [[Construct]] 方法，因此不能被用为构造函数，使用 new 调用箭头函数会抛出错误。
- 没有原型： 既然不能对箭头函数使用 new ，那么它也不需要原型，也就是没有prototype 属性。
- 不能更改 this ： this 的值在函数内部不能被修改，在函数的整个生命周期内其值会保持不变。
- 没有 arguments 对象： 既然箭头函数没有 arguments 绑定，你必须依赖于具名参数或剩余参数来访问函数的参数。
- 不允许重复的具名参数： 箭头函数不允许拥有重复的具名参数，无论是否在严格模式下；而相对来说，传统函数只有在严格模式下才禁止这种重复。 

```javascript
var reflect = value => value;
// 有效等价于：
var reflect = function(value) {
return value;
};


var sum = (num1, num2) => num1 + num2;
// 有效等价于：
var sum = function(num1, num2) {
return num1 + num2;
};


var getName = () => "Nicholas";
// 有效等价于：
var getName = function() {
return "Nicholas";
};


var sum = (num1, num2) => {
return num1 + num2;
};
// 有效等价于：
var sum = function(num1, num2) {
return num1 + num2;
};


var doNothing = () => {};
// 有效等价于：
var doNothing = function() {};

//但若箭头函数想要从函数体内向外返回一个对象字面量，就必须将该字面量包裹在圆括号内
var getTempItem = id => ({ id: id, name: "Temp" });
// 有效等价于：
var getTempItem = function(id) {
	return {
    	id: id,
   		name: "Temp"
    };
};
```



#### 6.1 创建立即调用函数表达式

```javascript
//传统方式
let person = function(name) {
return {
    getName: function() {
    	return name;
    	}
    };
}("Nicholas");
console.log(person.getName()); // "Nicholas"

//箭头函数表表达式
let person = ((name) => {
    return {
    getName: function() {
    	return name;
     }
    };
})("Nicholas");
console.log(person.getName()); // "Nicholas"
```

使用传统函数时， (function(){/*函数体*/})(); 与 (function(){/*函数体*/}());这两种方式都是可行的。
但若使用箭头函数，则只有下面的写法是有效的： (() => {/*函数体*/})(); 

#### 6.2 this 绑定

```javascript
var PageHandler = {
id: "123456",
init: function() {
document.addEventListener("click", function(event) {
    this.doSomething(event.type); // 错误 此时这个this指代的是document
    }, false);
},
doSomething: function(type) {
	console.log("Handling " + type + " for " + this.id);
	}
};


var PageHandler = {
id: "123456",
init: function() {
document.addEventListener("click",
	event => this.doSomething(event.type), false);
},
doSomething: function(type) {
	console.log("Handling " + type + " for " + this.id);
	}
};
```

**箭头函数没有 this 绑定，意味着箭头函数内部的 this 值只能通过查找作用域链来确定。**如果箭头函数被包含在一个非箭头函数内，那么 this 值就会与该函数的相等；否则，this 值就会是全局对象（在浏览器中是 window ，在 nodejs 中是 global ） 。 



#### 6.3 箭头函数与数组

```javascript
var result = values.sort((a, b) => a - b);
//能使用回调函数的数组方法（例如 sort() 、 map() 与 reduce() 方法）
```



#### 6.4 没有 arguments 绑定 

尽管箭头函数没有自己的 arguments 对象，但仍然能访问包含它的函数的 arguments 对象。无论此后箭头函数在何处执行，该对象都是可用的。 

```javascript
function createArrowFunctionReturningFirstArg() {
	return () => arguments[0];
} 
var arrowFunction = createArrowFunctionReturningFirstArg(5);
console.log(arrowFunction()); // 5

var sum = (num1, num2) => num1 + num2;
console.log(sum.call(null, 1, 2)); // 3
console.log(sum.apply(null, [1, 2])); // 3
var boundSum = sum.bind(null, 1, 2);
console.log(boundSum()); // 3
```



## 第四章 扩展的对象功能

### 1.对象字面量语法的扩展

#### 1.1 简写

```javascript
function createPerson(name, age) {
return {
    name: name,
    age: age
    };
}

//名称简写相当于
function createPerson(name, age) {
return {
    name,
    age
	};
}


var person = {
    name: "Nicholas",
    sayName: function() {
        console.log(this.name);
        }
};

// 方法简写
var person = {
	name: "Nicholas",
    sayName() {
    console.log(this.name);
    }
};

//对象字面量内的方括号表明该属性名需要计算，其结果是一个字符串。
var suffix = " name";
var person = {
["first" + suffix]: "Nicholas",
["last" + suffix]: "Zakas"
};
console.log(person["first name"]); // "Nicholas"
console.log(person["last name"]); // "Zakas"
```

#### 1.2 Object.is() 方法 

在许多情况下， Object.is() 的结果与 === 运算符是相同的，仅有的例外是：它会认为+0 与 -0 不相等，而且 NaN 等于 NaN 。不过仍然没必要停止使用严格相等运算符，选择 Object.is() ，还是选择 == 或 === ，取决于代码的实际情况。 

```javascript
console.log(+0 == -0); // true
console.log(+0 === -0); // true
console.log(Object.is(+0, -0)); // false
console.log(NaN == NaN); // false
console.log(NaN === NaN); // false
console.log(Object.is(NaN, NaN)); // true
console.log(5 == 5); // true
console.log(5 == "5"); // true
console.log(5 === 5); // true
console.log(5 === "5"); // false
console.log(Object.is(5, 5)); // true
console.log(Object.is(5, "5")); // false
```

#### 1.3 Object.assign() 方法 

  [参见]（http://es6.ruanyifeng.com/#docs/object#Object-assign）

`Object.assign`方法用于对象的合并，将源对象（source）的所有可枚举属性，复制到目标对象（target）。

```javascript
const target = { a: 1 };

const source1 = { b: 2 };
const source2 = { c: 3 };

Object.assign(target, source1, source2);
target // {a:1, b:2, c:3}
```

`Object.assign`方法的第一个参数是目标对象，后面的参数都是源对象。

注意，如果目标对象与源对象有同名属性，或多个源对象有同名属性，则后面的属性会覆盖前面的属性。

```javascript
const target = { a: 1, b: 1 };

const source1 = { b: 2, c: 2 };
const source2 = { c: 3 };

Object.assign(target, source1, source2);
target // {a:1, b:2, c:3}
```

如果只有一个参数，`Object.assign`会直接返回该参数。

```javascript
const obj = {a: 1};
Object.assign(obj) === obj // true
```

如果该参数不是对象，则会先转成对象，然后返回。

```javascript
typeof Object.assign(2) // "object"
```

由于`undefined`和`null`无法转成对象，所以如果它们作为参数，就会报错。

```javascript
Object.assign(undefined) // 报错
Object.assign(null) // 报错
```

如果非对象参数出现在源对象的位置（即非首参数），那么处理规则有所不同。首先，这些参数都会转成对象，如果无法转成对象，就会跳过。这意味着，如果`undefined`和`null`不在首参数，就不会报错。

```javascript
let obj = {a: 1};
Object.assign(obj, undefined) === obj // true
Object.assign(obj, null) === obj // true
```

其他类型的值（即数值、字符串和布尔值）不在首参数，也不会报错。但是，除了字符串会以数组形式，拷贝入目标对象，其他值都不会产生效果。

```javascript
const v1 = 'abc';
const v2 = true;
const v3 = 10;

const obj = Object.assign({}, v1, v2, v3);
console.log(obj); // { "0": "a", "1": "b", "2": "c" }
```

上面代码中，`v1`、`v2`、`v3`分别是字符串、布尔值和数值，结果只有字符串合入目标对象（以字符数组的形式），数值和布尔值都会被忽略。这是因为只有字符串的包装对象，会产生可枚举属性。

```javascript
Object(true) // {[[PrimitiveValue]]: true}
Object(10)  //  {[[PrimitiveValue]]: 10}
Object('abc') // {0: "a", 1: "b", 2: "c", length: 3, [[PrimitiveValue]]: "abc"}
```

上面代码中，布尔值、数值、字符串分别转成对应的包装对象，可以看到它们的原始值都在包装对象的内部属性`[[PrimitiveValue]]`上面，这个属性是不会被`Object.assign`拷贝的。只有字符串的包装对象，会产生可枚举的实义属性，那些属性则会被拷贝。

`Object.assign`拷贝的属性是有限制的，只拷贝源对象的自身属性（不拷贝继承属性），也不拷贝不可枚举的属性（`enumerable: false`）。

```javascript
Object.assign({b: 'c'},
  Object.defineProperty({}, 'invisible', {
    enumerable: false,
    value: 'hello'
  })
)
// { b: 'c' }
```

上面代码中，`Object.assign`要拷贝的对象只有一个不可枚举属性`invisible`，这个属性并没有被拷贝进去。

属性名为 Symbol 值的属性，也会被`Object.assign`拷贝。

```javascript
Object.assign({ a: 'b' }, { [Symbol('c')]: 'd' })
// { a: 'b', Symbol(c): 'd' }
```

**注意点**:

**（1）浅拷贝**

`Object.assign`方法实行的是浅拷贝，而不是深拷贝。也就是说，如果源对象某个属性的值是对象，那么目标对象拷贝得到的是这个对象的引用。

```javascript
const obj1 = {a: {b: 1}};
const obj2 = Object.assign({}, obj1);

obj1.a.b = 2;
obj2.a.b // 2
```

上面代码中，源对象`obj1`的`a`属性的值是一个对象，`Object.assign`拷贝得到的是这个对象的引用。这个对象的任何变化，都会反映到目标对象上面。

**（2）同名属性的替换**

对于这种嵌套的对象，一旦遇到同名属性，`Object.assign`的处理方法是替换，而不是添加。

```javascript
const target = { a: { b: 'c', d: 'e' } }
const source = { a: { b: 'hello' } }
Object.assign(target, source)
// { a: { b: 'hello' } }
```

上面代码中，`target`对象的`a`属性被`source`对象的`a`属性整个替换掉了，而不会得到`{ a: { b: 'hello', d: 'e' } }`的结果。这通常不是开发者想要的，需要特别小心。

一些函数库提供`Object.assign`的定制版本（比如 Lodash 的`_.defaultsDeep`方法），可以得到深拷贝的合并。

**（3）数组的处理**

`Object.assign`可以用来处理数组，但是会把数组视为对象。

```javascript
Object.assign([1, 2, 3], [4, 5])
// [4, 5, 3]
```

上面代码中，`Object.assign`把数组视为属性名为 0、1、2 的对象，因此源数组的 0 号属性`4`覆盖了目标数组的 0 号属性`1`。

**（4）取值函数的处理**

`Object.assign`只能进行值的复制，如果要复制的值是一个取值函数，那么将求值后再复制。

```javascript
const source = {
  get foo() { return 1 }
};
const target = {};

Object.assign(target, source)
// { foo: 1 }
```

上面代码中，`source`对象的`foo`属性是一个取值函数，`Object.assign`不会复制这个取值函数，只会拿到值以后，将这个值复制过去。

### 2.修改对象的原型 

ES5 添加了 **Object.getPrototypeOf()** 方法来从任意指定对象中获取其原型，但仍然缺少在初始化之后更改对象原型的标准方法。ES6 通过添加 **Object.setPrototypeOf()** 方法而改变了这种假定，此方法允许你修改任意指定对象的原型。它接受两个参数：需要被修改原型的对象，以及将会成为前者原型的对象。

```javascript
let person = {
    getGreeting() {
    	return "Hello";
    }
};
let dog = {
    getGreeting() {
    	return "Woof";
    }
};
// 原型为 person
let friend = Object.create(person);
console.log(friend.getGreeting()); // "Hello"
console.log(Object.getPrototypeOf(friend) === person); // true
// 将原型设置为 dog
Object.setPrototypeOf(friend, dog);
console.log(friend.getGreeting()); // "Woof"
console.log(Object.getPrototypeOf(friend) === dog); // true
```

对象原型的实际值被存储在一个内部属性 **[[Prototype]]** 上， Object.getPrototypeOf() 方法会返回此属性存储的值，而 Object.setPrototypeOf() 方法则能够修改该值。 



### 3.使用 super 引用的简单原型访问 

```javascript
let person = {
    getGreeting() {
    	return "Hello";
    }
    };
let dog = {
    getGreeting() {
    	return "Woof";
    }
};
let friend = {
    getGreeting() {
    	return Object.getPrototypeOf(this).getGreeting.call(this) + ", hi!";
    }
};
// 将原型设置为 person
Object.setPrototypeOf(friend, person);
console.log(friend.getGreeting()); // "Hello, hi!"
console.log(Object.getPrototypeOf(friend) === person); // true
// 将原型设置为 dog
Object.setPrototypeOf(friend, dog);
console.log(friend.getGreeting()); // "Woof, hi!"
console.log(Object.getPrototypeOf(friend) === dog); // true



//缺点
let person = {
    getGreeting() {
    	return "Hello";
    }
};
// 原型为 person
let friend = {
    getGreeting() {
    	return Object.getPrototypeOf(this).getGreeting.call(this) + ", hi!";
    }
};
Object.setPrototypeOf(friend, person);
// 原型为 friend
let relative = Object.create(friend);
console.log(person.getGreeting()); // "Hello"
console.log(friend.getGreeting()); // "Hello, hi!"
console.log(relative.getGreeting()); // error!



//ES6 解决方案
let person = {
    getGreeting() {
   		return "Hello";
    }
};
// 原型为 person
let friend = {
    getGreeting() {
    	return super.getGreeting() + ", hi!";
    }
};
Object.setPrototypeOf(friend, person);
// 原型为 friend
let relative = Object.create(friend);
console.log(person.getGreeting()); // "Hello"
console.log(friend.getGreeting()); // "Hello, hi!"
console.log(relative.getGreeting()); // "Hello, hi!"
```



### 4.正式的“方法”定义 

S6 则正式做出了定义：方法是一个拥有 [[HomeObject]] 内部属性的函数，此内部属性指向该方法所属的对象。 

```javascript
let person = {
	// 方法
    getGreeting() {
    	return "Hello";
    }
};
// 并非方法
function shareGreeting() {
	return "Hi!";
}



let person = {
    getGreeting() {
    	return "Hello";
    }
};
// 原型为 person
let friend = {
    getGreeting() {
    	return super.getGreeting() + ", hi!";
	}
};
Object.setPrototypeOf(friend, person);
console.log(friend.getGreeting()); // "Hello, hi!"
```



## 第五章 结构：更方便的数据访问

### 1.对象解构

```javascript
//1.对象结构
let node = {
    type: "Identifier",
    name: "foo"
};
let { type, name } = node;
console.log(type); // "Identifier"
console.log(name); // "foo"


let node = {
    type: "Identifier",
    name: "foo"
},
type = "Literal",
name = 5;
// 使用解构来分配不同的值
({ type, name } = node);
console.log(type); // "Identifier"
console.log(name); // "foo"


//2. 传递值给函数
let node = {
    type: "Identifier",
    name: "foo"
},
type = "Literal",
name = 5;
function outputInfo(value) {
	console.log(value === node); // true
} 
outputInfo({ type, name } = node);
console.log(type); // "Identifier"
console.log(name); // "foo"

//3. 默认值
let node = {
    type: "Identifier",
    name: "foo"
};
let { type, name, value = true } = node;
console.log(type); // "Identifier"
console.log(name); // "foo"
console.log(value); // true


//4. 赋值给不同的本地变量名
let node = {
    type: "Identifier",
    name: "foo"
};
let { type: localType, name: localName } = node;
console.log(localType); // "Identifier"
console.log(localName); // "foo"

//5. 赋值给不同的本地变量名 并添加默认值
let node = {
	type: "Identifier"
};
let { type: localType, name: localName = "bar" } = node;
console.log(localType); // "Identifier"
console.log(localName); // "bar

//6. 嵌套对象解构
let node = {
    type: "Identifier",
    name: "foo",
    loc: {
        start: {
        line: 1,
        column: 1
        },
    end: {
        line: 1,
        column: 4
        }
    }
};
let { loc: { start }} = node;
console.log(start.line); // 1
console.log(start.column); // 1

//7.在对象的嵌套解构中同样能为本地变量使用不同的名称
let node = {
    type: "Identifier",
    name: "foo",
    loc: {
        start: {
        line: 1,
        column: 1
        },
    end: {
        line: 1,
        column: 4
        }
    }
};
// 提取 node.loc.start
let { loc: { start: localStart }} = node;
console.log(localStart.line); // 1
console.log(localStart.column); // 1
```

### 2.数组解构

```javascript
// 1.数组解构
let colors = [ "red", "green", "blue" ];
let [ firstColor, secondColor ] = colors;
console.log(firstColor); // "red"
console.log(secondColor); // "green"

let colors = [ "red", "green", "blue" ];
let [ , , thirdColor ] = colors;
console.log(thirdColor); // "blue


//2.解构赋值
let colors = [ "red", "green", "blue" ],
firstColor = "black",
secondColor = "purple";
[ firstColor, secondColor ] = colors;
console.log(firstColor); // "red"
console.log(secondColor); // "green"

//3.互换值
let a = 1,
b = 2;
[ a, b ] = [ b, a ];
console.log(a); // 2
console.log(b); // 1

// 4.默认值
let colors = [ "red" ];
let [ firstColor, secondColor = "green" ] = colors;
console.log(firstColor); // "red"
console.log(secondColor); // "green"

// 5.嵌套解构
let colors = [ "red", [ "green", "lightgreen" ], "blue" ];
let [ firstColor, [ secondColor ] ] = colors;
console.log(firstColor); // "red"
console.log(secondColor); // "green"

// 6.剩余项解构
let colors = [ "red", "green", "blue" ];
let [ firstColor, ...restColors ] = colors;
console.log(firstColor); // "red"
console.log(restColors.length); // 2
console.log(restColors[0]); // "green"
console.log(restColors[1]); // "blue"

// 7.在 ES6 中克隆数组
let colors = [ "red", "green", "blue" ];
let [ ...clonedColors ] = colors;
console.log(clonedColors); //"[red,green,blue]"
```



### 3.混合解构

```javascript
let node = {
    type: "Identifier",
    name: "foo",
    loc: {
        start: {
            line: 1,
            column: 1
        },
        end: {
            line: 1,
            column: 4
        }
    },
    range: [0, 3]
};
let {
    loc: {start},
    range: [startIndex]
} = node;
console.log(start.line); // 1
console.log(start.column); // 1
console.log(startIndex); // 0
```



### 4.参数结构

**解构的参数是必需的** 

```javascript
// 1.参数解构
function setCookie(name, value, { secure, path, domain, expires }) {
// 设置 cookie 的代码
} s
etCookie("type", "js", {
secure: true,
expires: 60000
});

// 2. 解构的参数是必需的 
function setCookie(name, value, { secure, path, domain, expires } = {}) {
// ...
}

// 3. 参数解构默认值
function setCookie(name, value,
{
secure = false,
path = "/",
domain = "example.com",
expires = new Date(Date.now() + 360000000)
} = {}
) {
// ...
}
```



## 第七章 Set与Map

### 1.Set

#### 1.1 Set

主要方法: new Set()、add()、has()、delete()、clear()、foreach()  属性: size

```javascript
//1. Set 不会使用强制类型转换来判断值是否重复
let set = new Set();
set.add(5);
set.add("5");
console.log(set.size); // 2

let set = new Set(),
key1 = {},
key2 = {};
set.add(key1);
set.add(key2);
console.log(set.size); // 2

// 2.数组来初始化一个 Set 
let set = new Set([1, 2, 3, 4, 5, 5, 5, 5]);
console.log(set.size); // 5

// 3.has() 方法来测试某个值是否存在于 Set 中
let set = new Set();
set.add(5);
set.add("5");
console.log(set.has(5)); // true
console.log(set.has(6)); // false

// 4. 移除值
let set = new Set();
set.add(5);
set.add("5");
console.log(set.has(5)); // true
set.delete(5);
console.log(set.has(5)); // false
console.log(set.size); // 1
set.clear();
console.log(set.has("5")); // false
console.log(set.size); // 0

//4.值的遍历
let set = new Set([1, 2]);
set.forEach(function(value, key, ownerSet) {
console.log(key + " " + value);
console.log(ownerSet === set);
});
//1 1
//true
//2 2
//true

//5. 在 foreach 中绑定作用域 两种方式
let set = new Set([1, 2]);
let processor = {
    output(value) {
    console.log(value);
    },
process(dataSet) {
    dataSet.forEach(function(value) {
    this.output(value);
    }, this);
    //如果想在回调函数中使用 this ，你可以给 forEach() 传入一个 this值作为第二个参数
    }
};
processor.process(set);

//使用箭头函数绑定
let set = new Set([1, 2]);
let processor = {
    output(value) {
    console.log(value);
},
process(dataSet) {
	dataSet.forEach((value) => this.output(value));
	}
};
processor.process(set);

//6. 箭头函数转换为数组
let set = new Set([1, 2, 3, 3, 3, 4, 5]),
array = [...set];
console.log(array); // [1,2,3,4,5]

// 7.对垃圾回收的影响
let set = new Set(),
key = {};
set.add(key);
console.log(set.size); // 1
// 取消原始引用
key = null;
console.log(set.size); // 1
// 重新获得原始引用
key = [...set][0];
```

#### 1.2 WeakSet 

WeakSet 使用 WeakSet 构造器来创建，并包含 add() 方法、 has() 方法以及 delete()方法。以下例子使用了这三个方法：

```javascript
let set = new WeakSet(),
key = {};
// 将对象加入 set
set.add(key);
console.log(set.has(key)); // true
set.delete(key);
console.log(set.has(key)); // false

//1.与Set区别
let set = new WeakSet(),
key = {};
// 将对象加入 set
set.add(key);
console.log(set.has(key)); // true
// 移除对于键的最后一个强引用，同时从 Weak Set 中移除
key = null;
```

1. 对于 WeakSet 的实例，若调用 add() 方法时传入了非对象的参数，就会抛出错误（has() 或 delete() 则会在传入了非对象的参数时返回 false ） ；
2. Weak Set 不可迭代，因此不能被用在 for-of 循环中；
3. Weak Set 无法暴露出任何迭代器（例如 keys() 与 values() 方法） ，因此没有任何编程手段可用于判断 Weak Set 的内容；
4. Weak Set 没有 forEach() 方法；
5. Weak Set 没有 size 属性。 

### 2.Map

#### 2.1 Map

Map 与 Set 共享了几个方法，这是有意的，允许你使用相似的方式来与 Map 及 Set 进行交互。以下三个方法在 Map 与 Set 上都存在：

- has(key) ：判断指定的键是否存在于 Map 中；

- delete(key) ：移除 Map 中的键以及对应的值；

- clear() ：移除 Map 中所有的键与值。

  ```javascript
  let map = new Map();
  map.set("name", "Nicholas");
  map.set("age", 25);
  console.log(map.size); // 2
  console.log(map.has("name")); // true
  console.log(map.get("name")); // "Nicholas"
  console.log(map.has("age")); // true
  console.log(map.get("age")); // 25
  map.delete("name");
  console.log(map.has("name")); // false
  console.log(map.get("name")); // undefined
  console.log(map.size); // 1
  map.clear();
  console.log(map.has("name")); // false
  console.log(map.get("name")); // undefined
  console.log(map.has("age")); // false
  console.log(map.get("age")); // undefined
  console.log(map.size); // 0
  
  //1.初始化map
  let map = new Map([["name", "Nicholas"], ["age", 25]]);
  console.log(map.has("name")); // true
  console.log(map.get("name")); // "Nicholas"
  console.log(map.has("age")); // true
  console.log(map.get("age")); // 25
  console.log(map.size); // 2
  
  //2.map遍历
  let map = new Map([ ["name", "Nicholas"], ["age", 25]]);
  map.forEach(function(value, key, ownerMap) {
      console.log(key + " " + value);
      console.log(ownerMap === map);
  });
  //name Nicholas
  //true
  //age 25
  //true
  ```


#### 2.2 WeakMap

```javascript
//1、与Map区别
let map = new WeakMap(),
element = document.querySelector(".element");
map.set(element, "Original");
let value = map.get(element);
console.log(value); // "Original"
// 移除元素
element.parentNode.removeChild(element);
element = null;
// 该 Weak Map 在此处为空

//2、WeakMap 初始化
let key1 = {},
key2 = {},
map = new WeakMap([[key1, "Hello"], [key2, 42]]);
console.log(map.has(key1)); // true
console.log(map.get(key1)); // "Hello"
console.log(map.has(key2)); // true
console.log(map.get(key2)); // 42
```

Weak Map 只有两个附加方法能用来与键值对交互。 has() 方法用于判断指定的键是否存在于 Map 中，而 delete() 方法则用于移除一个特定的键值对。 clear() 方法不存在，这是因为没必要对键进行枚举，并且枚举 Weak Map 也是不可能的，这与 Weak Set 相同。以下例子同时用到了 has() 与 delete() 方法： 

```javascript
let map = new WeakMap(),
element = document.querySelector(".element");
map.set(element, "Original");
console.log(map.has(element)); // true
console.log(map.get(element)); // "Original"
map.delete(element);
console.log(map.has(element)); // false
console.log(map.get(element)); // undefined
```

