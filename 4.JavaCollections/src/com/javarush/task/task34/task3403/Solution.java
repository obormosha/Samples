package com.javarush.task.task34.task3403;

/* 
Разложение на множители с помощью рекурсии
*/
public class Solution {
    public void recursion(int n) {
        int[] primeIntegers = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199};

        for (int i : primeIntegers) {
            if (n % i == 0) {
                System.out.print(i + " ");
                recursion(n / i);
                break;
            }
        }

    }
/*
    public static void main(String[] args){
        Solution solution = new Solution();
        solution.recursion(132);
    }*/

}
