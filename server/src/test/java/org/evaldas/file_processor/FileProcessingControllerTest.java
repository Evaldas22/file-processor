package org.evaldas.file_processor;

import org.evaldas.file_processor.service.TemporaryDirectoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = FileProcessorApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
public class FileProcessingControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private TemporaryDirectoryService temporaryDirectoryService;

	@AfterEach
	public void cleanUp(){
		temporaryDirectoryService.deleteTempDirectory();
	}

	@Test
	public void processFile_shouldSuccessfullyParseAndSendResponse_WhenTwoFilesUploaded() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "test1.txt", "text/plain", "This is some \"sample\" text, ok?".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "test1.txt", "text/plain", "All words are ok, so do not worry!".getBytes());

		mvc.perform(multipart("/process-files")
				.file(file1)
				.file(file2))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.wordFrequenciesFromAtoG.filename", Matchers.endsWith("-a-g.txt")))
				.andExpect(jsonPath("$.wordFrequenciesFromAtoG.words.all", equalTo(1)))
				.andExpect(jsonPath("$.wordFrequenciesFromAtoG.words.are", equalTo(1)))
				.andExpect(jsonPath("$.wordFrequenciesFromAtoG.words.do", equalTo(1)))

				.andExpect(jsonPath("$.wordFrequenciesFromHtoN.filename", Matchers.endsWith("-h-n.txt")))
				.andExpect(jsonPath("$.wordFrequenciesFromHtoN.words.is", equalTo(1)))
				.andExpect(jsonPath("$.wordFrequenciesFromHtoN.words.not", equalTo(1)))

				.andExpect(jsonPath("$.wordFrequenciesFromOtoU.filename", Matchers.endsWith("-o-u.txt")))
				.andExpect(jsonPath("$.wordFrequenciesFromOtoU.words.ok", equalTo(2)))
				.andExpect(jsonPath("$.wordFrequenciesFromOtoU.words.sample", equalTo(1)))
				.andExpect(jsonPath("$.wordFrequenciesFromOtoU.words.so", equalTo(1)))
				.andExpect(jsonPath("$.wordFrequenciesFromOtoU.words.some", equalTo(1)))
				.andExpect(jsonPath("$.wordFrequenciesFromOtoU.words.text", equalTo(1)))
				.andExpect(jsonPath("$.wordFrequenciesFromOtoU.words.this", equalTo(1)))

				.andExpect(jsonPath("$.wordFrequenciesFromVtoZ.filename", Matchers.endsWith("-v-z.txt")))
				.andExpect(jsonPath("$.wordFrequenciesFromVtoZ.words.words", equalTo(1)))
				.andExpect(jsonPath("$.wordFrequenciesFromVtoZ.words.worry", equalTo(1)));

		assertEquals(4, temporaryDirectoryService.loadAllFromTempDirectory().size());
	}
}
