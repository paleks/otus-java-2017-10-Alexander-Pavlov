package ru.otus.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;

public class MyArrayListTests {

    private MyArrayList<Person> origList = new MyArrayList<Person>();

    private class Person implements Comparable {
        private int id;
        private String name;
        private int age;

        public Person(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person person = (Person) o;

            return getId() == person.getId();
        }

        @Override
        public int hashCode() {
            return getId();
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            if (o != null && o instanceof Person) {
                if (this.getId() > ((Person) o).getId()) {
                    return 1;
                }
                if (((Person) o).getId() == this.getId()) {
                    return 0;
                }
            }
            return -1;
        }
    }

    @Before
    public void init() {
        origList.clear();
        origList.add(new Person(1, "Alex", 20));
        origList.add(new Person(3, "Bob", 47));
        origList.add(new Person(5, "Fred", 45));
        origList.add(new Person(2, "Mike", 15));
        origList.add(new Person(4, "Alice", 14));
    }

    @Test
    public void addAllTest() {
        init();
        System.out.println("=======>>> addAllTest");
        Collections.addAll(origList,
                new Person(10, "Petr", 22),
                new Person(11, "Ksu", 20),
                new Person(12, "Lex", 29));
        //System.out.println(origList.toString());
        Assert.assertEquals(origList.size(), 8);
    }

    @Test
    public void copyTest() {
        init();
        System.out.println("=======>>> copyTest");
        MyArrayList<Person> copy = new MyArrayList<Person>();
        copy.add(new Person(100, "Alex", 20));
        copy.add(new Person(300, "Bob", 21));
        copy.add(new Person(500, "Fred", 45));
        copy.add(new Person(200, "Mike", 15));
        copy.add(new Person(400, "Alice", 34));
        copy.add(new Person(700, "Asya", 31));
        copy.add(new Person(800, "Hook", 34));
        Collections.copy(copy, origList);
        //System.out.println(copy.toString());
        Assert.assertArrayEquals(copy.subList(0, 4).toArray(new Person[5]), origList.toArray(new Person[5]));
    }

    @Test
    public void naturalSortTest() {
        init();
        System.out.println("=======>>> sortTest | natural sort");
        Collections.sort(origList, Comparator.naturalOrder());
        //System.out.println(origList.toString());
        Assert.assertArrayEquals(origList.toArray(new Person[5]),
                new Person[]{
                    new Person(1, "Alex", 20),
                    new Person(2, "Mike", 15),
                    new Person(3, "Bob", 47),
                    new Person(4, "Alice", 14),
                    new Person(5, "Fred", 45)});
    }

    @Test
    public void customSortTest() {
        init();
        System.out.println("=======>>> sortTest | custom sort by age");
        Collections.sort(origList, (p1, p2) -> {
            if (p1.age > p2.age) {
                return 1;
            } else if (p1.age == p2.age) {
                return 0;
            } else {
                return -1;
            }

        });
        //System.out.println(origList.toString());
        Assert.assertArrayEquals(origList.toArray(new Person[5]),
                new Person[]{
                        new Person(4, "Alice", 14),
                        new Person(2, "Mike", 15),
                        new Person(1, "Alex", 20),
                        new Person(5, "Fred", 45),
                        new Person(3, "Bob", 47)});
    }
}