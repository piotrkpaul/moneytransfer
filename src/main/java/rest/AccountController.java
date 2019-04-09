package rest;

import dao.AccountRepository;
import model.Account;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/accounts")
public class AccountController {

    private final AccountRepository repository = AccountRepository.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        return Response.ok(repository.getAll()).build();
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
        if (repository.create(account) != null)
            return Response.status(Response.Status.CONFLICT)
                    .entity("Account with id " + account.getId() + " already exists.")
                    .build();

        return Response.ok(account).build();
    }


}
