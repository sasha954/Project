package ua.nure.fomenko.final_project.web.filters;

import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.Path;
import ua.nure.fomenko.final_project.db.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by fomenko on 18.09.2017.
 */
public class TabFilter implements Filter {

    private List<String> administratorUserTabs;
    private List<String> administratorCategories;
    private List<String> abiturientTabs;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        administratorUserTabs = asList(filterConfig.getInitParameter("administrator_user"));
        abiturientTabs = asList(filterConfig.getInitParameter("abiturient"));
        administratorCategories = asList(filterConfig.getInitParameter("administrator_categories"));
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute(Params.SESSION_USER);
        String tabName = request.getParameter("tab");
        String categoryName = request.getParameter("category");


        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (user.getRole().getName().equals(Params.ADMINISTRATOR)) {
            request.getSession().setAttribute("tabsUser", administratorUserTabs);
            request.getSession().setAttribute("categories", administratorCategories);

            String path = Path.PERSONAL_CABINET_SERVLET_URI + "?category=" + categoryName;

            if (categoryName == null || !administratorCategories.contains(categoryName)) {
                categoryName = administratorCategories.get(0);
                path = Path.PERSONAL_CABINET_SERVLET_URI + "?category=" + categoryName;
                if(categoryName.equals(Params.CATEGORY_FACULTIES)){
                    response.sendRedirect(path);
                    return;
                }
            }
            if (tabName == null && categoryName.equals(Params.CATEGORY_ABITURIENTS)) {
                tabName = administratorUserTabs.get(0);
                path += "&tab=" + administratorUserTabs.get(0);
                if(tabName.equals("request")) {
                    response.sendRedirect(path);
                    return;
                }
            }

        } else if (user.getRole().getName().equals(Params.ABITURIENT)) {
            request.getSession().setAttribute("tabs", abiturientTabs);
            if (tabName == null || !abiturientTabs.contains(tabName)) {
                String path = Path.PERSONAL_CABINET_SERVLET_URI + "?tab=" + abiturientTabs.get(0);
                response.sendRedirect(path);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }
}
