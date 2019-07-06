package utility;

import java.io.Serializable;

public enum RequestType implements Serializable {
    login,
    signup,
    message,
    forget_password
}