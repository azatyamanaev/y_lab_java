package ru.ylab.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import ru.ylab.config.datasource.DataSourceConfig;
import ru.ylab.dto.mappers.HabitMapper;
import ru.ylab.dto.mappers.HabitMapperImpl;

/**
 * Class representing application context.
 *
 * @author azatyamanaev
 */
@Getter
public class AppContext {

    /**
     * Instance of a {@link DataSourceConfig}.
     */
    private final DataSourceConfig dataSourceConfig;

    /**
     * Instance of a {@link RepositoriesConfig}.
     */
    private final RepositoriesConfig repositoriesConfig;

    /**
     * Instance of a {@link ServicesConfig}.
     */
    private final ServicesConfig servicesConfig;

    /**
     * Instance of an {@link ObjectMapper}.
     */
    private final ObjectMapper mapper;

    /**
     * Instance of a {@link HabitMapper}.
     */
    private final HabitMapper habitMapper;

    /**
     * Creates new AppContext.
     */
    public AppContext() {
        this.dataSourceConfig = new DataSourceConfig();
        this.repositoriesConfig = new RepositoriesConfig(dataSourceConfig);
        this.servicesConfig = new ServicesConfig(dataSourceConfig, repositoriesConfig);

        this.mapper = mapper();
        this.habitMapper = new HabitMapperImpl();
    }

    /**
     * ObjectMapper config.
     *
     * @return instance of an ObjectMapper
     */
    public ObjectMapper mapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }

    /**
     * Creates new instance of an AppContext class.
     *
     * @return instance of an AppContext
     */
    public static AppContext createContext() {
        return new AppContext();
    }
}
