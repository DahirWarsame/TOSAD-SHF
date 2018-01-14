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

    public Rule getRulebyID(int i){
    	Rule rule=null;
        try (Connection conn = super.getConnection()) {
        	conn.createStatement().execute("alter session set current_schema=TOSAD_2017_2C_TEAM2");
            PreparedStatement statement=conn.prepareStatement("SELECT * from business_rule where code="+i);
            statement.executeQuery();
            ResultSet rs = statement.executeQuery();
            
            String code;
            String table;
            String column;
            String trigger;
            RuleType type=null;
            RuleFunction func=null;
            String category;
            
            while (rs.next()){
            	code=rs.getString("code");
            	table=rs.getString("targettable");
            	column=rs.getString("targetcolumn");
            	trigger=rs.getString("trigger_event");
            	type=getRuleTypebyID(rs.getInt("rule_type_id"));
            	func=getRuleFunctionbyID(rs.getInt("Function_type_id"));
            	if (rs.getString("category_type_id").equals("1")){
            		category="Static";
            	}
            	else{
            		category="Dynamic";
            	}
            	rule=new Rule(code, type, func, category,table, column, trigger);
            }
            rs.close();
            statement.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rule;
    }
    public RuleFunction getRuleFunctionbyID(int i){
    	 RuleFunction func=null;
        try (Connection conn = super.getConnection()) {
        	conn.createStatement().execute("alter session set current_schema=TOSAD_2017_2C_TEAM2");
            PreparedStatement statement=conn.prepareStatement("SELECT * from business_rule_Function where id="+i);
            statement.executeQuery();
            ResultSet rs = statement.executeQuery();
            
            String id;
            String subtype;
            String type;
            int min;
            int max;
            String value;
            
            while (rs.next()){
            	id=rs.getString("id");
            	subtype=rs.getString("fk_code_operator");
            	type=rs.getString("fk_code_function");
            	if (type.equals("RNG")){
            		min=rs.getInt("min_range_value");
            		max=rs.getInt("max_range_value");
            		func=new RangeOperator(id, min, max, subtype);
            	}
            	if (type.equals("LIS")){
            		value=rs.getString("list_value");
            		func=new ListOperator(id, value, subtype);
            	}
            	if (type.equals("OTH")){
            		value=rs.getString("other_value");
            		func=new Other(id, value);
            	}
            	if (type.equals("CMP")){
            		value=rs.getString("compare_value");
            		func=new CompareOperator(id, value, subtype);
            	}
            }
            rs.close();
            statement.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return func;
    }
    public RuleType getRuleTypebyID(int i){
   	 RuleType rt=null;
       try (Connection conn = super.getConnection()) {
       	conn.createStatement().execute("alter session set current_schema=TOSAD_2017_2C_TEAM2");
           PreparedStatement statement=conn.prepareStatement("SELECT * from business_rule_Type where id="+i);
           statement.executeQuery();
           ResultSet rs = statement.executeQuery();
           
           String id;
           String code;
           String type;
           String desc;
           String table="";
           String column="";
           
           while (rs.next()){
           	id=rs.getString("id");
           	code=rs.getString("code");
           	if (code.equals("A")){
           		type="Attribute";
           		desc="Attribute Business Rule";
             	rt=new RuleType(id, code, desc, type, table, column);
           	}
         	if (code.equals("T")){
           		type="Tuple";
           		desc="Tuple Business Rule";
             	rt=new RuleType(id, code, desc, type, table, column);
           	}
         	if (code.equals("I")){
           		type="Inter-Entity";
           		desc="Inter-Entity Business Rule";
             	rt=new RuleType(id, code, desc, type, table, column);
           	}
         	if (code.equals("E")){
           		type="Entity";
           		desc="Entity Business Rule";
           		table=rs.getString("othertable");
           		column=rs.getString("othercolumn");
             	rt=new RuleType(id, code, desc, type, table, column);
           	}
         	if (code.equals("M")){
           		type="Modify";
           		desc="Modify Business Rule";
             	rt=new RuleType(id, code, desc, type, table, column);
           	}
           }
           rs.close();
           statement.close();
           conn.close();
           
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return rt;
   }
}

