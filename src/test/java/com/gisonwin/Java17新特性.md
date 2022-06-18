## Java17新特性

- sealed classes 密封类
- switch 表达式
- instanceof 模式匹配
- record keyword
- 文本块
- Helpful NullpointerExceptions
- 日期周期格式化
- 精简数字格式化支持
- Stream.toList简化

#### 一。文本块

通过三个双引号定义一个文本块，且结束的三个双引号不能和开始的在同一行。

```java
    public void textBlock(){
        var json = """
                {
                "name":"GisonWin",
                "sex":"male",
                "address":"Beijing,China"
                }
                """;
        System.out.println(json);
    }
```

#### 二。switch表达式



#### 三。record关键字

record用来创建不可变的数据类。

```java
    public void testRecord(){
        Animal cat = new Animal("Cat");
        System.out.println(cat);
        Animal dog = new Animal("Dog");
        System.out.println(dog);
        System.out.println(cat.equals(dog));
        //
        record AnimalRecord(String category){
            AnimalRecord{
                System.out.println("category" + category);
                if(null==category)
                    throw new IllegalArgumentException("分类不能为空");
            }
        }
        AnimalRecord catRecord = new AnimalRecord(cat.getCategory());
        AnimalRecord dogRecord = new AnimalRecord(dog.getCategory());
        System.out.println(catRecord);
        System.out.println(dogRecord);
    }
```

#### 四。Helpful NullpointerExceptions

NPE异常信息更详细，

```java
    private void testNPE() {
        Person person = new Person();
        throw new NullPointerException("NPE");
    }
```

#### 五。Stream.toList（）新增

```java
    public void testStream(){
        Stream<String> stream = Stream.of("a", "b", "e", "f", "u");
        //17以前使用collect方法返回List
        List<String> collect = stream.collect(Collectors.toList());
        System.out.println(collect);
        stream = Stream.of("a", "b", "e", "f", "u");
        //17以后直接调用toList 方法
        List<String> strings = stream.toList();
        System.out.println(strings);
    }
```



