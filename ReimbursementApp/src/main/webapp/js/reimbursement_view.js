var username;
var password;
var remember_employee;
var logout;
var logged_in;
var submission_response;
var submission_response_type;

set_vars();
check_logged_in(); 
submission_response_alert()

$(document).ready(function () {
    $(document).on('change', '.btn-file :file', function () {
        var input = $(this)
        var label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [label]);
    });
    $('.btn-file :file').on('fileselect', function (event, label) {
        var input = $(this).parents('.input-group').find(':file'),
            log = label;

        if (input.length) {
            input.val(log);
        } else {
            if (log) alert(log);
        }
    });

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#img-upload').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }
    $("#imgInp").change(function () {
        readURL(this);
    });
});

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
    }
    return true;
}

function check_logged_in(){
    if (logged_in == undefined || logged_in == null){
        var wait = false;
        while (!wait){
            submission_response = 'You Must Log In First.';
            submission_response_type = 'error';
            wait = submission_response_alert();
        }
        (function(){
            let xhr = new XMLHttpRequest();
            
            xhr.onreadystatechange = function() {
                if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                    // TODO: Wait For User To Click Button On Alert.
                    // After The Button Is Clicked, Redirect To Home
                }
            }
            /*
            submission_response = 'You Must Log In First.';
            submission_response_type = 'error';
            submission_response_alert();
            */
            xhr.open("GET", "../EmployeeServlet", false);
            xhr.send();

        })();
    }
}