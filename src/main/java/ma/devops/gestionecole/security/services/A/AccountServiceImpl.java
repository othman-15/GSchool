package ma.devops.gestionecole.security.services.A;

import lombok.AllArgsConstructor;
import ma.devops.gestionecole.security.entities.AppRole;
import ma.devops.gestionecole.security.entities.AppUser;
import ma.devops.gestionecole.security.repo.AppRoleRepository;
import ma.devops.gestionecole.security.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(String username, String password, String confirmPassword) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser != null) throw new RuntimeException("User already exists");
        if(!password.equals(confirmPassword)) throw new RuntimeException("Passwords do not match");
        appUser  =AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
         AppUser savedAppUser= appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {
    AppRole appRole=appRoleRepository.findById(role).orElse(null);
    if (appRole != null) throw new RuntimeException("Role already exists");
    appRole=AppRole.builder()
            .role(role)
            .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {

        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }


        AppRole appRole = appRoleRepository.findById(role)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));


        appUser.getRoles().add(appRole);


        appUserRepository.save(appUser);
    }
    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {

        return  appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();

    }
    @Override
    public List<AppRole> getAllRoles() {
        return appRoleRepository.findAll();
    }
}
