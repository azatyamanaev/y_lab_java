package ru.ylab.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import ru.ylab.dto.mappers.HabitMapper;
import ru.ylab.dto.mappers.HabitMapperImpl;
import ru.ylab.dto.mappers.UserMapper;
import ru.ylab.dto.mappers.UserMapperImpl;

/**
 * Mappers configuration class.
 *
 * @author azatyamanaev
 */
@Getter
public class MappersConfig {

    /**
     * Instance of an {@link ObjectMapper}.
     */
    private final ObjectMapper mapper;

    /**
     * Instance of a {@link HabitMapper}.
     */
    private final HabitMapper habitMapper;

    /**
     * Instance of a {@link UserMapper}.
     */
    private final UserMapper userMapper;

    public MappersConfig() {
        this.mapper = mapper();
        this.habitMapper = new HabitMapperImpl();
        this.userMapper = new UserMapperImpl();
    }

    /**
     * ObjectMapper config.
     *
     * @return instance of an ObjectMapper
     */
    public static ObjectMapper mapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }
}
