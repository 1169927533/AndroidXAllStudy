package com.example.a11699.lib_javatest;

/**
 * Create time 2020/9/23
 * Create Yu
 * description:
 */
class JavaDeveloper implements Developer {
    String mName;

    public JavaDeveloper(String name) {
        mName = name;
    }


    @Override
    public void code() {
        System.out.println("JavaDevelop执行了code方法"+mName);
    }

    @Override
    public void debug() {
        System.out.println("JavaDevelop执行了debug方法"+mName);
    }
}
