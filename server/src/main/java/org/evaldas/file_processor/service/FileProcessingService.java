package org.evaldas.file_processor.service;

import lombok.AllArgsConstructor;
import org.evaldas.file_processor.controller.response.FileProcessingResultResponse;
import org.evaldas.file_processor.runnable.FileProcessingRunnable;
import org.evaldas.file_processor.util.MapSortingUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class FileProcessingService {

	private final MapSortingUtility mapSortingUtility;

	public FileProcessingResultResponse process(List<MultipartFile> files) {
		Map<String, Integer> wordsFrequency = new ConcurrentHashMap<>();
		List<Thread> threads = new ArrayList<>();

		long start = System.currentTimeMillis();

		files.forEach(file -> {
			Thread t = new Thread(new FileProcessingRunnable(file, wordsFrequency));
			t.start();
			threads.add(t);
		});

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long finish = System.currentTimeMillis();
		System.out.println("File processing took " + (finish - start) + " ms");

		return buildResponseAndSaveResults(mapSortingUtility.sortMapByWordLengthAndAlphabetically(wordsFrequency));
	}

	private FileProcessingResultResponse buildResponseAndSaveResults(Map<String, Integer> wordsFrequency) {
		Map<String, Integer> wordFrequenciesFromAtoG = new LinkedHashMap<>();
		Map<String, Integer> wordFrequenciesFromHtoN = new LinkedHashMap<>();
		Map<String, Integer> wordFrequenciesFromOtoU = new LinkedHashMap<>();
		Map<String, Integer> wordFrequenciesFromVtoZ = new LinkedHashMap<>();

		wordsFrequency.forEach((word, frequency) -> {
			char firstLetter = word.charAt(0);
			if (firstLetter >= 'a' && firstLetter <= 'g') {
				wordFrequenciesFromAtoG.put(word, frequency);
			} else if (firstLetter >= 'h' && firstLetter <= 'n') {
				wordFrequenciesFromHtoN.put(word, frequency);
			} else if (firstLetter >= 'o' && firstLetter <= 'u') {
				wordFrequenciesFromOtoU.put(word, frequency);
			} else if (firstLetter >= 'v' && firstLetter <= 'z') {
				wordFrequenciesFromVtoZ.put(word, frequency);
			}
		});

		// TODO: save to 4 result file

		return new FileProcessingResultResponse(
				wordFrequenciesFromAtoG,
				wordFrequenciesFromHtoN,
				wordFrequenciesFromOtoU,
				wordFrequenciesFromVtoZ
		);
	}
}
