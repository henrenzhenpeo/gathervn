package com.biel.qmsgatherCgVn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/9/18 17:40
 */
public class CheckMachineCode {

    private static final Logger log = LoggerFactory.getLogger(CheckTypeConfig.class);

    private static final Map<String, Integer> MAPPING = new ConcurrentHashMap<>();

    static {
        MAPPING.put("1-1L#", 0);
        MAPPING.put("1-1R#", 1);
        MAPPING.put("2-1L#", 2);
        MAPPING.put("2-1R#", 3);
        MAPPING.put("3-1L#", 4);
        MAPPING.put("3-1R#", 5);
        MAPPING.put("4-1L#", 6);
        MAPPING.put("4-1R#", 7);

// n=2 部分
        MAPPING.put("1-2L#", 8);
        MAPPING.put("1-2R#", 9);
        MAPPING.put("2-2L#", 10);
        MAPPING.put("2-2R#", 11);
        MAPPING.put("3-2L#", 12);
        MAPPING.put("3-2R#", 13);
        MAPPING.put("4-2L#", 14);
        MAPPING.put("4-2R#", 15);

// n=3 部分
        MAPPING.put("1-3L#", 16);
        MAPPING.put("1-3R#", 17);
        MAPPING.put("2-3L#", 18);
        MAPPING.put("2-3R#", 19);
        MAPPING.put("3-3L#", 20);
        MAPPING.put("3-3R#", 21);
        MAPPING.put("4-3L#", 22);
        MAPPING.put("4-3R#", 23);

// n=4 部分
        MAPPING.put("1-4L#", 24);
        MAPPING.put("1-4R#", 25);
        MAPPING.put("2-4L#", 26);
        MAPPING.put("2-4R#", 27);
        MAPPING.put("3-4L#", 28);
        MAPPING.put("3-4R#", 29);
        MAPPING.put("4-4L#", 30);
        MAPPING.put("4-4R#", 31);

// n=5 部分
        MAPPING.put("1-5L#", 32);
        MAPPING.put("1-5R#", 33);
        MAPPING.put("2-5L#", 34);
        MAPPING.put("2-5R#", 35);
        MAPPING.put("3-5L#", 36);
        MAPPING.put("3-5R#", 37);
        MAPPING.put("4-5L#", 38);
        MAPPING.put("4-5R#", 39);

// n=6 部分
        MAPPING.put("1-6L#", 40);
        MAPPING.put("1-6R#", 41);
        MAPPING.put("2-6L#", 42);
        MAPPING.put("2-6R#", 43);
        MAPPING.put("3-6L#", 44);
        MAPPING.put("3-6R#", 45);
        MAPPING.put("4-6L#", 46);
        MAPPING.put("4-6R#", 47);

// n=7 部分
        MAPPING.put("1-7L#", 48);
        MAPPING.put("1-7R#", 49);
        MAPPING.put("2-7L#", 50);
        MAPPING.put("2-7R#", 51);
        MAPPING.put("3-7L#", 52);
        MAPPING.put("3-7R#", 53);
        MAPPING.put("4-7L#", 54);
        MAPPING.put("4-7R#", 55);
    }

    /**
     * 解析 remark 对应的数值；大小写不敏感、自动 trim。
     * @return 匹配返回 code；未匹配返回 null
     */
    public static Integer resolve(String r) {
        if (r == null) return null;
        String up = r.trim().toUpperCase(Locale.ROOT);
        if (up.isEmpty()) return null;
        return MAPPING.get(up);
    }

    /**
     * 构建消息用的 CheckType：
     * - 已知 remark 返回数值
     * - 未知 remark 返回原始字符串
     * - 空白 remark 返回 null
     */
    public static Object mapForMachineCode(String r) {
        try {
            Integer code = resolve(r);
            if (code != null) return code;
            if (r != null && !r.trim().isEmpty()) {
                log.warn("Unknown CheckType remark: {}. Fallback to original string.", r);
                return r;
            }
            return null;
        } catch (Exception ex) {
            log.error("CheckType mapping error for remark={}, fallback to original.", r, ex);
            return r;
        }
    }

}
