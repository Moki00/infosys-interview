package com.infosys2.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.infosys2.demo.model.CsvRecord;

@Component
public class CsvDataRunner implements CommandLineRunner {

    private final CsvService csvService;

    public CsvDataRunner(CsvService csvService) {
        this.csvService = csvService; // Service is injected by Spring
    }

    @Override
    public void run(String... args) throws Exception {
        // --- THIS CODE RUNS SAFELY AFTER SPRING IS READY ---
        
        System.out.println("--- Starting CSV Status Output ---");
        
        // 1. You can now call your service and safely read the file
        List<CsvRecord> records = csvService.getAllRecords();

        // 2. Output the status to the terminal
        records.forEach(record -> {
            System.out.printf("Title: %s, Status: %s%n", record.getTitle(), record.getStatus());
        });
        
        System.out.println("--- CSV Status Output Complete ---");
    }
}