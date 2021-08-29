package org.evaldas.file_processor.util;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MapSortingUtility {

	public Map<String, Integer> sortMapByWordLengthAndAlphabetically(Map<String, Integer> map) {
		return map.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey,
						Map.Entry::getValue,
						(e1, e2) -> e1,
						LinkedHashMap::new));
	}
}
