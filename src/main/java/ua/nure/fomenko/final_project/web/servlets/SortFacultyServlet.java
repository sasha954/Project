package ua.nure.fomenko.final_project.web.servlets;

import org.json.JSONArray;
import org.json.JSONObject;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.Path;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.dto.FilterParam;
import ua.nure.fomenko.final_project.services.FacultyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/sort.do")
public class SortFacultyServlet extends HttpServlet {
    private FacultyService facultyService;

    @Override
    public void init() throws ServletException {
        facultyService = (FacultyService)getServletContext().getAttribute(Params.FACULTY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterParam filterParam = constructFilterParam(req);
        List<Faculty> list = facultyService.getFacultiesByConditions(filterParam);
        req.getSession().setAttribute("sortedFaculties", list);
        resp.sendRedirect(Path.INDEX_SERVLET_URI);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private String createJson(List<Faculty> list) {
        JSONArray array = new JSONArray();
        for(Faculty faculty : list) {
            JSONObject object = new JSONObject(faculty);
            array.put(object);
        }
        return array.toString();
    }

    private FilterParam constructFilterParam(HttpServletRequest req) {
        FilterParam filterParam = new FilterParam();
        String order = req.getParameter("orderField");
        if(order != null && !order.isEmpty()) {
            filterParam.setOrderBy(order);
        }
        filterParam.setDesc(Boolean.parseBoolean(req.getParameter("isDesc")));
        return filterParam;
    }


}
