package com.justathought;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/ControllerServlet2")
public class ControllerServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	@Resource(name="jdbc/just_a_thought")
	private DataSource ds;
	private DBUtil DBOb;
	
	public void init() throws ServletException {
		super.init();
		
		try {
			DBOb = new DBUtil(ds);
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqCommand = (String)request.getAttribute("command1");
		
		//reading from servlet1
		if(reqCommand != null) {
			switch(reqCommand) {
				case "LIST_THOUGHTS" : listThoughts(request,response);
									   break;
			}
		}
		
		//reading from parameters
		String command = request.getParameter("command");
		//System.out.println(command);
		switch(command) {
			case "LIST_THOUGHTS" : listThoughts(request,response);
								   break;
			case "UPDATE_PROFILE": updateProfile(request,response);
								   break;
			case "SAVE_INFO"     : saveInfo(request,response);
								   break;
			case "NEW_POST"      : newPost(request,response);
			   					   break;
			case "EDIT_THOUGHT"  : editThought(request,response);
								   break;
			case "DELETE_THOUGHT": deleteThought(request,response);
			   					   break;
			case "CHANGE_PICTURE": changeThoughtImage(request,response);
			                       break;
			case "LOGOUT"  		 : logout(request,response);
			   					   break;
		}
	}
	

	private void changeThoughtImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		try {
			int idString = (int)session.getAttribute("LOGGED_IN_USER_ID");
			int thoughtId = Integer.parseInt(request.getParameter("id"));
			Thought theThought = DBOb.getThought(idString,thoughtId);
			session.setAttribute("THE_THOUGHT", theThought);
			RequestDispatcher rd = request.getRequestDispatcher("/update-thought-image.jsp");
			rd.forward(request,response);
		}catch(Exception e) {
			PrintWriter out= response.getWriter();
			out.println("SESSION EXPIRED");
		}	
	}

	private void deleteThought(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int thoughtId = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		try {
			int idString = (int)session.getAttribute("LOGGED_IN_USER_ID");
			DBOb.deleteThought(thoughtId);
			listThoughts(request,response);
		}catch(Exception e) {
			PrintWriter out= response.getWriter();
			out.println("SESSION EXPIRED");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//check whether there is file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		FileItem theFile=null;
		

		
		if(isMultipart) {
			try {
				String fileAction=null;
				HttpSession session = request.getSession();
				int ID = (int)session.getAttribute("LOGGED_IN_USER_ID");
				//get the required objects for file upload
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);	
					
				//parse the request to get the list of files
				List<FileItem> files = upload.parseRequest(request);
							
				for(FileItem f : files) {
					//get the file and command and id
					if(!f.isFormField()) {
						theFile = f;
					}else {
						fileAction = f.getString();
					}
				}
				
				if(fileAction.matches("update-author-image")) {
					addAuthorImage(request, response, theFile, ID);
				}
				if(fileAction.matches("update-thought-image")) {
					addThoughtImage(request, response, theFile);
				}
				PrintWriter out = response.getWriter();
				out.println("Succesful");
				return;
			}catch(Exception e) {
					e.printStackTrace();
			}
		}
		
		
		String reqCommand = (String)request.getAttribute("command1");
		
		//reading from servlet1
		if(reqCommand != null) {
			switch(reqCommand) {
				case "LIST_THOUGHTS" : listThoughts(request,response);
									   break;
			}
		}
		String command = request.getParameter("command");
		//System.out.println(command);
		if(command != null) {
			switch(command) {
			case "SAVE_THOUGHT"  : addThought(request, response);
								   break;
			case "SAVE_EDITED_THOUGHT"  : saveThought(request, response);
			   							  break;
			}
		}
	}
	
	private void addThoughtImage(HttpServletRequest request, HttpServletResponse response, FileItem theFile) {
		HttpSession session = request.getSession();
		Thought theThought = (Thought)session.getAttribute("THE_THOUGHT");
		int id = theThought.getId();
		//get path to store images from web.xml
		String filePath = getServletContext().getInitParameter("file-upload"); 	
		try {
			theFile.write(new File(filePath + File.separator+theFile.getName()));
		} catch (Exception e) {
			e.printStackTrace();
			}
		DBOb.setThoughtImage(id,theFile.getName().substring(theFile.getName().lastIndexOf("\\")+1));		
	}

	private void addAuthorImage(HttpServletRequest request, HttpServletResponse response, FileItem theFile, int ID){
		//get path to store images from web.xml
		String filePath = getServletContext().getInitParameter("file-upload"); 	
		try {
			theFile.write(new File(filePath + File.separator+theFile.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBOb.setAuthorImage(ID,theFile.getName().substring(theFile.getName().lastIndexOf("\\")+1));
	}

	private void saveThought(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int thoughtId = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		HttpSession session = request.getSession();
		try {
			int idString = (int)session.getAttribute("LOGGED_IN_USER_ID");
			Thought theThought = new Thought(thoughtId,title,text,idString);
			DBOb.updateThought(theThought);
			listThoughts(request,response);
		}catch(Exception e) {
			PrintWriter out= response.getWriter();
			out.println("SESSION EXPIRED");
		}
	}

	private void editThought(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		try {
			int idString = (int)session.getAttribute("LOGGED_IN_USER_ID");
			int thoughtId = Integer.parseInt(request.getParameter("id"));
			Thought theThought = DBOb.getThought(idString,thoughtId);
			request.setAttribute("THE_THOUGHT", theThought);
			RequestDispatcher rd = request.getRequestDispatcher("/edit-thought.jsp");
			rd.forward(request,response);
		}catch(Exception e) {
			PrintWriter out= response.getWriter();
			out.println("SESSION EXPIRED");
		}
		
	}

	private void newPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		try {
			int idString = (int)session.getAttribute("LOGGED_IN_USER_ID");
			System.out.print(idString);
			RequestDispatcher rd = request.getRequestDispatcher("/add-thought.html");
			rd.forward(request,response);
		}catch(Exception e) {
			PrintWriter out= response.getWriter();
			out.println("SESSION EXPIRED");
		}
		
	}

	private void saveInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("author_name");
		String password = request.getParameter("author_password");
		String email = request.getParameter("author_email");
		
		HttpSession session = request.getSession();
		Author theAuthor = (Author)session.getAttribute("THE_AUTHOR");
		theAuthor.setAuthor_name(name);
		theAuthor.setAuthor_email(email);
		theAuthor.setAuthor_password(password);
		DBOb.updateAuthor(theAuthor);
		
		listThoughts(request,response);
		
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("log-in.jsp");
		
	}

	private void addThought(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		try {
			listThoughts(request,response);
			HttpSession session = request.getSession();
			int idString = (int)session.getAttribute("LOGGED_IN_USER_ID");
			Thought theThought = new Thought(title,text,idString);
			DBOb.addThought(theThought);
		} catch (IOException e) {
			PrintWriter out= response.getWriter();
			out.println("SESSION EXPIRED");
		}
	}

	private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		try {
			int idString = (int)session.getAttribute("LOGGED_IN_USER_ID");
			Author theAuthor = DBOb.getAuthor(idString);
			session.setAttribute("THE_AUTHOR", theAuthor);
			RequestDispatcher rd = request.getRequestDispatcher("/update-author.jsp");
			rd.forward(request,response);
		}catch(Exception e) {
			PrintWriter out= response.getWriter();
			out.println("SESSION EXPIRED");
		}
		
	}

	
	
	private void listThoughts(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		try {
			int idString = (int)session.getAttribute("LOGGED_IN_USER_ID");
			List<?> theList = DBOb.readMind(idString);
			String imageUrl = DBOb.getAuthorImage(idString);
			request.setAttribute("THOUGHTS", theList);
			request.setAttribute("IMG", imageUrl);
			RequestDispatcher rd = request.getRequestDispatcher("/my-mind.jsp");
			rd.forward(request,response);
		}catch(Exception e) {
			PrintWriter out= response.getWriter();
			out.println("SESSION EXPIRED");
		}
		
	}

}
