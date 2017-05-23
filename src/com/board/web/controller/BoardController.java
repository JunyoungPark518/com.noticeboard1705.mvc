package com.board.web.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.board.web.domain.ArticleBean;
import com.board.web.service.BoardService;
import com.board.web.serviceImpl.BoardServiceImpl;

@WebServlet("/board.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardService service =BoardServiceImpl.getInstance();
		ArticleBean bean=new ArticleBean(); 
	    String  path=request.getServletPath(),
	    		directory =path.substring(0,path.indexOf(".")),
	    		action=request.getParameter("action"),
	    		foo=request.getParameter("pageNumber"),
	    		pageName=request.getParameter("pageName"),
	    		title="",content="";
	    		if(action==null){action="list";}
	    		if(foo==null){foo="0";}
	    		System.out.println("########### action #########"+action);
	    String view="/WEB-INF/views/"+directory+"/"+action+".jsp";
	        int pageNumber=Integer.parseInt(foo);
	    	int	pagesPerOneBlock=5,
	    		rowsPerOnePage=5,
	    		theNumberOfRows=service.numberOfArticles(),
	    		theNumberOfPages=(theNumberOfRows%rowsPerOnePage==0)?theNumberOfRows/rowsPerOnePage:theNumberOfRows/rowsPerOnePage+1,
	    		startPage=pageNumber-((pageNumber-1)%pagesPerOneBlock),
	    		endPage=((startPage+rowsPerOnePage-1) < theNumberOfPages)?startPage+pagesPerOneBlock-1:theNumberOfPages,
	    		startRow=(pageNumber-1)*rowsPerOnePage+1,
	    		endRow= pageNumber*rowsPerOnePage,
	    	    prevBlock= startPage-pagesPerOneBlock,
	    	    nextBlock=startPage+pagesPerOneBlock;
		HashMap<String, Object> param=new HashMap<>();
		switch (action) {
		case "move":	
			System.out.println("###########move#########");
			view="/WEB-INF/views/"+directory+"/"+pageName+".jsp";
			request.getRequestDispatcher(view).forward(request, response);
		case "list":
			List<ArticleBean> list= service.findArticles(param);
			System.out.println("controller list"+list.get(0));
			System.out.println("controller list" +list);
			request.setAttribute("list", list);
			request.setAttribute("prevBlock", prevBlock);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("pageNumber", pageNumber);
			request.getRequestDispatcher(view).forward(request, response);
			break;
		
		case "write":
			String writer=request.getParameter("writer");
			String regiDate=request.getParameter("regiDate");
			title=request.getParameter("title");
			content=request.getParameter("content");
			bean.setWriter(writer);
			bean.setRegiDate(regiDate);
			bean.setTitle(title);
			bean.setContent(content);
			service.writeArticle(bean);
			request.setAttribute("title", bean.getTitle());
			request.setAttribute("content", bean.getContent());
			request.setAttribute("writer", bean.getWriter());
			request.setAttribute("regiDate", bean.getRegiDate());
			request.getRequestDispatcher(view).forward(request, response);
			
		case "update":
			title=request.getParameter("title");
			content=request.getParameter("content");
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
			String deleteObect=request.getParameter("deleteObject");
			bean.setSeqNo(deleteObect);
			bean=service.deleteArticle(bean);
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
