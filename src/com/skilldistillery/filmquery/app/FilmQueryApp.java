package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    FilmQueryApp app = new FilmQueryApp();
//    app.test();
    app.launch();
  }

//  private void test()throws SQLException {
//    Film film = db.findFilmById(1);
//    System.out.println(film);
//    Actor actor = db.findActorById(1);
//    System.out.println(actor);
//    List<Actor> actors = db.findActorsByFilmId(5);
//    for (Actor actor2 : actors) {
//		System.out.println(actor2);
//	}
//  }

  private void launch() throws SQLException{
    Scanner input = new Scanner(System.in);
    
    startUserInterface(input);
    
    input.close();
  }

  private void startUserInterface(Scanner input) throws SQLException {
	  do {
	  boolean validMenuSelect = false;
	  boolean validFilmId = false;
	  boolean validActorId = false;
	  int select = 0;
	  
	do {
    System.out.println("Welcome what would you like to search for today?");
    System.out.println("1. Film by ID");
    System.out.println("2. Actor by ID");
    System.out.println("3. Actors by Film ID");
    System.out.println("4. Film by keyword");
    System.out.println("5. Exit");
    try {
    	select = Integer.parseInt(input.nextLine());
    }catch(Exception e) {
    	System.out.println("I'm sorry that was an invalid input. Try again");
    	continue;
    }
    switch (select) {
    case 1:
    		System.out.println("What is the Film ID you would like to search for?");
    		int filmID = 0;
    		do {
    		try {
    	    	filmID = Integer.parseInt(input.nextLine());
    	    	validFilmId = true;
    	    }catch(Exception e) {
    	    	System.out.println("I'm sorry that was an invalid input. Try again");
    	    	continue;
    	    }
    		}while (!validFilmId);
    			Film film = db.findFilmById(filmID);
    	    System.out.println(film);
    	validMenuSelect = true;
    	break;
    case 2:
    	System.out.println("What is the Actor ID you would like to search for?");
		int actorID = 0;
		do {
		try {
	    	actorID = Integer.parseInt(input.nextLine());
	    	validActorId = true;
	    }catch(Exception e) {
	    	System.out.println("I'm sorry that was an invalid input. Try again");
	    	continue;
	    }
		}while (!validActorId);
			Actor actor = db.findActorById(actorID);
	    System.out.println(actor);
    	validMenuSelect = true;
    	break;
    case 3:
    	List<Actor> actors = new ArrayList<>();
    	System.out.println("What is the Film ID you would like to search for?");
		int filmId = 0;
		do {
		try {
	    	filmId = Integer.parseInt(input.nextLine());
	    	validFilmId = true;
	    }catch(Exception e) {
	    	System.out.println("I'm sorry that was an invalid input. Try again");
	    	continue;
	    }
		}while (!validActorId);
			actors = db.findActorsByFilmId(filmId);
			for (Actor actor2 : actors) {
				System.out.println(actor2);
				
			}
    	validMenuSelect = true;
    	break;
    case 4:
    	List<Film> films = new ArrayList<>();
    	System.out.println("What is the keyword you would like to search for?");
		String keyword = input.nextLine();
			films = db.findFilmByKeyword(keyword);
			if(films.size()==0) {
				System.out.println("I'm sorry that search didnt return any results");	
			}
			for (Film film2 : films) {
				System.out.println(film2);
			}

    
    	validMenuSelect = true;
    	break;
    case 5:
    	System.out.println("Goodbye!");
    	System.exit(0);
    default:
    	System.out.println("That doesn't appear to be a valid selection. Try again");
    	validMenuSelect = false;
    }
  }while (!validMenuSelect);
  }while (true);
}
}
