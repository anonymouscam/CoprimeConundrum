import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        int n = stdin.nextInt();

        int[] numFactors = countFactors(n);
        long result = allValidPairs(n, numFactors);

        System.out.println(result);

    }

    private static long allValidPairs(int n, int[] numFactors) {
        long result = 0L;
        for (int i = 2; i <= n; i++) {
            //if we have x factors then we have (2^x-2)/2 valid pairs
            result += ((1L << numFactors[i]) - 2L) >> 1;
        }
        return result;
    }

    private static int[] countFactors(int n) {
        int[] numFactors = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            if (numFactors[i] == 0) {
                //i is prime
                numFactors[i] = 1;
                for (int j = i + i; j <= n; j += i) {
                    numFactors[j] += 1;
                }
            }
        }

        return numFactors;
    }
}
