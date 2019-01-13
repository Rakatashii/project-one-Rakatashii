var employee_username;
var employee_password;
var remember_me = false;

employee_login();

function employee_login() {
    document.getElementById("employee-login-form").innerHTML = `
    <div class="container employee-login">
        <h1>Reimbursements</h1>
        <form class="form-signin text-center" action="../EmployeeServlet" enctype="multipart/form-data" method="POST">
            <h1 class="h3 mb-3 font-weight-normal">Employee Sign In</h1>

            <label for="inputEmail" class="sr-only">Email address</label>
            <input type="text" name="username" id="username" class="form-control" placeholder="Username"
                required autofocus>

            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" name="password" id="password" class="form-control" placeholder="Password"
                required>

            <div class="checkbox mb-3">
                <label>
                    <input type="checkbox" name="remember_login" value="true"> Remember me
                </label>
            </div>

            <button class="submit-btn btn btn-lg btn-block" type="submit" active>Sign in</button>
        </form>
    </div>
    `;
}

/*
function authenticateEmployee(username, password){
    if(username.value == "") {
        alert("Username cannot be blank! Please enter in a valid username.");
    } else if(password.value == ""){
        alert("Password cannot be blank! Please enter in a valid password.");
    } else {

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

        xhr.open("POST", "http://localhost:8080/ExpenseReimbursementSystem/authentication", false);

        let credentialArray = [username.value, password.value, "employee"];

        xhr.send(credentialArray);

     })();

    }
}
*/



