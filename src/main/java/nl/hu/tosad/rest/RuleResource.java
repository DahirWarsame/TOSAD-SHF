package nl.hu.tosad.rest;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nl.hu.tosad.data.RuleDAO;
import nl.hu.tosad.domain.PLSQLTranslator;
import nl.hu.tosad.domain.Rule;

@Path("rule")
public class RuleResource {
	RuleDAO dao=new RuleDAO();
	
	@Path("getall")
	@GET
	@Produces("application/json")
	public String CountryList() {
		JsonArrayBuilder jab = Json.createArrayBuilder();

		for (Rule r : dao.getAllRules()) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("code", r.getCode());
			jab.add(job);
		}

		JsonArray array = jab.build();

		return (array.toString());
	}
	@Path("get/{id}")
	@GET
	public String CountrybyCode(@PathParam("id") String code) {
		Rule country = dao.getRuleByID(code);
		PLSQLTranslator pl=new PLSQLTranslator();
		System.out.println(pl.generateDemo(country));
		return "Rule collected!";
}}