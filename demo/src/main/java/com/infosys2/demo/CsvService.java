package com.infosys2.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.infosys2.demo.model.CsvRecord;

@Service
public class CsvService {

    /**
     * Parses a CSV file and returns a list of CsvRecord objects.
     *
     * @param filePath the path to the CSV file
     * @return a list of CsvRecord objects
     */
    public List<CsvRecord> parseCsv(InputStream inputStream) {
        List<CsvRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                CsvRecord record = new CsvRecord(
                    Integer.parseInt(values[0]),
                    values[1],
                    values[2],
                    values[3]
                );
                records.add(record);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
            e.printStackTrace();
        }
        return records;
    }

    /**
     * Returns a mapping of titles to their statuses from a CSV input stream.
     *
     * @param csvInputStream the input stream of the CSV file
     * @return a map of titles to statuses
     */
    public Map<String, String> getStatusByTitle(InputStream csvInputStream) {
        List<CsvRecord> records = parseCsv(csvInputStream);
        return records.stream()
                      .collect(Collectors.toMap(
                          CsvRecord::getTitle,
                          CsvRecord::getStatus
                      ));
    }

    public List<CsvRecord> getAllRecords() {
        String resourcePath = "static/example.csv";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream != null) {
                return parseCsv(inputStream);
            } else {
                System.err.println("Resource not found: " + resourcePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Failed to load records from resource: " + resourcePath);
    }

}