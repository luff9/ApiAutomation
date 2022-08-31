package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Order {
    Integer id;
    Integer petId;
    Integer quantity;
    String shipDate;
    String status;
    OrderStatus order;
    boolean complete;
}
