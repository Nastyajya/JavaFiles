package guru.qa;

import com.google.gson.Gson;
import guru.qa.model.CarModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


public class JsonTest {
    ClassLoader cl = FilesTests.class.getClassLoader();
    Gson gson = new Gson();

    @Test
    void jsonTestCar() throws IOException {
        try (InputStream stream = cl.getResourceAsStream("car.json");
             Reader reader = new InputStreamReader(stream)) {
            CarModel car = gson.fromJson(reader, CarModel.class);
            Assertions.assertEquals(2, car.getCars().size());
            Assertions.assertEquals("Ford", car.getCars().get(0).getName());
            Assertions.assertEquals("Fiesta", car.getCars().get(0).getModel());
            Assertions.assertEquals(2018, car.getCars().get(0).getYear());
            Assertions.assertEquals("Suzuki", car.getCars().get(1).getName());
            Assertions.assertEquals("Jimny", car.getCars().get(1).getModel());
            Assertions.assertEquals(2020, car.getCars().get(1).getYear());
        }
    }
}