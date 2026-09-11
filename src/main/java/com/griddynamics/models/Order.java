package com.griddynamics.models;

import java.util.List;
import java.util.UUID;

public record Order(UUID id, List<OrderItem> items) {
}
