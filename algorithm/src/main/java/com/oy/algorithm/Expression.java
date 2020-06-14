package com.oy.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Expression {

    private static Stack<Character> operatorStack = new Stack<>();
    private static Stack<Integer> valueStack = new Stack<>();
    private static Map<Character, Integer> operatorPriorityMap = new HashMap<>();

    static {
        operatorPriorityMap.put(')', 0);
        operatorPriorityMap.put('+', 1);
        operatorPriorityMap.put('-', 1);
        operatorPriorityMap.put('*', 2);
        operatorPriorityMap.put('/', 2);
        operatorPriorityMap.put('^', 3);
        operatorPriorityMap.put('(', 4);
    }

    public static void main(String[] args) {
        //3*2^(4+2*2-6*3)-5
        String expression = "3*2^(4+6*2-2*2)-5";//=4097

        int fromIndex = 0;
        for (int i = 0; i < expression.length(); i++) {
            char currOperator = expression.charAt(i);
            //判断是否为操作符
            if (operatorPriorityMap.containsKey(currOperator)) {
                //获取左值
                pushValueStack(expression, fromIndex, i);
                fromIndex = i + 1;
                pushOperatorStack(currOperator);
            }
        }
        pushValueStack(expression, fromIndex, expression.length());
        char topOperator = operatorStack.pop();
        calc(topOperator);
        System.out.println(valueStack.lastElement());
    }

    private static void pushValueStack(String expression, int fromIndex, int toIndex) {
        if (fromIndex < toIndex) {
            String subValue = expression.substring(fromIndex, toIndex);
            valueStack.push(Integer.parseInt(subValue));
        }
    }

    private static void pushOperatorStack(char currOperator) {
        if (operatorStack.isEmpty()) {
            operatorStack.push(currOperator);
            return;
        }
        char topOperator = operatorStack.lastElement();
        if (topOperator == '(' && currOperator == ')') {
            operatorStack.pop();
            return;
        }
        if (topOperator == '(') {
            operatorStack.push(currOperator);
            return;
        }
        int topLevel = operatorPriorityMap.get(topOperator);
        int currLevel = operatorPriorityMap.get(currOperator);
        if (topLevel >= currLevel) {
            operatorStack.pop();
            calc(topOperator);
            pushOperatorStack(currOperator);
        } else {
            operatorStack.push(currOperator);
        }
    }

    private static void calc(char topOperator) {
        int right = valueStack.pop();
        int left = valueStack.pop();
        int ret = -99999999;
        switch (topOperator) {
            case '+':
                ret = left + right;
                break;
            case '-':
                ret = left - right;
                break;
            case '*':
                ret = left * right;
                break;
            case '/':
                ret = left / right;
                break;
            case '^': {
                int n = right;
                ret = 1;
                while (n != 0) {
                    ret = ret * left;
                    n--;
                }
            }
            break;
            default:
                break;
        }
        valueStack.push(ret);
    }
}
