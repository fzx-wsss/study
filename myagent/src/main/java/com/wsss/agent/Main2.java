package com.wsss.agent;

import java.util.Stack;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        while(true) {
            Thread.sleep(1000);
            Main2.test();
        }
    }

    public static void test() {
        System.out.println("test");
    }

    public static int longestValidParentheses(String s) {
        boolean[] flag = new boolean[s.length()];
        Stack<Integer> stack = new Stack<>();

        for(int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(i);
            }
            if(c == ')') {
                if(!stack.isEmpty()) {
                    int last = stack.pop();
                    flag[i] = true;
                    flag[last] = true;
                    continue;
                }
            }
        }
        int max = 0;
        int current = 0;
        for(boolean b : flag) {
            if(b) {
                current++;
                if(current > max) {
                    max = current;
                }
            } else {
                current = 0;
            }
        }
        return max;
    }
}
