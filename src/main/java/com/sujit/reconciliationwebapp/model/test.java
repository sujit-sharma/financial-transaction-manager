package com.sujit.reconciliationwebapp.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class test {

    public static void main(String[] args) {
        Map<Integer, List<String >> result = new LinkedHashMap<>();
//        Map<Integer, Integer> intMap = new LinkedHashMap<>();
//        intMap.getOrDefault(1, 2);
//        result.put(1, result.getOrDefault(1,new ArrayList<>()).add("val"));
        List<String> someArray = result.getOrDefault(2, new ArrayList<>());
//        someArray.add("someTrans");
//        result.put(2,someArray);
        result.put(2, new ArrayList<>());
        System.out.println("Result is" + result.get(2));
    }
}
