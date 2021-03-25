package com.sujit.reconciliationwebapp.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class test {

    public static void main(String[] args) {
        Map<Integer, List<String >> result = new LinkedHashMap<>();
//        result.computeIfAbsent(result.getOrDefault(1, new ArrayList<>())add("val");
        result.put(2, new ArrayList<>());
        System.out.println("Result is" + result.get(2));
    }
}
