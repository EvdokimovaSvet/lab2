package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static void writeToFile(String string, String nameOfFile) {
        try (FileWriter writer = new FileWriter(nameOfFile, false)) {
            writer.write(string);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String readFromFile(String nameOfFile) {
        StringBuilder resultString = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(nameOfFile))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                resultString.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString.toString();
    }
}
