package ru.ylab.web.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import ru.ylab.config.AppContext;
import ru.ylab.dto.in.SignInForm;
import ru.ylab.dto.in.UserForm;
import ru.ylab.dto.out.SignInResult;
import ru.ylab.services.auth.AuthService;
import ru.ylab.services.auth.JwtService;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.constants.WebConstants.AUTH_URL;
import static ru.ylab.utils.constants.WebConstants.REFRESH_TOKEN_URL;
import static ru.ylab.utils.constants.WebConstants.SIGN_IN_URL;
import static ru.ylab.utils.constants.WebConstants.SIGN_UP_URL;

/**
 * Servlet for handling authorization HTTP requests.
 *
 * @author azatyamanaev
 */
@WebServlet(name = WebConstants.AUTH_SERVLET_NAME,
        urlPatterns = {AUTH_URL + SIGN_IN_URL,
                AUTH_URL + SIGN_UP_URL,
                AUTH_URL + REFRESH_TOKEN_URL})
public class AuthServlet extends HttpServlet implements HttpRequestHandler {

    /**
     * Instance of an {@link ObjectMapper}.
     */
    private ObjectMapper mapper;

    /**
     * Instance of an {@link AuthService}.
     */
    private AuthService authService;

    /**
     * Instance of an {@link JwtService}.
     */
    private JwtService jwtService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        AppContext appContext = (AppContext) context.getAttribute("appContext");

        this.mapper = appContext.getMapper();
        this.authService = appContext.getServicesConfig().getAuthService();
        this.jwtService = appContext.getServicesConfig().getJwtService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req);
        String response = "";
        if (uri.equals(AUTH_URL + REFRESH_TOKEN_URL)) {
            String refresh = mapper.readValue(IOUtils.toString(req.getReader()), String.class);
            response = mapper.writeValueAsString(jwtService.generateAccess(refresh));
        }

        setResponse(resp, HttpServletResponse.SC_OK, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req);
        String response = "";
        SignInResult signInResult;

        switch (uri) {
            case AUTH_URL + SIGN_IN_URL:
                SignInForm form = mapper.readValue(IOUtils.toString(req.getReader()), SignInForm.class);
                signInResult = authService.signIn(form);
                response = mapper.writeValueAsString(signInResult);
                break;
            case AUTH_URL + SIGN_UP_URL:
                UserForm userForm = mapper.readValue(IOUtils.toString(req.getReader()), UserForm.class);
                signInResult = authService.signUp(userForm);
                response = mapper.writeValueAsString(signInResult);
                break;
        }

        setResponse(resp, HttpServletResponse.SC_OK, response);
    }
}
