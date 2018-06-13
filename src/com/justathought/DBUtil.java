package com.justathought;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class DBUtil {
	private DataSource ds;
	
	public DBUtil(DataSource ds) {
		this.ds = ds;
	}
	
	private void close(Connection myConn, Statement myst, ResultSet rs) {
		
		try {
			//close statement and resultset
			if(myst != null) {
				myst.close();
			}
			if(rs != null) {
				rs.close();
			}
			//close myConn.....doesnt really close ...just puts back to connection pool 
			if(myConn != null) {
				myConn.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public int addAuthor(Author theAuthor) {
		Connection myConn = null;
		PreparedStatement myst = null;
		ResultSet rs = null;
		int id=1;
		
		try {
			//get connection
			myConn = ds.getConnection();
			
			//create query for inserting author 
			String sql = "insert into author(author_name,author_email,author_password) values(?,?,?)";
			myst = myConn.prepareStatement(sql);
			
			//set param values
			myst.setString(1,theAuthor.getAuthor_name());
			myst.setString(2,theAuthor.getAuthor_email());
			myst.setString(3,theAuthor.getAuthor_password());
			
			//execute Query
			myst.execute();
			myst.close();
			
			//create query for getting id
			String sql1 = "select author_id from author where author_email=?";
			myst = myConn.prepareStatement(sql1);
			myst.setString(1, theAuthor.getAuthor_email());
			
			//execute
			rs = myst.executeQuery();
			rs.next();
			id = rs.getInt("author_id");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
		return id; 
	}
 
		
	public void setAuthorImage(int ID, String path){
		Connection myConn = null;
		PreparedStatement myst= null;
		
		try {
			myConn = ds.getConnection();
			System.out.print(ID);
			String sql = "update author set author_image=? where author_id=?";
			myst = myConn.prepareStatement(sql);
			String thePath="uploaded-images"+File.separator+path;
			myst.setString(1,thePath);
			myst.setInt(2, ID);
			myst.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
	}

	public void addThought(Thought theThought) {
		Connection myConn = null;
		PreparedStatement myst = null;
		
		try {
			//get connection
			myConn = ds.getConnection();
			
			//create query for inserting author 
			String sql = "insert into thought(thought_title,thought_text,author_id) values(?,?,?)";
			myst = myConn.prepareStatement(sql);
			
			//set param values
			myst.setString(1,theThought.getTitle());
			myst.setString(2,theThought.getText());
			myst.setInt(3,theThought.getAid());
			
			//execute Query
			myst.execute();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		} 
	}

	public Author getAuthor(String username, String password) {
		Connection myConn = null;
		PreparedStatement myst = null;
		ResultSet rs = null;
		Author theAuthor=null;
		try {
			//get connection
			myConn = ds.getConnection();
			
			//create query for inserting author 
			String sql = "select * from author where author_email=? and author_password=?";
			myst = myConn.prepareStatement(sql);
			
			//set param values
			myst.setString(1,username);
			myst.setString(2,password);
			
			//execute Query
			rs  = myst.executeQuery();
			rs.next();
			int id = rs.getInt("author_id");
			String name = rs.getString("author_name");
			String em = rs.getString("author_email");
			String pass = rs.getString("author_password");
			int rat = rs.getInt("author_rating");
			String img = rs.getString("author_image");
			int posts = rs.getInt("no_of_posts");
			
			theAuthor = new Author(id,name,em,pass,rat,img,posts);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
		return theAuthor;
	}

	public List readMind(int id) {
		List<Thought> theList = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myst = null;
		ResultSet rs = null;
		
		try {
			myConn = ds.getConnection(); 
			String sql = "select * from thought where author_id=?";
			myst = myConn.prepareStatement(sql);
			myst.setInt(1,id);
			rs = myst.executeQuery();
			
			while(rs.next()) {
				int tid = rs.getInt("thought_id");
				String title = rs.getString("thought_title");
				String text = rs.getString("thought_text");
				String imgUrl = rs.getString("thought_image");
				
				Thought theThought = new Thought(tid,title,text,id,imgUrl);
				theList.add(theThought);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
		return theList;
	}

	public Author getAuthor(int id) {
		Connection myConn = null;
		PreparedStatement myst = null;
		ResultSet rs = null;
		Author theAuthor=null;
		
		try {
			myConn = ds.getConnection(); 
			String sql = "select * from author where author_id=?";
			myst = myConn.prepareStatement(sql);
			myst.setInt(1,id);
			
			rs = myst.executeQuery();
			rs.next();
			
			String name = rs.getString("author_name");
			String email = rs.getString("author_email");
			String pass = rs.getString("author_password");
			int rating = rs.getInt("author_rating");
			String image = rs.getString("author_image");
			int posts = rs.getInt("no_of_posts");
			
			theAuthor = new Author(id,name,email,pass,rating,image,posts);	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
		return theAuthor;
	}

	public void updateAuthor(Author theAuthor) {
		Connection myConn = null;
		PreparedStatement myst = null;
		
		try {
			myConn = ds.getConnection(); 
			String sql = "update author set author_name=?, author_email=?, author_password=? where author_id=?";
			myst = myConn.prepareStatement(sql);
			myst.setString(1,theAuthor.getAuthor_name());
			myst.setString(2,theAuthor.getAuthor_email());
			myst.setString(3,theAuthor.getAuthor_password());
			myst.setInt(4,theAuthor.getAuthor_id());
			
			myst.execute();	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
	}

	public Thought getThought(int aid, int tid) {
		Thought theThought = null;
		Connection myConn = null;
		PreparedStatement myst = null;
		ResultSet rs = null;
		
		try {
			myConn = ds.getConnection(); 
			String sql = "select * from thought where thought_id=?";
			myst = myConn.prepareStatement(sql);
			myst.setInt(1,tid);
			
			rs = myst.executeQuery();
			rs.next();
			//rs.next();
			String title = rs.getString("thought_title");
			String text = rs.getString("thought_text");
			
			theThought = new Thought(tid,title,text,aid);	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
		return theThought;
	}

	public void updateThought(Thought theThought) {
		Connection myConn = null;
		PreparedStatement myst = null;
		
		try {
			myConn = ds.getConnection(); 
			String sql = "update thought set thought_title=?, thought_text=? where thought_id=?";
			myst = myConn.prepareStatement(sql);
			myst.setString(1, theThought.getTitle());
			myst.setString(2,theThought.getText());
			myst.setInt(3,theThought.getId());
			myst.execute();	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
		
	}

	public void deleteThought(int id) {
		Connection myConn = null;
		PreparedStatement myst = null;
		
		try {
			myConn = ds.getConnection(); 
			String sql = "delete from thought where thought_id=?";
			myst = myConn.prepareStatement(sql);
			myst.setInt(1,id);
			myst.execute();	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
	}

	public String getAuthorImage(int id) {
		String url=null;
		Connection myConn = null;
		PreparedStatement myst = null;
		ResultSet rs = null;
		
		try {
			myConn = ds.getConnection(); 
			String sql = "select author_image from author where author_id=?";
			myst = myConn.prepareStatement(sql);
			myst.setInt(1,id);
			
			rs = myst.executeQuery();
			rs.next();
			url = rs.getString("author_image");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
		return url;
	}

	
	public void setThoughtImage(int id, String substring) {
		Connection myConn = null;
		PreparedStatement myst= null;
		System.out.println("xoxo");
		try {
			myConn = ds.getConnection();
			String sql = "update thought set thought_image=? where thought_id=?";
			myst = myConn.prepareStatement(sql);
			String thePath="uploaded-images"+File.separator+substring;
			myst.setString(1,thePath);
			myst.setInt(2, id);
			myst.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
	}

	public List<?> readAllPosts() {
		List<DetailedThought> theList = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myst = null;
		ResultSet rs = null;
		
		try {
			myConn = ds.getConnection(); 
			String sql = "select * from thought";
			myst = myConn.prepareStatement(sql);
			rs = myst.executeQuery();
			
			while(rs.next()) {
				int tid = rs.getInt("thought_id");
				String title = rs.getString("thought_title");
				String text = rs.getString("thought_text");
				int aid = rs.getInt("author_id");
				String imgUrl = rs.getString("thought_image");
				
				Thought theThought = new Thought(tid,title,text,aid,imgUrl);
				Author theAuthor = this.getAuthor(aid);
				DetailedThought theDetailedThought = new DetailedThought(theThought, theAuthor);
				theList.add(theDetailedThought);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(myConn,myst,null);
		}
		return theList;
	}
	
}
