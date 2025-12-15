// package com.infosys2.demo;

import java.io.BufferedReader;
import java.io.FileReader; // Remove FileReader/filePath usage in favor of ClassLoader
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// import org.springframework.stereotype.Service;
// import com.infosys2.demo.model.CsvRecord;

// NOTE: You must place your file in src/main/resources/static/example.csv
// The path used below is "static/example.csv"

// Service class to handle CSV operations
public class Notes {

    // --- REUSABLE PARSING METHOD (Handles mapping from InputStream) ---

    /**
     * Parses a CSV from an InputStream and returns a list of CsvRecord objects.
     * This method is reusable whether the source is a file path or an uploaded stream.
     */
    public List<CsvRecord> parseCsvFromStream(InputStream inputStream) throws IOException {
        List<CsvRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Add validation (length check, trim) for production code
                if (values.length < 4) continue; 
                
                CsvRecord record = new CsvRecord(
                    Integer.parseInt(values[0].trim()),
                    values[1].trim(),
                    values[2].trim(),
                    values[3].trim() 
                );
                records.add(record);
            }
        }
        return records;
    }

    // --- SOLUTION FOR READING FROM RESOURCES FOLDER ---

    /**
     * Reads the file from the src/main/resources folder and calls the reusable parser.
     */
    public List<CsvRecord> getAllRecords() {
        // 1. Define the path relative to the resources root
        String resourcePath = "static/example.csv"; 
        
        // 2. Use the ClassLoader to safely get the file as an InputStream
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            
            if (inputStream == null) {
                throw new IOException("Resource file not found: " + resourcePath);
            }
            
            // 3. Call the reusable parsing method
            return parseCsvFromStream(inputStream);
            
        } catch (IOException e) {
            // Log a robust error message and throw a RuntimeException or custom exception
            System.err.println("Error reading internal resource CSV: " + e.getMessage());
            throw new RuntimeException("Failed to load internal CSV data.", e);
        }
    }
    
    // --- The original file-path method is now obsolete/deprecated for resource files ---
    
    // NOTE: Your original getStatusByTitle method already takes an InputStream, 
    // making it perfectly reusable for an uploaded file!

}