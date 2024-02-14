package com.hellcaster.blogging.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AppUtils {
    public Integer get4DigitOtp() {
        return new Random().nextInt(9000) + 1000;
    }
}
