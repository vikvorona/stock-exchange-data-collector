package com.sedc.managers;

import com.sedc.core.model.Symbol;

import java.util.List;

public interface SymbolManager {

    List<String> getStringSymbolsBySource(String sourceEngineName);

    List<Symbol> getSymbolsBySource(String sourceEngineName);

}
