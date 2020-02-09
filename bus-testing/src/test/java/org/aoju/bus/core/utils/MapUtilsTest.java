package org.aoju.bus.core.utils;

import org.aoju.bus.core.convert.Convert;
import org.aoju.bus.core.lang.Editor;
import org.aoju.bus.core.lang.Filter;
import org.aoju.bus.core.lang.Normal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtilsTest {

    @Test
    public void filterTest() {
        Map<String, String> map = MapUtils.newHashMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");

        Map<String, String> map2 = MapUtils.filter(map, (Filter<Entry<String, String>>) t -> Convert.toInt(t.getValue()) % 2 == 0);

        Assertions.assertEquals(2, map2.size());

        Assertions.assertEquals("2", map2.get("b"));
        Assertions.assertEquals("4", map2.get("d"));
    }

    @Test
    public void filterForEditorTest() {
        Map<String, String> map = MapUtils.newHashMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");

        Map<String, String> map2 = MapUtils.filter(map, (Editor<Entry<String, String>>) t -> {
            // 修改每个值使之*10
            t.setValue(t.getValue() + "0");
            return t;
        });

        Assertions.assertEquals(4, map2.size());

        Assertions.assertEquals("10", map2.get("a"));
        Assertions.assertEquals("20", map2.get("b"));
        Assertions.assertEquals("30", map2.get("c"));
        Assertions.assertEquals("40", map2.get("d"));
    }

    @Test
    public void reverseTest() {
        Map<String, String> map = MapUtils.newHashMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");

        Map<String, String> map2 = MapUtils.reverse(map);

        Assertions.assertEquals("a", map2.get("1"));
        Assertions.assertEquals("b", map2.get("2"));
        Assertions.assertEquals("c", map2.get("3"));
        Assertions.assertEquals("d", map2.get("4"));
    }

    @Test
    public void toObjectArrayTest() {
        Map<String, String> map = MapUtils.newHashMap(true);
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");

        Object[][] objectArray = MapUtils.toObjectArray(map);
        Assertions.assertEquals("a", objectArray[0][0]);
        Assertions.assertEquals("1", objectArray[0][1]);
        Assertions.assertEquals("b", objectArray[1][0]);
        Assertions.assertEquals("2", objectArray[1][1]);
        Assertions.assertEquals("c", objectArray[2][0]);
        Assertions.assertEquals("3", objectArray[2][1]);
        Assertions.assertEquals("d", objectArray[3][0]);
        Assertions.assertEquals("4", objectArray[3][1]);
    }

    @Test
    public void sortJoinTest() {
        Map<String, String> build = MapUtils.builder(new HashMap<String, String>())
                .put("key1", "value1")
                .put("key3", "value3")
                .put("key2", "value2").build();

        String join1 = MapUtils.sortJoin(build, Normal.EMPTY, Normal.EMPTY, false);
        Assertions.assertEquals("key1value1key2value2key3value3", join1);

        String join2 = MapUtils.sortJoin(build, Normal.EMPTY, Normal.EMPTY, false, "123");
        Assertions.assertEquals("key1value1key2value2key3value3123", join2);

        String join3 = MapUtils.sortJoin(build, Normal.EMPTY, Normal.EMPTY, false, "123", "abc");
        Assertions.assertEquals("key1value1key2value2key3value3123abc", join3);
    }

}