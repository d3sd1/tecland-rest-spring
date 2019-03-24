package TecLand.dashboard.repository;

import TecLand.ORM.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findByCode(String code);
}