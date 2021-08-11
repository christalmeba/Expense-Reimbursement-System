package ers_dao;

import java.util.List;

import ers_model.Reimbursement;

public interface ReimbursementDAO {
	
	void requestReimbursement(Reimbursement request);  //add new reimbursement request in the system
	Reimbursement getReimbursement(int reimb_id);
	List<Reimbursement> viewPastTickets(int usersId);
	List<Reimbursement> viewAllReimbursements();
	List<Reimbursement> viewReimbursementsByStatus(int reimbStatusId);
	void approveOrDenyReimbursement(Reimbursement ticket); //update the status of reimbursement (reimb_status_id and reimb resolver)
	
	
	

}
