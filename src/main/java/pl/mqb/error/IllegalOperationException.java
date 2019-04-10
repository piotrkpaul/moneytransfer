package pl.mqb.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalOperationException extends RuntimeException implements ExceptionMapper<IllegalOperationException> {

    public IllegalOperationException() {
        super("Operation can't be performed.");
    }

    public IllegalOperationException(String message) {
        super(message);
    }

    @Override
    public Response toResponse(IllegalOperationException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .type("text/plain")
                .build();
    }
}

