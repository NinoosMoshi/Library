document.addEventListener('DOMContentLoaded', () => {

    const form = document.querySelector('form');

    form.addEventListener('submit', function(ev) {
        ev.preventDefault();

        // get today's date in YYYY-MM-DD format
        const today = new Date().toISOString().split('T')[0];
        console.log('Today: ', today);


        const formData = {
          firstName: document.getElementById('firstName').value,
          lastName: document.getElementById('lastName').value,
          dateOfBirth: document.getElementById('dob').value,
          address: {
            streetName:document.getElementById('streetName').value,
            streetNumber:document.getElementById('streetName').value,
            zipCode:document.getElementById('zipCode').value,
            placeName:document.getElementById('placeName').value,
            country:document.getElementById('country').value,
            additionalInfo:document.getElementById('addInfo').value
          },
          email: document.getElementById('email').value,
          phone: document.getElementById('phone').value,
          membershipStarted: today,
          isActive: true,
          barcodeNumber: document.getElementById('barcode').value
      };

        addNewMember(formData, form);
     });
});


function addNewMember(data, form) {
    console.log('Attempting to add new member', data);

    fetch('http://localhost:8080/api/members/addMember', {
        method:'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      })
      .then(response => {
        if(!response.ok) {
            throw new Error('Failed to add new member');
        }
        return response.json();
      })
      .then(data => {
        console.log('Member added successfully: ', data);
        alert('New Member added successfully!');
        form.reset();
      })
      .catch(error =>{
        console.error('Error:', error);
        alert('An error occured.');
      });

}

