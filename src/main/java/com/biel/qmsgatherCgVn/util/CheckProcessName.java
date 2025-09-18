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
 * @DATE 2025/9/17 13:32
 */
public class CheckProcessName {
    private static final Logger log = LoggerFactory.getLogger(CheckProcessName.class);

    private static final Map<String, String> MAPPING = new ConcurrentHashMap<>();

    static {
        MAPPING.put("丝印光油", "YGY");
        MAPPING.put ("白片 QC2", "BP2");
        MAPPING.put ("丝印贴膜", "STM");
        MAPPING.put ("丝印一层", "SY1");
        MAPPING.put ("IR二层", "IR2");
        MAPPING.put ("2D镭码", "LM0");
        MAPPING.put ("电膜PVD", "BAR");
        MAPPING.put ("电膜AR", "AR0");
        MAPPING.put ("电膜AS", "AS0");
        MAPPING.put ("脱油", "TY0");
        MAPPING.put ("UMP", "UMP");
        MAPPING.put ("Plasma", "PLS");
        MAPPING.put ("蒸汽测试", "ZQC");
        MAPPING.put ("AOI3", "FQ3");
        MAPPING.put ("ISM", "ISM");
        MAPPING.put ("粗磨一次", "CM1");
        MAPPING.put ("粗磨二次", "CM2");
        MAPPING.put ("白片QC1", "BP1");
        MAPPING.put ("贴1:1膜", "T1M");
        MAPPING.put ("FQC3", "FQ3");
        MAPPING.put ("SSB", "SSB");
        MAPPING.put ("自动分 Bin", "ZDB");
        MAPPING.put ("CNC", "CNC");
        MAPPING.put ("精磨", "JM0");
        MAPPING.put ("返磨", "FM0");
        MAPPING.put ("RI Bin BDS", "HX1");
        MAPPING.put ("HB插锅", "HBG");
        MAPPING.put ("SPM", "SPM");
        MAPPING.put ("精磨 QC", "JQ0");
        MAPPING.put ("凹槽抛光", "ACP");
        MAPPING.put ("加硬", "JY0");
        MAPPING.put ("Confocal1", "CF1");
        MAPPING.put ("HB镀膜", "DHB");
        MAPPING.put ("Confocal2", "CF2");
        MAPPING.put ("丝印0层", "SY0");
        MAPPING.put ("丝印二层", "SY2");
        MAPPING.put ("HB平板2", "HX2");
        MAPPING.put ("丝印线框", "YXK");
        MAPPING.put ("IR一层", "IR1");
        MAPPING.put ("酒精擦拭", "JJC");
        MAPPING.put ("AOI2+AOI3", "A23");
        MAPPING.put ("HB PF贴膜", "HBT");
        MAPPING.put ("激光开料", "JGK");
    }

    /**
     * 解析 remark 对应的数值；大小写不敏感、自动 trim。
     * @return 匹配返回 code；未匹配返回 null
     */
    public static String resolve(String remark) {
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
    public static Object mapForProcessName(String remark) {
        try {
            String code = resolve(remark);
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

}
