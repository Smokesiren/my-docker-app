package edu.zut.awir.awir1.web.mapper;

import edu.zut.awir.awir1.model.User;
import edu.zut.awir.awir1.web.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User u) {
        return new UserDto(u.getId(), u.getName(), u.getEmail());
    }

    public User toEntity(UserDto dto) {
        var u = new User();
        u.setId(dto.id());
        u.setName(dto.name());
        u.setEmail(dto.email());
        return u;
    }
}
