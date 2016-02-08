<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*,java.util.*, opennlp.tools.doccat.*, com.jovi.rronline.DocCategory" %>
<%
	
	  if(DocCategory.model == null){
	  	DocCategory.trainAPI(application.getRealPath("/")+"//"+"category_training.txt");
	  }
    
%>
<html>
<head>
<meta charset="UTF-8">
<title>Reviewer Recommendation</title>
</head>
<body>
<form action="get_reviewer.jsp" id="usrform">
  Name: <input type="text" name="title" size="200" >
<br>
<textarea rows="30" cols="200" name="abstract" form="usrform">
In web search, users queries are formulated using only few terms and term-matching retrieval functions could fail at retrieving relevant documents. Given a user query, the technique of query expansion (QE) consists in selecting related terms that could enhance the likelihood of retrieving relevant documents. Selecting such expansion terms is challenging and requires a computational framework capable of encoding complex semantic relationships. In this paper, we propose a novel method for learning, in a supervised way, semantic representations for words and phrases. By embedding queries and documents in special matrices, our model disposes of an increased representational power with respect to existing approaches adopting a vector representation. We show that our model produces high-quality query expansion terms. Our expansion increase IR mesures beyond expansion from current word-embeddings models and well-established traditional QE methods.</textarea>
<p><b>Note:</b> The form attribute is not supported in IE.</p>
<input type="submit">
</form>
</body>
</html>