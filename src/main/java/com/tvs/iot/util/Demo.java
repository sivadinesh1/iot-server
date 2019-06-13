package com.tvs.iot.util;

import java.util.*;
import java.util.TimerTask;
import java.util.concurrent.Callable;


public class Demo implements Callable {

    String popvalue = "";
    Stack<String> STACK = new Stack<String>();

    public Demo() {

        STACK.push("Welcome");
        STACK.push("To");
        STACK.push("Geeks");
        STACK.push("For");
        STACK.push("Geeks");
    }

    public String call() {
        if(STACK.empty()) {
            STACK.push("Welcome");
            STACK.push("To");
            STACK.push("Geeks");
            STACK.push("For");
            STACK.push("Geeks");
        }

        System.out.println("AFTER FIRST push " + STACK.peek());
        String temppop = STACK.pop();
        return temppop;
    }
}