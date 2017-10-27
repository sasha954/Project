package ua.nure.fomenko.final_project.web.filters;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.db.entity.Role;
import ua.nure.fomenko.final_project.db.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by fomenko on 17.09.2017.
 */
public class ControllerAccessFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(ControllerAccessFilter.class);

    private Map<Role, List<String>> accessMap = new HashMap<>();
    private List<String> outOfControl = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("Access filter initialization start.");
        accessMap.put(new Role(1, "Administrator"), asList(filterConfig.getInitParameter("administrator")));
        accessMap.put(new Role(2, "Abiturient"), asList(filterConfig.getInitParameter("abiturient")));
        LOGGER.trace("Access map ---> " + accessMap);

        outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
        LOGGER.trace("Out-of-control ---> " + outOfControl);

        LOGGER.debug("Access filter initialization finished.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(isAccessAllowed(request)) {
            LOGGER.trace("Access allowed.");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            LOGGER.trace("Forbidden");
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendError(403);
        }
    }

    private boolean isAccessAllowed(HttpServletRequest request) {
        LOGGER.debug(request.getRequestURI());
        String requestUri = request.getRequestURI();
        if(outOfControl.contains(requestUri)) {
            return true;
        }
        HttpSession session = request.getSession(false);
        if(session == null) {
            return false;
        }

        User user = (User)session.getAttribute(Params.SESSION_USER);
        if(user == null) {
            return false;
        }

        return accessMap.get(user.getRole()).contains(requestUri);
    }

    @Override
    public void destroy() {

    }

    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list;
    }
}
