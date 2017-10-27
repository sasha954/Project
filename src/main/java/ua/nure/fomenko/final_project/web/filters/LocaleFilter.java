package ua.nure.fomenko.final_project.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by fomenko on 26.08.2017.
 */
public class LocaleFilter implements Filter {

    private List<String> defaultLocales;

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse responce = (HttpServletResponse) arg1;
        HttpSession session = request.getSession();
        String lang = request.getParameter("lang");
        Locale locale;
        if(lang!=null && !lang.isEmpty()){
            if(!defaultLocales.contains(lang)){
                lang = defaultLocales.get(0);
            }
            locale = new Locale(lang);
            session.setAttribute("locale", locale);
        }else{
            locale = (Locale) session.getAttribute("locale");
            if(locale==null){
                Enumeration<Locale> localeEnumeration = request.getLocales();
                locale = takeLocale(localeEnumeration);
                session.setAttribute("locale", locale);
            }
        }
        arg2.doFilter(request, responce);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLocales = new ArrayList<>();
        String[] tempArr = filterConfig.getInitParameter("locales").split(" ");
        defaultLocales.addAll(Arrays.asList(tempArr));
    }


    private Locale takeLocale(Enumeration<Locale> localeEnumeration) {
        Locale result = new Locale(defaultLocales.get(0));
        while (localeEnumeration.hasMoreElements()) {
            Locale temp = localeEnumeration.nextElement();
            if (defaultLocales.contains(temp.getLanguage())) {
                result = temp;
                break;
            }
        }
        return result;
    }


}
