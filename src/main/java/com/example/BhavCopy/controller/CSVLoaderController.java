package com.example.BhavCopy.controller;

import com.example.BhavCopy.entity.BhavcopyData;
import com.example.BhavCopy.response.Response;
import com.example.BhavCopy.response.ResponseModel;
import com.example.BhavCopy.service.BhavcopyDataService;
import com.example.BhavCopy.service.CSVLoaderService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/bhavcopy")
public class CSVLoaderController {

    @Autowired
    private CSVLoaderService csvLoaderService;

    @Autowired
    private BhavcopyDataService bhavcopyDataService;

    @PostMapping("/loadCSV")
    public Response loadCSV(@RequestParam("file") MultipartFile file) {
        try {
            csvLoaderService.loadCSV(file);
            return new Response("CSV loaded successfully!", "success");
        } catch (Exception e) {
            return new Response("Failed to load CSV: " + e.getMessage(), "error");
        }
    }

    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<?> getSymbolInfo(@PathVariable String symbol) {
        try {
            List<BhavcopyData> data = bhavcopyDataService.getSymbolInfo(symbol);
            ResponseModel<List<BhavcopyData>> response = new ResponseModel<>("success", "Data retrieved successfully",
                    data);
            Map<String, Object> list = response.getData();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (RuntimeException e) {
            ResponseModel<List<BhavcopyData>> response = new ResponseModel<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/count/{series}")
    public ResponseEntity<ResponseModel<Long>> countSymbols(@PathVariable String series) {
        try {
            Long count = bhavcopyDataService.countSymbolsBySeries(series);
            ResponseModel<Long> response = new ResponseModel<>("success", "Count retrieved successfully", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseModel<Long> response = new ResponseModel<>("error", "Failed to retrieve count", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/symbolsWithGain/{gainPercentage}")
    public ResponseEntity<ResponseModel<List<String>>> getSymbolsWithGain(
            @PathVariable Double gainPercentage) {
        List<String> symbols = bhavcopyDataService.getSymbolsWithGainGreaterThan(gainPercentage);
        ResponseModel<List<String>> response = new ResponseModel<>("success", "Symbols retrieved successfully",
                symbols);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/symbolsWithHighLow/{threshold}")
    public ResponseEntity<ResponseModel<List<String>>> getSymbolsWithHighLow(
            @PathVariable Double threshold) {
        List<String> symbols = bhavcopyDataService.getSymbolsWithHighLowGreaterThan(threshold);
        ResponseModel<List<String>> response = new ResponseModel<>("success", "Symbols retrieved successfully",
                symbols);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/standardDeviation/{series}")
    public ResponseEntity<ResponseModel<Double>> getStandardDeviation(@PathVariable String series) {
        Double standardDeviation = bhavcopyDataService.calculateStandardDeviation(series);
        ResponseModel<Double> response = new ResponseModel<>("success", "Standard deviation calculated successfully",
                standardDeviation);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/topGainSymbols/{n}")
    public ResponseEntity<ResponseModel<List<String>>> getTopGainSymbols(@PathVariable int n) {
        List<String> symbols = bhavcopyDataService.findTopNGainSymbols(n);
        ResponseModel<List<String>> response = new ResponseModel<>("success", "Top gain symbols retrieved successfully",
                symbols);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bottomLaggards/{n}")
    public ResponseEntity<ResponseModel<List<String>>> getBottomLaggards(@PathVariable int n) {
        List<String> symbols = bhavcopyDataService.findBottomNLaggards(n);
        ResponseModel<List<String>> response = new ResponseModel<>("success", "Bottom laggards retrieved successfully",
                symbols);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/topTraded/{n}")
    public ResponseEntity<ResponseModel<List<String>>> getTopTradedSymbols(@PathVariable int n) {
        List<String> symbols = bhavcopyDataService.findTopNTradedSymbols(n);
        ResponseModel<List<String>> response = new ResponseModel<>("success",
                "Top traded symbols retrieved successfully", symbols);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bottomTraded/{n}")
    public ResponseEntity<ResponseModel<List<String>>> getBottomTradedSymbols(@PathVariable int n) {
        List<String> symbols = bhavcopyDataService.findBottomNTradedSymbols(n);
        ResponseModel<List<String>> response = new ResponseModel<>("success",
                "Bottom traded symbols retrieved successfully", symbols);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/highLow/{series}")
    public ResponseEntity<ResponseModel<Map<String, String>>> getHighLowSymbols(@PathVariable String series) {
        Map<String, String> symbols = bhavcopyDataService.findHighLowTradedSymbols(series);
        ResponseModel<Map<String, String>> response = new ResponseModel<>("success",
                "High and low traded symbols retrieved successfully", symbols);
        return ResponseEntity.ok(response);
    }
}
