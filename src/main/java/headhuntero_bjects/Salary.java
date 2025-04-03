package headhuntero_bjects;


import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Salary {
    @Expose
    int from;
    @Expose
    int to;
    @Expose
    String currency;
}
