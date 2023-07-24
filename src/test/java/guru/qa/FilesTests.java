package guru.qa;

import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class FilesTests {

    @Test
    void filesZip() throws Exception {
        try (ZipFile zipFile = new ZipFile("src/test/resources/fileXLS.zip")) {
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
        try (ZipFile zipFile = new ZipFile("src/test/resources/filetest.CSV.zip");) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                Reader reader = new InputStreamReader(stream);
                CSVReader csvReader = new CSVReader(reader);
                List<String[]> content = csvReader.readAll();
                Assertions.assertEquals(3, content.size());

                final String[] firstRow = content.get(0);
                final String[] secondRow = content.get(1);
                final String[] thirdRow = content.get(2);

                Assertions.assertArrayEquals(new String[]{"yellow", "color"}, firstRow);
                Assertions.assertArrayEquals(new String[]{"flowers", "tulips"}, secondRow);
                Assertions.assertArrayEquals(new String[]{"month", "of March"}, thirdRow);

            }
        }
    }
}




