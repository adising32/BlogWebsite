package com.justathought;

public class Thought {
	private int id;
	private String title;
	private String text;
	private int aid;
	private String imgUrl;
	
	
	

	public Thought(int id, String title, String text, int aid) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.aid = aid;
	}
	
	


	public Thought(int id, String title, String text, int aid, String imgUrl) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.aid = aid;
		this.imgUrl = imgUrl;
	}




	public Thought(String title, String text, int aid) {
		super();
		this.title = title;
		this.text = text;
		this.aid = aid;
	}




	public String getImgUrl() {
		return imgUrl;
	}




	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}




	public int getAid() {
		return aid;
	}




	public void setAid(int aid) {
		this.aid = aid;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}
	
}
