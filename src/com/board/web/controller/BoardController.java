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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.board.web.domain.ArticleBean;
import com.board.web.service.BoardService;
import com.board.web.serviceImpl.BoardServiceImpl;
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
*/
@SuppressWarnings("unused")
@WebServlet("/board.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "upload";
	private static final String VIEW_DIRECTORY   = "/WEB-INF/views/";
	private static final String SAVE_PATH   = "C:\\Users\\hb2000\\JavaProjects\\eclipse4class14\\workspace\\noticeboard1705mvc\\WebContent\\resources\\upload\\";
	private static final String SAVE_DIRECTORY   = "upload"; 
	private static final int MAX_MEMORY_SIZE     = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath(), directory = path.substring(0, path.indexOf(".")),
				action = request.getParameter("action"), foo = request.getParameter("pageNumber"),
				pageName = request.getParameter("pageName"), title = "", content = "";
		action=(action == null) ? "list" : action;
		foo=(foo==null)?"0":foo;	
		String view = VIEW_DIRECTORY + directory + "/" + action + ".jsp";
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
			request
			.getRequestDispatcher(VIEW_DIRECTORY + directory + "/" + pageName + ".jsp")
			.forward(request, response);
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
			request
			.getRequestDispatcher(VIEW_DIRECTORY + directory + "/list.jsp")
			.forward(request, response);
			break;
		case "write": 
			request
			.getRequestDispatcher(VIEW_DIRECTORY + directory + "/upload.jsp")
			.forward(request, response);
			break;
		case "upload":
	        String savePath=SAVE_PATH;
	        System.out.println("WRITE ENTER RealPath :"+savePath);
			if (!ServletFileUpload.isMultipartContent(request)) {
	        	System.out.println("ServletFileUpload is Null");
	            return;
	        }
	        FileItemFactory factory = new DiskFileItemFactory();
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        upload.setFileSizeMax(MAX_FILE_SIZE);
	        upload.setSizeMax(MAX_REQUEST_SIZE);
	        List<FileItem> items=null;
	        try {
	        	File file ;
	        	items = upload.parseRequest( request); 
	            Iterator<FileItem> iter = items.iterator();
	            while (iter.hasNext()) {
	                FileItem item = (FileItem) iter.next();
	                if (!item.isFormField()) {
	                        String fieldName = item.getFieldName();
	                        String fileName = item.getName();
	                        boolean isInMemory = item.isInMemory();
	                        long sizeInBytes = item.getSize();
	                        file = new File( savePath + fileName) ;
	                        item.write( file ) ;
	    			} else {
	    				System.out.println("item.isFormField() error");
	    			}
	            }
	        } catch (Exception ex) {
	        	System.out.println("fail");
	        }
	        request
			.getRequestDispatcher(VIEW_DIRECTORY + directory + "/detail.jsp")
			.forward(request, response);
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
			request
			.getRequestDispatcher(VIEW_DIRECTORY + directory + "/update.jsp")
			.forward(request, response);
			break;

		case "delete":
			String deleteObect = request.getParameter("deleteObject");
			bean.setSeqNo(deleteObect);
			bean = service.deleteArticle(bean);
			request.setAttribute("title", bean.getTitle());
			request.setAttribute("content", bean.getContent());
			request.setAttribute("writer", bean.getWriter());
			request.setAttribute("regiDate", bean.getRegiDate());
			request
			.getRequestDispatcher(VIEW_DIRECTORY + directory + "/list.jsp")
			.forward(request, response);
			break;
		case "detail":
			request
			.getRequestDispatcher(VIEW_DIRECTORY + directory + "/detail.jsp")
			.forward(request, response);
			break;	
		}
	}
}
