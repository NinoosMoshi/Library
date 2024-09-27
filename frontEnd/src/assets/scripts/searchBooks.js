document.addEventListener('DOMContentLoaded', () => {

    const form = document.querySelector('form');

    form.addEventListener('submit', function(ev) {
        ev.preventDefault();

        // retrieve input values from the from
        const barcodeNumber = document.getElementById('barcodeNumber').value;
        const isbn = document.getElementById('isbn').value;
        const author = document.getElementById('author').value;
        const title = document.getElementById('title').value;

        // construct the API request URL with query parameters
        const url = `http://localhost:8080/api/books/search?barcodeNumber=${encodeURIComponent(barcodeNumber)}&isbn=${encodeURIComponent(isbn)}&author=${encodeURIComponent(author)}&title=${encodeURIComponent(title)}`;

        // make API request using fetch
        fetch(url)
        .then(response => {
            if(!response.ok) {
                alert('Network response was not ok.');
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data =>{
            console.log('Search result:', data);
            displayResults(data);
        })
        .catch(error => {
            console.error('Error fetching data: , error');
            alert('An error occured.');
        });

    });


});

function displayResults(data) {
    
    let html = '<table><tr><th>Title</th><th>Author</th><th>Available copies</th></tr>';
 
    data.forEach(book =>{
 
       html += `<tr>
                  <td>${book.title}</td>
                  <td>${book.author}</td>
                  <td>${book.noOfAvailableCopies}</td>
                  <td><a href="book-info.html?id=${book.id}">View Details</a></td>
                </tr>`
 
    });
 
    html += '</table>';
 
    const resultsContainer = document.getElementById('resultsContainer');
 
    resultsContainer.innerHTML = html;

}