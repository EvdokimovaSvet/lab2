package util;

import model.Warehouse;

import java.util.List;

public interface FileUtil {
    void serealise(Warehouse list, String nameOfFile);

    Warehouse deserealise(String nameOfFile) ;
}
