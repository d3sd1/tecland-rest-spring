package TecLand.Logger;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import javax.validation.constraints.NotNull;



@Document(collection = "users")
@JsonPropertyOrder({"userId", "name"})
public class Log implements Serializable {

    private static final long serialVersionUID = -7788619177798333712L;

    @Id
    @NotNull
    private long objectId;

    @NotNull
    private String text;

    @NotNull
    private LogLevel level;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }
}
