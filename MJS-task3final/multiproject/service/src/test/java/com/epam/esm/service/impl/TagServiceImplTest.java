package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.StatsEntity;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validator.TagBusinessValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.metrics.StartupStep;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TagServiceImplTest {

    private final static Long TAG_ID = 1L;
    private final static Long NON_VALID_TAG_ID = 9L;

    @Mock
    private TagDAO tagDAO;
    @Mock
    private TagBusinessValidator tagBusinessValidator;

    @InjectMocks
    private TagServiceImpl tagService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteTag() throws ServiceException, DAOException, BusinessValidationException {
        when(tagDAO.get(TAG_ID)).thenReturn(new Tag());
        when(tagDAO.delete(TAG_ID)).thenReturn(Boolean.TRUE);
        tagService.delete(TAG_ID);
        verify(tagDAO, times(1)).delete(TAG_ID);
    }

    @Test(expected = ServiceException.class)
    public void whenDeleteUnavailableTag() throws ServiceException, DAOException, BusinessValidationException {
        when(tagDAO.delete(TAG_ID)).thenThrow(DAOException.class);
        tagService.delete(TAG_ID);
    }

    @Test
    public void testRead() throws ServiceException, DAOException, BusinessValidationException {
        Tag tag = createTag();
        when(tagDAO.get(TAG_ID)).thenReturn(tag);
        Tag tagActual = tagService.get(TAG_ID);
        assertEquals(tag, tagActual);
        verify(tagDAO, times(1)).get(TAG_ID);
    }

    @Test(expected = BusinessValidationException.class)
    public void whenReadUnavailableTag() throws ServiceException, BusinessValidationException, DAOException {
        doThrow(BusinessValidationException.class).when(tagBusinessValidator).validateIfExistsById(NON_VALID_TAG_ID);
        tagService.get(NON_VALID_TAG_ID);
    }

    @Test(expected = ServiceException.class)
    public void whenReadTag() throws ServiceException, BusinessValidationException, DAOException {
        when(tagDAO.get(NON_VALID_TAG_ID)).thenThrow(DAOException.class);
        tagService.get(NON_VALID_TAG_ID);
    }

    @Test
    public void testCreate() throws ServiceException, DAOException, BusinessValidationException {
        Tag tag = createTag();
        tagService.create(tag);
        verify(tagDAO, times(1)).create(tag);
    }

    @Test(expected = ServiceException.class)
    public void whenIssueWhileCreatingTag() throws ServiceException, DAOException, BusinessValidationException {
        Tag tag = createTag();
        when(tagDAO.create(tag)).thenThrow(DAOException.class);
        tagService.create(tag);
    }


    @Test(expected = BusinessValidationException.class)
    public void whenCreateExistingTag() throws ServiceException, DAOException, BusinessValidationException {
        Tag tag = createTag();
        doThrow(BusinessValidationException.class).when(tagBusinessValidator).validateIfExistsByName(tag.getName());
        tagService.create(tag);
    }

    @Test
    public void testFind() throws ServiceException, DAOException, BusinessValidationException {
        Tag tag = createTag();
        when(tagDAO.find("name")).thenThrow(DAOException.class);
        tagService.find(tag);
        verify(tagDAO, times(1)).find(tag.getName());
    }

    @Test(expected = ServiceException.class)
    public void whenFindUnavailableTag() throws ServiceException, DAOException, BusinessValidationException {
        Tag tag = createTag();
        when(tagDAO.find("tag")).thenThrow(DAOException.class);
        tagService.find(tag);
    }

    @Test
    public void testUpdate() throws DAOException {
        Tag tag = createTag();
        tagService.update(tag);
        doNothing().when(tagDAO).update(tag);
    }

    @Test
    public void testGetAllTags() throws ServiceException, DAOException {
        List<Tag> tags = createListOfTag();
        when(tagDAO.getAllTags(null)).thenReturn(tags);
        tagService.getAllTags(null);
        verify(tagDAO, times(1)).getAllTags(null);
    }

    @Test(expected = ServiceException.class)
    public void whenNoTags() throws ServiceException, DAOException {
        when(tagDAO.getAllTags(null)).thenThrow(DAOException.class);
        tagService.getAllTags(null);
    }

    @Test
    public void testGetTagStats() throws ServiceException, DAOException {
        StatsEntity stats = new StatsEntity();
        when(tagDAO.getTagStats()).thenReturn(stats);
        tagService.getTagStats();
        verify(tagDAO, times(1)).getTagStats();
    }

    @Test(expected = ServiceException.class)
    public void whenExceptionWhileGetTagStats() throws ServiceException, DAOException {
        doThrow(DAOException.class).when(tagDAO).getTagStats();
        tagService.getTagStats();
    }

    @Test
    public void testGetTags() throws ServiceException, DAOException {
        List<Tag> tags = createListOfTag();
        when(tagDAO.getAllTags()).thenReturn(tags);
        tagService.getAllTags();
        verify(tagDAO, times(1)).getAllTags();
    }



    private Tag createTag() {
        Tag tag = new Tag();
        tag.setId(0L);
        tag.setName("tag");
        return tag;
    }

    private List<Tag> createListOfTag() {
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(0L);
        tag1.setName("tag1");
        tags.add(tag1);
        Tag tag2 = new Tag();
        tag2.setId(1L);
        tag2.setName("tag2");
        tags.add(tag2);
        return tags;
    }
}