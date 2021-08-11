
window.onload = function(){ 
    document
        .getElementById("submitRequest")
        .addEventListener('click', makeRequest);
    document
        .getElementById("viewTickets")
        .addEventListener('click', viewUserPastTickets);
        
}

function makeRequest(eve){

   eve.preventDefault();
   
    let xhttp = new XMLHttpRequest();

    xhttp.open("PUT", `http://localhost:8080/ERS_Project_1/forwarding/request-reimbursement`);

    xhttp.setRequestHeader("content-type", "application/json");
    
    xhttp.send(JSON.stringify(ourDOMManipulation()));
    document.getElementById("amount").value="";
    document.getElementById("description").value="";
}

function ourDOMManipulation(){
    let amount=document.getElementById("amount").value;
    console.log(amount);
if ((amount >0)&&(amount<10000)){
let request = {
        "reimbAmount":document.getElementById("amount").value ,
        "reimbDescription": document.getElementById("description").value,
        "reimbAuthor": document.getElementById("employeeId").value ,
        "reimbTypeId": document.getElementById("reimb_type").value
    }
    
    // document.getElementById("pokeImage").setAttribute("src", ourObjectFromJSON.sprites.front_default);

    return request;
}
else
    alert("the amount should be greater than 0 or less than 10000");
}



function viewUserPastTickets(){

	fetch('http://localhost:8080/ERS_Project_1/forwarding/json/user-request')
		.then(function(daResponse){
					const convertedResponse = daResponse.json();
					return convertedResponse;
				})
		.then(function(daSecondResponse){
					ourDOMManipulation1(daSecondResponse);
				})
    document.getElementById("viewTickets").setAttribute("disabled","true"); 
}

function ourDOMManipulation1(ourJSON){
	//we are about to do some HEAVY DOM manipulation
	
	/*
		you COULD check to see if they are a employee or manager then dynamically add new buttons
		and/or html elements
	*/
	
	for(let i = 0; i< ourJSON.length; i++){
		////////////CREATE ELEMENTS DYNAMICALLY////////////////
		
		//step1: creating our new element
		let newTR = document.createElement("tr");
		let newTH = document.createElement("th");
		
		let newTD1 = document.createElement("td");
		let newTD2 = document.createElement("td");
		let newTD3 = document.createElement("td");
        let newTD4 = document.createElement("td");
		
		//step 2: populate creations
		newTH.setAttribute("scope", "row");
        let date=new Date(ourJSON[i].reimbSubmitted);
		let myTextH = document.createTextNode(date.toLocaleDateString());

		let myTextD1 = document.createTextNode(ourJSON[i].reimbAmount);

		let myTextD2 = document.createTextNode(ourJSON[i].reimbDescription);
        
        // get a string for type of reimbursement
        let reimbType;
        switch(ourJSON[i].reimbTypeId){
            case 1: reimbType="Food"; break;
            case 2: reimbType="Lodging"; break;
            case 3: reimbType="Travel"; break;
            case 4: reimbType="Other"; break;
        }

		let myTextD3 = document.createTextNode(reimbType);

    // get a string for status of reimbursement
        let reimbStatus;
        switch(ourJSON[i].reimbStatusId){
        case 1: reimbStatus="Approved"; break;
        case 2: reimbStatus="Denied"; break;
        case 3: reimbStatus="Pending"; break;
        }

        let myTextD4 = document.createTextNode(reimbStatus);
		
		//all appending
		newTH.appendChild(myTextH);
		newTD1.appendChild(myTextD1);
		newTD2.appendChild(myTextD2);
		newTD3.appendChild(myTextD3);
        newTD4.appendChild(myTextD4);
		
		newTR.appendChild(newTH);
		newTR.appendChild(newTD1);
		newTR.appendChild(newTD2);
		newTR.appendChild(newTD3);
        newTR.appendChild(newTD4);
		
		let newSelection = document.querySelector("#myVillTable");
		newSelection.appendChild(newTR);
	}
}
