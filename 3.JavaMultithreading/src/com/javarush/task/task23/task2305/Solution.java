package com.javarush.task.task23.task2305;

/* 
Inner
*/
public class Solution {
    public InnerClass[] innerClasses = new InnerClass[2];

    public class InnerClass {
    }

    public static Solution[] getTwoSolutions() {

        /*
        Solution sol1 = new Solution();
        sol1.innerClasses[0] = sol1.new InnerClass();
        sol1.innerClasses[1] = sol1.new InnerClass();

        Solution sol2 = new Solution();
        sol2.innerClasses[0] = sol1.new InnerClass();
        sol2.innerClasses[1] = sol1.new InnerClass();
        Solution[] solutions = {sol1, sol2};*/

        Solution[] solutions = new Solution[2];
        for(Solution s : solutions){
            s = new Solution();
            for (Solution.InnerClass in : s.innerClasses){
                in = s.new InnerClass();
            }
        }

        return solutions;
    }

    public static void main(String[] args) {
        Solution[] sol = getTwoSolutions();
        System.out.println(sol[0]);
        System.out.println(sol[1]);
    }
}
