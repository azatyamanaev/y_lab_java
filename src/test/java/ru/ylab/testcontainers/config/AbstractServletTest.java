package ru.ylab.testcontainers.config;

public class AbstractServletTest extends PostgresConfig {

//    protected static ServletConfig config;
//    protected static ServletContext context;
//
//    protected HttpServletRequest request;
//    protected HttpServletResponse response;
//    protected StringWriter writer;
//    protected ObjectMapper mapper;
//    protected int statusCode = 0;
//    protected String contentType = "";
//
//
//    @BeforeAll
//    public static void setUpContext() {
//        config = mock(ServletConfig.class);
//        context = mock(ServletContext.class);
//        when(config.getServletContext()).thenReturn(context);
//        when(context.getAttribute("appContext")).thenReturn(appContext);
//    }
//
//    public HttpServletRequest mockRequest(String method, String uri,
//                                          BufferedReader reader, Map<String, String> parameters) throws IOException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getMethod()).thenReturn(method);
//        when(request.getRequestURI()).thenReturn(uri);
//        if (reader != null) {
//            when(request.getReader()).thenReturn(reader);
//        }
//        if (parameters != null) {
//            for (Map.Entry<String, String> entry : parameters.entrySet()) {
//                when(request.getParameter(entry.getKey())).thenReturn(entry.getValue());
//            }
//        }
//        return request;
//    }
//
//    public HttpServletResponse mockResponse(StringWriter writer) throws IOException {
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        when(response.getWriter()).thenReturn(new PrintWriter(writer));
//        Mockito.doAnswer((Answer<Void>) invocationOnMock -> {
//            statusCode = (Integer) invocationOnMock.getArguments()[0];
//            return null;
//        }).when(response).setStatus(anyInt());
//        Mockito.doAnswer((Answer<Void>) invocationOnMock -> {
//            contentType = (String) invocationOnMock.getArguments()[0];
//            return null;
//        }).when(response).setContentType(anyString());
//        return response;
//    }
//
//    public BufferedReader convertToReader(Object object) throws JsonProcessingException {
//        return new BufferedReader(new InputStreamReader(
//                new ByteArrayInputStream(mapper.writeValueAsString(object).getBytes()),
//                StandardCharsets.UTF_8));
//    }
}
