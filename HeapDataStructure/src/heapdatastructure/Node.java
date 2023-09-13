/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package heapdatastructure;

/**
 *
 * @author Hamza
 */
public class Node<K extends Comparable<K>, V> {

    K key; // the cossinSimilraty
    V value; // the User_id
    Node<K, V> left, right;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        left = right = null;
    }
}
