package com.endava.frequence;

import java.net.URISyntaxException;
import java.util.Map;

public class TestClass {
    public static void main(String[] args) throws URISyntaxException {//only method we know haw to execute

        FrequencyCreator fc = new FrequencyCreator();
        Map<String, Integer> result = fc.calculateFrequency("SnowWhite.txt");
        System.out.println(result);

    }
}
