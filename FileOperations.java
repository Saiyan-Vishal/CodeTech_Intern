package org.example;
import java.io.*;
public class FileOperations {




        private static final String FILE_PATH = "Nova.txt";

        public static void main(String[] args) {
            writeToFile("This is a sample text file.\nWelcome to file handling in Java!\n");
            readFromFile();
            modifyFile("sample", "example");
            readFromFile();
        }

        // Method to write to a file
        public static void writeToFile(String content) {
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                writer.write(content);
                System.out.println("File written successfully.");
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }

        // Method to read from a file
        public static void readFromFile() {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                System.out.println("\nReading File Content:");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
            }
        }

        // Method to modify a file by replacing text
        public static void modifyFile(String oldWord, String newWord) {
            File file = new File(FILE_PATH);
            StringBuilder content = new StringBuilder();

            // Read the file content
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line.replaceAll(oldWord, newWord)).append("\n");
                }
            } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
                return;
            }

            // Write the modified content back to the file
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content.toString());
                System.out.println("File modified successfully.");
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }

}
