package util;

import model.Warehouse;

import java.util.List;

public interface FileUtil {
    void serealise(List<Warehouse> list, String nameOfFile);

    List<Warehouse> deserealise(String nameOfFile) ;
}
