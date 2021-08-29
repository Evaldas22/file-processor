package org.evaldas.file_processor.controller;

import lombok.AllArgsConstructor;
import org.evaldas.file_processor.controller.response.FileProcessingResultResponse;
import org.evaldas.file_processor.service.FileProcessingService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*") // TODO: remove before prod
public class FileProcessingController {

	private final FileProcessingService fileProcessingService;

	@PostMapping(value = "process-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FileProcessingResultResponse processFile(@RequestPart("files") List<MultipartFile> files) {
		return fileProcessingService.process(files);
	}
}
