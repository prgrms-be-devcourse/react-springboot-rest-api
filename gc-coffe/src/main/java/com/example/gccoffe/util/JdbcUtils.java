package com.example.gccoffe.util;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class JdbcUtils {
    /**
     * bytes를 UUID로 변환하는 method
     * @param bytes
     * @return 변환된 UUID
     */
    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    /**
     * timestamp를 LocalDateTime으로 바꿔주는 method
     * @param timestamp
     * @return timestamp가 null이 아니면 LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
