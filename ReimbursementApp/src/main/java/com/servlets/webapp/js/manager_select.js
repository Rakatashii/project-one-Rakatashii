var username;
var remember_manager;
var logout;
var manager_logged_in;
var submission_response;
var submission_response_type;

manager_login();
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

function make_row(eid){
    table = document.getElementById('table');
    new_row = document.createElement('row');
    new_row.innerHTML = `
    <form>
        <label for="employee${eid}" class=""></label>
        <input type="text" name="employee${id}" class="" 
    </form>
    `
}