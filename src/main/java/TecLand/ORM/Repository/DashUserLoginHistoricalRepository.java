package TecLand.ORM.Repository;

import TecLand.ORM.Model.DashUserLoginHistorical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DashUserLoginHistoricalRepository extends JpaRepository<DashUserLoginHistorical, Long> {
}