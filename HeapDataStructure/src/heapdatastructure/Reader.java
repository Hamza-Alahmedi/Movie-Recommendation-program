/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package heapdatastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Hamza
 */
public class Reader {

    public static int[][] readExcel(String filePath) {
        int[][] matrix = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            Scanner scanner = new Scanner(fis);

            List<String[]> lines = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                lines.add(values);
            }

            int numRows = lines.size();
            int numCols = lines.get(0).length;

            matrix = new int[numRows][numCols];

            for (int i = 0; i < numRows; i++) {
                String[] values = lines.get(i);
                for (int j = 0; j < numCols; j++) {
                    matrix[i][j] = Integer.parseInt(values[j]);
                }
            }

            scanner.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + "\t");  // Use "\t" for tab-separated columns
            }
            System.out.println();  // Move to the next line after printing each row
        }
    }
    // Method to calculate the cosine similarity
    public static double cosineSimilarity(int[] vectorA, int[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
       
   
    // Print the maximum users from the heap
    public static void printMaxUsers(int numUsers, MaxHeap heap) {
        for (int i = 0; i < numUsers; i++) {
            Node<Double, Integer> maxNode = heap.extractMax();
            if (maxNode == null) {
                break;  // If there are no more nodes in the heap, exit the loop
            }
            System.out.println("User ID: " + maxNode.value + ", Value: " + maxNode.key);
        }
    }
}