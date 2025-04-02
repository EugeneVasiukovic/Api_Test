package reqres_objects;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class UnknownList {

    @Expose
    ArrayList<Unknown> data;
}
