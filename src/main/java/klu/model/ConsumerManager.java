package klu.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import klu.repository.ArtregRepository;
import klu.repository.ConsumerRepository;
import klu.repository.ShippingRepository;


@Service
public class ConsumerManager {

	@Autowired
	ConsumerRepository CR;
	
	@Autowired
	ArtregRepository AR;
	
	@Autowired
	ShippingRepository SR;
	
	public String insertUser(Consumer C) 
	{
		try
		{
			if(CR.validateEmail(C.email)>0)
			throw new Exception("Email Alredy Exist");
			CR.save(C);
			return "redirectLogin";
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
	}

	public Consumer loginUser(String cemail,String pwd)
	{
		try
		{
			Consumer C = CR.loginvalidate(cemail, pwd);
			return C;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public String insertArt(Artreg A) 
	{
		try
		{

			AR.save(A);
			return "artsuccess";
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
		
	}
	
	public String insertship(Shipping S) 
	{
		try
		{

			SR.save(S);
			return "ordersuccess";
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
		
	}
	
	public List<Artreg> getAllArts() 
	{
		return AR.findAll();
	}
	
	
	public List<Artreg> getMyArts(String sellerid) 
	{
		return AR.findBySellerid(sellerid);
	}

	public List<Shipping> getmyorders(String buyerid)
	{
		return SR.findByBuyerid(buyerid);
	}

	public String cancelOrder(String orderid,String orderstatus) 
	{
		try
		{
		int rowsAffected = SR.cancelorder(orderid,orderstatus);
		 if (rowsAffected > 0) {
	            return "cancelorder"; 
	        }
		 else
		 {
	            return "No order found with that ID."; 

		 }
		}
		catch (Exception e) 
		{
			return e.getMessage();
		}
	}

	public List<Shipping> getvieworders(String artseller) {
		
		return SR.findByArtseller(artseller);
	}

	public String updateMyArt(Artreg A) 
	{
		try
		{
			int rowsAffected = AR.updatemyart(A.getArtid(),A.getArttitle(),A.getArtcost(),A.getArtdimensions(),A.getArtmedium(),A.getArtdescription(),A.getAvailstatus());
			if(rowsAffected>0)
			{
				return "updateartsuccess";
			}
			else
			{
				return "No Art ID exist";
			}
					
		}
		catch (Exception e) 
		{
			return e.getMessage();
		}
		
	}

	public String deleteArtById(String artId) 
	{
		try
		{
			int arteffected = AR.deleteArtById(artId);
			if(arteffected>0)
			{
				return "deletedart";
			}
			else
			{
				return "No Art Deleted";
			}
			
		}
		catch (Exception e) 
		{
			return e.getMessage();
		}
	}

	public String updateOrder(String orderid, String orderstatus) 
	{
		try
		{
		int rowsAffected = SR.updateorder(orderid,orderstatus);
		 if (rowsAffected > 0) {
	            return "updatedorder"; 
	        }
		 else
		 {
	            return "No order found with that ID."; 

		 }
		}
		catch (Exception e) 
		{
			return e.getMessage();
		}
	}

	public List<Consumer> getviewcustomers() 
	{
	return CR.findAll();
	}

	

	public String updateUser(Long userid,String name, String gender, String contactno, String address, String dob,
			String artPreference, String newsletter) {
		try
		{
		int rowsAffected = CR.updateCustomer(userid,name,gender,contactno,address,dob,artPreference,newsletter);
		 if (rowsAffected > 0) {
	            return "updateduser"; 
	        }
		 else
		 {
	            return "No user found with that ID."; 

		 }
		}
		catch (Exception e) 
		{
			return e.getMessage();
		}
	}

	public String deleteCustomerById(String userId)
	{
		try
		{
			int consumereffected = CR.deleteCustomerById(userId);
			if(consumereffected>0)
			{
				return "deletedconsumer";
			}
			else
			{
				return "No consumer Deleted";
			}
			
		}
		catch (Exception e) 
		{
			return e.getMessage();
		}
	}

	public List<Shipping> getallorders() 
	{
		return SR.findAll();
	}

	public String deleteallArtById(String artId) 
	{
		try
		{
			int allarteffected = AR.deleteArtById(artId);
			if(allarteffected>0)
			{
				return "deletedallart";
			}
			else
			{
				return "No all art Deleted";
			}
			
		}
		catch (Exception e) 
		{
			return e.getMessage();
		}
	}

	public String deleteallOrderById(String orderId) 
	{
		try
		{
			int allordereffected = SR.deleteOrderById(orderId);
			if(allordereffected>0)
			{
				return "deletedallorder";
			}
			else
			{
				return "No all art Deleted";
			}
			
		}
		catch (Exception e) 
		{
			return e.getMessage();
		}
	}


	

}
