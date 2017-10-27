package ua.nure.fomenko.final_project.services;

import ua.nure.fomenko.final_project.db.entity.Role;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.db.entity.dto.UserDto;

/**
 * Created by fomenko on 10.09.2017.
 */
public class RegistrationService {

    public User getUserFromDto(UserDto userDto) {
        User user = new User();
        Role abiturientRole = new Role();
        abiturientRole.setId(2);
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMiddleName(userDto.getMiddleName());
        user.setRegion(userDto.getRegion());
        user.setCity(userDto.getCity());
        user.setSchool(userDto.getSchool());
        user.setRole(abiturientRole);
        user.setCertificateUrl(userDto.getCertificate());
        return user;
    }

}
