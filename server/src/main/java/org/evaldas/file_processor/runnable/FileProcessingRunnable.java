package org.evaldas.file_processor.runnable;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

@AllArgsConstructor
public class FileProcessingRunnable implements Runnable {

	private final MultipartFile file;
	private final Map<String, Integer> wordsFrequency;

	@Override
	public void run() {
		String filename = file.getOriginalFilename();
		System.out.println(Thread.currentThread().getName() + " processing file with name " + filename);

		try (
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))
		) {
			bufferedReader.lines()
					.filter(StringUtils::isNotBlank)
					.map(line -> line.split("[\\W|\\d]+"))
					.flatMap(Arrays::stream)
					.map(String::toLowerCase)
					.filter(StringUtils::isNotBlank)
					.forEach(word -> wordsFrequency.merge(word, 1, Integer::sum));

			System.out.println(Thread.currentThread().getName() + " done processing file with name " + filename);
		} catch (IOException e) {
			System.out.printf("Failed to get input stream from file %s with message %s", filename, e.getMessage());
		}
	}
}
