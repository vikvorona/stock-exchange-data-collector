package com.sedc.managers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SystemVariableManagerStub implements SystemVariableManager {

    @Override
    public String getSystemVariable(String name) {
        if (SystemVariables.LAST_UPDATE_DATE.equals(name)) {
            return LocalDateTime.now().minusWeeks(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
        return null;
    }

    @Override
    public void setSystemVariable(String name, String value) {
        throw new UnsupportedOperationException("Unimplemented");
    }
}
