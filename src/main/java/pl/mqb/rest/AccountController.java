package pl.mqb.rest;

import pl.mqb.dao.AccountRepository;
import pl.mqb.model.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;


@Path("/accounts")
public class AccountController {

    private final AccountRepository repository = AccountRepository.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        return Response.ok(Collections.unmodifiableCollection(repository.getAll())).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountById(@PathParam("id") String id) {
        Account account = repository.getById(id);
        if (account == null)
            return Response.noContent().build();

        return Response.ok(account).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewAccount(Account account) {
        repository.addAccount(account);
        return Response.ok(account).build();
    }


}
