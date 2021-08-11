package ers_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ers_model.Reimbursement;


public class ReimbursementDAOImpl implements ReimbursementDAO{
	
final static Logger loggy = Logger.getLogger(ReimbursementDAOImpl.class);
	
	{
		loggy.setLevel(Level.ALL);
	}
	
	public String url="jdbc:postgresql://mydatabase.c8kadxt5iwbx.us-east-2.rds.amazonaws.com/Project_1_database";
	public String username="mydatabase";
	public String password="p4sswOrd";
	
	static { //(this would normally go into your dao layer)
	      try {
	          Class.forName("org.postgresql.Driver");
	      }catch(ClassNotFoundException e) {
	          e.printStackTrace();
	          System.out.println("Static block has failed me");
	      }
	}
	
	
	@Override
	public void requestReimbursement(Reimbursement request) {
		
		try (Connection conn=DriverManager.getConnection(url, username, password)){
			String sql="INSERT INTO ERS_REIMBURSEMENT (reimb_amount,reimb_description,reimb_author,reimb_type_id)\r\n"
					+ "VALUES (?,?,?,?)";
			
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ps.setDouble(1, request.getReimbAmount());
			ps.setString(2, request.getReimbDescription());
			ps.setInt(3, request.getReimbAuthor());
			ps.setInt(4, request.getReimbTypeId());
			
			
			ps.executeUpdate();
			
		}catch(SQLException e) {
			loggy.error("Error happened when a user try to request a reimbursement", e);
			System.out.println("SQLException occur during reimbursement request operation : " +e);
		}
		
	}

	@Override
	public List<Reimbursement> viewPastTickets(int authorId) {
		List<Reimbursement> myReimbursementList=new ArrayList<>();
		Reimbursement ticket=null;
		try (Connection conn=DriverManager.getConnection(url, username, password)){
			String sql="SELECT * FROM ERS_REIMBURSEMENT WHERE reimb_author=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ps.setInt(1, authorId);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				ticket=new Reimbursement();
				
				ticket.setReimbId(rs.getInt(1));
				ticket.setReimbAmount(rs.getDouble(2));
				ticket.setReimbSubmitted(rs.getDate(3));
				ticket.setReimbResolved(rs.getDate(4));
				ticket.setReimbDescription(rs.getString(5));
				ticket.setReimbReceipt(rs.getByte(6));
				ticket.setReimbAuthor(rs.getInt(7));
				ticket.setReimbResolver(rs.getInt(8));
				ticket.setReimbStatusId(rs.getInt(9));
				ticket.setReimbTypeId(rs.getInt(10));
				
				myReimbursementList.add(ticket);
				
			}
		}catch(SQLException e) {
			loggy.error("Error happened when a user try to see its past tickets", e);
			System.out.println("SQLException occur during veiwPastTickets  operation : " +e);
		}
		
		return myReimbursementList;
	}

	@Override
	public List<Reimbursement> viewAllReimbursements() {
		List<Reimbursement> myReimbursementList=new ArrayList<>();
		Reimbursement ticket=null;
		try (Connection conn=DriverManager.getConnection(url, username, password)){
			String sql="SELECT * FROM ERS_REIMBURSEMENT ";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				ticket=new Reimbursement();
				
				ticket.setReimbId(rs.getInt(1));
				ticket.setReimbAmount(rs.getDouble(2));
				ticket.setReimbSubmitted(rs.getDate(3));
				ticket.setReimbResolved(rs.getDate(4));
				ticket.setReimbDescription(rs.getString(5));
				ticket.setReimbReceipt(rs.getByte(6));
				ticket.setReimbAuthor(rs.getInt(7));
				ticket.setReimbResolver(rs.getInt(8));
				ticket.setReimbStatusId(rs.getInt(9));
				ticket.setReimbTypeId(rs.getInt(10));
				
				myReimbursementList.add(ticket);
				
			}
		}catch(SQLException e) {
			loggy.error("Error happened when a user try to see all reimbursements requests", e);
			System.out.println("SQLException occur during viewAllReimbursements  operation : " +e);
		}
		
		return myReimbursementList;
	}

	@Override
	public List<Reimbursement> viewReimbursementsByStatus(int reimbStatusId) {
		List<Reimbursement> myReimbursementList=new ArrayList<>();
		Reimbursement ticket=null;
		try (Connection conn=DriverManager.getConnection(url, username, password)){
			String sql="SELECT * FROM ERS_REIMBURSEMENT WHERE reimb_status_id=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ps.setInt(1, reimbStatusId);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				ticket=new Reimbursement();
				
				ticket.setReimbId(rs.getInt(1));
				ticket.setReimbAmount(rs.getDouble(2));
				ticket.setReimbSubmitted(rs.getDate(3));
				ticket.setReimbResolved(rs.getDate(4));
				ticket.setReimbDescription(rs.getString(5));
				ticket.setReimbReceipt(rs.getByte(6));
				ticket.setReimbAuthor(rs.getInt(7));
				ticket.setReimbResolver(rs.getInt(8));
				ticket.setReimbStatusId(rs.getInt(9));
				ticket.setReimbTypeId(rs.getInt(10));
				
				myReimbursementList.add(ticket);
				
			}
		}catch(SQLException e) {
			//loggy.error("Error happened when a user try to see all accounts information", e);
			System.out.println("SQLException occur during viewReimbursementsByStatus  operation : " +e);
		}
		
		return myReimbursementList;
	}

	@Override
	public void approveOrDenyReimbursement(Reimbursement ticket) {
		
		try (Connection conn=DriverManager.getConnection(url, username, password)){
			String sql="UPDATE ERS_REIMBURSEMENT\r\n"
					+ "SET reimb_resolved =current_date, reimb_resolver =?,reimb_status_id =?\r\n"
					+ "WHERE reimb_id=? AND reimb_status_id=3";
			
			// 3 as status id is as pending. that means the update is only for the pending ticket
			
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ps.setInt(1, ticket.getReimbResolver());
			ps.setInt(2, ticket.getReimbStatusId());
			ps.setInt(3, ticket.getReimbId());
			
			
			
			ps.executeUpdate();
			
		}catch(SQLException e) {
			loggy.error("Error happened when a user try to update information", e);
			System.out.println("SQLException occur during reimbursement request operation : " +e);
		}

		
	}


	@Override
	public Reimbursement getReimbursement(int reimb_id) {
		Reimbursement ticket=null;
		try (Connection conn=DriverManager.getConnection(url, username, password)){
			String sql="SELECT * FROM ERS_REIMBURSEMENT WHERE reimb_id=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ps.setInt(1, reimb_id);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				ticket=new Reimbursement();
				ticket.setReimbId(rs.getInt(1));
				ticket.setReimbAmount(rs.getDouble(2));
				ticket.setReimbSubmitted(rs.getDate(3));
				ticket.setReimbResolved(rs.getDate(4));
				ticket.setReimbDescription(rs.getString(5));
				ticket.setReimbReceipt(rs.getByte(6));
				ticket.setReimbAuthor(rs.getInt(7));
				ticket.setReimbResolver(rs.getInt(8));
				ticket.setReimbStatusId(rs.getInt(9));
				ticket.setReimbTypeId(rs.getInt(10));
				
			}
		}catch(SQLException e) {
			
			System.out.println("SQLException occur during getreimbursement operation : " +e);
		}
		
		return ticket;
	}

}
