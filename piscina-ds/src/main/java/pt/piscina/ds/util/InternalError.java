package pt.piscina.ds.util;

import java.util.ArrayList;
import javax.ws.rs.core.Response;
import pt.uc.dei.itf.errors.ErrorMessage;

public class InternalError {
	
	public static Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).
			entity(new ArrayList<ErrorMessage>().add(ErrorMessage.FATAL_ERROR)).build();
	
	public InternalError() {
	}

	public static Response getResponse() {
		return response;
	}

	public static void setResponse(Response response) {
		InternalError.response = response;
	}


}
