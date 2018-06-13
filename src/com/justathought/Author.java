package com.justathought;

public class Author {
	private int author_id;
	private String author_name;
	private String author_email;
	private String author_password;
	private int author_rating;
	private String author_image;
	private int no_of_posts;
	

	public Author(String author_name, String author_email, String author_password) {
		super();
		this.author_name = author_name;
		this.author_email = author_email;
		this.author_password = author_password;
	}



	public Author(int author_id, String author_name, String author_email, String author_password, int author_rating,
			String author_image, int no_of_posts) {
		super();
		this.author_id = author_id;
		this.author_name = author_name;
		this.author_email = author_email;
		this.author_password = author_password;
		this.author_rating = author_rating;
		this.author_image = author_image;
		this.no_of_posts = no_of_posts;
	}



	public int getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getAuthor_email() {
		return author_email;
	}

	public void setAuthor_email(String author_email) {
		this.author_email = author_email;
	}

	public String getAuthor_password() {
		return author_password;
	}

	public void setAuthor_password(String author_password) {
		this.author_password = author_password;
	}

	public int getAuthor_rating() {
		return author_rating;
	}

	public void setAuthor_rating(int author_rating) {
		this.author_rating = author_rating;
	}

	public String getAuthor_image() {
		return author_image;
	}

	public void setAuthor_image(String author_image) {
		this.author_image = author_image;
	}

	public int getNo_of_posts() {
		return no_of_posts;
	}

	public void setNo_of_posts(int no_of_posts) {
		this.no_of_posts = no_of_posts;
	}
	
	
	
}
