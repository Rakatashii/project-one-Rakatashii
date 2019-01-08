// AJAX = Asynchronous JavaScript and XML
let xhr = new XMLHttpRequest();

// Handle the Response that we will get back from the HTTP Request.
xhr.onreadystatechange = function(){
    if (this.readyState === 4 && this.status === 200){
        console.log(xhr.responseText);
        //var skywalkerJSON = xhr.responseText.toString();
        console.log(skywalkerJSON);
    } else {
        console.log('Error');
    }
}

// Details of the Request.

// Can access local files as well - such as luke.json... 
// When we make the request, in the first place, all we are asking for is a file anyway.
xhr.open('GET', 'https://swapi.co/api/people/1/');
// How to get the Luke.json?

// Make the Request Call.
xhr.send();


// ^ Is basically Equiv to V (last two blocks)
/*
xhr.onload = function(){
    console.log(xhr.responseText);
}
// equivalent to: */
/*
xhr.onload = () => {
    console.log(xhr.responseText);
}

xhr.onerror = () => {
    console.log('Error')
}
*/

/*
// Sneding data in POST
let postXHR = new XMLHttpRequest();
postXHR.open('POST', "url")
// etc...
*/