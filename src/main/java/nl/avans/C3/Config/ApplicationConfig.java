package nl.avans.C3.Config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author Petri Kainulainen
 *
 * Dit is een van de configuratiebestanden die vanuit de Application.java class worden
 * aangeroepen om je applicatie te initialiseren.
 *
 */
public class ApplicationConfig implements WebApplicationInitializer {
    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String DISPATCHER_SERVLET_MAPPING = "/";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationContext.class);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);

//        FilterRegistration.Dynamic sitemesh = servletContext.addFilter("sitemesh", new ConfigurableSiteMeshFilter());
//        EnumSet<DispatcherType> sitemeshDispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
//        sitemesh.addMappingForUrlPatterns(sitemeshDispatcherTypes, true, "*.jsp");

        servletContext.addListener(new ContextLoaderListener(rootContext));
    }
}
