package com.tvs.iot.util;

import java.util.*;
import java.util.TimerTask;


public class DemoThread extends TimerTask {

    String popvalue = "";
    Stack<String> STACK = new Stack<String>();


    public DemoThread() {


        STACK.push("Welcome 1");
        STACK.push("To");
        STACK.push("Geeks");
        STACK.push("For");
        STACK.push("Geeks");

        // Displaying the Stack
        System.out.println("Initial  >>: " + STACK);
    }


    public void run() {

        if(STACK.empty()) {
            STACK.push("Welcome");
            STACK.push("To");
            STACK.push("Geeks");
            STACK.push("For");
            STACK.push("Geeks");
        }

        System.out.println("AFTER FIRST push " + STACK.peek());
        String temppop = STACK.pop();

    }
}