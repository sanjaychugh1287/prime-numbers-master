package com.sanjay.example.prime.service;

import com.sanjay.example.prime.util.PrimeNumberUtil;

import java.util.List;

/**
 * Service for functions related to prime-number calculation
 *
 * @author Sanjay Chugh
 */
public interface PrimeNumberService {

    /**
     * @see PrimeNumberUtil#isPrimeByOdds(int)
     */
    boolean isPrime(int n);

    /**
     * @see PrimeNumberUtil#isPrimeBySieve(int)
     */
    boolean isPrimeSieve(int n);

    /**
     * @see PrimeNumberUtil#isPrimeByOddsExcludingMultiplesOfThree(int)
     */
    boolean isPrimeFastLoop(int n);

    /**
     * Returns a {@link List} of prime numbers given an (inclusive) upper bound
     * @param upperBound The inclusive upper bound to limit the size of the returned primes
     * @return a {@link List} of prime numbers given an (inclusive) upper bound
     */
    List<Integer> getPrimes(int upperBound);
}
