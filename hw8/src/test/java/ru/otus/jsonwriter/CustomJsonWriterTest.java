package ru.otus.jsonwriter;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import ru.otus.jsonwriter.ru.otus.jsonwriter.data.Address;
import ru.otus.jsonwriter.ru.otus.jsonwriter.data.Person;
import ru.otus.jsonwriter.ru.otus.jsonwriter.data.Phone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class CustomJsonWriterTest {

    @Test
    public void testNull() {
        simpleTest(null, null);
    }

    @Test
    public void testString() {
        simpleTest("string", "string");
    }

    @Test
    public void testNumber() {
        simpleTest(100, 100);
    }

    @Test
    public void testNumberArray() throws IllegalAccessException {
        System.out.println(new Integer[]{1,2,3});

        String jsonString = MyJsonWriter.write(new Integer[]{1,2,3});
        System.out.println(jsonString);
        String gsonString = new Gson().toJson(new Integer[]{1,2,3});
        System.out.println(gsonString);
        Integer[] arrFromJson = new Gson().fromJson(jsonString, Integer[].class);

        assertArrayEquals(new Integer[]{1,2,3}, arrFromJson);
    }

    @Test
    public void testMultiDimNumberArray() throws IllegalAccessException {
        String jsonString = MyJsonWriter.write(new Integer[][]{{1,2,3}, {4,5,6}});
        System.out.println(jsonString);
        String gsonString = new Gson().toJson(new Integer[][]{{1,2,3}, {4,5,6}});
        System.out.println(gsonString);
        Integer[][] arrFromJson = new Gson().fromJson(jsonString, Integer[][].class);

        assertArrayEquals(new Integer[][]{{1,2,3}, {4,5,6}}, arrFromJson);
    }

    @Test
    public void testCollection() throws IllegalAccessException {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        String jsonString = MyJsonWriter.write(list);
        System.out.println(jsonString);
        String gsonString = new Gson().toJson(list);
        System.out.println(gsonString);

        HashSet<String> set = new HashSet(list);
        set.removeAll(new Gson().fromJson(jsonString, ArrayList.class));
        Assert.assertTrue(set.isEmpty());
    }

    @Test
    public void testComplexObject() throws Exception {
        Person personOrig = createTestPersonInstance();
        System.out.println("======>>>>> ORIGINAL PERSON...");
        System.out.println(personOrig);
        String jsonString = MyJsonWriter.write(personOrig);
        System.out.println("======>>>>> JSON ORIGINAL PERSON...");
        System.out.println(jsonString);
        System.out.println(new Gson().toJson(personOrig).toString());

        Person personFromJson = new Gson().fromJson(jsonString, Person.class);

        System.out.println("======>>>>> PERSON FROM JSON...");
        System.out.println(personFromJson);

        assertEquals(personFromJson, personOrig);
    }

    @Test
    public void testComplexArray() throws IllegalAccessException {
        Person[] arr = new Person[2];
        arr[0] = createTestPersonInstance();
        arr[1] = createTestPersonInstance();

        String jsonString = MyJsonWriter.write(arr);
        System.out.println("======>>>>> JSON ORIGINAL PERSONS ARRAY...");
        System.out.println(jsonString);

        Person[] personsArrFromJson = new Gson().fromJson(jsonString, Person[].class);

        assertArrayEquals(personsArrFromJson, arr);
    }

    @Test
    public void testComplexList() throws IllegalAccessException {
        List<Person> list = new ArrayList();
        Person person1 = createTestPersonInstance();
        person1.setFirstName("Alice");
        person1.setLastName("Cooper");
        Person person2 = createTestPersonInstance();
        person2.setFirstName("Bob");
        person2.setLastName("Dyllan");
        list.add(person1);
        list.add(person2);

        String jsonString = MyJsonWriter.write(list);
        System.out.println("======>>>>> JSON ORIGINAL PERSONS LIST...");
        System.out.println(jsonString);
        System.out.println(new Gson().toJson(list).toString());

        Person[] personsArrFromJson = new Gson().fromJson(jsonString, Person[].class);
        System.out.println("======>>>>> PERSONS LIST FROM JSON...");
        System.out.println(personsArrFromJson);

        assertArrayEquals(personsArrFromJson, list.toArray(new Person[2]));
    }

    @Test
    public void testComplexSet() throws IllegalAccessException {
        Set<Person> set = new HashSet();
        Person person1 = createTestPersonInstance();
        person1.setFirstName("Alice");
        person1.setLastName("Cooper");
        Person person2 = createTestPersonInstance();
        person2.setFirstName("Bob");
        person2.setLastName("Dyllan");
        set.add(person1);
        set.add(person2);

        String jsonString = MyJsonWriter.write(set);
        System.out.println("======>>>>> JSON ORIGINAL PERSONS SET...");
        System.out.println(jsonString);
        System.out.println(new Gson().toJson(set).toString());

        Person[] personsArrFromJson = new Gson().fromJson(jsonString, Person[].class);

        assertArrayEquals(personsArrFromJson, set.toArray(new Person[2]));
    }

    @Test
    public void testEmptyPerson() throws IllegalAccessException {
        String jsonString = MyJsonWriter.write(new Person());
        System.out.println("======>>>>> JSON ORIGINAL PERSON...");
        System.out.println(jsonString);
        System.out.println(new Gson().toJson(new Person()).toString());

        Person personFromJson = new Gson().fromJson(jsonString, Person.class);

        System.out.println("======>>>>> PERSON FROM JSON...");
        System.out.println(personFromJson);

        assertEquals(personFromJson, new Person());
    }

    private Person createTestPersonInstance() {
        Person person = new Person();
        Address address = new Address();
        address.setCity("Moscow");
        address.setCountry("Russia");
        Phone[] phones = new Phone[2];
        phones[0] = new Phone();
        phones[0].setNumber("123-456");
        phones[0].setType("mobile");
        phones[1] = new Phone();
        phones[1].setNumber("789-123");
        phones[1].setType("home");
        person.setFinAccounts(new int[]{111, 222, 333});
        person.setProducts(new String[]{"internet", "iptv"});
        person.setPhones(phones);
        address.setZipCode(107150);
        person.setAge(37);
        person.setFirstName("Alexander");
        person.setLastName("Pushkin");
        person.setAddress(address);

        return person;
    }

    private void simpleTest(Object toJson, Object toGson) {
        String jsonString = null;
        try {
            jsonString = MyJsonWriter.write(toJson);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(jsonString);
        String gsonString = new Gson().toJson(toGson);
        System.out.println(gsonString);

        Assert.assertTrue(jsonString.equals(gsonString));
    }
}