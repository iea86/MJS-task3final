package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {InitTestDataBaseConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class TagDAOImplIntTest {

    private final static Long TAG_ID = 1L;
    private final static Long TAG_ID_TO_DELETE = 5L;
    private final static String TAG_NAME = "#test1";
    private final static int NUMBER_OF_TAGS = 5;
    private final static Long NEW_TAG_ID = 7L;
    private final static Long TAG_ID_THAT_DOESNT_EXIST = 10L;

    @Autowired
    private TagDAO tagDAO;

    @Test
    public void testCreate() throws DAOException {
        Tag tag = createTag();
        Long id = tagDAO.create(tag);
        Tag tagCreated = tagDAO.get(id);
        assertEquals(NEW_TAG_ID, tagCreated.getId());
    }

    @Test
    public void testRead() throws DAOException {
        Tag tag = tagDAO.get(TAG_ID);
        assertEquals(TAG_ID, tag.getId());
        assertEquals(TAG_NAME, tag.getName());
    }

    @Test
    public void testReadWhenTagDoesntExist() throws DAOException {
        assertNull(tagDAO.get(TAG_ID_THAT_DOESNT_EXIST));
    }

    @Test
    public void testDelete() throws DAOException {
        tagDAO.delete(TAG_ID_TO_DELETE);
        assertNull(tagDAO.get(TAG_ID_TO_DELETE));
    }

    @Test
    public void testFindTag() throws DAOException {
        Tag tag = tagDAO.find(TAG_NAME);
        assertEquals(TAG_ID, tag.getId());
    }

    @Test
    public void testGetAllTags() throws DAOException {
        assertEquals(NUMBER_OF_TAGS, tagDAO.getAllTags().size());
    }

    @Test
    public void testGetAllTagsWithPagination() throws DAOException {
        int tagsSize = tagDAO.getAllTags(new Pagination()).size();
        assertEquals(5, tagsSize);
    }

    private Tag createTag() {
        String name = "name";
        Tag tag = new Tag();
        tag.setName(name);
        return tag;
    }
}


