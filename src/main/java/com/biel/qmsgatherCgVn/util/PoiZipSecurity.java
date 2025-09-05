package com.biel.qmsgatherCgVn.util;

import org.apache.poi.openxml4j.util.ZipSecureFile;

/**
 * 统一管理 Apache POI 的 Zip 安全参数配置，确保只配置一次，便于在多个模块重用。
 */
public final class PoiZipSecurity {
    private PoiZipSecurity() {}

    private static volatile boolean CONFIGURED = false;

    public static void configure() {
        if (!CONFIGURED) {
            synchronized (PoiZipSecurity.class) {
                if (!CONFIGURED) {
                    // 允许更低的解压膨胀比（关闭该检查），并设置合理上限，避免真正的 zip 炸弹
                    ZipSecureFile.setMinInflateRatio(0.0);
                    ZipSecureFile.setMaxEntrySize(100L * 1024 * 1024); // 100MB
                    ZipSecureFile.setMaxTextSize(10L * 1024 * 1024);   // 10MB 文本
                    CONFIGURED = true;
                }
            }
        }
    }
}