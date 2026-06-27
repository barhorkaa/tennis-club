package cz.hornakova.barbora.tennisclub.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Value("${app.init-data:false}")
    private boolean initData;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {

        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.xml");

        if (initData) {
            liquibase.setContexts("init-data");
        } else {
            liquibase.setContexts("none");
        }

        return liquibase;
    }
}
