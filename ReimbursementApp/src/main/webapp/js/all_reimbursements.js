var username;
var password;
var remember_employee;
var logout;
var logged_in;
var image_error;
var submission_response;
var submission_response_type;

set_vars();
//check_logged_in(); 
get_reimbursement_info();
//submission_response_alert()

set_vars();
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
    if (qmap.has('submission_response')) {
        submission_response = decodeURI(qmap.get('submission_response'));
        if (qmap.has('submission_response_type')) submission_response_type = decodeURI(qmap.get('submission_response_type'));
    }
};

function submission_response_alert() {
    if (submission_response != undefined && submission_response != null){
        //swal(`${submission_response}`);
        if (submission_response_type != undefined && submission_response_type != null){
            let alert_title = (submission_response_type == 'success') ? 'Completed' : submission_response_type.charAt(0).toUpperCase() + submission_response_type.slice(1);
            swal({
                type: submission_response_type,
                title: alert_title,
                text: submission_response
            });
        } else {
            swal(submission_response);
        }
        
        submission_response = null;
        submission_response_type = null;
        qmap.set('submission_response', null);
        qmap.set('submission_response_type', null);
    }
    return true;
}

/*
function check_logged_in() {
    if (qmap.has('submission_response_type') && qmap.get('submission_response_type') == 'login_error'){
        qmap.set('submission_response_type', 'error')
        if (submission_response != undefined && submission_response != null){
            let alert_title = 'Error';
            swal({
                type: submission_response_type,
                title: alert_title,
                text: submission_response
            });
        } else {
            swal(submission_response);
        }
    }
}
*/

function get_reimbursement_info(){
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200){
            res1 = JSON.parse(xhr.responseXML)
            res2 = 'hi'
            res3 = JSON.parse(xhr.responseText)
        } else {
            res = 'Error'
        }
    }
    xhr.open('POST', 'http://localhost:8080/Reimbursements/AllReimbursements', true);
    xhr.send();

}