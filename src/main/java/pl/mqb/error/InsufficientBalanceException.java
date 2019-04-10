package pl.mqb.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InsufficientBalanceException extends RuntimeException implements ExceptionMapper<InsufficientBalanceException> {

    public InsufficientBalanceException() {
        super("Insufficient account balance to perform this operation.");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }

    @Override
    public Response toResponse(InsufficientBalanceException exception) {
        return Response
                .status(Response.Status.CONFLICT)
                .entity(exception.getMessage())
                .type("text/plain")
                .build();
    }
}
