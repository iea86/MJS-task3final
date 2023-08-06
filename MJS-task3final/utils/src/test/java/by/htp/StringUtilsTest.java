package by.htp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StringUtilsTest {
    @Test
    public void testNumbersIsPositiveNumberMethodShouldReturnTrue() {
        assertTrue(StringUtils.isPositiveNumber("22342"));
    }

    @Test
    public void testStringIsPositiveNumberMethodShouldReturnFalse() {
        assertFalse(StringUtils.isPositiveNumber("sdsd"));
    }

    @Test
    public void testNegativeIsPositiveNumberMethodShouldReturnFalse() {
        assertFalse(StringUtils.isPositiveNumber("-2"));
    }

    @Test
    public void testEmptyValueIsPositiveNumberMethodShouldReturnFalse() {
        assertFalse(StringUtils.isPositiveNumber(""));
    }

    @Test
    public void testZeroValueIsPositiveNumberMethodShouldReturnFalse() {
        assertTrue(StringUtils.isPositiveNumber("0"));
    }

}

