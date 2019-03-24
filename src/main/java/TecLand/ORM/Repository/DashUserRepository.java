package TecLand.ORM.Repository;

import TecLand.ORM.Model.DashUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DashUserRepository extends JpaRepository<DashUser, Long> {
    DashUser findByEmail(String email);
}