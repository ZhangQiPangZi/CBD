package com.cbd.cbdcommoninterface.utils;

/**
 * 字符串相似度匹配
 */
public class JaroWinklerDistance {

    private static float p = 0.1f;
    private static final float MAX_P = 0.25f;
    private static final int MAX_L = 4;

    /**
     *
     * @param s1
     * @param s2
     * @return Jaro-Winkler相似度
     */
    public static float JaroWinklerSimilarity(String s1, String s2) {
        if (s1 == null || s2 == null) {
            if (s1 == null && s2 == null) {
                return 1.0f;
            }
            return 0.0f;
        }
        int[] matchesInfo = getMatchesInfo(s1, s2);
        if (matchesInfo[0] == 0) {
            return 0.0f;
        } else {
            float m = matchesInfo[0];
            float j = ((m / s1.length() + m / s2.length() + (m - matchesInfo[1]) / m)) / 3;
            return j + matchesInfo[2] * p * (1 - j);
        }
    }

    private static int[] getMatchesInfo(String s1, String s2) {
        String min, max;
        if (s1.length() < s2.length()) {
            min = s1;
            max = s2;
        } else {
            min = s2;
            max = s1;
        }
        //计算字符匹配的范围
        int matchRange = Math.max(max.length() / 2 - 1, 0);
        //记录两个字符串字符匹配状态
        boolean[] minMatchFlag = new boolean[min.length()];
        boolean[] maxMatchFlag = new boolean[max.length()];
        //匹配数量
        int matches = 0;
        for (int i = 0; i < min.length(); i++) {
            char minChar = min.charAt(i);
            for (int j = Math.max(i - matchRange, 0); j < Math.min(i + matchRange + 1, max.length()); j++) {
                if (!maxMatchFlag[j] && minChar == max.charAt(j)) {
                    maxMatchFlag[j] = true;
                    minMatchFlag[i] = true;
                    matches++;
                    break;
                }
            }
        }
        //转换次数，需要除以2
        int transpositions = 0;
        //共同前缀长度，最大为4
        int prefix = 0;
        //计算transpositions与prefix
        int j = 0;
        for (int i = 0; i < min.length(); i++) {
            if (minMatchFlag[i]) {
                for (; !maxMatchFlag[j]; j++) {
                }
                if (min.charAt(i) == max.charAt(j) && transpositions == 0) {
                    prefix++;
                } else if (min.charAt(i) != max.charAt(j)) {
                    transpositions++;
                }
                j++;
            }
        }
        return new int[] { matches, transpositions / 2, prefix > MAX_L ? MAX_L : prefix };
    }
}