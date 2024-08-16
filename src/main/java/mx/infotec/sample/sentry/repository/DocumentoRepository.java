package mx.infotec.sample.sentry.repository;

import mx.infotec.sample.sentry.domain.Documento;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Documento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentoRepository extends ReactiveMongoRepository<Documento, String> {}
