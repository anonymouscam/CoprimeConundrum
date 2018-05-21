import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.min;

public class Solution {

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        int n = stdin.nextInt();

        long result = allValidPairs(n);

        System.out.println(result);

    }

    //return list of primes from 0 to n
    private static List<Integer> findPrimes(int n) {
        List<Integer> primeList = new ArrayList<Integer>();
        boolean isPrime[] = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;
        for (int i = 2; i <= n; i++) {

            if (isPrime[i]) {
                //i is prime
                primeList.add(i);
                for (int j = i + i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        return primeList;
    }

    private static long allValidPairs(int n) {

        long result = 0L;

        int sqrtn = (int) Math.ceil(Math.sqrt(n));
        List<Integer> primeList = findPrimes(sqrtn);
        int lo = 1;
        int hi = sqrtn;

        while (lo <= n) {

            long numValidPairs = countValidPairs(lo, hi, primeList);

            result += numValidPairs;

            lo += sqrtn;
            hi += sqrtn;
            hi = min(hi, n);
        }

        return result;
    }

    //returns array where
    //numFactors[x] = the number of prime factors for (x+lo)
    private static long countValidPairs(int lo, int hi, List<Integer> primeList) {

        int[] numFactors = new int[hi - lo + 1];
        int[] remainingFactors = new int[hi - lo + 1];
        for (int i = lo; i <= hi; i++) {
            remainingFactors[i - lo] = i;
        }

        for (int prime : primeList) {

            int first = (lo / prime) * prime;
            if (first < lo) {
                first += prime;
            }

            int last = (hi / prime) * prime;

            for (int j = first; j <= last; j += prime) {
                numFactors[j - lo] += 1;
                while (remainingFactors[j - lo] % prime == 0) {
                    remainingFactors[j - lo] /= prime;
                }
            }
        }

        long numValidPairs = 0L;

        for (int i = 0; i < numFactors.length; i++) {

            //correct for any prime factors > ceil(sqrt(n))
            if (remainingFactors[i] > 1) {
                numFactors[i] += 1;
            }

            if (numFactors[i] > 1) {
                long pairs = ((1L << numFactors[i]) - 2L) >> 1;
                numValidPairs += pairs;
            }

        }

        return numValidPairs;
    }

}
