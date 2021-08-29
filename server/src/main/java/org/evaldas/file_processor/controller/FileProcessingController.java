package org.evaldas.file_processor.controller;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.evaldas.file_processor.controller.response.FileProcessingResultResponse;
import org.evaldas.file_processor.service.FileDownloadService;
import org.evaldas.file_processor.service.FileProcessingService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*") // TODO: remove before prod
public class FileProcessingController {

	private final FileDownloadService fileDownloadService;
	private final FileProcessingService fileProcessingService;

	@PostMapping(value = "process-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FileProcessingResultResponse processFile(@RequestPart("files") List<MultipartFile> files) {
		return fileProcessingService.process(files);
	}

	@GetMapping(value = "download")
	public ResponseEntity<Resource> download(@RequestParam String filename) throws IOException {
		File file = fileDownloadService.getFile(filename);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok()
				.headers(header)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}
}
