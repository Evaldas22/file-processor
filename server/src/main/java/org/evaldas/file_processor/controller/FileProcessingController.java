package org.evaldas.file_processor.controller;

import lombok.AllArgsConstructor;
import org.evaldas.file_processor.service.FileProcessingService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class FileProcessingController {

	private final FileProcessingService fileProcessingService;

	@PostMapping(value = "process-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Map<String, Integer> processFile(@RequestPart("files") List<MultipartFile> files) {
		return fileProcessingService.process(files);
	}
}
