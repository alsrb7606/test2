<%-- <%@page import="java.util.Calendar"%>
<%@page import="calendar.MakeCalendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int year=Integer.parseInt(request.getParameter("year"));
	int month=Integer.parseInt(request.getParameter("month"));
	MakeCalendar obj=new MakeCalendar(year,month);
	Calendar cal=obj.getCal();
	int week=cal.get(Calendar.DAY_OF_WEEK);
	int first_day=cal.get(Calendar.DAY_OF_MONTH);
	int last_day=cal.getActualMaximum(Calendar.DATE);
	int count=0;//찍는 횟수
	int i=0;
%>
<!DOCTYPE html>
<html>
<head>
<style>

table
{
	border-collapse: collapse;
	width: 100%;
}
th:first-child {
	color: red;
}
th:last-child {
	color:blue;
}

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
년도:<%=year%>
월:<%=month%>
week:<%=week%>
firstday:<%=first_day%>
lastday:<%=last_day %>
<table border="1">
<tr>
	<th>일</th>
	<th>월</th>
	<th>화</th>
	<th>수</th>
	<th>목</th>
	<th>금</th>
	<th>토</th>
<tr>
<%
while(first_day<=last_day)
{
	 ++i;
	
	
	if(first_day>last_day)
	{
		break;
	}
	if(count%7==0)
	{
		%>
		<tr></tr>
		<%
		System.out.println();
		count=0;
	}

	if(i>=week)
    {
		%>
		<td><%=first_day%></td>
		<%
    	System.out.print(first_day+"\t");
    	++first_day;
    }else
    {
    	%>
    	<td></td>
    	<%
    	 System.out.print(" \t");
    }
	++count;
}
%>

</table>
</body>
</html> --%>