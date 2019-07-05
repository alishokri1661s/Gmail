package utility;

import java.io.Serializable;

public class Message implements Serializable {
    private String Text;
    private RequestType requestType;

    public Message(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getText() {
        return Text;
    }
}
