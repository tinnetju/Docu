package nl.avans.C3.Config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import nl.avans.C3.DataStorage.ClientRepository;

/**
 * Deze configuratie wordt alleen geladen als in de omgevingsvariabelen de setting 'production'
 * aanwezig is. Dat kan via de command line bij het starten van de app via
 * java -jar -Dspring.profiles.active=production appnaam.jar.
 *
 */
@SuppressWarnings("Duplicates")
@Configuration
@Profile("production")
@EnableTransactionManagement
public class ProductionContext {

    protected static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    protected static final String PROPERTY_NAME_DATABASE_PASSWORD = "_avans_spring_";
    protected static final String PROPERTY_NAME_DATABASE_URL = "jdbc:mysql://104.155.74.161:3306/library";
    protected static final String PROPERTY_NAME_DATABASE_USERNAME = "spring";

    @Resource
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
        dataSource.setUrl(PROPERTY_NAME_DATABASE_URL);
        dataSource.setUsername(PROPERTY_NAME_DATABASE_USERNAME);
        dataSource.setPassword(PROPERTY_NAME_DATABASE_PASSWORD);

        return dataSource;
    }

    @Bean
    @Qualifier("PersistenceContext")
    @Primary
    public ClientRepository getClientRepository() {
        return new ClientRepository(this.dataSource());
    }
}
