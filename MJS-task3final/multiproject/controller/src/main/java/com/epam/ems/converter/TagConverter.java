package com.epam.ems.converter;

import com.epam.ems.dto.TagDTO;
import com.epam.esm.entity.Tag;

public class TagConverter {

    private TagConverter() {
    }

    public static TagDTO convertToDto(Tag tag) {
            TagDTO tagDTO = new TagDTO();
            tagDTO.setId(tag.getId());
            tagDTO.setName(tag.getName());
            return tagDTO;
        }

        public static Tag convertToEntity(TagDTO tagDTO) {
            Tag tag = new Tag();
            tag.setId(tagDTO.getId());
            tag.setName(tagDTO.getName());
            return tag;
        }
    }

