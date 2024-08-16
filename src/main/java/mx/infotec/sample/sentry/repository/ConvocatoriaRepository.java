package mx.infotec.sample.sentry.repository;

import mx.infotec.sample.sentry.domain.Convocatoria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Convocatoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConvocatoriaRepository extends ReactiveMongoRepository<Convocatoria, String> {}
