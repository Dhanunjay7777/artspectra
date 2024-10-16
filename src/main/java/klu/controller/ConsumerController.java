package klu.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import klu.model.Artreg;
import klu.model.Consumer;
import klu.model.ConsumerManager;
import klu.model.Shipping;

import org.springframework.web.bind.annotation.PostMapping;






@RestController
@RequestMapping("/")//consumer
public class ConsumerController 
{
	@Autowired
	ConsumerManager CM;
	
//	@Autowired
//	private JavaMailSender mailSender;
	
	@PostMapping("/register")
    public String register(HttpServletRequest request,HttpServletResponse response)throws IOException {
        Consumer C = new Consumer(); // Create a new Consumer object

        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String contactno = request.getParameter("contactno");
        String address = request.getParameter("address");
        String dob = request.getParameter("dob");
        String artPreference = request.getParameter("artPreference");
        String newsletter = request.getParameter("newsletter");

        Random random = new Random();
        Long userid = (long) (random.nextInt(9000) + 1000);
        C.setUserid(userid);
        C.setEmail(email);
        C.setName(name);
        C.setPassword(password);
        C.setGender(gender);
        C.setContactno(contactno);
        C.setAddress(address);
        C.setDateOfBirth(dob);
        C.setArtPreference(artPreference);
        C.setNewsletter(newsletter);
        C.setUserRole("buyer");

        String result =  CM.insertUser(C); 
        if ("redirectLogin".equals(result)) {
            response.sendRedirect("login");  //here instead of this keep another file and then in that login link
            return null; 
        } else {
            return result;
        }    }
	
	@PostMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");

	    Consumer C = CM.loginUser(email, password);
	    if (C != null) {
	        HttpSession session = request.getSession();
	        session.setAttribute("cms", C);
	       // sendLoginSuccessEmail(C.getEmail(),C.getName());

	        if ("buyer".equalsIgnoreCase(C.getUserRole())) {
	            response.sendRedirect("buyerhome");
	        } else if ("seller".equalsIgnoreCase(C.getUserRole())) {
	            response.sendRedirect("sellerhome");
	        } else if ("admin".equalsIgnoreCase(C.getUserRole())) {
	            response.sendRedirect("adminhome");
	        }
	        return null; 
	    } else {
	       return "Invalid credentials"; 
	    }
	}
	

//	private void sendLoginSuccessEmail(String userEmail,String userName) {
//	    SimpleMailMessage message = new SimpleMailMessage();
//	    message.setTo(userEmail);
//	    message.setSubject("Login Successful");
//	    String messageBody = String.format(
//	            "Dear %s,\n\n" +
//	            "You have successfully logged in to your account. If this was not you, please contact our support team immediately.\n\n" +
//	            "If you have any questions or need assistance, feel free to reach out to us at:\n" +
//	            "Email: support@artspectrum.com\n" +
//	            "Phone: (123) 456-7890\n\n" +
//	            "Warm regards,\n" +
//	            "The Art Spectrum Team",
//	            userName
//	            
//	        );
//	    message.setText(messageBody);
//
//	    try {
//	        mailSender.send(message);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	}


	
	
  @PostMapping("/sellartreg")
  public String sellartreg(HttpServletRequest request,HttpServletResponse response,@RequestParam("imageurl") MultipartFile imageFile)throws IOException 
  {
      Artreg A = new Artreg();
      HttpSession session = request.getSession();
      
      Consumer seller = (Consumer) session.getAttribute("cms");
      
	  Random random = new Random();
	  int randomDigits = 100000 + random.nextInt(900000);
	  String artid = "ART" + randomDigits;
	 
	  String sellername = seller.getName();
	  String sellerid = seller.getUserid().toString();

      String arttitle = request.getParameter("arttitle");
      String artdescription = request.getParameter("artdescription");
      String artmedium = request.getParameter("artmedium");
      String artdimensions = request.getParameter("artdimensions");
      String artcost = request.getParameter("artcost");
      String datelisted = request.getParameter("datelisted");
      String availstatus = request.getParameter("availstatus");
      String appPath = request.getServletContext().getRealPath(""); // Get the real path of the web application
      String folder = appPath + "arts/";
      String imageFileName = sellerid + "_image" + randomDigits + ".jpg";
      String filePath = folder + imageFileName;
      File file = new File(filePath);
      imageFile.transferTo(file);
      String imageUrl = "arts/" + imageFileName;
      A.setImageurl(imageUrl);
      
      A.setArtid(artid);
      A.setSellername(sellername);
      A.setSellerid(sellerid);
      A.setArttitle(arttitle);
      A.setArtdescription(artdescription);
      A.setArtmedium(artmedium);
      A.setArtdimensions(artdimensions);
      A.setArtcost(artcost);
      A.setDatelisted(datelisted);
      A.setAvailstatus(availstatus);
      
    
      
     String result = CM.insertArt(A);
     if("artsuccess".equals(result)) 
     {
    	 response.sendRedirect("artsuccess");
    	 return null;
     }
     else
     {
    	 //response.sendRedirect("artfailure");
    	 return result;
     }
      
      
   
  }
  @PostMapping("/shipping")
  public String shipping(HttpServletRequest request,HttpServletResponse response)throws IOException {
      Shipping S = new Shipping(); // Create a new Consumer object
      HttpSession session = request.getSession();
      
      Consumer buyer = (Consumer) session.getAttribute("cms");

      String orderid = URLEncoder.encode("#order-" + (100000 + new Random().nextInt(900000)) + "-" + (100000 + new Random().nextInt(900000)));
      String artid = request.getParameter("artid");
      String arttitle = request.getParameter("arttitle");
      String artmedium = request.getParameter("artmedium");
      String artdimensions = request.getParameter("artdimensions");
      String artcost =  request.getParameter("artcost");
      String shippingaddress = request.getParameter("shippingaddress");
      String buyerid =  buyer.getUserid().toString();
      String buyername = buyer.getName();
      String artseller = request.getParameter("artseller");
      String orderstatus = "Ordered";
      String artimage = request.getParameter("artimage");
      String buyerEmail = buyer.getEmail();
      
      
      
      S.setOrderid(orderid);
      S.setArtid(artid);
      S.setArttitle(arttitle);
      S.setArtmedium(artmedium);
      S.setArtdimensions(artdimensions);
      S.setArtcost(artcost);
      S.setShippingaddress(shippingaddress);
      S.setBuyerid(buyerid);
      S.setBuyername(buyername);
      S.setArtseller(artseller);
      S.setOrderstatus(orderstatus);
      S.setArtimage(artimage);
      
      String result = CM.insertship(S);
      if("ordersuccess".equals(result))
      {
       // sendOrderConfirmationEmail(buyerEmail,buyername, orderid.replaceFirst("%23", "#"), arttitle, artcost, shippingaddress, orderstatus,artimage);

    	  response.sendRedirect("ordersuccess?artseller=" + Base64.getEncoder().encodeToString(artseller.getBytes("UTF-8")) +
                  "&artid=" + Base64.getEncoder().encodeToString(artid.getBytes("UTF-8")) +
                  "&orderid=" + Base64.getEncoder().encodeToString(orderid.getBytes("UTF-8")) +
                  "&shippingaddress=" + Base64.getEncoder().encodeToString(shippingaddress.getBytes("UTF-8")) +
                  "&artcost=" + Base64.getEncoder().encodeToString(artcost.getBytes("UTF-8")) +
                  "&orderstatus=" + Base64.getEncoder().encodeToString("Ordered".getBytes("UTF-8")) +
                  "&arttitle=" + Base64.getEncoder().encodeToString(arttitle.getBytes("UTF-8")) +
                  "&artdimensions=" + Base64.getEncoder().encodeToString(artdimensions.getBytes("UTF-8")) +
                  "&artimage=" + Base64.getEncoder().encodeToString(artimage.getBytes("UTF-8")));
return null;

      }
      else
      {
    	  return result;
      }
      
  }
   

//  private void sendOrderConfirmationEmail(String buyerEmail, String buyername, String orderid, String arttitle, String artcost, String shippingaddress, String orderstatus, String artimage) {
//	    try {
//	        MimeMessage mimeMessage = mailSender.createMimeMessage();
//	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//
//	        helper.setTo(buyerEmail);
//	        helper.setSubject("Order Confirmation: " + orderid);
//
//	        String messageBody = String.format(
//	                "<html>" +
//	                        "<body>" +
//	                        "<p>Dear %s,</p>" +
//	                        "<p>Thank you for your purchase! Below are the details of your order:</p>" +
//	                        "<p>Order ID: %s<br>" +
//	                        "Art Title: %s<br>" +
//	                        "Art Cost: %s<br>" +
//	                        "Shipping Address: %s<br>" +
//	                        "Order Status: %s</p>" +
//	                        "<p>Here is the image of the artwork:</p>" +
//	                        "<img src='cid:artImage' alt='Art Image' style='width:300px; height:200px;' />"+
//	                        "<p>We will notify you once your order is shipped.</p>" +
//	                        "<p>Best regards,<br>Art Gallery Team</p>" +
//	                        "</body>" +
//	                        "</html>",
//	                buyername, orderid, arttitle, artcost, shippingaddress, orderstatus
//	        );
//
//	        helper.setText(messageBody, true);
//	        FileSystemResource file = new FileSystemResource(new File("src/main/webapp/" + artimage));
//	        helper.addInline("artImage", file); // Embed the image in the email body with 'cid:artImage'
//	        mailSender.send(mimeMessage);
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	}
//  
//  
  @PostMapping("cancelorder")
  public String cancelorder(HttpServletRequest request,HttpServletResponse response)throws IOException
  {
    String orderid = request.getParameter("orderid").replace("#", "%23");
    String orderstatus = request.getParameter("orderstatus");

   
    
    String result = CM.cancelOrder(orderid,orderstatus);
    if("cancelorder".equals(result)) 
    {
   	 response.sendRedirect("myorders");
   	 return null;
    }
    else
    {
   	 return result;
    }
     
  }
  
  @PostMapping("updatemyart")
  public String updatemyart(HttpServletRequest request,HttpServletResponse response)throws IOException 
  {
   Artreg A = new Artreg();
   String artId = request.getParameter("artId");
   String artTitle = request.getParameter("artTitle");
   String artPrice = request.getParameter("artPrice");
   String artDimensions = request.getParameter("artDimensions");
   String artMedium = request.getParameter("artMedium");
   String artDescription = request.getParameter("artDescription");
   String artStatus = request.getParameter("artStatus");
   
   
   A.setArtid(artId);
   A.setArttitle(artTitle);
   A.setArtcost(artPrice);
   A.setArtdimensions(artDimensions);
   A.setArtmedium(artMedium);
   A.setArtdescription(artDescription);
   A.setAvailstatus(artStatus);
   
   String result = CM.updateMyArt(A);
   if("updateartsuccess".equals(result))
   {
	   response.sendRedirect("myarts");
	   return null;
   }
   else
   {
	   return result;
   }
  }
  
  
  @PostMapping("updateorder")
  public String updateorder(HttpServletRequest request,HttpServletResponse response)throws IOException
  {
    String orderid = request.getParameter("orderid").replace("#", "%23");
    String orderstatus = request.getParameter("orderstatus");

   
    
    String result = CM.updateOrder(orderid,orderstatus);
    if("updatedorder".equals(result)) 
    {
   	 response.sendRedirect("vieworders");
   	 return null;
    }
    else
    {
   	 return result;
    }
     
  }
  
  @PostMapping("deletemyart")
  public String deletemyart(HttpServletRequest request,HttpServletResponse response)throws IOException
  {
	    String artId = request.getParameter("artId");

	    String result = CM.deleteArtById(artId);
	    
	    if("deletedart".equals(result))
	    {
	    	response.sendRedirect("myarts");
	    	return null;
	    }
	    else
	    {
	    	return result;
	    }

  }
  
  
  
  @PostMapping("/updatecustomers")
  public String updatecustomers(HttpServletRequest request,HttpServletResponse response)throws IOException {
	    
	  String useridParam = request.getParameter("userId");  
	  Long userid = Long.valueOf(useridParam);
	  String name = request.getParameter("name");
      String gender = request.getParameter("gender");
      String contactno = request.getParameter("contactno");
      String address = request.getParameter("address");
      String dob = request.getParameter("dob");
      String artPreference = request.getParameter("artPreference");
      String newsletter = request.getParameter("newsletter");



      String result =  CM.updateUser(userid,name,gender,contactno,address,dob,artPreference,newsletter); 
      if ("updateduser".equals(result)) {
          response.sendRedirect("viewcustomers?message=User updated successfully");
          return null; 
      } else {
          return result;
      }    }
  
  
  
  
  @PostMapping("deletecustomer")
  public String deletecustomer(HttpServletRequest request,HttpServletResponse response)throws IOException
  {
	    String userId = request.getParameter("userId");

	    String result = CM.deleteCustomerById(userId);
	    
	    if("deletedconsumer".equals(result))
	    {
	    	response.sendRedirect("viewcustomers");
	    	return null;
	    }
	    else
	    {
	    	return result;
	    }

  }
  
  
  @PostMapping("deleteallart")
  public String deleteallart(HttpServletRequest request,HttpServletResponse response)throws IOException
  {
	    String artId = request.getParameter("artId");

	    String result = CM.deleteallArtById(artId);
	    
	    if("deletedallart".equals(result))
	    {
	    	response.sendRedirect("viewarts");
	    	return null;
	    }
	    else
	    {
	    	return result;
	    }

  }
  
  
  @PostMapping("deleteallorder")
  public String deleteallorder(HttpServletRequest request,HttpServletResponse response)throws IOException
  {
	    String orderId = request.getParameter("orderId").replace("#", "%23");

	    String result = CM.deleteallOrderById(orderId);
	    
	    if("deletedallorder".equals(result))
	    {
	    	response.sendRedirect("viewallorders");
	    	return null;
	    }
	    else
	    {
	    	return result;
	    }

  }
  
  
  
}
	
	
	


