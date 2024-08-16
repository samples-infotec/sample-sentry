package mx.infotec.sample.sentry.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import mx.infotec.sample.sentry.domain.Documento;
import mx.infotec.sample.sentry.repository.DocumentoRepository;
import mx.infotec.sample.sentry.service.DocumentoService;
import mx.infotec.sample.sentry.service.dto.DocumentoDTO;
import mx.infotec.sample.sentry.service.mapper.DocumentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Documento}.
 */
@Service
public class DocumentoServiceImpl implements DocumentoService {

    private final Logger log = LoggerFactory.getLogger(DocumentoServiceImpl.class);

    private final DocumentoRepository documentoRepository;

    private final DocumentoMapper documentoMapper;

    public DocumentoServiceImpl(DocumentoRepository documentoRepository, DocumentoMapper documentoMapper) {
        this.documentoRepository = documentoRepository;
        this.documentoMapper = documentoMapper;
    }

    @Override
    public Mono<DocumentoDTO> save(DocumentoDTO documentoDTO) {
        log.debug("Request to save Documento : {}", documentoDTO);
        return documentoRepository.save(documentoMapper.toEntity(documentoDTO)).map(documentoMapper::toDto);
    }

    @Override
    public Mono<DocumentoDTO> update(DocumentoDTO documentoDTO) {
        log.debug("Request to update Documento : {}", documentoDTO);
        return documentoRepository.save(documentoMapper.toEntity(documentoDTO)).map(documentoMapper::toDto);
    }

    @Override
    public Mono<DocumentoDTO> partialUpdate(DocumentoDTO documentoDTO) {
        log.debug("Request to partially update Documento : {}", documentoDTO);

        return documentoRepository
            .findById(documentoDTO.getId())
            .map(existingDocumento -> {
                documentoMapper.partialUpdate(existingDocumento, documentoDTO);

                return existingDocumento;
            })
            .flatMap(documentoRepository::save)
            .map(documentoMapper::toDto);
    }

    @Override
    public Flux<DocumentoDTO> findAll() {
        log.debug("Request to get all Documentos");
        return documentoRepository.findAll().map(documentoMapper::toDto);
    }

    public Mono<Long> countAll() {
        return documentoRepository.count();
    }

    @Override
    public Mono<DocumentoDTO> findOne(String id) {
        log.debug("Request to get Documento : {}", id);
        return documentoRepository.findById(id).map(documentoMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Documento : {}", id);
        return documentoRepository.deleteById(id);
    }
}
