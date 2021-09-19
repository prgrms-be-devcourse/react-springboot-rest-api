package com.programmers.coffeeorder.entity.delivery;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public abstract class Delivery {
    protected Long id;
    protected final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    protected Delivery(Long id) {
        this.id = id;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    protected void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    protected abstract static class DTO {
        protected final long id;
        protected final LocalDateTime createdAt;
        protected final LocalDateTime updatedAt;
    }
}
