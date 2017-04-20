package com.sedc.managers;

import com.sedc.core.model.Symbol;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SymbolManagerImpl implements SymbolManager {

    private static final Logger LOG = Logger.getLogger(SymbolManager.class);

    private SessionFactory sessionFactory;

    @Override
    public List<String> getStringSymbolsByRegion(String region) {

        if (StringUtils.isEmpty(region)) {
            return Collections.emptyList();
        }

        if (region.equalsIgnoreCase("FOREX")){
            return Arrays.asList("EURUSD","GBPUSD", "YHOO");
        }

        Session session = sessionFactory.openSession();
        List<String> symbols = (List<String>) session.createSQLQuery("" +
                "SELECT DISTINCT s.name\n" +
                "FROM symbol s\n" +
                "    join exchange e on s.ex_id = e.ex_id\n" +
                "    join code_generic cg on e.region_cg_id = cg.cg_id\n" +
                "where cg.type = 'REGION'\n" +
                "    and cg.name = :region")
                .addScalar("name", StandardBasicTypes.STRING)
                .setString("region", region)
                .list();
        session.clear();
        session.close();

        if (symbols.isEmpty()) {
            return Collections.emptyList();
        }

        return symbols;
    }

    @Override
    public List<Symbol> getSymbolsBySource(String sourceEngineName) {
        return Collections.emptyList();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
