package com.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static List<Integer> decodeIntList (final String s) {
        return Arrays.asList(s.split(",")).stream()
                .map(x -> Integer.parseInt(x)).collect(Collectors.toList());
    }

    public static String decodeParam (final String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            return "";
        }
    }
}
