package ru.otus.spring.hw24.model.secure;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRole implements GrantedAuthority {

    Role role;

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
