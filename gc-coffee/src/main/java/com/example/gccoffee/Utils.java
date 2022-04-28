package com.example.gccoffee;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Utils {
     public static UUID toUUID(byte[] product_ids) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(product_ids);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static LocalDateTime toLocalDateTime(Timestamp timestamp){
        return timestamp != null ? timestamp.toLocalDateTime(): null;
    }
}
