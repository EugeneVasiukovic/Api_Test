package reqres_objects;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class LoginList {
    ArrayList<Login> data;
}
