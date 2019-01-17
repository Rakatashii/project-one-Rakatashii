var username;
var password;
var remember_employee;
var logout;
var logged_in;
var submission_response;
var submission_response_type;

employee_login();
set_vars();

(function () {
    if (submission_response_type != undefined && submission_response_type == "login_error"){
        submission_response_type = 'error';
        if (submission_response != undefined && submission_response != null){
            let alert_title = 'Error';
            swal({
                type: submission_response_type,
                title: alert_title,
                text: submission_response,
                timer: 2250
            });
        } else {
            swal(submission_response);
        }
        submission_response_type = null;
        submission_response = null;
        qmap.set('submission_response', null);
        qmap.set('submission_response_type', null);

        setTimeout(function() {
            window.location.href = window.location.toString().replace(query, "").replace("?", "");
        }, 2300);
    }
} ());

function animate_popup(){
    popup = document.getElementById('popup_spot');
    popup.innerHTML = `
        <span id='popup' selector:'[rel="popover"]' type="button" class="popover arrow" data-container="body" data-toggle="popover" data-placement="bottom" 
            data-content="Use The Dropdown Panel To Select Menu Options.">
        </span>`
    //setTimeout($('#popup').popover('hide'), 500);
};
animate_popup();
$(function () {
    $('#popup').popover({
      container: 'body',
      delay: 1,
      animation: true
    })
})
$(document).ready(function () {
    showPopup = setTimeout(
        function(){
            $('.popover').popover('show');
        } 
    ,1500);
    hidePopup = setTimeout(
        function(){
            $('.popover').popover('hide');
            popup.setAttribute('display', 'none');
        }
    ,5000);
});


function employee_login() {
    document.getElementById("employee-login-form-container").innerHTML = `
    
    <div class="container employee-login">
    	<h1 id="employee-login-form-title">Employee Portal</h1>
        <form id="employee-login-form" onSubmit="authenticateEmployee(username, password)" action="../EmployeeLogin" class="form-signin text-center" enctype="multipart/form-data"> 
            <h1 class="h3 mb-3 font-weight-normal" style="transform:scale(1.0); white-space:pre">Please  Sign In</h1>

            <label for="inputEmail" class="sr-only">Email address</label>
            <input type="text" name="username" id="username" class="form-control" placeholder="Username"
                required autofocus>

            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" name="password" id="password" class="form-control" placeholder="Password"
                required>
            <div class="checkbox mb-3">
                <label>
                    <input type="checkbox" name="remember_employee" value="true"> Remember me
                </label>
            </div>
            <button class="submit-btn btn btn-lg btn-block" type="submit" active>Sign in</button>
        </form>
    </div>
    `;
}

function authenticateEmployee(username, password){
    if(username.value.length > 20) {
        alert("Username is too long! Please enter in a valid username.");
    } else if(password.value.length > 20){
        alert("Password is too long! Please enter in a valid password.");
    } else if(username.value.length > 0 && password.value.length > 0){
        
        document.getElementById('employee-login-form').setAttribute("method", "POST");
        document.getElementById('employee-login-form').method = "POST";
        xhr.onreadystatechange = function(){
            if (this.readyState === 4 && this.status === 200){
                console.log(xhr.responseText);
                //var skywalkerJSON = xhr.responseText.toString();
                console.log(skywalkerJSON);
            } else {
                console.log('Error');
            }
        }
    }
}

function submission_response_alert() {
    if (submission_response != undefined && submission_response != null){
        if (submission_response_type != undefined && submission_response_type != null){
            let alert_title = (submission_response_type == 'success') ? 'Completed' : submission_response_type.charAt(0).toUpperCase() + submission_response_type.slice(1);
            setTimeout(swal({
                type: submission_response_type,
                title: alert_title,
                text: submission_response
            }), 2000);
        } else {
            swal(submission_response);
        }

        submission_response = null;
        submission_response_type = null;
    }
}

function set_vars() {
    query = window.location.search.substring(1);
    let qkeys = [], qvals = [];
    qmap = new Map;
    let keyvals = query.split("&");
    keyvals.forEach(function(element, index){
        keyvals[index] = element.split("=")
    })
    for (let i = 0; i < keyvals.length; i++){
        qkeys[i] = keyvals[i][0];
        qvals[i] = keyvals[i][1];
        qmap.set(qkeys[i], qvals[i])
    }
    if (qmap.has('username')) username = qmap.get('username');
    if (qmap.has('password')) password = qmap.get('password');
    if (qmap.has('remember_employee')) remember_employee = qmap.get('remember_employee');
    if (qmap.has('logout')) logout = qmap.get('logout');
    if (qmap.has('logged_in')) logged_in = qmap.get('logged_in');
    if (qmap.has('submission_response')) submission_response = decodeURI(qmap.get('submission_response'));
    if (qmap.has('submission_response')) {
        submission_response = decodeURI(qmap.get('submission_response'));
        if (qmap.has('submission_response_type')) submission_response_type = decodeURI(qmap.get('submission_response_type'));
    }
};

/*
function display_img_cards(){
    for (let i = 0; i < num_images; i++){
        reimbursements.setAttribute(src) = 
    }
}
*/