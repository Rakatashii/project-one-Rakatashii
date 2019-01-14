var username;
var password;
var remember_employee;
var logout;
var logged_in;
var submission_response;

set_vars();
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

function submission_response_alert() {
    if (submission_response != undefined && submission_response != null){
        swal(`${submission_response}`);
        swal('heeyyy')
        swal(submission_response);
        submission_response = null;
    }
}