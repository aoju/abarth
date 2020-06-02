package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.lang.Dict;
import org.aoju.bus.core.lang.Editor;
import org.aoju.bus.core.lang.Filter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * 集合工具类单元测试
 */
public class CollKitTest {

    @Test
    public void newHashSetTest() {
        Set<String> set = CollKit.newHashSet((String[]) null);
        Assertions.assertNotNull(set);
    }

    @Test
    public void valuesOfKeysTest() {
        Dict v1 = Dict.create().set("id", 12).set("name", "张三").set("age", 23);
        Dict v2 = Dict.create().set("age", 13).set("id", 15).set("name", "李四");

        final String[] keys = v1.keySet().toArray(new String[v1.size()]);
        ArrayList<Object> v1s = CollKit.valuesOfKeys(v1, keys);
        Assertions.assertTrue(v1s.contains(12));
        Assertions.assertTrue(v1s.contains(23));
        Assertions.assertTrue(v1s.contains("张三"));

        ArrayList<Object> v2s = CollKit.valuesOfKeys(v2, keys);
        Assertions.assertTrue(v2s.contains(15));
        Assertions.assertTrue(v2s.contains(13));
        Assertions.assertTrue(v2s.contains("李四"));
    }

    @Test
    public void unionTest() {
        ArrayList<String> list1 = CollKit.newArrayList("a", "b", "b", "c", "d", "x");
        ArrayList<String> list2 = CollKit.newArrayList("a", "b", "b", "b", "c", "d");

        Collection<String> union = CollKit.union(list1, list2);

        Assertions.assertEquals(3, CollKit.count(union, t -> t.equals("b")));
    }

    @Test
    public void intersectionTest() {
        ArrayList<String> list1 = CollKit.newArrayList("a", "b", "b", "c", "d", "x");
        ArrayList<String> list2 = CollKit.newArrayList("a", "b", "b", "b", "c", "d");

        Collection<String> union = CollKit.intersection(list1, list2);
        Assertions.assertEquals(2, CollKit.count(union, t -> t.equals("b")));
    }

    @Test
    public void disjunctionTest() {
        ArrayList<String> list1 = CollKit.newArrayList("a", "b", "b", "c", "d", "x");
        ArrayList<String> list2 = CollKit.newArrayList("a", "b", "b", "b", "c", "d", "x2");

        Collection<String> disjunction = CollKit.disjunction(list1, list2);
        Assertions.assertTrue(disjunction.contains("b"));
        Assertions.assertTrue(disjunction.contains("x2"));
        Assertions.assertTrue(disjunction.contains("x"));

        Collection<String> disjunction2 = CollKit.disjunction(list2, list1);
        Assertions.assertTrue(disjunction2.contains("b"));
        Assertions.assertTrue(disjunction2.contains("x2"));
        Assertions.assertTrue(disjunction2.contains("x"));
    }

    @Test
    public void disjunctionTest2() {
        // 任意一个集合为空，差集为另一个集合
        ArrayList<String> list1 = CollKit.newArrayList();
        ArrayList<String> list2 = CollKit.newArrayList("a", "b", "b", "b", "c", "d", "x2");

        Collection<String> disjunction = CollKit.disjunction(list1, list2);
        Assertions.assertEquals(list2, disjunction);
        Collection<String> disjunction2 = CollKit.disjunction(list2, list1);
        Assertions.assertEquals(list2, disjunction2);
    }

    @Test
    public void disjunctionTest3() {
        // 无交集下返回共同的元素
        ArrayList<String> list1 = CollKit.newArrayList("1", "2", "3");
        ArrayList<String> list2 = CollKit.newArrayList("a", "b", "c");

        Collection<String> disjunction = CollKit.disjunction(list1, list2);
        Assertions.assertTrue(disjunction.contains("1"));
        Assertions.assertTrue(disjunction.contains("2"));
        Assertions.assertTrue(disjunction.contains("3"));
        Assertions.assertTrue(disjunction.contains("a"));
        Assertions.assertTrue(disjunction.contains("b"));
        Assertions.assertTrue(disjunction.contains("c"));
        Collection<String> disjunction2 = CollKit.disjunction(list2, list1);
        Assertions.assertTrue(disjunction2.contains("1"));
        Assertions.assertTrue(disjunction2.contains("2"));
        Assertions.assertTrue(disjunction2.contains("3"));
        Assertions.assertTrue(disjunction2.contains("a"));
        Assertions.assertTrue(disjunction2.contains("b"));
        Assertions.assertTrue(disjunction2.contains("c"));
    }

    @Test
    public void toMapListAndToListMapTest() {
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("a", "值1");
        map1.put("b", "值1");

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("a", "值2");
        map2.put("c", "值3");

        // ----------------------------------------------------------------------------------------
        ArrayList<HashMap<String, String>> list = CollKit.newArrayList(map1, map2);
        Map<String, List<String>> map = CollKit.toListMap(list);
        Assertions.assertEquals("值1", map.get("a").get(0));
        Assertions.assertEquals("值2", map.get("a").get(1));

        // ----------------------------------------------------------------------------------------
        List<Map<String, String>> listMap = CollKit.toMapList(map);
        Assertions.assertEquals("值1", listMap.get(0).get("a"));
        Assertions.assertEquals("值2", listMap.get(1).get("a"));
    }

    @Test
    public void getFieldValuesTest() {
        Dict v1 = Dict.create().set("id", 12).set("name", "张三").set("age", 23);
        Dict v2 = Dict.create().set("age", 13).set("id", 15).set("name", "李四");
        ArrayList<Dict> list = CollKit.newArrayList(v1, v2);

        List<Object> fieldValues = CollKit.getFieldValues(list, "name");
        Assertions.assertEquals("张三", fieldValues.get(0));
        Assertions.assertEquals("李四", fieldValues.get(1));
    }

    @Test
    public void splitTest() {
        final ArrayList<Integer> list = CollKit.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<List<Integer>> split = CollKit.split(list, 3);
        Assertions.assertEquals(3, split.size());
        Assertions.assertEquals(3, split.get(0).size());
    }

    @Test
    public void foreachTest() {
        HashMap<String, String> map = MapKit.newHashMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        final String[] result = new String[1];
        CollKit.forEach(map, (key, value, index) -> {
            if (key.equals("a")) {
                result[0] = value;
            }
        });
        Assertions.assertEquals("1", result[0]);
    }

    @Test
    public void filterTest() {
        List<String> list = CollKit.newArrayList("a", "b", "c");

        Collection<String> filtered = CollKit.filter(list, (Editor<String>) t -> t + 1);

        Assertions.assertEquals(CollKit.newArrayList("a1", "b1", "c1"), filtered);
    }

    @Test
    public void filterTest2() {
        List<String> list = CollKit.newArrayList("a", "b", "c");

        List<String> filtered = CollKit.filter(list, (Filter<String>) t -> false == "a".equals(t));

        // 原地过滤
        Assertions.assertSame(list, filtered);
        Assertions.assertEquals(CollKit.newArrayList("b", "c"), filtered);
    }

    @Test
    public void removeNullTest() {
        List<String> list = CollKit.newArrayList("a", "b", "c", null, "", "  ");

        List<String> filtered = (List<String>) CollKit.removeNull(list);

        // 原地过滤
        Assertions.assertSame(list, filtered);
        Assertions.assertEquals(CollKit.newArrayList("a", "b", "c", "", "  "), filtered);
    }

    @Test
    public void removeEmptyTest() {
        ArrayList<String> list = CollKit.newArrayList("a", "b", "c", null, "", "  ");

        ArrayList<String> filtered = (ArrayList<String>) CollKit.removeEmpty(list);

        // 原地过滤
        Assertions.assertSame(list, filtered);
        Assertions.assertEquals(CollKit.newArrayList("a", "b", "c", "  "), filtered);
    }

    @Test
    public void removeBlankTest() {
        ArrayList<String> list = CollKit.newArrayList("a", "b", "c", null, "", "  ");

        ArrayList<String> filtered = (ArrayList<String>) CollKit.removeBlank(list);

        // 原地过滤
        Assertions.assertSame(list, filtered);
        Assertions.assertEquals(CollKit.newArrayList("a", "b", "c"), filtered);
    }

    @Test
    public void groupTest() {
        List<String> list = CollKit.newArrayList("1", "2", "3", "4", "5", "6");
        List<List<String>> group = CollKit.group(list, null);
        Assertions.assertTrue(group.size() > 0);

        List<List<String>> group2 = CollKit.group(list, t -> {
            // 按照奇数偶数分类
            return Integer.parseInt(t) % 2;
        });
        Assertions.assertEquals(CollKit.newArrayList("2", "4", "6"), group2.get(0));
        Assertions.assertEquals(CollKit.newArrayList("1", "3", "5"), group2.get(1));
    }

    @Test
    public void groupByFieldTest() {
        List<TestBean> list = CollKit.newArrayList(new TestBean("张三", 12), new TestBean("李四", 13), new TestBean("王五", 12));
        List<List<TestBean>> groupByField = CollKit.groupByField(list, "age");
        Assertions.assertEquals("张三", groupByField.get(0).get(0).getName());
        Assertions.assertEquals("王五", groupByField.get(0).get(1).getName());

        Assertions.assertEquals("李四", groupByField.get(1).get(0).getName());
    }

    @Test
    public void sortByPropertyTest() {
        List<TestBean> list = CollKit.newArrayList(new TestBean("张三", 12, DateKit.parse("2018-05-01")), //
                new TestBean("李四", 13, DateKit.parse("2018-03-01")), //
                new TestBean("王五", 12, DateKit.parse("2018-04-01"))//
        );

        CollKit.sortByProperty(list, "createTime");
        Assertions.assertEquals("李四", list.get(0).getName());
        Assertions.assertEquals("王五", list.get(1).getName());
        Assertions.assertEquals("张三", list.get(2).getName());
    }

    @Test
    public void listTest() {
        List<Object> list1 = CollKit.list(false);
        List<Object> list2 = CollKit.list(true);

        Assertions.assertTrue(list1 instanceof ArrayList);
        Assertions.assertTrue(list2 instanceof LinkedList);
    }

    @Test
    public void listTest2() {
        List<String> list1 = CollKit.list(false, "a", "b", "c");
        List<String> list2 = CollKit.list(true, "a", "b", "c");
        Assertions.assertEquals("[a, b, c]", list1.toString());
        Assertions.assertEquals("[a, b, c]", list2.toString());
    }

    @Test
    public void listTest3() {
        HashSet<String> set = new LinkedHashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");

        List<String> list1 = CollKit.list(false, set);
        List<String> list2 = CollKit.list(true, set);
        Assertions.assertEquals("[a, b, c]", list1.toString());
        Assertions.assertEquals("[a, b, c]", list2.toString());
    }

    @Test
    public void getTest() {
        HashSet<String> set = CollKit.newHashSet(true, new String[]{"A", "B", "C", "D"});
        String str = CollKit.get(set, 2);
        Assertions.assertEquals("C", str);

        str = CollKit.get(set, -1);
        Assertions.assertEquals("D", str);
    }

    @Test
    public void addAllIfNotContainsTest() {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("2");
        list2.add("3");
        CollKit.addAllIfNotContains(list1, list2);

        Assertions.assertEquals(3, list1.size());
        Assertions.assertEquals("1", list1.get(0));
        Assertions.assertEquals("2", list1.get(1));
        Assertions.assertEquals("3", list1.get(2));
    }

    @Test
    public void subInput1PositiveNegativePositiveOutput1() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 3;
        final int end = -1;
        final int step = 2;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        arrayList.add(null);
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput1ZeroPositivePositiveOutput1() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 0;
        final int end = 1;
        final int step = 2;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);

        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        arrayList.add(null);
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput1PositiveZeroOutput0() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 1;
        final int end = 0;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end);

        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput0ZeroZeroZeroOutputNull() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        final int start = 0;
        final int end = 0;
        final int step = 0;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        Assertions.assertTrue(retval.isEmpty());
    }

    @Test
    public void subInput1PositiveNegativeZeroOutput0() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 1;
        final int end = -2_147_483_648;
        final int step = 0;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput1PositivePositivePositiveOutput0() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 2_147_483_647;
        final int end = 2_147_483_647;
        final int step = 1_073_741_824;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput0ZeroPositiveNegativeOutputNull() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        final int start = 0;
        final int end = 1;
        final int step = -2_147_483_646;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        Assertions.assertTrue(retval.isEmpty());
    }

    @Test
    public void subInput1PositivePositivePositiveOutput02() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 2_147_483_643;
        final int end = 2_147_483_642;
        final int step = 1_073_741_824;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput1ZeroZeroPositiveOutput0() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(0);
        final int start = 0;
        final int end = 0;
        final int step = 2;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput1NegativeZeroPositiveOutput0() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(0);
        final int start = -1;
        final int end = 0;
        final int step = 2;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput0ZeroZeroOutputNull() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        final int start = 0;
        final int end = 0;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end);
        // Assert result
        Assertions.assertTrue(retval.isEmpty());
    }

    @Test
    public void sortPageAllTest() {
        ArrayList<Integer> list = CollKit.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> sortPageAll = CollKit.sortPageAll(2, 5, Comparator.reverseOrder(), list);

        Assertions.assertEquals(CollKit.newArrayList(4, 3, 2, 1), sortPageAll);
    }

    @Test
    public void containsAnyTest() {
        ArrayList<Integer> list1 = CollKit.newArrayList(1, 2, 3, 4, 5);
        ArrayList<Integer> list2 = CollKit.newArrayList(5, 3, 1, 9, 11);

        Assertions.assertTrue(CollKit.containsAny(list1, list2));
    }

    @Test
    public void containsAllTest() {
        ArrayList<Integer> list1 = CollKit.newArrayList(1, 2, 3, 4, 5);
        ArrayList<Integer> list2 = CollKit.newArrayList(5, 3, 1);

        Assertions.assertTrue(CollKit.containsAll(list1, list2));
    }

    @Test
    public void getLastTest() {
        // 测试：空数组返回null而不是报错
        List<String> test = CollKit.newArrayList();
        String last = CollKit.getLast(test);
        Assertions.assertNull(last);
    }

    @Test
    public void zipTest() {
        Collection<String> keys = CollKit.newArrayList("a", "b", "c", "d");
        Collection<Integer> values = CollKit.newArrayList(1, 2, 3, 4);

        Map<String, Integer> map = CollKit.zip(keys, values);

        Assertions.assertEquals(4, map.size());

        Assertions.assertEquals(1, map.get("a").intValue());
        Assertions.assertEquals(2, map.get("b").intValue());
        Assertions.assertEquals(3, map.get("c").intValue());
        Assertions.assertEquals(4, map.get("d").intValue());
    }

    public static class TestBean {
        private String name;
        private int age;
        private Date createTime;

        public TestBean(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public TestBean(String name, int age, Date createTime) {
            this.name = name;
            this.age = age;
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return "TestBeans [name=" + name + ", age=" + age + "]";
        }
    }

}