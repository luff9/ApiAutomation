package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ApiResponse {
    Integer code;
    String type;
    String message;
}
