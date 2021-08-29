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

import static org.hamcrest.Matchers.*;
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
	public void cleanUp() {
		temporaryDirectoryService.deleteTempDirectory();
	}

	@Test
	public void processFile_shouldSuccessfullyParseAndSendResponse_whenTwoFilesUploaded() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "test1.txt", "text/plain", "This is some \"sample\" text, ok?".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "test1.txt", "text/plain", "All words are ok, so do not worry!".getBytes());

		mvc.perform(multipart("/process-files")
				.file(file1)
				.file(file2))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.wordFrequenciesFromAtoG.filename", Matchers.endsWith("-a-g.txt")))
				.andExpect(jsonPath("$.wordFrequenciesFromAtoG.words", hasSize(3)))
				.andExpect(jsonPath("$.wordFrequenciesFromAtoG.words", hasItems("all - 1", "are - 1", "do - 1")))

				.andExpect(jsonPath("$.wordFrequenciesFromHtoN.filename", Matchers.endsWith("-h-n.txt")))
				.andExpect(jsonPath("$.wordFrequenciesFromHtoN.words", hasSize(2)))
				.andExpect(jsonPath("$.wordFrequenciesFromHtoN.words", hasItems("is - 1", "not - 1")))

				.andExpect(jsonPath("$.wordFrequenciesFromOtoU.filename", Matchers.endsWith("-o-u.txt")))
				.andExpect(jsonPath("$.wordFrequenciesFromOtoU.words", hasSize(6)))
				.andExpect(jsonPath("$.wordFrequenciesFromOtoU.words", hasItems("ok - 2", "sample - 1", "so - 1",
						"some - 1", "text - 1", "this - 1")))

				.andExpect(jsonPath("$.wordFrequenciesFromVtoZ.filename", Matchers.endsWith("-v-z.txt")))
				.andExpect(jsonPath("$.wordFrequenciesFromVtoZ.words", hasSize(2)))
				.andExpect(jsonPath("$.wordFrequenciesFromVtoZ.words", hasItems("words - 1", "worry - 1")));

		assertEquals(4, temporaryDirectoryService.loadAllFromTempDirectory().size());
	}

	@Test
	public void processFile_shouldReturnBadRequest_whenNoFileUploaded() throws Exception {
		mvc.perform(multipart("/process-files"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void processFile_shouldReturnBadRequest_whenEmptyFilesUploaded() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "test1.txt", "text/plain", "".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "test1.txt", "text/plain", "".getBytes());

		mvc.perform(multipart("/process-files")
				.file(file1)
				.file(file2))
				.andExpect(status().isBadRequest());
	}
}
