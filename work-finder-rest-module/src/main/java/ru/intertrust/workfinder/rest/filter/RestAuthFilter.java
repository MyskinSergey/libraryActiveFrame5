package ru.intertrust.workfinder.rest.filter;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.intertrust.cm.core.business.api.dto.UserCredentials;
import ru.intertrust.cm.core.business.api.dto.UserUidWithPassword;
import ru.intertrust.cm.core.dao.api.ExtensionService;
import ru.intertrust.cm.core.gui.api.server.LoginService;
import ru.intertrust.cm.core.gui.api.server.extension.AuthenticationExtentionHandler;
import ru.intertrust.cm.core.gui.impl.server.JeeServerFamily;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;


/**
 * Created by Vitaliy Orlov on 21.12.2016.
 */
public class RestAuthFilter implements Filter {
    private static Logger log = LoggerFactory.getLogger(RestAuthFilter.class);
    private static final Set<String> noAccessRequried = new HashSet<>();
    static {
        noAccessRequried.add("restAdapter/auth");
        noAccessRequried.add("restAdapter/registration");
        noAccessRequried.add("restAdapter/dictionary");



    }

    private ExtensionService extensionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        this.extensionService = (ExtensionService)ctx.getBean(ExtensionService.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();

        if(!this.isAccessRequried(requestURI, servletRequest) || "_wadl".equalsIgnoreCase(request.getQueryString())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            AuthenticationExtentionHandler authExtHandler = (AuthenticationExtentionHandler)this.extensionService.getExtentionPoint(AuthenticationExtentionHandler.class, (String)null);
            authExtHandler.onBeforeAuthentication(request, response);
            UserCredentials credentials = (UserCredentials)session.getAttribute("_USER_CREDENTIALS");

            if(credentials == null) {
                // check if basic auth
                String authCredentials = request.getHeader("Authorization");
                if(authCredentials != null && !authCredentials.trim().toString().isEmpty()){

                    try{
                        final String encodedUserPassword = authCredentials.replaceFirst("Basic"
                                + " ", "");
                        String usernameAndPassword = null;
                        try {
                            byte[] decodedBytes = Base64.decodeBase64(
                                    encodedUserPassword);
                            usernameAndPassword = new String(decodedBytes, "UTF-8");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
                        final String username = tokenizer.nextToken();
                        final String password = tokenizer.nextToken();

                        LoginService loginService = new ru.intertrust.cm.core.gui.impl.server.LoginServiceImpl(); // todo - get rid
                        loginService.login(request, new UserUidWithPassword(username, password));

                        filterChain.doFilter(servletRequest, servletResponse);
                        //request.login(username, password);
                    }catch (Exception e){
                        this.forwardToNoAccess(servletRequest, servletResponse);
                    }
                }else{
                    this.forwardToNoAccess(servletRequest, servletResponse);

                }


            } else {
                UserUidWithPassword userUidWithPassword = (UserUidWithPassword)credentials;
                if(request.getUserPrincipal() == null) {
                    try {
                        request.login(userUidWithPassword.getUserUid(), userUidWithPassword.getPassword());
                    } catch (ServletException var19) {
                        this.forwardToNoAccess(servletRequest, servletResponse);
                    }
                } else {
                    log.info(Thread.currentThread().getId() + " => user principal is already in request");
                }

                try {
                    filterChain.doFilter(servletRequest, servletResponse);
                } finally {
                    if(JeeServerFamily.isLogoutRequired(request)) {
                        try {
                            request.logout();
                        } catch (ServletException var18) {
                            log.error("request logout failed", var18);
                        }
                    } else {
                        log.info(Thread.currentThread().getId() + " => no user principal. Do NOT log out");
                    }

                }

            }
        }
    }

    private boolean isAccessRequried(String requestURI, ServletRequest servletRequest) {
        boolean required = true;
        for(String link : noAccessRequried){
            if(requestURI.contains(link)){
                required = false;
            }
        }
        if(required){
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            if(httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")){
                String header = httpServletRequest.getHeader("Access-Control-Request-Headers");
                if(header != null && (header.contains("authorization") && header.contains("content-type"))){
                    required = false;
                }
            }
        }

        return required;
    }


    private void forwardToNoAccess(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        StringBuilder loginPath = (new StringBuilder(request.getContextPath())).append("/ws/restAdapter/auth/noAccess/");
        ((HttpServletResponse)servletResponse).setHeader("WWW-Authenticate", "Basic");
        ((HttpServletResponse)servletResponse).setStatus(401);
/*


        Response.status(401).header("WWW-Authenticate", "Basic").build()

        ((HttpServletResponse)servletResponse).sendRedirect(loginPath.toString());
*/
    }

    @Override
    public void destroy() {

    }
}
