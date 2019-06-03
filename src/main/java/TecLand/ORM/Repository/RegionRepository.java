package TecLand.ORM.Repository;

import TecLand.ORM.Generic.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findByCode(String code);
}