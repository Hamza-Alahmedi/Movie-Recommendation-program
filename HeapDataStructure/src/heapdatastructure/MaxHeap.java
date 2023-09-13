/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package heapdatastructure;

/**
 *
 * @author Hamza
 */
public class MaxHeap<K extends Comparable<K>, V> {
    Node<K, V>[] Heap;
    int size;
    int maxsize;

    public MaxHeap(int capacity) {
        Heap = new Node[capacity];
        size = 0;
        maxsize = capacity;
    }

    
    public boolean isEmpty() {
    return size == 0;
}

    // Calculates the index of the parent node of the given position
    private int parent(int pos) {
        return (pos - 1) / 2;
    }

    // Calculates the index of the left child node of the given position
    private int leftChild(int pos) {
        return (2 * pos) + 1;
    }

    // Calculates the index of the right child node of the given position
    private int rightChild(int pos) {
        return (2 * pos) + 2;
    }

    // Checks if the given position is a leaf node
    private boolean isLeaf(int pos) {
        return pos > (size / 2) && pos <= size;
    }

    // Swaps the nodes at the given positions in the Heap array
   private void swap(int fpos, int spos) {
        Node<K, V> tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    // Maintains the max heap property starting from the given position
   // Maintains the max heap property starting from the given position
private void maxHeapify(int pos) {
    int largest = pos;
    int left = leftChild(pos);
    int right = rightChild(pos);

    // Compare the left child with the current position
    if (left < size && Heap[left].key.compareTo(Heap[largest].key) > 0) {
        largest = left;
    }

    // Compare the right child with the current largest
    if (right < size && Heap[right].key.compareTo(Heap[largest].key) > 0) {
        largest = right;
    }

    // If the largest element is not the current position, swap them
    if (largest != pos) {
        swap(pos, largest);
        maxHeapify(largest);
    }
}


   // Inserts a new node into the max heap
public void insert(Node<K, V> node) {
    if (size >= maxsize) {
        throw new IllegalStateException("Heap is full. Cannot insert more elements.");
    }

    Heap[size] = node;
    int current = size;
    size++;

    // Bubble-up the inserted node until it reaches the appropriate position
    while (current > 0 && Heap[current].key.compareTo(Heap[parent(current)].key) > 0) {
        swap(current, parent(current));
        current = parent(current);
    }

    // Perform maxHeapify on the root and its children to ensure the maximum three nodes property
    maxHeapify(0);
    if (leftChild(0) < size) {
        maxHeapify(leftChild(0));
    }
    if (rightChild(0) < size) {
        maxHeapify(rightChild(0));
    }
}



 public void Print() {
    PrintHelper(0);
}

private void PrintHelper(int index) {
    if (index >= size) {
        return;
    }

    // Traverse the left subtree
    PrintHelper(leftChild(index));

    // Traverse the right subtree
    PrintHelper(rightChild(index));

    // Print the current node
    System.out.println(" key: " + Heap[index].key+" value: " + Heap[index].value +", " );
}

   

    // Extracts the maximum node from the max heap
    public Node<K, V> extractMax() {
        Node<K, V> popped = Heap[0];
        Heap[0] = Heap[--size];
        maxHeapify(0);
        return popped;
    }
    
    
    // Retrieve the maximum users from the heap and return a new MaxHeap object
    public static MaxHeap<Double, Integer> getMaxUsers(int numUsers, MaxHeap heap) {
        MaxHeap<Double, Integer> maxUserheap = new MaxHeap<>(numUsers);
        for (int i = 0; i < numUsers; i++) {
            Node<Double, Integer> maxNode = heap.extractMax();
            if (maxNode == null) {
                break;  // If there are no more nodes in the heap, exit the loop
            }
            maxUserheap.insert(maxNode);
        }
        return maxUserheap;
    }


   
}
