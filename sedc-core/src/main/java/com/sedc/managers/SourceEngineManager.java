package com.sedc.managers;

import com.sedc.core.model.SourceCenterEngineInstance;

public interface SourceEngineManager {

    SourceCenterEngineInstance createSourceInstance(String source, String region);

    void updateSourceInstance(SourceCenterEngineInstance scei);
}
