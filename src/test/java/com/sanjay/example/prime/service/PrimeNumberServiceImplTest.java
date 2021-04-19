package com.sanjay.example.prime.service;

import com.sanjay.example.prime.PrimeController;
import com.sanjay.example.prime.util.PrimeNumberUtilTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * @author Sanjay Chugh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PrimeController.class)
public class PrimeNumberServiceImplTest {

    @Autowired
    private PrimeNumberService service;

    @Test
    public void testPrimeGenerator() {

        final List<Integer> primes = service.getPrimes(7919);
        Assert.assertEquals("'primes' and 'KNOWN_PRIMES' should be equal in size", PrimeNumberUtilTest.KNOWN_PRIMES.length, primes.size());

        for (int i = 0; i < PrimeNumberUtilTest.KNOWN_PRIMES.length; i++) {
            Assert.assertEquals("'primes.get(i)' should equal 'KNOWN_PRIMES[i]'", PrimeNumberUtilTest.KNOWN_PRIMES[i], primes.get(i).intValue());
        }
    }
}
