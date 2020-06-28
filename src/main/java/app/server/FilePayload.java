package app.server;

import java.io.Serializable;

public record FilePayload(byte[]bytes, boolean finished) implements Serializable {
}
