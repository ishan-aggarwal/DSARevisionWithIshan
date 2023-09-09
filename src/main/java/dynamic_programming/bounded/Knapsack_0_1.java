package dynamic_programming.bounded;

import java.util.Arrays;

public class Knapsack_0_1 {
    public static void main(String[] args) {
        int wt[] = {4, 4, 6, 8};
        int val[] = {2, 4, 4, 5};
        int weight = 15;

        // dp[i][j] represents the maximum value that can be obtained with a capacity of j and using items up to i
        int dp[][] = new int[wt.length + 1][weight + 1];

        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }

        System.out.println(knapsackRecursive(wt, val, weight, wt.length));
        System.out.println(knapsackMemoization(dp, wt, val, weight, wt.length)); // memoization is top down approach which uses recursion
        System.out.println(knapsackTabulation(val, wt, dp)); // tabulation is bottom up approach which uses iteration
    }


    public static int knapsackTabulation(int[] val, int[] wt, int[][] dp) {

        // start filling the dp matrix from the base condition
        // when the weight (j) is 0, we can't put any item in the bag
        // when the number of items (i) is 0, we can't put any item in the bag
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (i == 0 || j == 0)
                    dp[i][j] = 0;
            }
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (wt[i - 1] <= j) {
                    dp[i][j] = Math.max(val[i - 1] + dp[i - 1][j - wt[i - 1]], dp[i - 1][j]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }


    // kanpsack memoization is again recursion solution with storage approach
    // so that if a result is already calculated, we don't need to calculate it again
    // we can just return the result from the storage
    private static int knapsackMemoization(int[][] dp, int[] wt, int[] val, int W, int n) {

        // base condition
        // if W is 0 or n is 0, then we can't put any item in the bag
        if (n == 0 || W == 0) {
            return 0;
        }

        if (dp[n][W] != -1) {
            return dp[n][W];
        }

        // when the weight of the current item is less than or equal to the W
        // we have two choices:
        // 1. we can either include the current item in the bag
        // 2. we can exclude the current item from the bag
        if (wt[n - 1] <= W) {
            dp[n][W] = Math.max(val[n - 1] + knapsackMemoization(dp, wt, val, W - wt[n - 1], n - 1),
                    knapsackMemoization(dp, wt, val, W, n - 1));
        } else {
            // when the weight of the current item is greater than the W
            // we can't include the current item in the bag
            dp[n][W] = knapsackMemoization(dp, wt, val, W, n - 1);
        }
        return dp[n][W];
    }

    private static int knapsackRecursive(int[] wt, int[] val, int W, int n) {

        // base condition
        // if W is 0 or n is 0, then we can't put any item in the bag
        if (n == 0 || W == 0) {
            return 0;
        }

        // when the weight of the current item is less than or equal to the W
        // we have two choices:
        // 1. we can either include the current item in the bag
        // 2. we can exclude the current item from the bag
        if (wt[n - 1] <= W) {
            return Math.max(val[n - 1] + knapsackRecursive(wt, val, W - wt[n - 1], n - 1),
                    knapsackRecursive(wt, val, W, n - 1));
        } else {
            // when the weight of the current item is greater than the W
            // we can't include the current item in the bag
            return knapsackRecursive(wt, val, W, n - 1);
        }
    }
}
