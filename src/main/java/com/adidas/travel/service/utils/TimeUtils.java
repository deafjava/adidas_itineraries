package com.adidas.travel.service.utils;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class TimeUtils {

    public String parse(Duration d) {
        String s = d.toString();
        if (!s.contains("H")) {
            s = s.replace("PT", "PT00H");
        }
        s = s.replaceAll("(PT|S)", "");
        s = s.replaceAll("(H|M)", ":");
        String[] sa = s.split(":");
        IntStream.range(0, sa.length).filter(i -> sa[i].length() == 1).forEachOrdered(i -> sa[i] = "0" + sa[i]);
        return Arrays.stream(sa).collect(Collectors.joining(":"));
    }
}
