package org.evaldas.file_processor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileDownloadService {

	@Value("${tempDirName}")
	private String tempDirName;

	public File getFile(String filename) throws IllegalArgumentException {
		String pathString = System.getProperty("java.io.tmpdir") + "/" + tempDirName + "/" + filename;

		File file = new File(pathString);
		if (file.exists()) {
			return file;
		} else {
			throw new IllegalArgumentException("File with name " + filename + " does not exist");
		}
	}
}
