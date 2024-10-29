package ru.ylab.junit.servlets.mockito;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import ru.ylab.config.AppContext;
import ru.ylab.config.MappersConfig;
import ru.ylab.config.ServicesConfig;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class WebAppConfig extends TestDataConfig {

    protected static ServletConfig config;
    private static ServletContext context;
    private static AppContext appContext;
    protected static ServicesConfig servicesConfig;
    private static MappersConfig mappersConfig;

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected StringWriter writer;
    protected ObjectMapper mapper;
    protected int statusCode = 0;
    protected String contentType = "";


    @BeforeAll
    public static void setUpContext() {
        mappersConfig = new MappersConfig();
        config = mock(ServletConfig.class);
        context = mock(ServletContext.class);
        appContext = mock(AppContext.class);
        servicesConfig = mock(ServicesConfig.class);

        when(config.getServletContext()).thenReturn(context);
        when(context.getAttribute("appContext")).thenReturn(appContext);
        when(appContext.getMappersConfig()).thenReturn(mappersConfig);
        when(appContext.getServicesConfig()).thenReturn(servicesConfig);
    }

    public void mockRequest(String method, String uri,
                            BufferedReader reader, Map<String, String> parameters) throws IOException {
        request = mock(HttpServletRequest.class);

        when(request.getMethod()).thenReturn(method);
        when(request.getRequestURI()).thenReturn(uri);
        if (reader != null) {
            when(request.getReader()).thenReturn(reader);
        }

        if (parameters != null) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                when(request.getParameter(entry.getKey())).thenReturn(entry.getValue());
            }
        }
    }

    public void mockResponse(StringWriter writer) throws IOException {
        response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        Mockito.doAnswer((Answer<Void>) invocationOnMock -> {
            statusCode = (Integer) invocationOnMock.getArguments()[0];
            return null;
        }).when(response).setStatus(anyInt());
        Mockito.doAnswer((Answer<Void>) invocationOnMock -> {
            contentType = (String) invocationOnMock.getArguments()[0];
            return null;
        }).when(response).setContentType(anyString());
    }

    public BufferedReader convertToReader(Object object, ObjectMapper mapper) throws JsonProcessingException {
        return new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(mapper.writeValueAsString(object).getBytes()),
                StandardCharsets.UTF_8));
    }
}