let memberId = null;

document.addEventListener('DOMContentLoaded', () => {

   memberId = getQueryParam('id');
   if(!memberId){
      console.error('No member ID specified in the URL');
      return;
   }

   const url = `http://localhost:8080/api/members/${memberId}`;

   fetch(url)
   .then(response =>{
     if(!response.ok){
       throw new Error('Network response was not ok')
     }
     return response.json();
   })
   .then(data => {
      displayMemberDetails(data);
      // fetch checkout books as well
   })
   .catch(error => {
    console.error('An error occured');
    alert('An error occured');
   })

});

function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);  // reterive all params
    return urlParams.get(param);
}


function displayMemberDetails(member){
   document.getElementById('memberFullName').textContent = `${member.firstName} ${member.lastName}`;
   document.getElementById('cardNumber').textContent = `${member.id}`;

   let memberInfo = '';
   let address = 'N/A';
   if(member.address){
      if(memberInfo){
         memberInfo = `(member.address.memberInfo)`;
      }
      else{
        address = `${member.address.streetNumber} ${member.address.streetName}, ${member.address.zipCode} ${member.address.placeName} ${member.address.country} ${memberInfo}`;
      }
      
   }


   document.getElementById('address').textContent = address;
   document.getElementById('phone').textContent = `${member.phone}`;
   document.getElementById('email').textContent = `${member.email}`;

   let dob = new Date(member.dateOfBirth).toLocaleDateString("en-US")
   document.getElementById('dob').textContent = dob;

   let membershipStarted = new Date(member.membershipStarted).toLocaleDateString("en-US")
   document.getElementById('membershipStarted').textContent = membershipStarted;

   let membershipEnded = member.membershipEnded ? new Date(member.membershipEnded).toLocaleDateString("en-US"): '--';
   document.getElementById('membershipEnded').textContent = membershipEnded;

   document.getElementById('memberStatus').textContent = member.isActive ? 'Active' : 'Terminated';
}