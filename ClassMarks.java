/*
 * ClassMarks.swift
 * Author: MFROB
 * Date: 2026-11-09

 * Reads students.txt and assignments.txt into arrays, randomly generates
 * integer marks for each student (mean 75, standard deviation 10) using
 * GenerateMarks, and saves the results to marks.csv.
 */

import java.io.*;
import java.util.*;

public class ClassMarks {

    public static void main(String[] args) {
        // read both files into arrays
        String[] studentArray = readFile("students.txt");
        String[] assignmentsArray = readFile("assignments.txt");

        // generate the marks and save them to marks.csv
        String[][] marks = GenerateMarks(studentArray, assignmentsArray);
        writeCSV("marks.csv", marks);

        System.out.println("marks.csv created with " + (marks.length - 1)
                + " students and " + (marks[0].length - 1) + " assignments. ");
    }

    // Reads a text file (one item per line) and returns the lines as an array
    public static String[] readFile(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + filename + ": " + e.getMessage());
        }
        return lines.toArray(new String[0]);
    }

    // Builds the 2D array of names and randomly generated marks
    public static String[][] GenerateMarks(String[] studentArray, String[] assignmentsArray) {
        int numRows = studentArray.length + 1;       // +1 for header row
        int numCols = assignmentsArray.length + 1;   // +1 for student name column
        String[][] marks = new String[numRows][numCols];
        Random random = new Random();

        // header row
        marks[0][0] = "Student  ";
        for (int c = 1; c < numCols; c++) {
            marks[0][c] = assignmentsArray[c - 1];
        }

        // one row per student
        for (int r = 1; r < numRows; r++) {
            marks[r][0] = studentArray[r - 1];
            for (int c = 1; c < numCols; c++) {
                int mark = (int) Math.round(random.nextGaussian() * 10 + 75);
                marks[r][c] = String.valueOf(mark);
            }
        }
        return marks;
    }

    // Writes the 2D array to a CSV file
    public static void writeCSV(String filename, String[][] data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String[] row : data) {
                writer.println(String.join(",", row));
            }
        } catch (IOException e) {
            System.out.println("Could not write " + filename + ": " + e.getMessage());
        }
    }
}