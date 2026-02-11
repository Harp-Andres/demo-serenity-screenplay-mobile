package org.example.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.UserModel;

import java.io.File;
import java.io.IOException;

public class UserLoader {
    public static UserModel fromJson(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Busca el archivo en src/test/resources/data/
            return mapper.readValue(
                    new File("src/main/java/org/example/data/" + fileName + ".json"),
                    UserModel.class
            );
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el archivo de usuario: " + fileName, e);
        }
    }
}