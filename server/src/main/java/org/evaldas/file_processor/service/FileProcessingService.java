package org.evaldas.file_processor.service;

import lombok.AllArgsConstructor;
import org.evaldas.file_processor.controller.response.FileProcessingResultResponse;
import org.evaldas.file_processor.runnable.FileProcessingRunnable;
import org.evaldas.file_processor.util.MapSortingUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class FileProcessingService {

	private final StorageService storageService;
	private final MapSortingUtility mapSortingUtility;

	private final SimpleDateFormat fileDateFormat = new SimpleDateFormat("yy-MM-dd-HH:mm:ss.SSS");

	public FileProcessingResultResponse process(List<MultipartFile> files) {
		Map<String, Integer> wordsFrequency = new ConcurrentHashMap<>();
		List<Thread> threads = new ArrayList<>();

		long start = System.currentTimeMillis();

		// TODO: improve with executor service
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
		System.out.println("File parsing took " + (finish - start) + " ms");

		return buildResponseAndSaveResults(mapSortingUtility.sortMapByWordLengthAndAlphabetically(wordsFrequency));
	}

	private FileProcessingResultResponse buildResponseAndSaveResults(Map<String, Integer> wordsFrequency) {
		Map<String, Integer> wordFrequenciesFromAtoG = new LinkedHashMap<>();
		Map<String, Integer> wordFrequenciesFromHtoN = new LinkedHashMap<>();
		Map<String, Integer> wordFrequenciesFromOtoU = new LinkedHashMap<>();
		Map<String, Integer> wordFrequenciesFromVtoZ = new LinkedHashMap<>();

		sortIntoSeparateMapsByFirstLetter(wordsFrequency, wordFrequenciesFromAtoG, wordFrequenciesFromHtoN,
				wordFrequenciesFromOtoU, wordFrequenciesFromVtoZ);

		String aToGResultFilename = storageService.saveListIntoTempFile(
				convertMapToList(wordFrequenciesFromAtoG), createDatedFileName("-a-g.txt"));
		String hToNResultFilename = storageService.saveListIntoTempFile(
				convertMapToList(wordFrequenciesFromHtoN), createDatedFileName("-h-n.txt"));
		String oToUResultFilename = storageService.saveListIntoTempFile(
				convertMapToList(wordFrequenciesFromOtoU), createDatedFileName("-o-u.txt"));
		String vToZResultFilename = storageService.saveListIntoTempFile(
				convertMapToList(wordFrequenciesFromVtoZ), createDatedFileName("-v-z.txt"));

		return new FileProcessingResultResponse(
				new FileProcessingResultResponse.FileResponse(aToGResultFilename, wordFrequenciesFromAtoG),
				new FileProcessingResultResponse.FileResponse(hToNResultFilename, wordFrequenciesFromHtoN),
				new FileProcessingResultResponse.FileResponse(oToUResultFilename, wordFrequenciesFromOtoU),
				new FileProcessingResultResponse.FileResponse(vToZResultFilename, wordFrequenciesFromVtoZ)
		);
	}

	private void sortIntoSeparateMapsByFirstLetter(Map<String, Integer> wordsFrequency,
												   Map<String, Integer> wordFrequenciesFromAtoG,
												   Map<String, Integer> wordFrequenciesFromHtoN,
												   Map<String, Integer> wordFrequenciesFromOtoU,
												   Map<String, Integer> wordFrequenciesFromVtoZ) {
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
	}

	private List<String> convertMapToList(Map<String, Integer> map) {
		return map.entrySet().stream()
				.map(entry -> entry.getKey() + " - " + entry.getValue())
				.collect(toList());
	}

	private String createDatedFileName(String name) {
		return fileDateFormat.format(new Date()) + name;
	}
}
