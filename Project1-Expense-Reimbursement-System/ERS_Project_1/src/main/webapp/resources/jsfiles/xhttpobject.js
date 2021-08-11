
var ourRequest= new XMLHttpRequest();
ourRequest.open("GET", `http://localhost:8080/ERS_Project_1/forwarding/json/user?`);

ourRequest.onload = function(){ 
    console.log(ourRequest.responseText);	
	let user = JSON.parse(ourRequest.responseText);
    let welcome=document.getElementById("welcome");
    welcome.innerText="Welcome "+user.first_name+" "+user.last_name;
    welcome.style.color="blue";
    
if(user.role_id!=2){
  document.getElementById("managerField").setAttribute("disabled","true");
  document.getElementById("employeeId").value=user.users_id;  
}
else 
{
    document.getElementById("managerId").value=user.users_id; 
    document.getElementById("userRoleId").value=user.role_id;
    document.getElementById("userRoleId").style.display = "none";
}

}
ourRequest.send();



/* function viewDetail()
{
    let myElement=document.getElementById("detail").value;
    console.log(myElement);
}
 */

function updateRequest(eve){

    eve.preventDefault();
    
     let xhttp = new XMLHttpRequest();
 
     xhttp.open("PUT", `http://localhost:8080/ERS_Project_1/forwarding/request-reimbursement`);
 
     xhttp.setRequestHeader("content-type", "application/json");
     
     xhttp.send(JSON.stringify(ourDOMManipulation2()));
     document.getElementById('aName').value="";
     document.getElementById('amount').value="";
     document.getElementById('rType').value="";
     document.getElementById('aStatus').value="";
     document.getElementById('description').value="";
 }
 
 function ourDOMManipulation2(){


 var  st_Id=3;
 if(document.getElementById('denied').checked){
    st_Id=document.getElementById('denied').value;
    }
 if(document.getElementById('approved').checked){
    st_Id=document.getElementById('approved').value;
    }

 let request = {
       "reimbId":document.getElementById("reimbId").value ,
        "reimbResolver": document.getElementById("userRoleId").value,
        "reimbStatusId": st_Id

     }
 
     return request;
     
 }


var myJSONObject;
var myJSONUsers;
function viewAllTickets(){
    fetch('http://localhost:8080/ERS_Project_1/forwarding/json/allReimburmentsView')
    .then(function(daResponse){
                const convertedResponse = daResponse.json();
                return convertedResponse;
            })
    .then(function(daSecondResponse){
        myJSONUsers=daSecondResponse[1];
        myJSONObject=daSecondResponse[0];
                ourDOMManipulation1(daSecondResponse[0]);
            })

}

window.onload = function(){ 
    document.getElementById("reimbId").style.display = "none";

    document
        .getElementById("submitRequest")
        .addEventListener('click', updateRequest);

    document
        .getElementById("reimbStatus")
        .addEventListener('change', filterReimbursement);
   // document
    //    .getElementById("detail")
    //    .addEventListener('click', viewDetail);
    
    viewAllTickets();
        
}
function getUserFullName(iduser){
    let fullName;
    for(let x of myJSONUsers){
        if (x.users_id==iduser){
            fullName=x.first_name+" "+x.last_name;
        }
    }

    return fullName;
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
        newTR.setAttribute("name","tbr");
		let newTH = document.createElement("th");
		
		let newTD1 = document.createElement("td");
		let newTD2 = document.createElement("td");
		let newTD3 = document.createElement("td");
        let newTD4 = document.createElement("td");
        let newTD5 = document.createElement("td");

		
		//step 2: populate creations
		newTH.setAttribute("scope", "row");
        let date=new Date(ourJSON[i].reimbSubmitted);
		let myTextH = document.createTextNode(date.toLocaleDateString());

		let myTextD1 = document.createTextNode(ourJSON[i].reimbAmount);

		let myTextD2 = document.createTextNode((ourJSON[i].reimbDescription).substr(0,25)+" ...");
        
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
        
        let newB=document.createElement("input");
        //let newA=document.createElement("a");
        newB.setAttribute("type","button")
        //newA.setAttribute("id","detail"+ourJSON[i].reimbId)      
        newB.setAttribute("id",i);
        //newA.setAttribute("href",ourJSON[i].reimbId) 
        newB.setAttribute("value","details");
        
        //let aNode=document.createTextNode('detail');
        //newA.appendChild(newB);
        //newA.appendChild(aNode);
        //let myTextD5 = document.createTextNode("");
		
		//all appending
		newTH.appendChild(myTextH);
		newTD1.appendChild(myTextD1);
		newTD2.appendChild(myTextD2);
		newTD3.appendChild(myTextD3);
        newTD4.appendChild(myTextD4);
        newTD5.appendChild(newB);
		
		newTR.appendChild(newTH);
		newTR.appendChild(newTD1);
		newTR.appendChild(newTD2);
		newTR.appendChild(newTD3);
        newTR.appendChild(newTD4);
        newTR.appendChild(newTD5);
		
		let newSelection = document.querySelector("#myVillTable");
		newSelection.appendChild(newTR);
        newB.addEventListener('click',viewDetail);
        
	}
}


function viewDetail(e)
{

       var n = e.target.getAttribute("id");   //index of object in our JSON object
        console.log(myJSONObject[n]);
 document.getElementById('aName').value=getUserFullName(myJSONObject[n].reimbAuthor);
 document.getElementById('reimbId').value=myJSONObject[n].reimbId;
 document.getElementById('amount').value=myJSONObject[n].reimbAmount;
 document.getElementById('description').value=myJSONObject[n].reimbDescription;

 let reimbType;
 switch(myJSONObject[n].reimbTypeId){
     case 1: reimbType="Food"; break;
     case 2: reimbType="Lodging"; break;
     case 3: reimbType="Travel"; break;
     case 4: reimbType="Other"; break;
 }
 document.getElementById('rType').value=reimbType;

 let reimbStatus;
        switch(myJSONObject[n].reimbStatusId){
        case 1: reimbStatus="Approved"; break;
        case 2: reimbStatus="Denied"; break;
        case 3: reimbStatus="Pending"; break;
        }
document.getElementById('aStatus').value=reimbStatus;

}

function createTable(){
    // create a new table element
    let newTable = document.createElement("table");
    newTable.setAttribute("id","myVillTable");
    newTable.setAttribute("class","table");
    let mydiv=document.getElementById("divtable");
    mydiv.appendChild(newTable);
    //create a new ligne for table head
    let newTrow=document.createElement("tr");
    newTable.appendChild(newTrow);
    // create a column head
    let newth1=document.createElement("th");
    let myH1 = document.createTextNode("Submitted date");
    newth1.appendChild(myH1);
    newTrow.appendChild(newth1);

    let newth2=document.createElement("th");
    let myH2 = document.createTextNode("Amount request");
    newth2.appendChild(myH2);
    newTrow.appendChild(newth2);

    let newth3=document.createElement("th");
    let myH3 = document.createTextNode("Description");
    newth3.appendChild(myH3);
    newTrow.appendChild(newth3);

    let newth4=document.createElement("th");
    let myH4 = document.createTextNode("Type of reimbursement");
    newth4.appendChild(myH4);
    newTrow.appendChild(newth4);

    let newth5=document.createElement("th");
    let myH5 = document.createTextNode("Actual status");
    newth5.appendChild(myH5);
    newTrow.appendChild(newth5);

    let newth6=document.createElement("th");
    let myH6 = document.createTextNode("view details");
    newth6.appendChild(myH6);
    newTrow.appendChild(newth6);

    

}


function filterReimbursement(e){
    
    
    var element = document.getElementById("myVillTable");
    element.parentNode.removeChild(element);

    // get the value of selected option
    var reimbSts = this.value;
    
    if(reimbSts!=0){

    // filter my JSON Object based on selected option
    var newFilterJSONObject = myJSONObject.filter(
        (myObj, myIndex, myArray)=>{

            return myObj.reimbStatusId==reimbSts;

        }
    );
    }
    else{
        newFilterJSONObject=myJSONObject;
    }
    createTable();
    ourDOMManipulation1(newFilterJSONObject);


}



