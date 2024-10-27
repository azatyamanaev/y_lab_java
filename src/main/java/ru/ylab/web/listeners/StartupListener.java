package ru.ylab.web.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import ru.ylab.config.AppContext;

/**
 * Listener for initializing application context.
 *
 * @author azatyamanaev
 */
@WebListener
public class StartupListener implements ServletContextListener {

    /**
     * Application context.
     */
    private AppContext appContext;

    /**
     * Creates AppContext during app startup.
     *
     * @param event servlet context initialized
     */
    public void contextInitialized(ServletContextEvent event) {
        appContext = AppContext.createContext();
        appContext.getServicesConfig().getLiquibaseService().migrate();
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("appContext", appContext);
    }

    /**
     * Closes all connections to database during app shutdown.
     *
     * @param event servlet context destroyed
     */
    @SneakyThrows
    public void contextDestroyed(ServletContextEvent event) {
        appContext.getDataSourceConfig().getConnectionPool().shutdown();
    }
}
