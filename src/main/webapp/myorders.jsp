<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
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
<html>
<head>
    <title>My Orders</title>
    <link rel="stylesheet" href="css/myorder.css">
</head>
<body>
    <div class="container">
        <section class="order-list">
            <table class="order-table">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Buyer Name</th>
                        <th>Shipping Address</th>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Status</th>
                        <th>Actions</th> 
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<klu.model.Shipping> shipList = (List<klu.model.Shipping>) request.getAttribute("shipList");

                        if (shipList != null && !shipList.isEmpty()) {
                            for (klu.model.Shipping order : shipList) {
                                String orderIdWithHash = order.getOrderid().replace("%23", "#");
                                String ShippingAddress = order.getShippingaddress();
                                String artSeller = order.getArtseller();
                                String artId = order.getArtid();
                                String artCost = order.getArtcost();
                                String orderStatus = order.getOrderstatus();
                                String artTitle = order.getArttitle();
                                String artDimensions = order.getArtdimensions();
                                String artImage = order.getArtimage();

                                // Base64 encode parameters
                                String encodedOrderId = Base64.getEncoder().encodeToString(orderIdWithHash.getBytes("UTF-8"));
                                String encodedArtTitle = Base64.getEncoder().encodeToString(artTitle.getBytes("UTF-8"));
                                String encodedOrderStatus = Base64.getEncoder().encodeToString(orderStatus.getBytes("UTF-8"));
                                String encodedArtSeller = Base64.getEncoder().encodeToString(artSeller.getBytes("UTF-8"));
                                String encodedArtId = Base64.getEncoder().encodeToString(artId.getBytes("UTF-8"));
                                String encodedShippingAddress = Base64.getEncoder().encodeToString(ShippingAddress.getBytes("UTF-8"));
                                String encodedArtCost = Base64.getEncoder().encodeToString(artCost.getBytes("UTF-8"));
                                String encodedArtDimensions = Base64.getEncoder().encodeToString(artDimensions.getBytes("UTF-8"));
                                String encodedArtImage = Base64.getEncoder().encodeToString(artImage.getBytes("UTF-8"));
                    %>
                                <tr>
                                    <td><%= orderIdWithHash %></td>
                                    <td><%= order.getBuyername() %></td>
                                    <td><%= ShippingAddress %></td>
                                    <td><%= artTitle %></td>
                                    <td><%= artCost %></td>
                                    <td><%= orderStatus %></td>
                                    <td>
                                    
                                    <a href="<%= "cancelorder?orderid=" + encodedOrderId +
                                                "&arttitle=" + encodedArtTitle + 
                                                "&orderstatus=" + encodedOrderStatus %>" 
                                                class="button cancel-button" 
                                            <%= ("Canceled".equals(orderStatus) || "Delivered".equals(orderStatus)) ? "style='cursor: not-allowed;' disabled" : "" %>>
                                        Cancel
                                    </a>

                                    <a href="<%= "ordersuccess?artseller=" + encodedArtSeller +
                                                "&artid=" + encodedArtId +
                                                "&orderid=" + encodedOrderId +
                                                "&shippingaddress=" + encodedShippingAddress +
                                                "&artcost=" + encodedArtCost +
                                                "&orderstatus=" + encodedOrderStatus +
                                                "&arttitle=" + encodedArtTitle +
                                                "&artdimensions=" + encodedArtDimensions +
                                                "&artimage=" + encodedArtImage %>" 
                                       class="button download-button">
                                       Download
                                    </a>
                                    </td>
                                </tr>
                    <%
                            }
                        } else {
                    %>
                            <tr>
                                <td colspan="7" style="text-align:center;">No orders found.</td>
                            </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </section>
    </div>
</body>
</html>
