package ru.otus.spring.hw24.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw24.model.secure.User;
import ru.otus.spring.hw24.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MongoAuthUserDetailService  implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(userName);

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

        user.getAuthorities().forEach(userRole -> {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
        });
        return new User(user.getUsername(), user.getPassword(), grantedAuthoritySet);
    }
}
