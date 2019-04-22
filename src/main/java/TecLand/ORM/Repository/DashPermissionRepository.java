package TecLand.ORM.Repository;

import TecLand.ORM.Model.DashPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DashPermissionRepository extends JpaRepository<DashPermission, Long> {
    DashPermission findByPermissionKey(String permissionKey);
}