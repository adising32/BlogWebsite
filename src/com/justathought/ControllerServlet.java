package com.justathought;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource(name="jdbc/just_a_thought")
	private DataSource ds;
	private DBUtil DBOb;
    
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			DBOb = new DBUtil(ds);
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//read command
		String theCommand = request.getParameter("command");
				
		//route to appropriate method
		switch(theCommand) {
			case "ADD_AUTHOR"	: addAuthor(request,response); 
								  break;
			case "VIEW_POSTS"   : viewPosts(request,response);
			default             : //go back home
								  break;
		}	
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//read command
		String theCommand = request.getParameter("command");
		
		

		switch(theCommand) {
			case "LOG_IN"           : checkLogInCredentials(request,response);
			  						  break;
		}
	}
	
	
	private void checkLogInCredentials(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Author theAuthor = DBOb.getAuthor(username,password);
		
		if(theAuthor == null) {
			String msg = "Either username or password is wrong";
			request.setAttribute("msg", msg);
			RequestDispatcher rd = request.getRequestDispatcher("/log-in.jsp");
			rd.forward(request,response);
		}else {
			HttpSession session = request.getSession();
			session.setAttribute("LOGGED_IN_USER_ID", theAuthor.getAuthor_id());
			request.setAttribute("command1","LIST_THOUGHTS");
			RequestDispatcher rd = request.getRequestDispatcher("ControllerServlet2");
			rd.forward(request,response);
		}
	}
	
	

	private void addAuthor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("author_name");
		String email = request.getParameter("author_email");
		String pass = request.getParameter("author_password");
		
		Author theAuthor = new Author(name,email,pass);
		int id = DBOb.addAuthor(theAuthor);
		System.out.println(id);
		
		request.setAttribute("ID", id);
		RequestDispatcher rd = request.getRequestDispatcher("/update-image.jsp");
		rd.forward(request, response);
		
	}
	
	private void viewPosts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<?> theList = DBOb.readAllPosts();
		request.setAttribute("POSTS", theList);
		RequestDispatcher rd = request.getRequestDispatcher("/all-thoughts.jsp");
		rd.forward(request,response);
		
	}


}
