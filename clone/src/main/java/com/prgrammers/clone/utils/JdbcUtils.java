package com.prgrammers.clone.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class JdbcUtils {
	private JdbcUtils() {}

	public static Map<String, Object> buildParameters(List<UUID> copyIds) {
		HashMap<String, Object> orderIds = new HashMap<>();
		AtomicInteger number = new AtomicInteger();
		copyIds.forEach(orderId -> {
			orderIds.put("orderId" + number.getAndIncrement(), orderId.toString().getBytes());
		});

		return orderIds;
	}

	public static String consistParameterWords(int size) {
		String inQueryWords = String.join(",", Collections.nCopies(size, "UUID_TO_BIN(?)"));
		String[] inQueryParams = inQueryWords.split(",");
		AtomicInteger index = new AtomicInteger();
		return Arrays.stream(inQueryParams)
				.map(s -> s.replace("?", ":orderId" + index.getAndIncrement()))
				.collect(Collectors.joining(","));
	}
}
