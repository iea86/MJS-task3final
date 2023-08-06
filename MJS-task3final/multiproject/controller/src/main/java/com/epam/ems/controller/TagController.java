package com.epam.ems.controller;

import com.epam.ems.converter.StatsConverter;
import com.epam.ems.dto.TagStatsEntity;
import com.epam.ems.response.InfoResponse;
import com.epam.ems.validator.PaginationValidator;
import com.epam.ems.validator.TagValidator;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.StatsEntity;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;
import com.epam.ems.converter.TagConverter;
import com.epam.ems.dto.TagDTO;
import com.epam.esm.service.DataGenerator;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("tags")
public class TagController {

    private TagService tagService;
    private DataGenerator dataGeneratorService;
    private MessageSource messageSource;

    @Autowired
    public TagController(TagService tagService, DataGenerator dataGeneratorService,MessageSource messageSource) {
        this.tagService = tagService;
        this.dataGeneratorService = dataGeneratorService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public HttpEntity<List<TagDTO>> getTags(Pagination pagination, Locale locale) throws ServiceException, BusinessValidationException {
        PaginationValidator.validatePagination(pagination, locale);
        List<TagDTO> tagsDTO = tagService.getAllTags(pagination)
                .stream()
                .map(TagConverter::convertToDto)
                .collect(Collectors.toList());
        addTagsHATEOAS(tagsDTO, locale, pagination);
        return ResponseEntity.ok(tagsDTO);
    }

    @GetMapping("/{tagId}")
    public HttpEntity<TagDTO> getTag(@PathVariable Long tagId, Locale locale, Pagination pagination) throws ServiceException, BusinessValidationException {
        TagDTO tagDTO = TagConverter.convertToDto(tagService.get(tagId));
        addTagHATEOAS(tagDTO, locale, pagination);
        return ResponseEntity.ok(tagDTO);
    }

    @PostMapping
    public InfoResponse addTag(@RequestBody TagDTO tagDTO, Locale locale) throws ServiceException, BusinessValidationException {
        TagValidator.validateTag(tagDTO, locale);
        Tag tag = TagConverter.convertToEntity(tagDTO);
        Long tagId = tagService.create(tag);
        String message = messageSource.getMessage("label.tag.created", null, locale);
        return new InfoResponse(
                HttpStatus.CREATED.value(),
                message + ":" + tagId,
                HttpStatus.CREATED.toString());
    }

    @DeleteMapping("/{tagId}")
    public InfoResponse deleteTag(@PathVariable Long tagId, Locale locale) throws ServiceException, BusinessValidationException {
        tagService.delete(tagId);
        String message = messageSource.getMessage("label.tag.deleted", null, locale);
        return new InfoResponse(
                HttpStatus.NO_CONTENT.value(),
                message + ":" + tagId,
                HttpStatus.NO_CONTENT.toString());
    }

    @GetMapping("/stats")
    public TagStatsEntity getTagsStats() throws ServiceException {
        StatsEntity tagStatsEntity = tagService.getTagStats();
        return StatsConverter.convertToDto(tagStatsEntity);
    }

    @PostMapping("/generate")
    public InfoResponse generateTags(@RequestParam(value = "count") int count, Locale locale) throws ServiceException {
        dataGeneratorService.generateTags(count);
        int numberOfRows = tagService.getAllTags().size();
        String message = messageSource.getMessage("label.tags.total.rows", null, locale);
        return new InfoResponse(
                HttpStatus.OK.value(),
                message + ":" + numberOfRows,
                HttpStatus.OK.toString());
    }

    private void addTagsHATEOAS(List<TagDTO> tagsDTO, Locale locale, Pagination pagination) throws ServiceException, BusinessValidationException {
        for (TagDTO tagDTO : tagsDTO) {
            tagDTO.add(linkTo(methodOn(TagController.class).getTag(tagDTO.getId(), locale, pagination)).withSelfRel());
            tagDTO.add(linkTo(methodOn(TagController.class).deleteTag(tagDTO.getId(), locale)).withRel("delete"));
        }
    }

    private void addTagHATEOAS(TagDTO tagDTO, Locale locale, Pagination pagination) throws ServiceException, BusinessValidationException {
        tagDTO.add(linkTo(methodOn(TagController.class).getTag(tagDTO.getId(), locale, pagination)).withSelfRel());
        tagDTO.add(linkTo(methodOn(TagController.class).deleteTag(tagDTO.getId(), locale)).withRel("delete"));
        tagDTO.add(linkTo(methodOn(TagController.class).getTags(pagination, locale)).withRel("tags"));
    }
}
