package com.gisonwin;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/18 15:22
 */
class PerformanceControllerTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void test1() {

        //文本块
        textBlock();
        testRecord();
//        testNPE();
        testStream();
    }

    public void testStream(){
        Stream<String> stream = Stream.of("a", "b", "e", "f", "u");
        List<String> collect = stream.collect(Collectors.toList());
        System.out.println(collect);
        stream = Stream.of("a", "b", "e", "f", "u");
        List<String> strings = stream.toList();
        System.out.println(strings);
    }
    private void testNPE() {
        Person person = new Person();
        throw new NullPointerException("NPE");

    }

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
}