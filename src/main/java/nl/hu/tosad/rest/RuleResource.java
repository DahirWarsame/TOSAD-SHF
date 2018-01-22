package nl.hu.tosad.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import nl.hu.tosad.data.DataInterface;
import nl.hu.tosad.domain.DomainInterface;

@Path("rule")
public class RuleResource {
	DomainInterface domain=new DomainInterface();
	DataInterface data=new DataInterface();
	
	@Path("get/{id}+{lan}")
	@GET
	public String printRule(@PathParam("id") String code, @PathParam("lan") String language) throws Exception {
		domain.printRule(data.getRuleByID(code),language);
		return "Rule collected!";
	}
}