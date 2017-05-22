package com.board.web.daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.board.web.dao.BoardDAO;
import com.board.web.domain.ArticleBean;

public class BoardDAOImpl implements BoardDAO {
	public static BoardDAOImpl instance=new BoardDAOImpl();
	public static BoardDAOImpl getInstance() {
		return instance;
	}
	public static void setInstance(BoardDAOImpl instance) {
		BoardDAOImpl.instance = instance;
	}
	private BoardDAOImpl(){}
		
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://203.236.209.96:3306/hanbit";
	private static final String USER = "hanbit";
	private static final String PW = "hanbit";
/*	public static void main(String[] args) {
		String writer="";
		try {
			Class.forName(DRIVER);
			Connection connection= DriverManager.getConnection(URL,USER,PW);
			Statement stmt=connection.createStatement();
			String sql="SELECT * FROM Board WHERE seq_no='1'";
			ResultSet rs=stmt.executeQuery(sql);
			
			if(rs.next()){
				writer=rs.getString("writer");
			}
		} catch (Exception e) {
			System.out.println("에러발생");
			e.printStackTrace();
		}
		System.out.println("작성자: "+writer);
	}*/
	@Override
	public void insertArticle(ArticleBean article) {
		try {
			Class.forName(DRIVER);
			Connection connection=DriverManager.getConnection(URL,USER,PW);
			Statement stmt=connection.createStatement();
			String sql="INSERT INTO Board (title,writer,content,regi_date) VALUES ('"
					+article.getTitle()+"','"+article.getWriter()+"','"+article.getContent()+"','"+article.getRegiDate()+"')";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("에러 발생");
			e.printStackTrace();
		}
	}
	@Override
	public int countArticles() {
	int count=0;
	try {
		Class.forName(DRIVER);
		Connection connection=DriverManager.getConnection(URL,USER,PW);
		Statement stmt=connection.createStatement();
		String sql="SELECT COUNT(*) AS count FROM Board";
		ResultSet rs=stmt.executeQuery(sql);
		if(rs.next()){
			count=Integer.parseInt(rs.getString("count"));
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("count: "+ count);
	return count;
	}
	@Override
	public ArticleBean selectArticle(ArticleBean article) {
		ArticleBean temp=null;
		int hitCounts=0;
		try {
			Class.forName(DRIVER);
			Connection connection=DriverManager.getConnection(URL,USER,PW);
			Statement stmt=connection.createStatement();
			String sql="SELECT seq_no,title,content,writer,regi_date,count FROM Board WHERE seq_no='"+article.getSeqNo()+"'";
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				temp=new ArticleBean();
				temp.setSeqNo(rs.getString("seq_no"));
				System.out.println("rs.getString(seq_no) "+rs.getString("seq_no"));
				temp.setTitle(rs.getString("title"));
				System.out.println("rs.getString(title) "+rs.getString("title"));
				temp.setContent(rs.getString("content"));
				System.out.println("rs.getString(content) "+rs.getString("content"));
				temp.setWriter(rs.getString("writer"));
				System.out.println("rs.getString(writer) "+rs.getString("writer"));
				temp.setRegiDate(rs.getString("regi_date"));
				System.out.println("rs.getString(regiDate) "+rs.getString("regi_date"));
				hitCounts=Integer.parseInt(rs.getString("count"));
				temp.setHitCount(String.valueOf(++hitCounts));
				System.out.println("rs.getString(count)"+hitCounts);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	@Override
	public List<ArticleBean> selectArticles(HashMap<String, Object> paramMap)  {
		List<ArticleBean> listSome= new ArrayList<>();
		ArticleBean article =null;
		try {
			Class.forName(DRIVER);
			Connection conenction=DriverManager.getConnection(URL,USER,PW);
			Statement stmt=conenction.createStatement();
			String sql="SELECT seq_no,writer,title,content,regi_date,count FROM Board";
			ResultSet rs=stmt.executeQuery(sql);
			System.out.println("rs 값입니다"+rs);
			while(rs.next()){
				article=new ArticleBean();
				article.setSeqNo(rs.getString("seq_no"));
				System.out.println("rs.getString(seq_no)"+rs.getString("seq_no"));
				article.setTitle(rs.getString("title"));
				System.out.println("rs.getString (title)"+rs.getString("title"));
				article.setContent(rs.getString("content"));
				System.out.println("rs.content: "+ rs.getString("content"));
				article.setWriter(rs.getString("writer"));
				System.out.println("rs.writer: "+rs.getString("writer"));
				article.setRegiDate(rs.getString("regi_date"));
				System.out.println("rs.regi_date: "+rs.getString("regi_date"));
				article.setHitCount(rs.getString("count"));
				System.out.println("rs.count: "+rs.getString("count"));
				listSome.add(article);
			} 
		} catch (Exception e) {
			System.out.println("에러발생:");
			e.printStackTrace();
		}
		System.out.println("list DB: "+listSome.get(0));
		return listSome;
	}
	@Override
	public void updateArticle(ArticleBean article) {
		try {
			Class.forName(DRIVER);
			Connection connection=DriverManager.getConnection(URL,USER,PW);
			Statement stmt=connection.createStatement();
			String sql="UPDATE Board SET title='"+article.getTitle()+"',content='"+article.getContent()+"' WHERE seq_no='"+article.getSeqNo()+"'";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public ArticleBean deleteArticel(ArticleBean article) {
		try {
			Class.forName(DRIVER);
			Connection connection=DriverManager.getConnection(URL,USER,PW);
			Statement stmt=connection.createStatement();
			String sql="DELETE  FROM Board WHERE seq_no='"+article.getSeqNo()+"'";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("에러발생");
			e.printStackTrace();
		}
		return article;
	}
	
}
