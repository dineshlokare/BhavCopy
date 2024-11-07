package com.example.BhavCopy.service;

import com.example.BhavCopy.entity.BhavcopyData;
import com.example.BhavCopy.repository.BhavcopyDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class BhavcopyDataService {

    @Autowired
    private BhavcopyDataRepository bhavcopyDataRepository;

    public List<BhavcopyData> getSymbolInfo(String symbol) {
        String symbolInput = symbol.trim().toUpperCase();
        System.out.println("Querying for symbol: " + symbolInput);

        List<BhavcopyData> bhavcopyDataList = bhavcopyDataRepository.findBySymbolUsingProcedure(symbolInput);

        return bhavcopyDataList;
    }

    public Long countSymbolsBySeries(String series) {
        String seriesInput = series.trim().toUpperCase();
        return bhavcopyDataRepository.countBySeries(seriesInput);
    }

    public List<String> getSymbolsWithGainGreaterThan(Double gainPercentage) {
        return bhavcopyDataRepository.findSymbolsWithGainGreaterThan(gainPercentage);
    }

    public List<String> getSymbolsWithHighLowGreaterThan(Double threshold) {
        return bhavcopyDataRepository.findSymbolsWithHighLowGreaterThan(threshold);
    }

    public Double calculateStandardDeviation(String series) {
        return bhavcopyDataRepository.calculateStandardDeviation(series);
    }

    public List<String> findTopNGainSymbols(int n) {
        return bhavcopyDataRepository.findTopNGainSymbols(n);
    }

    public List<String> findBottomNLaggards(int n) {
        return bhavcopyDataRepository.findBottomNLaggards(n);
    }

    public List<String> findTopNTradedSymbols(int n) {
        return bhavcopyDataRepository.findTopNTradedSymbols(n);
    }

    public List<String> findBottomNTradedSymbols(int n) {
        return bhavcopyDataRepository.findBottomNTradedSymbols(n);
    }
    
    public Map<String, String> findHighLowTradedSymbols(String series) {
        List<Object[]> result = bhavcopyDataRepository.findHighLowTradedSymbols(series);
        
        Map<String, String> response = new HashMap<>();
        
        // Assuming result has only one row
        if (!result.isEmpty()) {
            Object[] symbols = result.get(0);
            String highSymbol = (String) symbols[0];
            String lowSymbol = (String) symbols[1];
            response.put("highSymbol", highSymbol);
            response.put("lowSymbol", lowSymbol);
        }
        
        return response;
    }
}
