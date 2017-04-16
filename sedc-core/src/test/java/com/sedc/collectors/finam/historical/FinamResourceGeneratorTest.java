package com.sedc.collectors.finam.historical;

import org.junit.Test;
import org.springframework.core.io.UrlResource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class FinamResourceGeneratorTest {

    @Test
    public void test() throws Exception {
        ArrayList<UrlResource> urls = new ArrayList<>();

        FinamResourceGenerator generator = new FinamResourceGenerator();
        generator.setDateFrom(LocalDate.now().minusMonths(1));
        generator.setDateTo(LocalDate.now());
        generator.setPeriod("8");
        generator.setUrls(urls);

        generator.write(Arrays.asList("GAZP"));

    }
}
