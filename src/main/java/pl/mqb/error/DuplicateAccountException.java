package pl.mqb.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DuplicateAccountException extends RuntimeException implements ExceptionMapper<DuplicateAccountException> {

    public DuplicateAccountException(String accountId) {
        super("Account with ID:" + accountId + " already exists. Duplicates are not allowed.");
    }

    public DuplicateAccountException() {
        super();
    }

    @Override
    public Response toResponse(DuplicateAccountException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .type("text/plain")
                .build();
    }
}
