package org.evaldas.file_processor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileProcessingService {

	public Map<String, Integer> process(List<MultipartFile> files) {
		return new HashMap<>();
	}
}
