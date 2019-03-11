package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.config.FileStorageProperties;
import com.antiplagiarism.fileuploadservice.core.repositories.DocumentRepository;
import com.antiplagiarism.fileuploadservice.domain.events.SaveDocumentEvent;
import com.thedeanda.lorem.LoremIpsum;
import org.junit.After;
import org.junit.AfterClass;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileStorageServiceTest {

    private static LoremIpsum lorem;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private InputStream inputStream;

    private static Path fileStorageLocation;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        lorem = LoremIpsum.getInstance();
        fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }

    @AfterClass
    public static void tearDown(){
        String fileName = StringUtils.cleanPath("testFile.doc");
        File file = fileStorageLocation.resolve(fileName).toFile();
        file.delete();
    }

    @Test
    public void save() throws IOException {
        multipartFile = mock(MultipartFile.class);
        inputStream = mock(InputStream.class);
        when(inputStream.read()).thenReturn(0);

        when(multipartFile.getName()).thenReturn("testFile.doc");
        when(multipartFile.getBytes()).thenReturn(lorem.getParagraphs(1,30).getBytes());
        when(multipartFile.getOriginalFilename()).thenReturn("testFile.doc");
        when(multipartFile.getInputStream()).thenReturn(inputStream);

        fileStorageService.save(new SaveDocumentEvent(multipartFile.getName(),multipartFile.getBytes(),null));
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        File file = fileStorageLocation.resolve(fileName).toFile();
        assertTrue(file.exists());
    }

    @Test(expected = IllegalStateException.class)
    public void saveTwoTimesSameFile() throws IOException {
        multipartFile = mock(MultipartFile.class);
        inputStream = mock(InputStream.class);
        when(inputStream.read()).thenReturn(0);

        when(multipartFile.getName()).thenReturn("testFile.doc");
        when(multipartFile.getBytes()).thenReturn(lorem.getParagraphs(1,30).getBytes());
        when(multipartFile.getOriginalFilename()).thenReturn("testFile.doc");
        when(multipartFile.getInputStream()).thenReturn(inputStream);

        fileStorageService.save(new SaveDocumentEvent(multipartFile.getName(),multipartFile.getBytes(),null));
    }

    @Test(expected = IllegalStateException.class)
    public void hasWrongName() throws IOException {
        multipartFile = mock(MultipartFile.class);
        inputStream = mock(InputStream.class);
        when(inputStream.read()).thenReturn(0);

        when(multipartFile.getName()).thenReturn("testFile...doc");
        when(multipartFile.getBytes()).thenReturn(lorem.getParagraphs(1,30).getBytes());
        when(multipartFile.getOriginalFilename()).thenReturn("testFile...doc");
        when(multipartFile.getInputStream()).thenReturn(inputStream);

        fileStorageService.save(new SaveDocumentEvent(multipartFile.getName(),multipartFile.getBytes(),null));
        fileStorageService.save(new SaveDocumentEvent(multipartFile.getName(),multipartFile.getBytes(),null));
    }
}
