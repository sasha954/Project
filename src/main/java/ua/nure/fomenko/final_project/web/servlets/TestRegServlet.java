package ua.nure.fomenko.final_project.web.servlets;

import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.util.ImageUploader;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

/**
 * Created by fomenko on 10.09.2017.
 */
@WebServlet(name = "TestRegServlet", urlPatterns = "/registr")
@MultipartConfig
public class TestRegServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/testreg.jsp").forward(request, response);
    }

}
