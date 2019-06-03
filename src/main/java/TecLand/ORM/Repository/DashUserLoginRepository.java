package TecLand.ORM.Repository;

import TecLand.ORM.Dashboard.DashUser;
import TecLand.ORM.Dashboard.DashUserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DashUserLoginRepository extends JpaRepository<DashUserLogin, Long> {
    DashUserLogin findByJwt(String jwt);

    List<DashUserLogin> findAllByDashUser(DashUser dashUser);
}