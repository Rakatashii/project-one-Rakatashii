var username;
var remember_manager;
var logout;
var manager_logged_in;
var submission_response;
var submission_response_type;

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

function checkSelectedIdIsNumeric(id){
    if (Number.parseInt(id) != id) {
        alert("Selection Must Be Numeric.");
        setTimeout(function(){ 
            widow.location.reload();
        }, 2300); 
        // window.reload
    } 
}

// TODO: Select Employees Table

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
        qmap.set('submission_response', null);
        qmap.set('submission_response_type', null);
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
    if (qmap.has('remember_manager')) remember_manager = qmap.get('remember_manager');
    if (qmap.has('logout')) logout = qmap.get('logout');
    if (qmap.has('manager_logged_in')) manager_logged_in = qmap.get('manager_logged_in');
    if (qmap.has('submission_response')) submission_response = decodeURI(qmap.get('submission_response'));
    if (qmap.has('submission_response')) {
        submission_response = decodeURI(qmap.get('submission_response'));
        if (qmap.has('submission_response_type')) submission_response_type = decodeURI(qmap.get('submission_response_type'));
    }
};

get_customer_info();
function get_customer_info() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            employees = JSON.parse(xhr.responseText)
            if (employees.length == 0) {
                document.getElementById('all_employees_table').innerHTML = 'No Records Were Found'
                return;
            } else {
                rows = [];
                for (let i = 0; i < employees.length; i++ ) {
                    /*
                    Client client = 
                        Clients.getClient(clients[i].clientID);
                    BankAccount account = 
                        Accounts.getAccount(clients[i].accountID);
                    */
                    // Need to format balance as dollars
                    employee = employees[i];
                    addRow(employee.employeeID, 20,
                        employee.username, 20,
                        employee.firstname, 20, 
                        employee.lastname, 20, 
                        employee.numReimbursements, 20
                    );
                }
                //tb = document.getElementById("table-body");
                //targetChild = tb.firstElementChild.firstElementChild.nextElementSibling;
                //targetChild.className += "focused";
            }
        } else {
            data = 'Error'
        }
    }
    xhr.open('POST', 'http://localhost:8080/Reimbursements/ManagerSelect', true);
    xhr.send();
}

function addRow(col1, width1, 
                col2, width2,
                col3, width3, 
                col4, width4,
                col5, width5) {
    if (!document.getElementsByTagName) {
        return;
    }
    var x = document.getElementById("entries").rows.length;
    tabBody=document.getElementsByTagName("tbody").item(0);
    row=document.createElement("tr");
    x =`employee${col1}`
    row.id = x
    $(`#${x}`).keyup(function(event) {
        if (event.keyCode === 13) {
            $("#view_selected_id").click();
            $("#view_selected_id").submit();
        }
    });
    cell1 = document.createElement("td");
    cell2 = document.createElement("td");
    cell3 = document.createElement("td");
    cell4 = document.createElement("td");
    cell5 = document.createElement("td");
    cell1.width=width1+"%";
    cell2.width=width2+"%";
    cell3.width=width3+"%";
    cell4.width=width4+"%";
    cell5.width=width5+"%";
    textnode1=document.createTextNode(col1);
    textnode2=document.createTextNode(col2);
    textnode3=document.createTextNode(col3);
    textnode4=document.createTextNode(col4);
    textnode5=document.createTextNode(col5);
    cell1.appendChild(textnode1);
    cell2.appendChild(textnode2);
    cell3.appendChild(textnode3);
    cell4.appendChild(textnode4);
    cell5.appendChild(textnode5);
    row.appendChild(cell1);
    row.appendChild(cell2);
    row.appendChild(cell3);
    row.appendChild(cell4);
    row.appendChild(cell5);
    tabBody.appendChild(row);
    tableHighlightRow();
}

var selected = null;
function tableHighlightRow() {
    if (document.getElementById && document.createTextNode) {
        var tables=document.getElementsByTagName('table');
        for ( var i=0; i<tables.length; i++ ) {
            if ( tables[i].className.toLowerCase() == 'TableListJS'.toLowerCase() ) {
                var trs = tables[i].getElementsByTagName('tr');
                for ( var j=0; j < trs.length; j++) {
                    if (trs[j].parentNode.nodeName.toLowerCase() =='tbody'.toLowerCase()) {
                        /*
                        trs[j].onmouseover = function(){
                                // 'highlight' color is set in tablelist.css
                            if ( this.className === '' || this.className == 'focus') {
                                this.className='highlight';
                            }
                            
                            return false
                        }
                        trs[j].onmouseout=function(){
                            if ( this.className === 'highlight') {
                                else this.className='';
                            }
                            return false
                        }
                        */
                        trs[j].onmousedown=function(){
                            //
                            // Toggle the selected state of this row
                            // 

                            // 'clicked' color is set in tablelist.css.
                            if ( this.className !== 'clicked' ) {
                                // Clear previous selection
                                if ( selected !== null ) {
                                    selected.className='';
                                }
                                // Mark this row as selected
                                
                                this.className='clicked';

                                if (document.getElementsByClassName('clicked').length > 1){
                                    document.getElementsByClassName('clicked')[0].className = ' ';
                                }

                                redirect_on_enter((document.getElementsByClassName('clicked')[0].id));
                               
                               
                                selected = this;
                            } else {
                                this.className='';
                                selected = null;
                            } 
                            return true
                        }
                    }
                }
            }
        }
    }
}


window.onload=function(){
    tableHighlightRow();
}
/*
function make_row(data){
    employee_table = document.getElementById("table-body");
    new_row = document.createElement('tr');

    row_id = data.employeeID;
    new_row.innerHTML = `
    <form action="../Employee./////" method="post" enctype="multipart/form-data">
        <label for="employee_login" class="nav-label">Employee Portal</label>
        <button id="employee_login" name="employee_login" class="nav-link" type="submit">
    </form>
    
    `
    employee_table.appendChild(new_row);
    //redirect_on_enter(new_row.firstElementChild);
    rows.push(new_row)
}
*/

function redirect_on_enter(row_id){
    document.getElementById(row_id)
    .addEventListener("keyup", function(event) {
        event.preventDefault();
        if (event.keyCode === 13) {
            document.getElementById('view_selected_id').click();
        }
    });
}

$(document).keydown(
    function(e)
    {    
        if (e.keyCode == 40) {      
            $(".focusable:focus").next().focus();

        }
        if (e.keyCode == 38) {      
            $(".focusable:focus").prev().focus();

        }
    }
);