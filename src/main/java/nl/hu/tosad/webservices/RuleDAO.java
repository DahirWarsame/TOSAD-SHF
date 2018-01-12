package nl.hu.tosad.webservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.hu.tosad.domain.*;

public class RuleDAO extends BaseDAO {
    private final String TABLE_NAME = "BRT_DEFINITIONS";

    public RuleDAO() {
    }

    public ArrayList<RuleType> getRules(){
    	ArrayList<RuleType>rulelist=new ArrayList<RuleType>();
        try (Connection conn = super.getConnection()) {
        	conn.createStatement().execute("alter session set current_schema=TOSAD_2017_2C_TEAM2");
            PreparedStatement statement=conn.prepareStatement("SELECT * from brt_definitions");
            statement.executeQuery();
            ResultSet rs = statement.executeQuery();
            
            String name;
            String code;
            String desc;
            RuleType rule;
            
            while (rs.next()){
            	name=rs.getString("name");
            	code=rs.getString("code");
            	desc=rs.getString("description");
            	rule=new RuleType(name,code,desc,"iets");
            	rulelist.add(rule);
            }
            rs.close();
            statement.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rulelist;
    }
}
