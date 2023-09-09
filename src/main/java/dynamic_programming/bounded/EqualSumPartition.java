package dynamic_programming.bounded;

import java.util.Arrays;

public class EqualSumPartition {
    public static void main(String[] args) {
        int arr[] = {12, 1, 5, 8, 10}; // true
//        int arr[] = {12, 1, 5, 8, 22}; // false

        int sum = Arrays.stream(arr).sum();
        if ((sum & 1) == 1) {
            System.out.println("Not possible to divide the array into two subsets with equal sum");
            return;
        }

        int total = sum / 2;

        int dp[][] = new int[arr.length + 1][total + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }
        boolean dpTD[][] = new boolean[arr.length + 1][total + 1];
        System.out.println(equalSumPartitionMemoization(arr, dp, total, arr.length));
        System.out.println(equalSumPartitionTabulation(arr, dpTD, total));
    }

    public static boolean equalSumPartitionMemoization(int[] arr, int[][] dp, int w, int n) {
        if (w == 0) return true;
        if (n == 0) return false;

        if (dp[n][w] != -1) {
            return dp[n][w] == 1;
        }

        if (arr[n - 1] <= w) {
            dp[n][w] = equalSumPartitionMemoization(arr, dp, w - arr[n - 1], n - 1) || equalSumPartitionMemoization(arr, dp, w, n - 1) ? 1 : 0;
        } else {
            dp[n][w] = equalSumPartitionMemoization(arr, dp, w, n - 1) ? 1 : 0;
        }

        return dp[n][w] == 1;
    }

    private static boolean equalSumPartitionTabulation(int[] arr, boolean dpTD[][], int w) {

        for (int i = 0; i < dpTD.length; i++) {
            dpTD[i][0] = true;
        }

        for (int j = 1; j < dpTD[0].length; j++) {
            dpTD[0][j] = false;
        }

        for (int i = 1; i < dpTD.length; i++) {
            for (int j = 1; j < dpTD[0].length; j++) {
                if (arr[i - 1] <= j) {
                    dpTD[i][j] = dpTD[i - 1][j - arr[i - 1]] || dpTD[i - 1][j];
                } else {
                    dpTD[i][j] = dpTD[i - 1][j];
                }
            }
        }

        return dpTD[dpTD.length - 1][dpTD[0].length - 1];
    }


}
