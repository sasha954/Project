$(document).ready(function () {
    $(".sign-in-button").click(function (e) {
        e.preventDefault();
    });
    fillRegInFaculty();
    registrationOnFaculty();

   // sort();
    showModal();


});
function sort() {
    $(".sort-btn").click(function (e) {
        e.preventDefault();
        var urlStr = window.location.href + $(this).attr('href');
        var url = new URL(urlStr);
        var order = url.searchParams.get("orderField");
        var isDesc = url.searchParams.get("isDesc");
       $.ajax({
           method: 'post',
           url: 'sort.do',
           dataType: 'json',
           data :{
               'orderField' : order,
               'isDesc' : isDesc
           },
           success : function (data) {

           }
       })
    });

}
function registrationOnFaculty() {
    var form = $("#reg-faculty-form");
    form.submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: form.serialize(),
            dataType: 'json',
            success: function (data) {
                errorNotification("User was registered on faculty successfully.");
            },
            error: function (xhr) {
                if (xhr.status === 409) {
                    var result = xhr.responseText;
                    errorNotification(result)
                }
                if (xhr.status === 500) {
                    var message = "You should fill all mark fields.";
                    errorNotification(message);
                }
            },
            complete: function () {
                $(".modal-backdrop.fade.in").remove();
                $("#regFacultyModal").removeClass('in').css("display", "none");
                window.location.reload();
            }
        });
        return false;
    })
}

function errorNotification(message) {

    $("#notification-message").text(message);
    $(".notification").addClass('animated').toggleClass("fadeOutDown fadeInUp");
    setTimeout(function () {
        $(".notification").toggleClass("fadeOutDown fadeInUp");
    }, 4000);
}

function fillRegInFaculty() {

    $(".faculty-reg").on('click', function () {
        $("label[id*='subj-mark-']").text("");
        var subjects = $(this).parent().parent().find("li[id*='subject-']");
        console.log($(subjects[0]).text());
        var firstSubject = $(subjects[0]).text(),
            secondSubject = $(subjects[1]).text(),
            thirdSubject = $(subjects[2]).text(),
            fourthSubject = $(subjects[3]).text(),
            facultyId = $(this).val();


        var labals = $("label[id*='subj-mark-");
        $(labals[0]).text(firstSubject);
        $(labals[1]).text(secondSubject);
        $(labals[2]).text(thirdSubject);
        $(labals[3]).text(fourthSubject);

        var inputs = $("input[id*='subj-mark-']");
        $(inputs[0]).attr('name', firstSubject);
        $(inputs[1]).attr('name', secondSubject);
        $(inputs[2]).attr('name', thirdSubject);
        $(inputs[3]).attr('name', fourthSubject);

        $("input#faculty-id").val(facultyId);
    });

}

function showModal() {
    var modal = document.getElementById('myModal');

    var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
    $("a[id*='update-']").each(function () {
            $(this).click(
                function () {
                    $("#myModal").addClass("animated").toggleClass("fadeOut fadeIn");
                    modal.style.display = "block";
                    $(".myModal .modal-content").addClass('animated').toggleClass("fadeOutUp fadeInDown");
                }
            )
        }
    );

// When the user clicks on <span> (x), close the modal
    if (typeof span !== 'undefined' && span !== null) {
        span.onclick = function () {
            modal.style.display = "none";
        }
    }

// When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == modal) {
            $("#myModal").toggleClass("fadeOut fadeIn");
            $(".myModal .modal-content").toggleClass("fadeOutUp fadeInDown");
            setTimeout(function () {
                modal.style.display = "none";
            }, 500);

        }
    }


}
