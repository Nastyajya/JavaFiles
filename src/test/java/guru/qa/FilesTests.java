package guru.qa;

import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import guru.qa.model.CarModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class FilesTests {
    ClassLoader cl = FilesTests.class.getClassLoader();
    Gson gson = new Gson();

    @Test
    void filesZip() throws Exception {
        try (ZipFile zipFile = new ZipFile("scr/test/resources/fileXLS.zip")) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                Assertions.assertEquals("file_example_XLS_10.xls", entry.getName());
                XLS xls = new XLS(stream);

                Assertions.assertEquals("Mara",
                        xls.excel.getSheetAt(0)
                                .getRow(2)
                                .getCell(1)
                                .getStringCellValue());
            }
        }
    }

    @Test
    public void zipPdfTest() throws Exception {
        try (ZipFile zipFile = new ZipFile("src/test/resources/filePDF.zip");) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                Assertions.assertEquals("file.pdf", entry.getName());
            }
        }
    }

    @Test
    public void csvTest() throws Exception {
        try (ZipFile zipFile = new ZipFile("src/test/resources/filesCSV.zip");) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                Reader reader = new InputStreamReader(stream);
                CSVReader csvReader = new CSVReader(reader);
                List<String[]> content = csvReader.readAll();
                Assertions.assertEquals(33, content.size());

                final String[] firstRow = content.get(0);
                final String[] secondRow = content.get(1);
                final String[] thirdRow = content.get(2);

                Assertions.assertArrayEquals(new String[]{"yellow", "color"}, firstRow);
                Assertions.assertArrayEquals(new String[]{"flowers", "tulips"}, secondRow);
                Assertions.assertArrayEquals(new String[]{"month", "of March"}, thirdRow);

            }
        }
    }


    @Test
    void jsonTestCar() throws IOException {
        try (InputStream stream = cl.getResourceAsStream("car.json");
             Reader reader = new InputStreamReader(stream)) {
            CarModel car = gson.fromJson(reader, CarModel.class);
            Assertions.assertEquals(2, car.getCars().size());
            Assertions.assertEquals("Ford", car.getCars().get(0).getName());
            Assertions.assertEquals("Fiesta", car.getCars().get(0).getModel());
            Assertions.assertEquals("2018", car.getCars().get(0).getYear());
            Assertions.assertEquals("Suzuki", car.getCars().get(1).getName());
            Assertions.assertEquals("Jimny", car.getCars().get(1).getModel());
            Assertions.assertEquals("2020", car.getCars().get(1).getYear());

        }
    }
}




