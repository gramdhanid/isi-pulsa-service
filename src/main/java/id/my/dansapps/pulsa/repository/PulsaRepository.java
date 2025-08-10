package id.my.dansapps.pulsa.repository;

import id.my.dansapps.pulsa.model.Pulsa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PulsaRepository extends JpaRepository<Pulsa, Long> {
    Optional<Pulsa> findByTransactionId(String transactionId);
}
