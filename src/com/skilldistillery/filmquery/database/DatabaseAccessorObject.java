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
			
			String sql = "select film.id, title, description, release_year, language.name, rental_duration, rental_rate, length, replacement_cost, rating, special_features from film join language on film.language_id=language.id where film.id = ?";
			try (
				Connection conn = DriverManager.getConnection(URL, user, pwd);
				PreparedStatement pst = conn.prepareStatement(sql);
				){
				pst.setString(1, ""+filmId);
				
				ResultSet rs = pst.executeQuery();
				
				if (rs.next()) {
					Film film = new Film(rs.getInt("film.id"),rs.getString("title"),rs.getString("description"),rs.getInt("release_year"),rs.getString("language.name"),rs.getInt("rental_duration"),rs.getDouble("rental_rate"),rs.getInt("length"), rs.getDouble("replacement_cost"),rs.getString("rating"),rs.getString("special_features") );
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
		
		String sql = "  select film_id, actor_id, first_name, last_name from actor join film_actor on actor.id = film_actor.actor_id join film on film_actor.film_id = film.id where film.id = ?";
		try (
			Connection conn = DriverManager.getConnection(URL, user, pwd);
			PreparedStatement pst = conn.prepareStatement(sql);
			){
			pst.setString(1, ""+filmId);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				actors.add(new Actor(rs.getInt("actor_id"),rs.getString("first_name"),rs.getString("last_name")));
			
			}
		}
	
		return actors;
	}
	public List<Film> findFilmByKeyword(String keyword)throws SQLException{
		String user= "student";
		String pwd= "student";
		List<Film> films= new ArrayList<>();
		
		String sql = "select film.id, title, description, release_year, language.name, rental_duration, rental_rate, length, replacement_cost, rating, special_features from film join language on film.language_id=language.id where title like ? or description like ?";
		try (
			Connection conn = DriverManager.getConnection(URL, user, pwd);
			PreparedStatement pst = conn.prepareStatement(sql);
			){
			pst.setString(1, "%"+keyword+"%");
			pst.setString(2, "%"+keyword+"%");
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				films.add(new Film(rs.getInt("film.id"),rs.getString("title"),rs.getString("description"),rs.getInt("release_year"),rs.getString("language.name"),rs.getInt("rental_duration"),rs.getDouble("rental_rate"),rs.getInt("length"), rs.getDouble("replacement_cost"),rs.getString("rating"),rs.getString("special_features") ));
			
			}
		}
	
		return films;
	}

static {
	try {
		Class.forName("com.mysql.jdbc.Driver");
	}catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
}
}
