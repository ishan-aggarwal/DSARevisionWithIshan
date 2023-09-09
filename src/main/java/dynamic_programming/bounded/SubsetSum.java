package dynamic_programming.bounded;

public class SubsetSum {
    public static void main(String[] args) {
        int arr[] = {2, 3, 5, 8, 10};
        int total = 18;
        int dp[][] = new int[arr.length + 1][total + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }
        boolean dpTD[][] = new boolean[arr.length + 1][total + 1];
        System.out.println(subsetSumRecursive(arr, total, arr.length));
        System.out.println(subsetSumMemoization(arr, dp, total, arr.length));
        System.out.println(subsetSumTabulation(arr, dpTD, total));
    }

    private static boolean subsetSumTabulation(int[] arr, boolean[][] dpTD, int w) {
        // base condition
        // if w is 0, then we can have an empty subset
        for (int i = 0; i < dpTD.length; i++) {
            dpTD[i][0] = true;
        }

        // if n is 0, then we can't have an empty subset to achieve a non zero sum
        for (int j = 1; j < dpTD[0].length; j++) {
            dpTD[0][j] = false;
        }

        // start filling the dp matrix from the base condition
        for (int i = 1; i < dpTD.length; i++) { // i represents elements weight
            for (int j = 1; j < dpTD[0].length; j++) { // weight
                if (arr[i - 1] <= j) {
                    dpTD[i][j] = dpTD[i - 1][j - arr[i - 1]] || dpTD[i - 1][j];
                } else {
                    dpTD[i][j] = dpTD[i - 1][j];
                }
            }
        }
        return dpTD[dpTD.length - 1][dpTD[0].length - 1];
    }

    private static boolean subsetSumMemoization(int[] arr, int[][] dp, int w, int n) {
        if (w == 0) return true; // w becomes zero -> returns true because we can have an empty subset
        if (n == 0)
            return false; // n becomes zero -> returns false because we can't have an empty subset to achieve a non zero sum

        if (dp[n][w] != -1) {
            return dp[n][w] == 1;
        }

        if (arr[n - 1] <= w) {
            dp[n][w] = subsetSumMemoization(arr, dp, w - arr[n - 1], n - 1) || subsetSumMemoization(arr, dp, w, n - 1) ? 1 : 0;
        } else {
            dp[n][w] = subsetSumMemoization(arr, dp, w, n - 1) ? 1 : 0;
        }
        return dp[n][w] == 1;
    }

    private static boolean subsetSumRecursive(int[] arr, int w, int n) {
        if (w == 0) return true; // w becomes zero -> returns true because we can have an empty subset
        if (n == 0)
            return false; // n becomes zero -> returns false because we can't have an empty subset to achieve a non zero sum

        // if the last element is greater than the sum, then we can't include it
        // so we will check for the remaining elements
        // if the last element is less than or equal to the sum, then we have two choices
        // 1. include the last element
        // 2. exclude the last element
        if (arr[n - 1] <= w) {
            return subsetSumRecursive(arr, w - arr[n - 1], n - 1) || subsetSumRecursive(arr, w, n - 1);
        } else {
            return subsetSumRecursive(arr, w, n - 1);
        }
    }

}
