package org.evaldas.file_processor.util;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MapSortingUtility {

	public Map<String, Integer> sortMapByWordLengthAndAlphabetically(Map<String, Integer> map) {
		return map.entrySet()
				.stream()
				.sorted((e1, e2) -> {
					if (e1.getKey().length() > e2.getKey().length()) return 1;
					if (e1.getKey().length() < e2.getKey().length()) return -1;
					else return e1.getKey().compareTo(e2.getKey());
				})
				.collect(Collectors.toMap(Map.Entry::getKey,
						Map.Entry::getValue,
						(e1, e2) -> e1,
						LinkedHashMap::new));
	}
}
