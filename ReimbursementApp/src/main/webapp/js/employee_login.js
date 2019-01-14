var username;
var password;
var remember_employee;
var logout;
var logged_in;
var image_error;

employee_login();
set_vars();

function employee_login() {
    document.getElementById("employee-login-form-container").innerHTML = `
    
    <div class="container employee-login">
    	<h1 id="employee-login-form-title">Employee Portal</h1>
        <form id="employee-login-form" onSubmit="authenticateEmployee(username, password)" action="../EmployeeServlet" class="form-signin text-center" enctype="multipart/form-data"> <!--action="../EmployeeServlet" method="POST">-->
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

        /*
        (function(){
            let xhr = new XMLHttpRequest();
            
            xhr.onreadystatechange = function() {
                if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                    let userInfo = JSON.parse(xhr.responseText);
                    if(userInfo.auth == "true"){
                        employeeId = userInfo.id;
                        employeeFirstName = userInfo.firstname;
                        employeeLastName = userInfo.lastname;
                        employeeEmailAddress = userInfo.emailaddress;
                        employeeAddress = userInfo.address;
                        employeeLogin();
                    } else {
                        alert("Incorrect credentials, try again!");
                    }
                }
            }

            xhr.open("POST", "../EmployeeServlet", false);

            let credentialArray = [username.value, password.value, remember_login.value];

            xhr.send(credentialArray);

        })();
        */
    }
}

set_vars();
function set_vars() {
    let query = window.location.search.substring(1);
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
};