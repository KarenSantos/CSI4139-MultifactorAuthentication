package model;

import java.math.*;
import java.util.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author karensaroc
 */
public class DiffieHellman {

    private static final SecureRandom rnd = new SecureRandom();

    private int bitLength = 512;
    private int certainty = 20;// probabilistic prime generator 1-2^-certainty => practically 'almost sure'

    private BigInteger primeValue;
    private BigInteger generatorValue;

    public DiffieHellman() {

        // Generating g and p
        primeValue = findPrime();
        generatorValue = findPrimeRoot(primeValue);
//        primeValue = 23;
//        generatorValue = 5;  
    }

    private BigInteger findPrime() {
        Random rnd = new Random();
        BigInteger p = BigInteger.ZERO;
        p = new BigInteger(bitLength, certainty, rnd);// sufficiently NSA SAFE?!!
        return p;
    }

    private BigInteger findPrimeRoot(BigInteger p) {
        int start = 2001;// first best probably precalculated by NSA?
        // preferably  3, 17 and 65537
        // if(start==2)compareWolfram(p);

        for (int i = start; i < 100000000; i++) {
            if (isPrimeRoot(BigInteger.valueOf(i), p)) {
                return BigInteger.valueOf(i);
            }
        }
        // if(isPrimeRoot(i,p))return BigInteger.valueOf(i);
        return BigInteger.valueOf(0);
    }

    public boolean isPrimeRoot(BigInteger g, BigInteger p) {
        BigInteger totient = p.subtract(BigInteger.ONE); //p-1 for primes;// factor.phi(p);
        List<BigInteger> factors = primeFactors(totient);
        int i = 0;
        int j = factors.size();
        for (; i < j; i++) {
            BigInteger factor = factors.get(i);//elementAt
            BigInteger t = totient.divide(factor);
            if (g.modPow(t, p).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }

    public List<BigInteger> primeFactors(BigInteger number) {
        BigInteger n = number;
        BigInteger i = BigInteger.valueOf(2);
        BigInteger limit = BigInteger.valueOf(10000);// speed hack! -> consequences ???
        List<BigInteger> factors = new ArrayList<BigInteger>();
        while (!n.equals(BigInteger.ONE)) {
            while (n.mod(i).equals(BigInteger.ZERO)) {
                factors.add(i);
                n = n.divide(i);
                // System.out.println(i);
                // System.out.println(n);
                if (isPrime(n)) {
                    factors.add(n);// yes?
                    return factors;
                }
            }
            i = i.add(BigInteger.ONE);
            if (i.equals(limit)) {
                return factors;// hack! -> consequences ???
            }                    // System.out.print(i+"    \r");
        }
        System.out.println(factors);
        return factors;
    }

    private boolean isPrime(BigInteger r) {
        return miller_rabin(r);
        // return BN_is_prime_fasttest_ex(r,bitLength)==1;
    }

    public static boolean miller_rabin(BigInteger n) {
        for (int repeat = 0; repeat < 20; repeat++) {
            BigInteger a;
            do {
                a = new BigInteger(n.bitLength(), rnd);
            } while (a.equals(BigInteger.ZERO));
            if (!miller_rabin_pass(a, n)) {
                return false;
            }
        }
        return true;
    }

    private static boolean miller_rabin_pass(BigInteger a, BigInteger n) {
        BigInteger n_minus_one = n.subtract(BigInteger.ONE);
        BigInteger d = n_minus_one;
        int s = d.getLowestSetBit();
        d = d.shiftRight(s);
        BigInteger a_to_power = a.modPow(d, n);
        if (a_to_power.equals(BigInteger.ONE)) {
            return true;
        }
        for (int i = 0; i < s - 1; i++) {
            if (a_to_power.equals(n_minus_one)) {
                return true;
            }
            a_to_power = a_to_power.multiply(a_to_power).mod(n);
        }
        if (a_to_power.equals(n_minus_one)) {
            return true;
        }
        return false;
    }

    public BigInteger calcAlpha(String password) {
        try {
            BigInteger secretA = new BigInteger(password.getBytes());
            BigInteger alpha = generatorValue.modPow(secretA, primeValue);
            return alpha;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigInteger calcBeta() {
        BigInteger secretB = new BigInteger(bitLength, rnd);
        BigInteger beta = generatorValue.modPow(secretB, primeValue);
        return beta;
    }

    public BigInteger calcK(String strBeta, String password) {
        BigInteger secretA = new BigInteger(password.getBytes());
        BigInteger beta = new BigInteger(strBeta, 16);
        BigInteger K = beta.modPow(secretA, primeValue);
        return K;
    }

    public byte[] genMAC(byte[] m, BigInteger k1) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(k1.toByteArray(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return mac.doFinal(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
