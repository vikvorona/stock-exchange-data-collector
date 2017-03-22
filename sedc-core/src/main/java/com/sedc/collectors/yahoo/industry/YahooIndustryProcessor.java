package com.sedc.collectors.yahoo.industry;

import com.sedc.collectors.yahoo.model.JaxbIndustry;
import com.sedc.core.model.Industry;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

public class YahooIndustryProcessor  implements ItemProcessor<JaxbIndustry, Industry> {

    @Override
    public Industry process(JaxbIndustry item) throws Exception {

        if (Objects.isNull(item)) return null;

        Industry o = new Industry();
        o.setId(item.getId());
        o.setName(item.getName());
        o.setSector(item.getSector());

        return o;
    }
}
