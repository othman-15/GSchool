package ma.devops.gestionecole.security.services.A;

import lombok.AllArgsConstructor;
import ma.devops.gestionecole.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser= accountService.loadUserByUsername(username);
        if(appUser==null) throw new UsernameNotFoundException(String.format("Username %s not found", username));

      String[] roles = appUser.getRoles().stream().map(u->u.getRole()).toArray(String[]::new);
        UserDetails userDetails= User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles).build();
        return userDetails;
    }
}
