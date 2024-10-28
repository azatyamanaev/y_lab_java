package ru.ylab.web.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import ru.ylab.config.AppContext;
import ru.ylab.dto.in.SignInForm;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.out.SignInResult;
import ru.ylab.exception.HttpException;
import ru.ylab.services.auth.AuthService;
import ru.ylab.services.auth.JwtService;
import ru.ylab.utils.StringUtil;
import ru.ylab.utils.constants.ErrorConstants;
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
    public void init() {
        ServletContext context = this.getServletContext();
        AppContext appContext = (AppContext) context.getAttribute("appContext");

        this.mapper = appContext.getMappersConfig().getMapper();
        this.authService = appContext.getServicesConfig().getAuthService();
        this.jwtService = appContext.getServicesConfig().getJwtService();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        String uri = StringUtil.parseReqUri(req.getRequestURI());
        if (method.equals("GET")) {
            if (uri.equals(AUTH_URL + REFRESH_TOKEN_URL)) {
                refreshToken(req, resp);
            } else {
                throw HttpException.methodNotAllowed()
                                   .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http GET");
            }
        } else if (method.equals("POST")) {
            switch (uri) {
                case AUTH_URL + SIGN_IN_URL:
                    signIn(req, resp);
                    break;
                case AUTH_URL + SIGN_UP_URL:
                    signUp(req, resp);
                    break;
                default:
                    throw HttpException.methodNotAllowed()
                                       .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http POST");
            }
        } else {
            super.service(req, resp);
        }
    }

    /**
     * Gets new access token for user and writes it to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void refreshToken(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String refresh = req.getParameter("token");
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(jwtService.generateAccess(refresh)));
    }

    /**
     * Signs in user and writes access and refresh tokens to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void signIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SignInForm form = mapper.readValue(IOUtils.toString(req.getReader()), SignInForm.class);
        SignInResult signInResult = authService.signIn(form);
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(signInResult));
    }

    /**
     * Signs up user and writes access and refresh tokens to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void signUp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SignUpForm form = mapper.readValue(IOUtils.toString(req.getReader()), SignUpForm.class);
        SignInResult signInResult = authService.signUp(form);
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(signInResult));
    }
}
