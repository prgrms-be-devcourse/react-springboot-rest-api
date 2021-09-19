package com.programmers.coffeeorder.entity.order;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public abstract class Order {
    protected Long id;
    protected final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    protected Order(Long id) {
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
