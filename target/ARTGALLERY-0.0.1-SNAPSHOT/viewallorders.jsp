<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="klu.model.Shipping" %>
<%@ include file="adminnav.jsp" %> <!-- Include navigation -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View All Orders</title>
    <link rel="stylesheet" href="css/viewallorders.css"> <!-- Link to your CSS file -->
</head>
<body>
    <%
        List<Shipping> shipList = (List<Shipping>) request.getAttribute("shipList");

        if (shipList != null && !shipList.isEmpty()) {
    %>
            <h1 style="text-align:center;">All Orders</h1>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Art Cost</th>
                            <th>Art Dimensions</th>
                            <th>Art ID</th>
                            <th>Art Image</th>
                            <th>Art Medium</th>
                            <th>Art Seller</th>
                            <th>Art Title</th>
                            <th>Buyer ID</th>
                            <th>Buyer Name</th>
                            <th>Order Status</th>
                            <th>Shipping Address</th>
                            <th>Actions</th> <!-- New column for actions -->
                        </tr>
                    </thead>
                    <tbody>
    <%
            for (Shipping order : shipList) {
                String orderId = order.getOrderid().replaceFirst("%23", "#");
                String artCost = order.getArtcost();
                String artDimensions = order.getArtdimensions();
                String artId = order.getArtid();
                String artImage = order.getArtimage();
                String artMedium = order.getArtmedium();
                String artSeller = order.getArtseller();
                String artTitle = order.getArttitle();
                String buyerId = order.getBuyerid();
                String buyerName = order.getBuyername();
                String orderStatus = order.getOrderstatus();
                String shippingAddress = order.getShippingaddress();
    %>
                        <tr>
                            <td><%= orderId %></td>
                            <td><%= artCost %></td>
                            <td><%= artDimensions %></td>
                            <td><%= artId %></td>
                            <td><img src="<%= artImage %>" alt="Artwork Image" style="max-width: 100px; max-height: 100px;"></td>
                            <td><%= artMedium %></td>
                            <td><%= artSeller %></td>
                            <td><%= artTitle %></td>
                            <td><%= buyerId %></td>
                            <td><%= buyerName %></td>
                            <td><%= orderStatus %></td>
                            <td><%= shippingAddress %></td>
                            <td>
                                <a href="updateallorder?orderId=<%= orderId %>" class="update-button">Update</a>
                                <button class="delete-button" onclick="openDeleteModal('<%= orderId %>')">Delete</button>
                            </td>
                        </tr>
    <%
            }
    %>
                    </tbody>
                </table>
            
            <!-- Modal for Delete Confirmation -->
            <div id="deleteModal" class="modal" style="display:none;">
                <div class="modal-content">
                    <h2>Confirm Deletion</h2>
                    <p>Are you sure you want to delete this order?</p>
                    <!-- Form for handling deletion with POST method -->
                    <form id="deleteForm" action="deleteallorder" method="POST">
                        <input type="hidden" name="orderId" id="orderIdInput" value=""> <!-- Hidden field to store the orderId -->
                        <button type="button" onclick="closeDeleteModal()">Cancel</button>
                        <button type="submit">Confirm Delete</button>
                    </form>
                </div>
            </div>

            <script>
                // Function to open the delete modal and set the orderId in the hidden input field
                function openDeleteModal(orderId) {
                    document.getElementById('orderIdInput').value = orderId; // Set orderId in the hidden input
                    document.getElementById('deleteModal').style.display = 'flex'; // Show the modal
                }

                // Function to close the delete modal
                function closeDeleteModal() {
                    document.getElementById('deleteModal').style.display = 'none'; // Hide the modal
                }
            </script>

    <%
        } else {
    %>
            <div>
                <p>No orders found.</p>
            </div>
    <%
        }
    %>
</body>
</html>
