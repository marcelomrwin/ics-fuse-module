package com.redhat.sre.rest;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/convite")
public class ConviteApi {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Inject
    protected StatusWebsocket ws;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPost(JsonObject json) {

	try {
	    logger.info("SRE Received "+InetAddress.getLocalHost().getHostAddress()+" - " + json.toString());
	    
	    ws.notifyClients(json);
	    
	    return Response.status(Status.CREATED).entity("SRE RESPONSE " + sdf.format(new Date())).build();
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    return Response.serverError().entity(e.getMessage()).build();
	}

    }

}
