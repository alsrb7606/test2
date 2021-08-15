<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
게시글이 삭제되었습니다.
<br>
<c:set var="pageNo" value="${empty param.pageNo? '1' : param.pageNo}"/>
<a href="list.do?pageNo=${pageNo}">[목록보기]</a>
</body>
</html>