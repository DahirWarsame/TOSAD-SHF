package nl.hu.tosad.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import nl.hu.tosad.data.DataInterface;
import nl.hu.tosad.writer.WriterInterface;

@Path("rule")
public class RuleResource {
	WriterInterface writer=new WriterInterface();
	DataInterface data=new DataInterface();
	
	@Path("get/{id}/{lan}")
	@GET
	public String printRule(@PathParam("id") String code, @PathParam("lan") String language) throws Exception {
		writer.print(code, writer.generateCode(data.getRuleByID(code), code, language));
		return "Rule collected!";
	}
}