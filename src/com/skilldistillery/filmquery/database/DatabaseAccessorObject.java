package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	public static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	@Override
	public Film findFilmById(int filmId)throws SQLException {
			String user= "student";
			String pwd= "student";
			
			String sql = "select id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features from film where id = ?";
			try (
				Connection conn = DriverManager.getConnection(URL, user, pwd);
				PreparedStatement pst = conn.prepareStatement(sql);
				){
				pst.setString(1, ""+filmId);
				
				ResultSet rs = pst.executeQuery();
				
				if (rs.next()) {
					Film film = new Film(rs.getInt("id"),rs.getString("title"),rs.getString("description"),rs.getInt("release_year"),rs.getInt("language_id"),rs.getInt("rental_duration"),rs.getDouble("rental_rate"),rs.getInt("length"), rs.getDouble("replacement_cost"),rs.getString("rating"),rs.getString("special_features") );
					return film;
				}else {
					return null;
			}
		}
		
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		String user= "student";
		String pwd= "student";
		
		String sql = "select id, first_name, last_name from actor where id = ?";
		try (
			Connection conn = DriverManager.getConnection(URL, user, pwd);
			PreparedStatement pst = conn.prepareStatement(sql);
			){
			pst.setString(1, ""+actorId);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				Actor actor = new Actor(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"));
				return actor;
			}else {
				return null;
		}
	}
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		String user= "student";
		String pwd= "student";
		List<Actor> actors= new ArrayList<>();
		
		String sql = "select id, first_name, last_name from actor where id = ?";
		try (
			Connection conn = DriverManager.getConnection(URL, user, pwd);
			PreparedStatement pst = conn.prepareStatement(sql);
			){
			pst.setString(1, ""+filmId);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				actors.add(new Actor(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name")));
			
			}
		}
	
		return actors;
	}

static {
	try {
		Class.forName("com.mysql.jdbc.Driver");
	}catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
}
}
