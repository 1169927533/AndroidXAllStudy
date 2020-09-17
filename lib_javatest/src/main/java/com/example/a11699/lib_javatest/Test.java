package com.example.a11699.lib_javatest;

/**
 * Create time 2020/6/26
 * Create Yu
 * description:
 */
public class Test {
    static {
        System.out.println("dsa)");
    }
    public static void main(String[] args) {
      //  B b = new Test.B();
    }
    private static class B {
        static {
            System.out.println("bbb)");
        }
    }
}
