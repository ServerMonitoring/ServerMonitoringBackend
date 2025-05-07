package com.example.back.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class EntityUtils {
    public static <T> boolean updateIfChanged(Supplier<T> getter, Consumer<T> setter, T newValue) {
        if (newValue != null && !newValue.equals(getter.get())) {
            setter.accept(newValue);
            return true;
        }
        return false;
    }
}
