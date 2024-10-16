<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="klu.model.Consumer" %>
<%@ page import="java.util.Base64" %>
<%
    Consumer consumer = (Consumer) session.getAttribute("cms");
    if (consumer == null) {
        response.sendRedirect("sessionexpiry.html");
        return; 
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cancel Order</title>
    <link rel="stylesheet" href="css/cancelorder.css"/> 
</head>
<body>
    <h2>Cancel Order</h2>

    <%
        String orderId = new String(Base64.getDecoder().decode(request.getParameter("orderid")), "UTF-8");
        String artTitle = new String(Base64.getDecoder().decode(request.getParameter("arttitle")), "UTF-8");
        String orderStatus = new String(Base64.getDecoder().decode(request.getParameter("orderstatus")), "UTF-8");
    %>

    <form action="cancelorder" method="post">
        <div>
            <label for="orderid">Order ID:</label>
            <input type="text" id="orderid" name="orderid" value="<%= orderId %>" readonly>
        </div>
        <div>
            <label for="arttitle">Art Title:</label>
            <input type="text" id="arttitle" name="arttitle" value="<%= artTitle %>" readonly>
        </div>
        <div>
            <label for="orderstatus">Order Status:</label>
            <select id="orderstatus" name="orderstatus">
                <option value="Ordered" <%= "Ordered".equals(orderStatus) ? "selected" : "" %>>Ordered</option>
                <option value="Canceled" <%= "Canceled".equals(orderStatus) ? "selected" : "" %>>Canceled</option>
            </select>
        </div>
        <button type="submit" class="button cancel-button">Confirm Cancellation</button>
    </form>
</body>
</html>
