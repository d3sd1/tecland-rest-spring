package TecLand.ORM.Repository;

import TecLand.ORM.Model.DashUserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DashUserLoginRepository extends JpaRepository<DashUserLogin, Long> {
}