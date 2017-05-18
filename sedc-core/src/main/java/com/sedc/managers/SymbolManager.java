package com.sedc.managers;

import com.sedc.core.model.Symbol;

import java.util.List;

public interface SymbolManager {

    List<String> getStringSymbolsByRegion(String sourceEngineName);

    List<Symbol> getSymbolsBySource(String sourceEngineName);

}
