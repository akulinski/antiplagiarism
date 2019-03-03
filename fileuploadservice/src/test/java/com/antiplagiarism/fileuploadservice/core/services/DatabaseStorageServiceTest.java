package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.core.repositories.DocumentRepository;
import com.antiplagiarism.fileuploadservice.domain.entites.DocumentEntity;
import com.thedeanda.lorem.LoremIpsum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DatabaseStorageServiceTest {

    private static LoremIpsum lorem;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DatabaseStorageService databaseStorageService;

    @Mock
    MultipartFile multipartFile;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        lorem = LoremIpsum.getInstance();
    }

    @Test
    public void save() {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setTitle("TestTitle.doc");
        documentEntity.setBytes(lorem.getParagraphs(1, 30).getBytes());
        documentRepository.save(documentEntity);
    }

    @Test
    public void saveGoodData() throws IOException {
        when(multipartFile.getName()).thenReturn("TestTitle.doc");
        when(multipartFile.getBytes()).thenReturn(lorem.getParagraphs(0, 30).getBytes());
        databaseStorageService.save(multipartFile);
    }

    @Test(expected = IOException.class)
    public void failSaveData() throws IOException {
        when(multipartFile.getName()).thenReturn("TestTitle.doc");
        when(multipartFile.getBytes()).thenReturn(null);
        databaseStorageService.save(multipartFile);
    }

}
