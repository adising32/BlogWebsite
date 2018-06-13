package com.justathought;

public class DetailedThought {
	Thought t;
	Author a;
	
	
	public DetailedThought(Thought t, Author a) {
		super();
		this.t = t;
		this.a = a;
	}
	public Thought getT() {
		return t;
	}
	public void setT(Thought t) {
		this.t = t;
	}
	public Author getA() {
		return a;
	}
	public void setA(Author a) {
		this.a = a;
	}
	
}
