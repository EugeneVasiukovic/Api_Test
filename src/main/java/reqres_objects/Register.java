package reqres_objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Register {
    private String email;
    private String password;
}
