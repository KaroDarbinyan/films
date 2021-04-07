package am.imdb.films.security.config;


import am.imdb.films.persistence.entity.RoleEntity;
import am.imdb.films.persistence.entity.UserEntity;
import am.imdb.films.persistence.entity.relation.UserRoleEntity;
import am.imdb.films.persistence.repository.UserRepository;
import am.imdb.films.security.config.session.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static am.imdb.films.security.config.session.SessionUser.SESSION_USER_KEY;

@Service
public class JwtUserDetailService implements UserDetailsService {

    final UserRepository userRepository;

    @Autowired
    public JwtUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = getUserEntityByUsername(username.toLowerCase()).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with name=%s was not found", username)));
        storeSessionUser(user);
        return new User(username, user.getPasswordHash(), getAuthorities(user));
    }

    private Optional<UserEntity> getUserEntityByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return Optional.ofNullable(user);
    }

    private void storeSessionUser(UserEntity user) {
        CurrentUser currentUser = new CurrentUser(user);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        servletRequestAttributes.getRequest().getSession().setAttribute(SESSION_USER_KEY, currentUser.getUser());
    }

    private List<SimpleGrantedAuthority> getAuthorities(UserEntity user) {
        return user.getListOfUserRole().stream()
                .map(UserRoleEntity::getRole)
                .map(RoleEntity::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
