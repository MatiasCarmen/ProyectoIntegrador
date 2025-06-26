package utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Clase de utilidad que proporciona métodos para trabajar con colecciones inmutables de Google Guava
 */
public class GuavaUtils {

    /**
     * Convierte una lista mutable en una lista inmutable
     */
    public static <T> ImmutableList<T> toImmutableList(List<T> list) {
        return ImmutableList.copyOf(list);
    }

    /**
     * Convierte un conjunto mutable en un conjunto inmutable
     */
    public static <T> ImmutableSet<T> toImmutableSet(Set<T> set) {
        return ImmutableSet.copyOf(set);
    }

    /**
     * Convierte un mapa mutable en un mapa inmutable
     */
    public static <K, V> ImmutableMap<K, V> toImmutableMap(Map<K, V> map) {
        return ImmutableMap.copyOf(map);
    }
}
