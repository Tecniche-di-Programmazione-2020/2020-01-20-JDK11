package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

public List<String> listRoles() {
		
		String sql = "SELECT DISTINCT authorship.role\r\n" + 
				"FROM authorship\r\n" + 
				"ORDER BY role ASC";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(res.getString("role"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

public List<Arco> listEdges(String role) {
	
	String sql = "SELECT t1.artist_id AS a1, t2.artist_id AS a2, COUNT(DISTINCT t1.exhibition_id) AS peso\r\n" + 
			"\r\n" + 
			"FROM (SELECT authorship.artist_id , exhibition_objects.exhibition_id\r\n" + 
			"		FROM authorship ,exhibition_objects\r\n" + 
			"		WHERE authorship.object_id=exhibition_objects.object_id AND authorship.role=?) AS t1,\r\n" + 
			"		\r\n" + 
			"		(SELECT authorship.artist_id , exhibition_objects.exhibition_id\r\n" + 
			"		FROM authorship ,exhibition_objects\r\n" + 
			"		WHERE authorship.object_id=exhibition_objects.object_id AND authorship.role=?) AS t2\r\n" + 
			"WHERE t1.exhibition_id=t2.exhibition_id AND t1.artist_id>t2.artist_id\r\n" + 
			"GROUP BY t1.artist_id,t2.artist_id ORDER BY peso DESC";
	
	List<Arco> result = new ArrayList<>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, role);
		st.setString(2, role);
		ResultSet res = st.executeQuery();
		while (res.next()) {

			
			result.add(new Arco(res.getInt("a1"),res.getInt("a2"),res.getInt("peso")));
		}
		conn.close();
		System.out.println("DAO : Resistuisco lista archi di dimensione: "+result.size());
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
}

public List<Integer> listArtistbyRole(String role) {
	
	String sql = "SELECT DISTINCT artist_id  \r\n" + 
			"		FROM authorship\r\n" + 
			"		WHERE authorship.role=?";
	List<Integer> result = new ArrayList<>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, role);
		ResultSet res = st.executeQuery();
		while (res.next()) {

			
			result.add(res.getInt("artist_id"));
		}
		conn.close();
		System.out.println("DAO : Resistuisco lista artisti filtrata di dimensione: "+result.size());
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
}
	
	
	
}
