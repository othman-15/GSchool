package ma.devops.gestionecole.security.repo;

import ma.devops.gestionecole.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
}
