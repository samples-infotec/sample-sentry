package mx.infotec.sample.sentry.repository;

import mx.infotec.sample.sentry.domain.Authority;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Spring Data MongoDB repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends ReactiveMongoRepository<Authority, String> {}
