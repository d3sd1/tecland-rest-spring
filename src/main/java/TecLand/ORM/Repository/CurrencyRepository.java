package TecLand.ORM.Repository;


import TecLand.ORM.Generic.CurrencyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyValue, Long> {
    CurrencyValue findByKeyName(String keyname);
}