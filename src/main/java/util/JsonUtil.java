package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Warehouse;

import java.io.IOException;
import java.util.List;

public class JsonUtil {
    public static void saveJson(List<Warehouse> list, String nameOfFile) throws IOException {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(list);
        FileUtil.writeToFile(jsonInString, nameOfFile);
    }

    public static List<Warehouse> getWarehouseFromJsonFile(String nameOfFile) {
        String warehouses = FileUtil.readFromFile(nameOfFile);
        Gson gson = new Gson();
        return gson.fromJson(warehouses, new TypeToken<List<Warehouse>>() {
        }.getType());
    }
}
