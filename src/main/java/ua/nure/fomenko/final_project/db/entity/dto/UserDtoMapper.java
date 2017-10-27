package ua.nure.fomenko.final_project.db.entity.dto;

import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.util.ImageUploader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by fomenko on 09.09.2017.
 */
public class UserDtoMapper {

    public UserDto getUser(HttpServletRequest request) throws IOException, ServletException {
        UserDto userDto = new UserDto();
        userDto.setEmail(request.getParameter(Params.USER_EMAIL));
        userDto.setPassword(request.getParameter(Params.USER_PASSWORD));
        userDto.setRepeatPassword(request.getParameter(Params.USER_REPEAT_PASSWORD));
        userDto.setFirstName(request.getParameter(Params.USER_FIRST_NAME));
        userDto.setLastName(request.getParameter(Params.USER_LAST_NAME));
        userDto.setMiddleName(request.getParameter(Params.USER_MIDDLE_NAME));
        userDto.setRegion(request.getParameter(Params.USER_REGION));
        userDto.setCity(request.getParameter(Params.USER_CITY));
        userDto.setSchool(request.getParameter(Params.USER_SCHOOL));
        userDto.setCertificate("noimage");
        return userDto;
    }

    private String getUploadedFileName(HttpServletRequest request, UserDto userDto) throws IOException, ServletException {
        return ImageUploader.upload(request, userDto);
    }
}
