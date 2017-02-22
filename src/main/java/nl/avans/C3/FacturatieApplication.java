package nl.avans.C3;

import nl.avans.C3.Config.ApplicationConfig;
import nl.avans.C3.Config.ApplicationContext;
import nl.avans.C3.Config.PersistenceContext;
import nl.avans.C3.Config.ProductionContext;
import nl.avans.C3.Config.SwaggerConfig;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties
@ComponentScan(basePackages = {"nl.avans.C3"})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class FacturatieApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    
    public static void main(String[] args) throws Exception {  
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ApplicationConfig.class);
        ctx.register(ApplicationContext.class);
        ctx.register(PersistenceContext.class);
        ctx.register(ProductionContext.class);
        ctx.register(SwaggerConfig.class);

        SpringApplication.run(FacturatieApplication.class);
    }
}