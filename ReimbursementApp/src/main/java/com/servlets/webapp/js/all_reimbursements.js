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
    let qkeys = [],
        qvals = [];
    qmap = new Map;
    let keyvals = query.split("&");
    keyvals.forEach(function (element, index) {
        keyvals[index] = element.split("=")
    })
    for (let i = 0; i < keyvals.length; i++) {
        qkeys[i] = keyvals[i][0];
        qvals[i] = keyvals[i][1];
        qmap.set(qkeys[i], qvals[i])
    }
    if (qmap.has('username')){
        username = qmap.get('username');
        userListItem = document.createElement('li');
        userListItem.className = 'nav-item';
        userListItem.innerHTML = `<p class='nav-label'>Welcome ${username}<\p>`;
        dropdown = document.getElementById('core-dropdown');
        dropdown.insertBefore(userListItem, dropdown.firstChild);
    }
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
    if (submission_response != undefined && submission_response != null) {
        //swal(`${submission_response}`);
        if (submission_response_type != undefined && submission_response_type != null) {
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
        // TODO: redirect without submission response parameters so that going back from allReimbursments page does not retrigger alert
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

function get_reimbursement_info() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            reimbursements = JSON.parse(xhr.responseText)
            if (reimbursements.length == 0) {
                document.getElementById('all-reimbursements-title').innerHTML = 'No Records Were Found'
                return;
            } else {
                for (i in reimbursements) {
                    data = reimbursements[i];
                    create_card_for(data);
                }
            }
        } else {
            data = 'Error'
        }
    }
    xhr.open('POST', 'http://localhost:8080/Reimbursements/AllReimbursements', true);
    xhr.send();
}

function create_card_for(reimbursement) {
    card = document.createElement('div')
    card.className = 'section'

    let comments = (reimbursement.comments == null || reimbursement.comments == "null") ? "N/A" : reimbursement.comments;
    let status = reimbursement.status.charAt(0).toUpperCase() + reimbursement.status.slice(1);
    let statusColor;
    if (status == 'Pending'){
        statusColor = "color:rgb(108, 49, 218)";
    }

    card.innerHTML = `
            <div class='row'>
                <div class='col-sm-6 col-sm-offset-2'>
                    <div class='card'>
                        <div class='card-block'>
                            <span> 
                            <h4 class='card-header column-text' style='color:rgba(12, 84, 112, 0.966)'>Reimbursement Status:<span style=\"${statusColor}\"> ${status}<\span></h4> 
                            </span>
                            <div class="grid-container first" style='grid-auto-flow: row'>
                                <div class='item1'>
                                    <p class='column-text'>Expense</p>
                                </div>
                                <div class='item2'>
                                    <p class='value-text'>${reimbursement.expense}</p>
                                </div>
                            </div>
                            <div class="grid-container" style='grid-auto-flow: row'>
                                <div class='item1'>
                                    <p class='column-text'>Source</p>
                                </div>
                                <div class='item2'>
                                    <p class='value-text'>${reimbursement.source}</p>
                                </div>
                            </div>
                            <div class="grid-container" style='grid-auto-flow: row'>
                                <div class='item1'>
                                    <p class='column-text'>Amount</p>
                                </div>
                                <div class='item2'>
                                    <p class='value-text'>$${(reimbursement.amount).toFixed(2)}</p>
                                </div>
                            </div>
                            <div class="grid-container last" style='grid-auto-flow: row'>
                                <div class='item1 last'>
                                    <p class='column-text'>Comments</p>
                                </div>
                                <div class='item2 last'>
                                    <p class='value-text'>${reimbursement.comments}</p>
                                </div>
                            </div>
                            <!--
                            <p class='card-text'> 
                                <span><p class='column-text'>Expense<p class='value-text'> ${reimbursement.expense}</p></p></span>
                            </p>
                            <p class='card-text'> 
                                <span><p class='column-text'>Source<p class='value-text'>    ${reimbursement.source}</p></p></span>
                            </p>
                            <p class='card-text'> 
                                <span><p class='column-text'>Amount<p class='value-text'> $${(reimbursement.amount).toFixed(2)}</p></p></span>
                            </p>
                            <p class='card-text'> 
                            <span><p class='column-text'>Comments<p class='value-text'> ${comments}</p></p></span>
                            </p>
                            -->
                        </div> 
                    </div> 
                </div> 
                <div class='col-sm-6'>
                <a href=${"http://localhost:8080/Reimbursements/uploads/" + reimbursement.relativePath} target="_self" src=${"http://localhost:8080/Reimbursements/uploads/" + reimbursement.relativePath}>
                    <img class='card-img-bottom' src=${"http://localhost:8080/Reimbursements/uploads/" + reimbursement.relativePath} alt='Unable To Retrive Image From Database' title=${reimbursement.relativePath}>
                </a>
                </div> 
            </div>  
    `
    document.getElementById('reimbursement-cards').appendChild(card);

};

function redirect_to_image_location(){

}