package com.lawfirm.common.util.collection;

import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class CollUtils {
    
    public static <T> List<T> emptyIfNull(List<T> list) {
        return list == null ? new ArrayList<>() : list;
    }
    
    public static <T> Set<T> emptyIfNull(Set<T> set) {
        return set == null ? new HashSet<>() : set;
    }
    
    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        if (collection == null || predicate == null) {
            return new ArrayList<>();
        }
        return collection.stream().filter(predicate).collect(Collectors.toList());
    }
    
    public static <T, R> List<R> map(Collection<T> collection, Function<T, R> mapper) {
        if (collection == null || mapper == null) {
            return new ArrayList<>();
        }
        return collection.stream().map(mapper).collect(Collectors.toList());
    }
    
    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (list == null || size <= 0) {
            return new ArrayList<>();
        }
        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            result.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return result;
    }
    
    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
    
    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> collection, Class<T> componentType) {
        if (isEmpty(collection)) {
            return (T[]) java.lang.reflect.Array.newInstance(componentType, 0);
        }
        T[] array = (T[]) java.lang.reflect.Array.newInstance(componentType, collection.size());
        return collection.toArray(array);
    }
} 