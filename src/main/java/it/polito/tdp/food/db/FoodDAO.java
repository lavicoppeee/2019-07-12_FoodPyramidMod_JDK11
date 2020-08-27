package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDAO {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}

	public List<Food> getVertici(int porzione) {
		String sql = "SELECT f.`food_code` as id, f.`display_name` as name " + 
				"FROM food as f, portion as p " + 
				"WHERE f.`food_code`=p.`food_code` " + 
				"GROUP BY id " + 
				"HAVING count(DISTINCT p.`portion_id`)= ? " + 
				"ORDER BY name ASC " ;
		
		List<Food> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, porzione);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Food f=new Food(res.getInt("food_code"),res.getString("display_name"));
					list.add(f);
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

	public List<Arco> getArco() {
		String sql = "SELECT f1.`food_code` as id1, f1.`display_name` as name1, f2.`food_code` as id2, f2.`display_name` as name2, AVG (c1.`condiment_calories`) as peso " + 
				"FROM food as f1, food as f2, food_condiment as fc1,  food_condiment as fc2, condiment as c1, condiment as c2 " + 
				"WHERE f1.`food_code`<>f2.`food_code` and fc1.`food_code`=f1.`food_code`and fc2.`food_code`=f2.`food_code`and fc1.`condiment_code`=c1.`condiment_code`and fc2.`condiment_code`=c2.`condiment_code` and c1.`condiment_code`=c2.`condiment_code` " + 
				"GROUP BY id1, id2 ";
		
		List<Arco> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
			
					Food f1=new Food(res.getInt("id1"), res.getString("name1"));
					Food f2=new Food(res.getInt("id2"), res.getString("name2"));
					
					Arco a = new Arco(f1, f2, res.getDouble("peso"));
					list.add(a);
					
				
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
	
	
}
