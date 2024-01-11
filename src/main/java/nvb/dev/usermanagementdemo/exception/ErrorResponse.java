package nvb.dev.usermanagementdemo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm:ss")
    private LocalDateTime timeStamp;
    private List<String> messages;

    public ErrorResponse(List<String> messages) {
        this.timeStamp = LocalDateTime.now();
        this.messages = messages;
    }
}
