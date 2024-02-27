package org.detelin.enums;

import java.util.Arrays;

public enum ApplicationEnum {
    STRING_REVERSE(1),
    STRING_WORD_COUNT(2),
    LIST_TRAVERSE(3),
    STRING_DUPLICATES_FIND(4),
    EXCEL_FILE_READ(5)
    ;

    private final int key;

    ApplicationEnum(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public static ApplicationEnum getByKey(int key) {
        return Arrays.stream(ApplicationEnum.values()).filter(a -> a.getKey() == key)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant found with key: " + key));
    }
}
