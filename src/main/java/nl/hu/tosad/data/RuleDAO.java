package nl.hu.tosad.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.hu.tosad.domain.CompareOperator;
import nl.hu.tosad.domain.ListOperator;
import nl.hu.tosad.domain.Other;
import nl.hu.tosad.domain.RangeOperator;
import nl.hu.tosad.domain.Rule;
import nl.hu.tosad.domain.RuleFunction;
import nl.hu.tosad.domain.RuleType;

public class RuleDAO extends BaseDAO {

    public RuleDAO() {
    }

    /**
     * Made it so that this func retrieves all rules and not rules based on id
     *
     * @return
     */
    public List<Rule> getAllRules() {
        List<Rule> rules = new ArrayList<>();
        try (Connection conn = super.getConnection()) {
            conn.createStatement().execute("alter session set current_schema=TOSAD_2017_2C_TEAM2");
            PreparedStatement statement = conn.prepareStatement("SELECT * from business_rule ");
            statement.executeQuery();
            ResultSet rs = statement.executeQuery();

            String code;
            String table;
            String column;
            String trigger;
            RuleType type = null;
            RuleFunction func = null;
            String category;

            while (rs.next()) {
                code = rs.getString("code");
                table = rs.getString("targettable");
                column = rs.getString("targetcolumn");
                trigger = rs.getString("trigger_event");
                type = getRuleTypebyID(rs.getInt("rule_type_id"));
                func = getRuleFunctionbyID(rs.getInt("Function_type_id"));
                if (rs.getString("category_type_id").equals("1")) {
                    category = "Static";
                } else {
                    category = "Dynamic";
                }
                Rule rule = new Rule(code, type, func, category, table, column, trigger);
                rules.add(rule);
            }
            rs.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rules;
    }
    public Rule getRuleByID(String id) {
        Rule r=null;
        try (Connection conn = super.getConnection()) {
            conn.createStatement().execute("alter session set current_schema=TOSAD_2017_2C_TEAM2");
            PreparedStatement statement = conn.prepareStatement("SELECT * from business_rule where code='"+id+"'");
            statement.executeQuery();
            ResultSet rs = statement.executeQuery();

            String code;
            String table;
            String column;
            String trigger;
            RuleType type = null;
            RuleFunction func = null;
            String category;

            while (rs.next()) {
                code = rs.getString("code");
                table = rs.getString("targettable");
                column = rs.getString("targetcolumn");
                trigger = rs.getString("trigger_event");
                type = getRuleTypebyID(rs.getInt("rule_type_id"));
                func = getRuleFunctionbyID(rs.getInt("Function_type_id"));
                if (rs.getString("category_type_id").equals("1")) {
                    category = "Static";
                } else {
                    category = "Dynamic";
                }
                r = new Rule(code, type, func, category, table, column, trigger);
            }
            rs.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    public RuleFunction getRuleFunctionbyID(int i) {
        RuleFunction func = null;
        try (Connection conn = super.getConnection()) {
            conn.createStatement().execute("alter session set current_schema=TOSAD_2017_2C_TEAM2");
            PreparedStatement statement = conn.prepareStatement("SELECT * from business_rule_Function where id=" + i);
            statement.executeQuery();
            ResultSet rs = statement.executeQuery();

            String id;
            String subtype;
            String type;
            int min;
            int max;
            String value;

            while (rs.next()) {
                id = rs.getString("id");
                subtype = rs.getString("fk_code_operator");
                type = rs.getString("fk_code_function");

                if (type.equals("RNG")) {
                    min = rs.getInt("min_range_value");
                    max = rs.getInt("max_range_value");
                    func = new RangeOperator(id, type, min, max, subtype);
                }
                if (type.equals("LIS")) {
                    value = rs.getString("list_value");
                    func = new ListOperator(id, type, value, subtype);

                }
                if (type.equals("OTH")) {
                    value = rs.getString("other_value");
                    func = new Other(id, type, value);

                }
                if (type.equals("CMP")) {
                    value = rs.getString("compare_value");
                    func = new CompareOperator(id, type, value, subtype);

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
                column=rs.getString("othercolumn");
             	rt=new RuleType(id, code, desc, type, table, column);
           	}
         	if (code.equals("I")){
           		type="Inter-Entity";
           		desc="Inter-Entity Business Rule";
                table=rs.getString("othertable");
           		column=rs.getString("othercolumn");
             	rt=new RuleType(id, code, desc, type, table, column);
           	}
         	if (code.equals("E")){
           		type="Entity";
           		desc="Entity Business Rule";
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

