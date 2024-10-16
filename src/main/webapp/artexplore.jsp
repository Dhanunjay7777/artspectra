<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="klu.model.Artreg" %>
<%@ page import="java.util.Base64" %>
<%@ include file="buyernav.jsp" %>
<%@ page import="klu.model.Consumer" %>
<%
    Consumer consumer = (Consumer) session.getAttribute("cms");
    if (consumer == null) {
        response.sendRedirect("sessionexpiry.html");
        return; 
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Art Explore</title>
    <link rel="stylesheet" href="css/artexpo.css"/>
</head>
<body>
<%
    List<Artreg> artList = (List<Artreg>) request.getAttribute("artList");
    if (artList != null && !artList.isEmpty()) {
        for (Artreg art : artList) {
%>
            <div class="card">
                <img src="<%= request.getContextPath() + '/' + art.getImageurl() %>" alt="Art Image">
                <h2><%= art.getArttitle() %></h2>
                <p><strong>Description:</strong> <%= art.getArtdescription() %></p>
                <p class="cost">Cost: $<%= art.getArtcost() %></p>
                <p><strong>Medium:</strong> <%= art.getArtmedium() %></p>
                <p><strong>Dimensions:</strong> <%= art.getArtdimensions() %></p>
                <%
                    String stockStatus = art.getAvailstatus();
                    if ("OutOfStock".equalsIgnoreCase(stockStatus)) {
                %>
                    <div class="out-of-stock">Out of Stock</div>
                    <button class="buy-button disabled" disabled>Buy Now</button>
                <%
                    } else {
                        String encodedArtId = Base64.getEncoder().encodeToString(art.getArtid().getBytes("UTF-8"));
                        String encodedArtTitle = Base64.getEncoder().encodeToString(art.getArttitle().getBytes("UTF-8"));
                        String encodedArtCost = Base64.getEncoder().encodeToString(String.valueOf(art.getArtcost()).getBytes("UTF-8"));
                        String encodedArtDimensions = Base64.getEncoder().encodeToString(art.getArtdimensions().getBytes("UTF-8"));
                        String encodedArtMedium = Base64.getEncoder().encodeToString(art.getArtmedium().getBytes("UTF-8"));
                        String encodedSellerName = Base64.getEncoder().encodeToString(art.getSellername().getBytes("UTF-8"));
                        String encodedImageUrl = Base64.getEncoder().encodeToString(art.getImageurl().getBytes("UTF-8"));
                %>
                    <div class="in-stock">In Stock</div>
                    <a class="buy-button" href="<%= request.getContextPath() + "/shipping?artid=" + encodedArtId + 
                        "&arttitle=" + encodedArtTitle + 
                        "&artcost=" + encodedArtCost + 
                        "&artdimensions=" + encodedArtDimensions + 
                        "&artmedium=" + encodedArtMedium + 
                        "&sellername=" + encodedSellerName + 
                        "&imageurl=" + encodedImageUrl %>">Buy Now</a>
                <%
                    }
                %>
            </div>
<%
        }
    } else {
%>
        <p>No art available.</p>
<%
    }
%>
</body>
</html>
