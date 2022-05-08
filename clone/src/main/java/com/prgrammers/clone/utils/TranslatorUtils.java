package com.prgrammers.clone.utils;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

public class TranslatorUtils {
	private TranslatorUtils() {
	}

	public static UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	public static LocalDateTime toLocalDateTIme(Timestamp timestamp) {
		return timestamp != null ? timestamp.toLocalDateTime() : null;
	}

	public static String bytesToHex(byte[] bytes) {
		return DatatypeConverter.printHexBinary(bytes);
	}

}
