/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package heapdatastructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Hamza
 */
public class GUI extends javax.swing.JFrame {

    DefaultListModel<String> model = new DefaultListModel<>();
    String targetUser = "C:\\Users\\hamza\\OneDrive\\Documents\\NetBeansProjects\\HeapDataStructure\\src\\heapdatastructure\\target_user.csv";
    String users = "C:\\Users\\hamza\\OneDrive\\Documents\\NetBeansProjects\\HeapDataStructure\\src\\heapdatastructure\\main_data.csv";
    String movieFile = "C:\\Users\\hamza\\OneDrive\\Documents\\NetBeansProjects\\HeapDataStructure\\src\\heapdatastructure\\movies.csv";
    MaxHeap<Integer, String> movieHeap = readMovieFile(movieFile);
    private boolean buttonClicked = false;

    public static List<Integer> getHighestRatedMovies(int[][] matrix, int numHighestRatedMovies) {
        List<Integer> highestRatedMovieIndices = new ArrayList<>();

        for (int user = 0; user < matrix.length; user++) {
            int[] ratings = matrix[user];
            List<Integer> userHighestRatedMovies = new ArrayList<>();

            for (int i = 0; i < numHighestRatedMovies; i++) {
                int maxRatingIndex = -1;
                int maxRating = 0;

                for (int movie = 1; movie < ratings.length; movie++) {
                    int rating = ratings[movie];

                    if (rating > maxRating && !userHighestRatedMovies.contains(movie)) {
                        maxRating = rating;
                        maxRatingIndex = movie;
                    }
                }

                if (maxRatingIndex != -1) {
                    userHighestRatedMovies.add(maxRatingIndex);
                }
            }

            highestRatedMovieIndices.addAll(userHighestRatedMovies);
        }

        return highestRatedMovieIndices;
    }

    public static <T> void printList(List<T> list) {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("List elements: ");
            for (T element : list) {
                System.out.println(element + " ");
            }
            System.out.println();
        }
    }

    public static int[][] createNewMatrix(int[][] originalMatrix, MaxHeap<Double, Integer> userHeap) {
        int numRows = userHeap.size;
        int numCols = originalMatrix[0].length;
        int[][] newMatrix = new int[numRows][numCols];

        int rowIndex = 0;
        while (!userHeap.isEmpty()) {
            int userId = userHeap.extractMax().value;
            for (int col = 0; col < numCols; col++) {
                newMatrix[rowIndex][col] = originalMatrix[userId][col];
            }
            rowIndex++;
        }

        return newMatrix;
    }

    public static MaxHeap<Integer, String> readMovieFile(String filename) {
        MaxHeap<Integer, String> movieHeap = new MaxHeap<>(10000);

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    int movieId = Integer.parseInt(parts[0].trim());
                    String movieName = parts[1].trim();
                    Node<Integer, String> node = new Node<>(movieId, movieName);
                    movieHeap.insert(node);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movieHeap;
    }

    public static List<String> searchMovieIDs(List<Integer> movieIDs, MaxHeap<Integer, String> movieHeap) {
        List<String> movieNames = new ArrayList<>();
        List<Node<Integer, String>> extractedNodes = new ArrayList<>();

        // Extract all nodes from the movie heap
        while (!movieHeap.isEmpty()) {
            extractedNodes.add(movieHeap.extractMax());
        }

        // Search for movie IDs in the extracted nodes
        for (int movieID : movieIDs) {
            boolean found = false;
            for (Node<Integer, String> node : extractedNodes) {
                if (node.key.equals(movieID)) {
                    String movieName = node.value;
                    movieNames.add(movieName);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Movie ID " + movieID + " not found in the movie heap.");
                movieNames.add("Movie Not Found");
            }
        }

        return movieNames;
    }

    // add movie names from combo to a list
    public static List<Object> getSelectedMovies(List<JComboBox<String>> comboBoxes) {
        List<Object> selectedMovies = new ArrayList<>();

        for (JComboBox<String> comboBox : comboBoxes) {
            Object selectedItem = comboBox.getSelectedItem();
            if (selectedItem != null) {
                selectedMovies.add(selectedItem);
            }
        }

        return selectedMovies;
    }

    public static int[] getValuesFromTextFields(JTextField[] textFields) {
        int[] values = new int[textFields.length];

        for (int i = 0; i < textFields.length; i++) {
            String valueString = textFields[i].getText();
            values[i] = Integer.parseInt(valueString);
        }

        return values;
    }

    // 
// public static List<Integer> searchMovieIDsByNames(List<Object> movieNames, MaxHeap<Integer, String> movieHeap) {
//    List<Integer> movieIDs = new ArrayList<>();
//    List<Node<Integer, String>> heapList = new ArrayList<>();
//
//    for (Object movieNameObj : movieNames) {
//        String movieName = (String) movieNameObj; // Perform type casting to String
//
//        int foundMovieID = -1;
//
//        for (Node<Integer, String> node : heapList) {
//            if (node.value.contains(movieName)) {
//                foundMovieID = node.key;
//                break;
//            }
//        }
//
//        if (foundMovieID != -1) {
//            movieIDs.add(foundMovieID);
//        } else {
//            System.out.println("Movie name \"" + movieName + "\" not found in the movie heap.");
//        }
//    }
//
//    return movieIDs;
//}
    public static List<Integer> searchMovieIDsByNames(List<Object> movieNames, MaxHeap<Integer, String> movieHeap) {
        List<Integer> movieID = new ArrayList<>();
        List<Node<Integer, String>> extractedNodes = new ArrayList<>();

        // Extract all nodes from the movie heap
        while (!movieHeap.isEmpty()) {
            extractedNodes.add(movieHeap.extractMax());
        }

        // Search for movie IDs in the extracted nodes
        for (Object movieName : movieNames) {
            boolean found = false;
            for (Node<Integer, String> node : extractedNodes) {
                if (node.value.equals(movieName)) {
                    int movieid = node.key;
                    movieID.add(movieid);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Movie name " + movieName + " not found in the movie heap.");
                movieNames.add("Movie Not Found");
            }
        }

        return movieID;
    }

    public static int[] createNewUserMatrix(int[] movieRatings, List<Integer> movieIDs, int newUserID, int matrixLength) {
        int[] newUserMatrix = new int[matrixLength];
        newUserMatrix[0] = newUserID; // Set the new user ID as the first element

        for (int i = 0; i < movieRatings.length; i++) {
            int movieID = movieIDs.get(i); // Get the movie ID from the list
            newUserMatrix[movieID] = movieRatings[i]; // Set the rating at the corresponding index
        }

        return newUserMatrix;
    }

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();

        // Column index to read from CSV
        int columnIndex = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(targetUser));
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] columns = line.split(",");
                if (columns.length > 0) {
                    String columnValue = columns[0]; // Get the value of the first column
                    jComboTarget.addItem(columnValue);
                } else {
                    System.err.println("Invalid column index on line " + lineNumber);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        jComboTarget.setSelectedIndex(1);

        // fill the movies combobox with the data from the csv file.
        // Column index to read from CSV
        try {
            BufferedReader br = new BufferedReader(new FileReader(movieFile));
            String line;
            Random random = new Random();

            // Skip the header line if present
            br.readLine();

            // Add random movies to each jComboBox
            for (int i = 2; i <= 6; i++) {
                javax.swing.JComboBox<String> comboBox = null;
                // add 10 movies to the choosen combobox .
                switch (i) {
                    case 2:
                        comboBox = jComboBox2;
                        break;
                    case 3:
                        comboBox = jComboBox3;
                        break;
                    case 4:
                        comboBox = jComboBox4;
                        break;
                    case 5:
                        comboBox = jComboBox5;
                        break;
                    case 6:
                        comboBox = jComboBox6;
                        break;
                }

                if (comboBox != null) {
                    int numMovies = 0;
                    while (numMovies < 10 && (line = br.readLine()) != null) {
                        String[] columns = line.split(",");
                        if (columns.length > 1) {
                            String movieName = columns[1]; // Get the value of the second column (movie's name)
                            comboBox.addItem(movieName);
                            numMovies++;
                        }
                    }
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboTarget = new javax.swing.JComboBox<>();
        User = new javax.swing.JTextField();
        Movie = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        xValue = new javax.swing.JTextField();
        kValue = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        userID = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 14)); // NOI18N
        jLabel2.setText("Target User ID:");

        jLabel4.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel4.setText("X (user's number) :");

        jLabel3.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel3.setText("K(highest rated movies) :");

        jComboTarget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboTargetActionPerformed(evt);
            }
        });

        Movie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MovieActionPerformed(evt);
            }
        });

        jButton1.setText("Get Recomandations");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        jLabel1.setText("jLabel1");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/heapdatastructure/Treetog-I-Videos.256.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Movie, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(User, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(User))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(Movie))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(36, 36, 36))
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel6.setText("X (user's number) :");

        jLabel7.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel7.setText("K(highest rated movies) :");

        jScrollPane2.setViewportView(jList2);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Rate the movies from 1 to 5 :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Your User ID:");

        userID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userIDActionPerformed(evt);
            }
        });

        jButton2.setText("Get Recomandations");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(xValue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(kValue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(userID, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2)
                .addGap(87, 87, 87))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userID, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xValue, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kValue, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 805, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("tab2", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboTargetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboTargetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboTargetActionPerformed

    private void MovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MovieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MovieActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Control if X and K are positive Integer values.
        String XKregex = "\\d+";
        String xValue = User.getText();
        String kValue = Movie.getText();

        if (!xValue.matches(XKregex) || !kValue.matches(XKregex)) {
            jLabel1.setText("* Please enter an Integer value !!");
        } else {
            jLabel1.setText("");

            // Read the target user matrix from the file
            int[][] target_user_matrix = Reader.readExcel(targetUser);

            // Read the main data matrix from the file
            int[][] main_data_matrix = Reader.readExcel(users);

            // Create a new max heap
            MaxHeap<Double, Integer> maxHeap = new MaxHeap<>(10000);

            for (int user_id = 0; user_id < main_data_matrix.length; user_id++) {
                double similarity = Reader.cosineSimilarity(target_user_matrix[jComboTarget.getSelectedIndex()], main_data_matrix[user_id]);
                Node<Double, Integer> node = new Node<>(similarity, user_id);
                maxHeap.insert(node);
            }

            int numberOfUsers = Integer.parseInt(xValue);
            int numberOfMovies = Integer.parseInt(kValue);
            // create a heap from movie file contain the movie_id and movie name
            MaxHeap<Integer, String> movieHeap = readMovieFile(movieFile);
            // Create a new max heap for the most similar users
            MaxHeap<Double, Integer> maxUserHeap = MaxHeap.getMaxUsers(numberOfUsers, maxHeap);
            //maxUserHeap.Print();
            int maxUserMatrix[][] = createNewMatrix(main_data_matrix, maxUserHeap);
            //Reader.printMatrix(maxUserMatrix);
            List<Integer> movieIds = getHighestRatedMovies(maxUserMatrix, numberOfMovies);
            printList(movieIds);

//            //movieHeap.Print();
            List<String> movieNames = searchMovieIDs(movieIds, movieHeap);
            printList(movieNames);
            model.clear();
            for (String movie : movieNames) {
                model.addElement(movie);
            }
            jList1.setModel(model);

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void userIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userIDActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        boolean isWork = true;

        if (jTextField3.getText().isEmpty() || jTextField4.getText().isEmpty() || jTextField5.getText().isEmpty()
                || jTextField6.getText().isEmpty() || jTextField7.getText().isEmpty() || xValue.getText().isEmpty()
                || kValue.getText().isEmpty() || userID.getText().isEmpty()) {
            isWork = false;
            jLabel5.setText("* You must fill all the fields !!!");
        } else {
            String regex = "[1-5]";
            String[] rateFields = {
                jTextField3.getText(), jTextField4.getText(), jTextField5.getText(), jTextField6.getText(),
                jTextField7.getText()
            };
            // Read the target user matrix from the file
            int[][] target_user_matrix = Reader.readExcel(targetUser);

            // Read the main data matrix from the file
            int[][] main_data_matrix = Reader.readExcel(users);
            jLabel1.setText("");
            System.out.println("test test");
            MaxHeap<Integer, String> movieHeap = readMovieFile(movieFile);
            List<JComboBox<String>> comboBoxes = new ArrayList<>();
            comboBoxes.add(jComboBox2);
            comboBoxes.add(jComboBox3);
            comboBoxes.add(jComboBox4);
            comboBoxes.add(jComboBox5);
            comboBoxes.add(jComboBox6);

            List<Object> selectedMovies = getSelectedMovies(comboBoxes);
            //printList(selectedMovies);
            int newuserID = Integer.parseInt(userID.getText());
            List<Integer> movieIds = searchMovieIDsByNames(selectedMovies, movieHeap);
            // printList(movieIds);
            int newUserId = Integer.parseInt(userID.getText());

            // get the rating from the txtfield into an array
            JTextField[] textFields = {jTextField3, jTextField4, jTextField5, jTextField6, jTextField7};
            int[] ratings = getValuesFromTextFields(textFields);
            int[] newUserMatrix = createNewUserMatrix(ratings, movieIds, newUserId, main_data_matrix[0].length);
//            // Print the newUserMatrix array
//            for (int i = 0; i < newUserMatrix.length; i++) {
//                System.out.print(newUserMatrix[i]+" ");
//            }
            MaxHeap<Double, Integer> maxHeap = new MaxHeap<>(10000);
            for (int user_id = 1; user_id < main_data_matrix.length; user_id++) {
                double similarity = Reader.cosineSimilarity(newUserMatrix, main_data_matrix[user_id]);
                Node<Double, Integer> node = new Node<>(similarity, user_id);
                maxHeap.insert(node);
            }
           // maxHeap.Print();
            int numberOfUsers = Integer.parseInt(xValue.getText());
            int numberOfMovies = Integer.parseInt(kValue.getText());
            // Create a new max heap for the most similar users
            MaxHeap<Double, Integer> maxUserHeap = MaxHeap.getMaxUsers(numberOfUsers, maxHeap);
            //maxUserHeap.Print();
            int maxUserMatrix[][] = createNewMatrix(main_data_matrix, maxUserHeap);
            //Reader.printMatrix(maxUserMatrix);
            List<Integer> highestRatedmovieIds = getHighestRatedMovies(maxUserMatrix, numberOfMovies);
            printList(highestRatedmovieIds);
            //craete movie heap to search by id for movie names
            MaxHeap<Integer, String> movieHeap2 = readMovieFile(movieFile);
            List<String> movieNames = searchMovieIDs(highestRatedmovieIds, movieHeap2);
            printList(movieNames);
            model.clear();
            for (String movie : movieNames) {
                model.addElement(movie);
            }
            jList2.setModel(model);

        
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Movie;
    private javax.swing.JTextField User;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboTarget;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField kValue;
    private javax.swing.JTextField userID;
    private javax.swing.JTextField xValue;
    // End of variables declaration//GEN-END:variables
}
