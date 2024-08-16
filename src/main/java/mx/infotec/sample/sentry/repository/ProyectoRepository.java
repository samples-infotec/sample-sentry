package mx.infotec.sample.sentry.repository;

import mx.infotec.sample.sentry.domain.Proyecto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Proyecto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProyectoRepository extends ReactiveMongoRepository<Proyecto, String> {}
