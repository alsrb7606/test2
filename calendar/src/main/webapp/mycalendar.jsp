<%@page import="java.util.Calendar"%>
<%@page import="calendar.MakeCalendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Calendar cal=Calendar.getInstance();
	int year=request.getParameter("year")==null?cal.get(Calendar.YEAR):Integer.parseInt(request.getParameter("year"));
	int month=request.getParameter("month")==null?cal.get(Calendar.MONTH)+1:Integer.parseInt(request.getParameter("month"));
	MakeCalendar obj=new MakeCalendar(cal,year,month);
	cal=obj.getCal();
	int week=cal.get(Calendar.DAY_OF_WEEK);
	int first_day=cal.get(Calendar.DAY_OF_MONTH);
	int last_day=cal.getActualMaximum(Calendar.DATE);
	int count=0; //찍는 횟수
	int i=0;
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="mycalendar.css">
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
	function nextMonth()
	{
		var year=Number($('input[name=year]').val());
		
		var month=Number($('input[name=month]').val());
		if (month == 12) {
			month=1;
			$('input[name=year]').attr('value',year+1);
			$('input[name=month]').attr('value',month);
		} else {
			$('input[name=month]').attr('value',month+1);
		}
		$("form").submit();
	}
	
	function previousMonth()
	{
		//var form=document.form;
		/* var year=Number(form.year.value);
		var month=Number(form.month.value); */
		var year=Number($('input[name=year]').val());
		var month=Number($('input[name=month]').val());
		if (month == 1) {
			/* form.year.value = year - 1;
			form.month.value = 12; */
			month=12;
			$('input[name=year]').attr('value',year-1);
			$('input[name=month]').attr('value',month);
		} else {
			$('input[name=month]').attr('value',month-1);
		}
		//form.submit();
		$("form").submit();
	}
</script>
</head>
<body>
<form name="form" method="Post">
<div id="head">
<input type="hidden" name="year" value="<%=year %>">
<input type="hidden" name="month" value="<%=month%>">
<input type="button" value="<-" onclick="previousMonth()" id="before">
<b><%=year%>년 <%=month%>월</b>
<input type="button" value="->" onclick="nextMonth()" id="after">
</div>
<div id="section">
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
		//System.out.println();
		count=0;
	}

	if(i>=week)
    {
		%>
		<td><%=first_day%></td>
		<%
    	//System.out.print(first_day+"\t");
    	++first_day;
    }else
    {
    	%>
    	<td></td>
    	<%
    //	 System.out.print(" \t");
    }
	++count;
}
%>

</table>
</div>
</form>
</body>
</html>