package com.midorimart.managementsystem.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;
@Component
public class NumberHelperUtil {
    // Get 2 digit after dot
    public static double fixNumberDecimal(double number){
        return BigDecimal.valueOf(number).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
