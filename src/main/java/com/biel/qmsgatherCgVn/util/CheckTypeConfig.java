package com.biel.qmsgatherCgVn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 统一的 CheckType 映射工具：
 * - 约定：CPK -> 1，FAI -> 4
 * - 未知类型：mapForPayload 会回退为原始字符串；resolve 返回 null
 * - 可扩展：register 可在运行时新增映射
 */
public final class CheckTypeConfig {

    private static final Logger log = LoggerFactory.getLogger(CheckTypeConfig.class);

    private static final Map<String, Integer> MAPPING = new ConcurrentHashMap<>();

    static {
        MAPPING.put("CPK", 1);
        MAPPING.put("FAI", 4);
        MAPPING.put("首检", 3);
        MAPPING.put("GL", 7);
        MAPPING.put("GL-QJ", 13);
        MAPPING.put("待料", 8);
        MAPPING.put("TJ2", 9);
        MAPPING.put("TJ", 2);
        MAPPING.put("TJ-NG", 10);
        MAPPING.put("TJ-OK", 11);
        MAPPING.put("TJP", 12);
        MAPPING.put("HSL", 14);
    }

    /**
     * 解析 remark 对应的数值；大小写不敏感、自动 trim。
     * @return 匹配返回 code；未匹配返回 null
     */
    public static Integer resolve(String remark) {
        if (remark == null) return null;
        String up = remark.trim().toUpperCase(Locale.ROOT);
        if (up.isEmpty()) return null;
        return MAPPING.get(up);
    }

    /**
     * 构建消息用的 CheckType：
     * - 已知 remark 返回数值
     * - 未知 remark 返回原始字符串
     * - 空白 remark 返回 null
     */
    public static Object mapForPayload(String remark) {
        try {
            Integer code = resolve(remark);
            if (code != null) return code;
            if (remark != null && !remark.trim().isEmpty()) {
                log.warn("Unknown CheckType remark: {}. Fallback to original string.", remark);
                return remark;
            }
            return null;
        } catch (Exception ex) {
            log.error("CheckType mapping error for remark={}, fallback to original.", remark, ex);
            return remark;
        }
    }

    /**
     * 动态注册新映射，便于扩展（大小写不敏感）。
     */
    public static void register(String remarkKey, int code) {
        if (remarkKey == null) return;
        String up = remarkKey.trim().toUpperCase(Locale.ROOT);
        if (up.isEmpty()) return;
        MAPPING.put(up, code);
    }

    private CheckTypeConfig() {}
}