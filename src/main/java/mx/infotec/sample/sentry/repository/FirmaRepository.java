package mx.infotec.sample.sentry.repository;

import mx.infotec.sample.sentry.domain.Firma;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Firma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FirmaRepository extends ReactiveMongoRepository<Firma, String> {}
