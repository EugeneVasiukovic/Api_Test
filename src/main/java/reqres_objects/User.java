package reqres_objects;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private UserData data;
    String name;
    String job;
    String id;
    String createdAt;

}
