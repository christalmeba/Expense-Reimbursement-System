var ourRequest = new XMLHttpRequest();
ourRequest.open('GET','/ERS_Project_1/forwarding/login');
ourRequest.onload=function(){
    console.log(ourRequest.responseText);
};
ourRequest.send();