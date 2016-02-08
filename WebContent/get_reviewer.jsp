<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*, com.jovi.rronline.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reviewer Information</title>
</head>
<body>

<%
      String title = request.getParameter("title");
	  String abstract_contect = request.getParameter("abstract");
	  String category = DocCategory.docClassify(abstract_contect);
	  //Reviewer.trainAPI(application.getRealPath("/")+"//"+category+"_reviewer");
	  //Reviewer rw = new Reviewer();
	  //String name = Reviewer.docClassify(abstract_contect);
	  
	  String author_max_sim = AbstractSimularity.getMaxCosSimularity(application.getRealPath("/")+"//"+"NEW-CAT//"+category+".txt", abstract_contect.toLowerCase());
		
		
	  Person person = Reviewer.getPersonInfo(application.getRealPath("/")+"//"+"AUTHOR//"+author_max_sim+".txt");
	  out.println("<h1>"+"Paper: "+title+"</h1>");
	  out.println("<br>");
	  out.println("<h2>"+"Assigned Reviewer:"+"</h2>");
	  out.println("<br>");
	  
	  out.println("<h1>   "+person.name+"</h1>");
	  out.println("<h4>      "+person.university+"</h4>");
	  out.println("<h4>      "+person.position+"</h4>");
	  
	  Iterator<String> it = person.research_interest.iterator();
	  while(it.hasNext()){
		  String interest = it.next();
		  out.println("       <h4>"+interest+"</h4>");
	  }
    
%>
</body>
</html>