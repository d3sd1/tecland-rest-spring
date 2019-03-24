package TecLand.dashboard.repository;

import TecLand.ORM.DashUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<DashUser, Long> {
    DashUser findByEmail(String email);
}