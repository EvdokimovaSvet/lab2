package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Warehouse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonUtil implements FileUtil {

    public void serealise(List<Warehouse> list, String nameOfFile) {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(list);
        try (FileWriter writer = new FileWriter(nameOfFile, false)) {
            writer.write(jsonInString);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Warehouse> deserealise(String nameOfFile) {
        StringBuilder resultString = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(nameOfFile))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
               resultString.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String warehouses = resultString.toString();
        Gson gson = new Gson();
        return gson.fromJson(warehouses, new TypeToken<List<Warehouse>>() {
        }.getType());
    }
}
