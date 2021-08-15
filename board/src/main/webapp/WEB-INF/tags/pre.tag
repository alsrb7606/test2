<%@ tag  pageEncoding="UTF-8" body-content="empty"%>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="value" type="java.lang.String" required="true" %>
<%
	value = value.replace("<", "&lt;");
	value = value.replace("\n", "<br>");
	value = value.replace("&", "&amp;");
	value = value.replace(" ", "&nbsp;");


%>
<%= value%>