package com.example.back.model.enums;

import java.util.Locale;

public enum MetricKey {
    CPU_TOTAL_LOAD("CPU total load exceeded threshold: %.2f", "Загрузка CPU превысила порог: %.2f"),
    MEMORY_PERCENT("Memory usage exceeded threshold: %.2f%%", "Использование памяти превысило порог: %.2f%%"),
    SWAP_PERCENT("Swap usage exceeded threshold: %.2f%%", "Использование подкачки превысило порог: %.2f%%"),
    DISK_USED_PERCENT("Disk usage exceeded threshold: %.2f%%", "Использование диска превысило порог: %.2f%%"),
    GPU_LOAD("GPU load exceeded threshold: %.2f%%", "Загрузка GPU превысила порог: %.2f%%"),
    GPU_MEMORY_USED_PERCENT("GPU memory usage exceeded threshold: %.2f%%", "Использование памяти GPU превысило порог: %.2f%%");


    private final String enMessageTemplate;
    private final String ruMessageTemplate;

    MetricKey(String enMessageTemplate, String ruMessageTemplate) {
        this.enMessageTemplate = enMessageTemplate;
        this.ruMessageTemplate = ruMessageTemplate;
    }

    public String getMessageTemplate(PreferredLanguage language) {
        return language == PreferredLanguage.RUSSIAN ? ruMessageTemplate : enMessageTemplate;
    }
}

