package ru.elseff.pcaccounting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elseff.pcaccounting.dao.entity.Document;
import ru.elseff.pcaccounting.dao.repository.DocumentRepository;
import ru.elseff.pcaccounting.dto.DocumentModel;
import ru.elseff.pcaccounting.generator.ModelGenerator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Document controller", description = "Управление документами")
public class DocumentController {

    DocumentRepository documentRepository;
    ModelGenerator modelGenerator;

    @Operation(
            method = "GET",
            summary = "Все документы",
            description = "Получить список всех документов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешно получен список всех документов",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentModel.class)))
                    )
            }
    )
    @GetMapping
    public List<DocumentModel> findAll() {
        return documentRepository.findAll()
                .stream()
                .map(modelGenerator::generateDocumentModel)
                .collect(Collectors.toList());
    }

    @Operation(
            method = "GET",
            summary = "Получить документ",
            description = "Получить pdf документ по номеру документа",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешно получен документ",
                            content = @Content(mediaType = MediaType.APPLICATION_PDF_VALUE)
                    )
            },
            parameters = {
                @Parameter(name = "number", description = "Номер документа")
            }
    )
    @GetMapping(value = "/{number}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> findByNumber(@PathVariable String number) {
        Optional<Document> optionalDocument = documentRepository.findByNumber(number);
        if (optionalDocument.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }

        Document document = optionalDocument.get();
        ByteArrayResource resource = new ByteArrayResource(document.getDocumentData());

        return ResponseEntity.ok(resource);
    }
}
