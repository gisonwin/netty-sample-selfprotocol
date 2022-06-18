package com.gisonwin;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/18 15:29
 */
public sealed class Animal permits Cat, Dog {

    private String category;//动物所属种类

    public Animal(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
