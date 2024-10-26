package ru.ylab.web.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import ru.ylab.config.AppContext;

@WebListener
public class StartupListener implements ServletContextListener {

    private AppContext appContext;

    public void contextInitialized(ServletContextEvent event) {
        appContext = AppContext.createContext();
        appContext.getServicesConfig().getLiquibaseService().migrate();
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("appContext", appContext);
    }

    @SneakyThrows
    public void contextDestroyed(ServletContextEvent event) {
        appContext.getDataSourceConfig().getConnectionPool().shutdown();
    }
}
