/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.config;

import com.vzw.booking.bg.batch.utils.AbstractMapper;
import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.ColumnDefinitions.Definition;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.vzw.booking.bg.batch.BookingWholesaleApplicationInit;
import com.vzw.booking.bg.batch.domain.casandra.FinancialEventCategory;
import com.vzw.booking.bg.batch.domain.casandra.FinancialMarket;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabasesConfigTest {

    //@Test
    public void testMeatDataSource() throws Exception {
        System.out.println("Check spring internal DB connection");
        DataSource result = BookingWholesaleApplicationInit.dataSource();
        assertNotNull(result);
    }

    
    public static Session getCasandraSession(String keyspace) {
        AuthProvider authProvider = new PlainTextAuthProvider("j6_dev_user", "Ireland");
        Cluster cluster = Cluster.builder().addContactPoint("170.127.114.154").withAuthProvider(authProvider).build();
        return cluster.connect(keyspace);
    }

    //@Test
    public void testCasandraConnectivity() throws Exception {
        System.out.println("*** Checking Casandra native connectivity using Datastax driver ***");
        AuthProvider authProvider = new PlainTextAuthProvider("j6_dev_user", "Ireland");
        Cluster cluster = Cluster.builder().addContactPoint("170.127.114.154").withAuthProvider(authProvider).build();
        assertNotNull(cluster);
        Metadata meta = cluster.getMetadata();
        List<KeyspaceMetadata> spaces = meta.getKeyspaces();
        assertNotNull(spaces);
        System.out.println("Keyspaces found: " + spaces.size());
        spaces.forEach((keyspace) -> {
            System.out.println("    " + keyspace.getName());
        });
        Session session = cluster.connect("j6_dev");
        assertNotNull(session);
        System.out.println("*** End of connectivity test ***");
    }

    @Test //(expected = NullPointerException.class)
    public void testTransferCassandraFinancialMarketTable() throws Throwable {
        System.out.println("*** Transfering Casandra Financial Market table to memory ***");
        AbstractMapper<FinancialMarket> financialMarketMapper = new AbstractMapper() {
            @Override
            protected Mapper<FinancialMarket> getMapper(MappingManager manager) {
                return manager.mapper(FinancialMarket.class);
            }
        };

        CassandraQueryBuilder<FinancialMarket> builder = new CassandraQueryBuilder();
        String cql = "select * from financialmarket";
        builder = builder.withConDetails("170.127.114.154", "j6_dev", "j6_dev_user", "Ireland")
                .openNewSession(true)
                .withCql(cql)
                .withMapper(financialMarketMapper);
        builder.build();
        assertNotNull(builder);

        List<FinancialMarket> markets = builder.getResults();
        assertNotNull(markets);
//        GenericSqlConverter converter = new GenericSqlConverter(FinancialMarket.class);
//        DataSource h2ds = BookingWholesaleApplicationInit.dataSource();
//        Connection con = h2ds.getConnection();
//        Statement stmt = con.createStatement();
//        //System.out.println("***");
//        for (FinancialMarket market : markets) {
//            String query = converter.createQueryFromModel(market);     
//            System.out.println(query);
//            stmt.executeUpdate(query);
//            //System.out.println("***");
//        }        
        System.out.println("Financial Market records found: "+ markets.size());
    }

    //@Test
    public void testCassandraResultCaching() throws Throwable {
        System.out.println("*** Checking Casandra Result Caching ***");
        AbstractMapper<FinancialMarket> financialMarketMapper = new AbstractMapper() {
            @Override
            protected Mapper<FinancialMarket> getMapper(MappingManager manager) {
                return manager.mapper(FinancialMarket.class);
            }
        };
        Date startDate = new Date();
        CassandraQueryBuilder<FinancialMarket> builder = new CassandraQueryBuilder();
        String cql = "select * from financialmarket where financialmarketid = '838'  ALLOW FILTERING";
        builder = builder.withConDetails("170.127.114.154", "j6_dev", "j6_dev_user", "Ireland")
                .openNewSession(true)
                .withCql(cql)
                .withMapper(financialMarketMapper);
        builder.build();
        assertNotNull(builder);
        
        List<FinancialMarket> markets = builder.getResults();
        Date endDate = new Date();
        long firstCallTime =  (endDate.getTime() - startDate .getTime()) / 1000;
        System.out.println("First call time: " + firstCallTime);
        
        startDate = new Date();
        markets = builder.getResults();
        endDate = new Date();
        long secondCallTime = (endDate.getTime() - startDate .getTime()) / 1000;
        System.out.println("Second call time: " + secondCallTime);
        
        //assertTrue(firstCallTime > secondCallTime);
    }
    
    //@Test
    public void testSystemSchemaAccess() throws Exception {
        System.out.println("*** Checking Casandra System Schema ***");
        Session session = getCasandraSession("system_schema");
        assertNotNull(session);

        ResultSet result = session.execute("select * from tables");
        assertTrue(result.all().size() > 0);
        System.out.println("Tables found: " + result.all().size());
        for (Row row : result) {
            System.out.println(row.getString("table_name"));
        }
        System.out.println("*** End of System Schema check ***");
    }
  
    //@Test
    public void testMisctranTable() throws Exception {
        System.out.println("*** Checking Casandra Misctran table ***");
        Session session = getCasandraSession("j6_dev");
        assertNotNull(session);
        System.out.println("Misctran table call without mapper, EXTRACTING structure:");

        ResultSet result1 = session.execute("select * from misctran");
        List<ColumnDefinitions.Definition> cols = result1.getColumnDefinitions().asList();
        assertNotNull(cols);
        assertEquals(4, cols.size());

        System.out.println("Looks like the TABLE_NAME is all I need to know about the table? So what is the issue actually ???");

        for (Definition def : cols) {
            System.out.println("Column: " + def.getName() + "   type: " + def.getType());
        }
        System.out.println("select * from misctran");
        System.out.println("All records in the table: " + result1.all().size());

        System.out.println("select * from misctran where companycode = 'C'");
        ResultSet result2 = session.execute("select * from misctran where companycode = 'C' ALLOW FILTERING");
        System.out.println("Filtered records retreived: " + result2.all().size());

        System.out.println("select * from misctran where miscfinancialtransactionnumber > 100");
        ResultSet result3 = session.execute("select * from misctran where miscfinancialtransactionnumber > 100 ALLOW FILTERING");
        System.out.println("Filtered records retreived: " + result3.all().size());

        System.out.println("let's print first 5 records then:");
        int i = 0;
        Iterator<Row> it = result3.all().iterator();
        while (it.hasNext()) {
            Row row = it.next();
            System.out.println("printing row: " + row);
            i++;
            if (i > 5) {
                break;
            }
        }
        System.out.println("It seeme that the only issue I have here is that I cannot retrieve rows from result set without a mapper !!!");
        System.out.println("Query never fails, regardless there is an output or not");
        System.out.println("*** End of Misctran check ***");
    }

    //@Test
    public void testFinancialEventCategoryTable() throws Throwable {
        System.out.println("*** Cheking Financial Event Category table ***");
        AbstractMapper<FinancialEventCategory> misctranMapper = new AbstractMapper() {
            @Override
            protected Mapper<FinancialEventCategory> getMapper(MappingManager manager) {
                return manager.mapper(FinancialEventCategory.class);
            }
        };
//        CassandraQueryBuilder<FinancialEventCategory> builder = new CassandraQueryBuilder();
//        String cql = "select * from financialeventcategory";
//        builder = builder.withConDetails("170.127.114.154", "j6_dev", "j6_dev_user", "Ireland")
//                .openNewSession(true)
//                .withCql(cql)
//                .withMapper(misctranMapper);
//        builder.build();
//        assertNotNull(builder);
//        
//        List<FinancialEventCategory> records = builder.getResults();
//        assertNotNull(records);
//        System.out.println("*** Query Output ***");
//        for (FinancialEventCategory rec : records) {
//            System.out.println(rec.toString());
//        }
//        System.out.println("*** End of output ***");

        System.out.println("*** Running conditianal query ***");
        CassandraQueryBuilder<FinancialEventCategory> builder = new CassandraQueryBuilder();
        String cql = "select * from financialeventcategory where billtypecode = 'something' ALLOW FILTERING";
        builder = builder.withConDetails("170.127.114.154", "j6_dev", "j6_dev_user", "Ireland")
                .openNewSession(true)
                .withCql(cql)
                .withMapper(misctranMapper);
        builder.build();
        assertNotNull(builder);

        List<FinancialEventCategory> records = builder.getResults();
        assertNotNull(records);
    }
}
