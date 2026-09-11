package com.griddynamics.steps.transformers;

import com.griddynamics.models.OrderItem;
import io.cucumber.java.DataTableType;
import org.jspecify.annotations.NonNull;

import java.util.Map;

@SuppressWarnings("unused")
public class CartItemTransformer {

    @DataTableType
    public OrderItem transformCartItem(@NonNull Map<String, String> entry) {
        return new OrderItem(
                entry.get("product"),
                Integer.parseInt(entry.get("quantity"))
        );
    }
}
