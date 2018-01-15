package com.cibc.api.training.accounts.handler;

import javax.sql.DataSource;

import org.openlegacy.core.rpc.RpcSession;
import org.openlegacy.utils.ActionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cibc.api.training.accounts.provider.OLProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfrpc.openlegacy.Itemde;
import com.mfrpc.openlegacy.Itemde.Dfhcommarea;
import com.networknt.config.Config;
import com.networknt.service.SingletonServiceFactory;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
* Class implements the retrieval of account information, based on customer ID.
*
* CIBC Reference Training Materials - API Foundation - 2017
*/
public class AccountsIdGetHandler implements HttpHandler {
    // set up the logger
    static final Logger logger = LoggerFactory.getLogger(AccountsIdGetHandler.class);

    // Access a configured DataSource; retrieve database connections from this DataSource
    private static final DataSource ds = (DataSource) SingletonServiceFactory.getBean(DataSource.class);

    // Get a Jackson JSON Object Mapper, usable for object serialization
    private static final ObjectMapper mapper = Config.getInstance().getMapper();

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
    	// Get input from query parameter - id
    	Integer itemId = Integer.valueOf(exchange.getQueryParameters().get("id").getFirst());
    	
    	// Get Mainframe RPC session from SDK project
    	RpcSession mfRpcRpcSession = OLProvider.INSTANCE.getApplicationContext().getBean(org.openlegacy.core.rpc.RpcSession.class);
    	
    	// Set input parameter
    	Itemde itemde = new Itemde();
    	Dfhcommarea dfhcommarea = new Dfhcommarea();
    	dfhcommarea.setItemNum(itemId);
    	itemde.setDfhcommarea(dfhcommarea);
    	
    	// Execute on mainframe
    	itemde = mfRpcRpcSession.doAction(ActionUtil.getRpcAction(Itemde.class), itemde);
    	
        // serialize the response object and set in the response
    	exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
    	exchange.setStatusCode(200);
        exchange.getResponseSender().send(new JsonObject(Json.encode(itemde.getDfhcommarea().getItemRecord())).encodePrettily());
    }
}
