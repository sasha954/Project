package ua.nure.fomenko.final_project.util;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.db.entity.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 * Created by fomenko on 10.09.2017.
 */
public class ImageUploader {
    private static final Logger LOGGER = Logger.getLogger(ImageUploader.class);
    private static final String SAVE_DIR = "WEB-INF\\upload\\images\\certificates";


    public static String upload(HttpServletRequest request, UserDto user) throws IOException, ServletException {
        if (request.getPart(Params.USER_CERTIFICATE) == null) {
            return "nocertificate.png";
        }
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + SAVE_DIR;

        File saveFileDir = new File(savePath);
        if (!saveFileDir.exists()) {
            saveFileDir.mkdirs();
        }


        Part part = request.getPart(Params.USER_CERTIFICATE);
        String fileName = user.getEmail().substring(0, user.getEmail().lastIndexOf(".")) + getFormat(part);
        fileName = new File(fileName).getName();
        part.write(savePath + File.separator + fileName);

        return fileName;
    }

    private static String getFormat(Part part) {
        String contentDisp = part.getHeader("Content-Disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("."), s.length() - 1);
            }
        }

        return "";
    }


}
