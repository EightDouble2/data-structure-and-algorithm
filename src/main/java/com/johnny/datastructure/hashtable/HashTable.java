package com.johnny.datastructure.hashtable;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author johnnyhao
 *
 * 哈希表
 *
 * 散列表(Hash Table，也叫哈希表)，是根据关键码值(Key Value)而直接进行访问的数据结构
 * 也就是说，它通过把关键码值映射到表中一个位置来访问记录，以加快查找的速度
 * 这个映射函数叫做散列函数，存放记录的数组叫做散列表
 */
public class HashTable {

    public static void main(String[] args) {
        // 初始化哈希表
        HashTableImpl hashTable = new HashTableImpl(16);

        hashTable.add(new Person(1, "张三"));
        hashTable.add(new Person(2, "李四"));
        hashTable.add(new Person(17, "王五"));
        hashTable.list();
        hashTable.search(2);
    }
}

/**
 * 定义Person对象
 * 每个Person对象就是一个节点
 */
@ToString(exclude = "next")
@Getter
@Setter
@RequiredArgsConstructor
class Person {
    /**
     * ID
     */
    @NonNull
    private int id;

    /**
     * 姓名
     */
    @NonNull
    private String name;

    /**
     * 下一节点
     */
    private Person next;
}

/**
 * 定义Person单链表
 */
class PersonLinkedList {
    /**
     * 初始化头节点，定义链表头部
     */
    private final Person head = new Person(0, "");

    /**
     * 添加Person节点到链表尾部
     * @param person 新节点
     */
    public void add(Person person) {
        Person temp = head;

        while (temp.getNext() != null) {
            temp = temp.getNext();
        }

        temp.setNext(person);
    }

    /**
     * 遍历显示链表
     */
    public void list() {
        Person temp = head.getNext();

        while (temp != null) {
            System.out.println(temp);
            temp = temp.getNext();
        }
    }

    /**
     * 通过id查找
     * @param id id
     * @return Person
     */
    public Person search(int id) {
        Person temp = head.getNext();
        while (temp != null) {
            if (temp.getId() == id) {
                return temp;
            }
            temp = temp.getNext();
        }
        return null;
    }
}

/**
 * 定义哈希表
 */
class HashTableImpl {

    /**
     * 定义散列表
     */
    private final PersonLinkedList[] personLinkedListArray;

    /**
     * 构造器
     * @param personLinkedListArraySize 散列表大小
     */
    public HashTableImpl(int personLinkedListArraySize) {
        personLinkedListArray = new PersonLinkedList[personLinkedListArraySize];

        // 初始化每个链表
        for (int i = 0; i < personLinkedListArray.length; i++) {
            personLinkedListArray[i] = new PersonLinkedList();
        }
    }

    /**
     * 散列函数，使用一个简单取模法
     * @param id id
     * @return 散列表下标
     */
    public int hashFunction(int id) {
        return id % personLinkedListArray.length;
    }

    /**
     * 添加Person节点到哈希表
     * @param person 新节点
     */
    public void add(Person person) {
        int personLinkedListIndex = hashFunction(person.getId());
        personLinkedListArray[personLinkedListIndex].add(person);
    }

    /**
     * 遍历显示哈希表
     */
    public void list() {
        for (PersonLinkedList personLinkedList : personLinkedListArray) {
            personLinkedList.list();
        }
    }

    /**
     * 通过id查找
     * @param id id
     */
    public void search(int id) {
        int personLinkedListIndex = hashFunction(id);
        Person person = personLinkedListArray[personLinkedListIndex].search(id);
        System.out.println(person);
    }
}