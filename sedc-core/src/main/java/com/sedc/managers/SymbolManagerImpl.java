package com.sedc.managers;

import com.sedc.core.model.Symbol;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SymbolManagerImpl implements SymbolManager {

    @Override
    public List<String> getStringSymbolsBySource(String sourceEngineName) {

        if (StringUtils.isEmpty(sourceEngineName)) {
            return Collections.emptyList();
        }

        switch (sourceEngineName) {
            case "EMEA":
                return Arrays.asList("GAZP", "RTS", "SBRF", "VTBR");
            case "NAM":
                return Arrays.asList("YHOO", "GE", "AAPL", "T", "MSFT");
        }

        return Collections.emptyList();
    }

    @Override
    public List<Symbol> getSymbolsBySource(String sourceEngineName) {
        return Collections.emptyList();
    }
}
