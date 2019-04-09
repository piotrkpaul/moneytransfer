package pl.mqb.rest;

import pl.mqb.model.MoneyTransfer;
import pl.mqb.service.TransactionService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transactions")
public class TransactionController {

    private final TransactionService transactionService = TransactionService.INSTANCE;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitMoneyTransfer(MoneyTransfer trx) {
        transactionService.transfer(trx);
        return Response.ok().build();
    }

}
