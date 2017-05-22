package com.board.web.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	    String 	pageName=request.getParameter("pageName"),
	    		path=request.getServletPath(),
	    		directory =path.substring(0,path.indexOf(".")),
	    		action=request.getParameter("action"),
	    		view="/WEB-INF/views/"+directory+"/"+pageName+".jsp",
	    		title="",
	    		content="";
	        int pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
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
	    	System.out.println("controller theNumberOfRows"+theNumberOfRows);
	    	System.out.println("controller theNumberOfPages "+theNumberOfPages);
	    	System.out.println("controller startPage"+ startPage);
	    	System.out.println("controller endPage"+endPage);
	    	System.out.println("controller startRow"+startRow);
	    	System.out.println("controller endRow"+endRow);
	    	System.out.println("controller prevBlock"+prevBlock);
	    	System.out.println("controller nextBlock"+nextBlock);
		System.out.println("BoardController 진입");
		System.out.println("controller action:"+ request.getParameter("action"));
		java.util.HashMap<String, Object> param=new HashMap<>();
		switch (action) {
		case "move":
			System.out.println("BoardService에서 가져온 count: "+service.numberOfArticles());
			request.setAttribute("count", service.numberOfArticles());
			System.out.println("BoardController count: "+request.getAttribute("count"));
			bean.setSeqNo("1");
			bean=service.findArticle(bean);
			request.setAttribute("seqNo", bean.getSeqNo());
			request.setAttribute("title", bean.getTitle());
			request.setAttribute("content", bean.getContent());
			request.setAttribute("writer", bean.getWriter());
			request.setAttribute("regiDate", bean.getRegiDate());
			request.setAttribute("hitCounts", bean.getHitCount());
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
			
		case "list":
			System.out.println("controller list enter");
		/*	param.put("pagNo", request.getParameter("pageNo"));
			param.put("count", service.numberOfArticles());*/
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
		}
	}
}
