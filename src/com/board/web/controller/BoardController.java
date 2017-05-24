package com.board.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.disk.*;
import org.apache.tomcat.util.http.fileupload.servlet.*;
import org.apache.tomcat.util.http.fileupload.util.*;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;


import com.board.web.domain.ArticleBean;
import com.board.web.service.BoardService;
import com.board.web.serviceImpl.BoardServiceImpl;

import org.apache.commons.fileupload.*;
/**
UPLOAD_DIRECTORY: name of the directory on the server where upload file will be stored. 
					The directory is chosen to be relative to the web application’s directory.
THRESHOLD_SIZE: file that has size less than this threshold value will be saved into memory. 
					If the size is greater than this value, it will be stored on disk, temporarily. 
					The value is measured in bytes.
MAX_FILE_SIZE: specifies the maximum size of an upload file. 
				We define this constant to hold a file up to 40MB.
MAX_REQUEST_SIZE: specifies the maximum size of a HTTP request which contains the upload file and other form’s data, 
					so this constant should be greater than the MAX_FILE_SIZE.
					All sizes are measured in bytes.
 * */
@SuppressWarnings("unused")
@WebServlet("/board.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getServletPath(), directory = path.substring(0, path.indexOf(".")),
				action = request.getParameter("action"), foo = request.getParameter("pageNumber"),
				pageName = request.getParameter("pageName"), title = "", content = "";
		if (action == null) {
			action = "list";
		}
		if (foo == null) {
			foo = "0";
		}
		System.out.println("ACTION :"+action);
		String view = "/WEB-INF/views/" + directory + "/" + action + ".jsp";
		BoardService service = BoardServiceImpl.getInstance();
		ArticleBean bean = new ArticleBean();
		int pageNumber = Integer.parseInt(foo);
		int pagesPerOneBlock = 5, rowsPerOnePage = 5, theNumberOfRows = service.numberOfArticles(),
				theNumberOfPages = (theNumberOfRows % rowsPerOnePage == 0) ? theNumberOfRows / rowsPerOnePage
						: theNumberOfRows / rowsPerOnePage + 1,
				startPage = pageNumber - ((pageNumber - 1) % pagesPerOneBlock),
				endPage = ((startPage + rowsPerOnePage - 1) < theNumberOfPages) ? startPage + pagesPerOneBlock - 1
						: theNumberOfPages,
				startRow = (pageNumber - 1) * rowsPerOnePage + 1, endRow = pageNumber * rowsPerOnePage,
				prevBlock = startPage - pagesPerOneBlock, nextBlock = startPage + pagesPerOneBlock;
		HashMap<String, Object> param = new HashMap<>();
		switch (action) {
		case "move":
			view = "/WEB-INF/views/" + directory + "/" + pageName + ".jsp";
			request.getRequestDispatcher(view).forward(request, response);
			break;
		case "list":
			List<ArticleBean> list = service.findArticles(param);
			System.out.println("controller list" + list.get(0));
			System.out.println("controller list" + list);
			request.setAttribute("list", list);
			request.setAttribute("prevBlock", prevBlock);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("pageNumber", pageNumber);
			request.getRequestDispatcher(view).forward(request, response);
			break;

		case "upload":
			System.out.println("ABCDEFG");
			Map<String, String> map = new HashMap<String, String>();
			view = "/WEB-INF/views/" + directory + "/" + "write" + ".jsp";
			System.out.println("WRITE ENTER ServletContext :"+getServletContext());
			System.out.println("WRITE ENTER RealPath :"+getServletContext().getRealPath(""));
			String realPath="C:\\Users\\hb2000\\JavaProjects\\eclipse4class14\\workspace\\noticeboard1705mvc\\WebContent\\resources\\upload\\";
			// checks if the request actually contains upload file
	        if (!ServletFileUpload.isMultipartContent(request)) {
	        	System.out.println("ServletFileUpload is Null");
	            return;
	        }
	        System.out.println("ServletFileUpload is Not Null");  
	        // configures upload settings
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(THRESHOLD_SIZE);
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
	         
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        upload.setFileSizeMax(MAX_FILE_SIZE);
	        upload.setSizeMax(MAX_REQUEST_SIZE);
	         
	        // constructs the directory path to store upload file
	     //   String uploadPath = getServletContext().getRealPath("")+ File.separator + UPLOAD_DIRECTORY;
	        // creates the directory if it does not exist
	        System.out.println("uploadPath :"+realPath);
	        File uploadDir = new File(realPath);
	        if (!uploadDir.exists()) {
	        	System.out.println("!uploadDir.exists()");
	            uploadDir.mkdir();
	        }
	        System.out.println("try before!!!!!!!!!");
	        try {
	        	System.out.println("try after!!!!!!!!!");
	    
	        	List<FileItem> items = upload.parseRequest(new ServletRequestContext(request)); 
	            System.out.println("List<?> items"+items);
	            Iterator<FileItem> iter = items.iterator();
	            System.out.println("Iterator<?> iter");
	            // iterates over form's fields
	            System.out.println("items.iterator() : iterator");
	            while (iter.hasNext()) {
	            	System.out.println("########while inner iter.hasNext()");
	                FileItem item = (FileItem) iter.next();
	                // processes only fields that are not form fields
	                if (!item.isFormField()) {
	                	map.put(item.getFieldName(), item.getString());
	                 //   String fileName = new File(item.getName()).getName();
	                 //   String filePath = realPath + File.separator + fileName;
	                 //   String filePath = realPath + fileName;
	                 //   File storeFile = new File(filePath);
	                     
	                    // saves the file on disk
	                  //  item.write(storeFile);
	                } else if (item.getFieldName().startsWith("file_workflow")) {

	                	map.put(item.getFieldName(), new String(item.get()));

	    				// uploaded file
	    			} else {

	    				// encode the uploaded file to base64
	    				//String currentAttachment = new String(Base64.encode(item.get()));

	    				// put the encoded attachment into the map
	    				map.put(item.getFieldName(), "test");
	    			}
	            }
	          //  request.setAttribute("message", "Upload has been done successfully!");
	        } catch (Exception ex) {
	        	System.out.println("fail");
	          //  request.setAttribute("message", "There was an error: " + ex.getMessage());
	        }
	     //   getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
	        System.out.println("view before@@@@@@@@");

			request.getRequestDispatcher(view).forward(request, response);
			break;
		case "update":
			title = request.getParameter("title");
			content = request.getParameter("content");
			bean.setSeqNo("42");
			bean.setTitle(title);
			bean.setContent(content);
			service.updateArticle(bean);
			request.setAttribute("title", bean.getTitle());
			request.setAttribute("content", bean.getContent());
			request.getRequestDispatcher(view).forward(request, response);
			break;

		case "delete":
			System.out.println("delete entered form controller");
			String deleteObect = request.getParameter("deleteObject");
			bean.setSeqNo(deleteObect);
			bean = service.deleteArticle(bean);
			System.out.println("삭제 완료");
			request.setAttribute("title", bean.getTitle());
			request.setAttribute("content", bean.getContent());
			request.setAttribute("writer", bean.getWriter());
			request.setAttribute("regiDate", bean.getRegiDate());
			request.getRequestDispatcher(view).forward(request, response);
			break;

		}
	}
}
