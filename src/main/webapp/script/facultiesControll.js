$(document).ready(function () {
    $("a[id*='delete-']").click(function (e) {
        e.preventDefault();
        $.deleteFaculty($(this));
    });

    $.addFaculty();
    $.updateFaculty();


    $("a[id*='update-']").click(function (e) {
        e.preventDefault();
        $.getFacultyData($(this));
    });




});

jQuery.extend({
        deleteFaculty: function (target) {
            $.ajax({
                method: 'post',
                contentType: 'application/x-www-form-urlencoded',
                url: 'deleteFaculty.do',
                data: {
                    'faculty-id': target.attr('value')
                },
                success: function (data) {
                    errorNotification(data);
                },
                complete: function () {
                    target.parent().parent().remove();
                }
            });
            return false;
        },
        addFaculty: function () {
            var form = $("#addFacultyForm");
            form.submit(function (event) {
                event.preventDefault();
                $.ajax({
                    url: form.attr('action'),
                    method: form.attr('method'),
                    data: form.serialize(),
                    dataType: 'json',
                    beforeSend: function () {
                        $("#addFaculty").addClass("loading-form").val("Adding...");
                    },
                    success: function (data) {
                        var result = data;
                        $("#faculty-list").append("<tr>" +
                            "<td>" + result.name + "</td>" +
                            fillSubj(result.subjectList)
                            +"<td>" + result.countStateFundedPlace + "</td>" +
                            "<td>" + result.allPlace + "</td>" +
                            "<td><a class='link' href='#' value='" + result.id + "'><span class='glyphicon glyphicon-edit'></span></a></td>" +
                            "<td><a class='link' href='#' id='delete-$" + result.id + "' value='" + result.id + "'><span class='glyphicon glyphicon-trash'></span></a></td>" +
                            "</tr>");
                    },
                    error: function (xhr, status, error) {
                        console.log(xhr);
                        if (xhr.status == 500) {
                            errorNotification("Error when adding faculty");
                        }
                    },
                    complete: function () {
                        $("#addFaculty").removeClass("loading-form").val("Add faculty");
                        $(".modal-backdrop.fade.in").remove();
                        $("#addFacultyModal").removeClass('in').css("display", "none");
                    }

                });
                return false;
            });
        },
        getFacultyData: function (element) {
            var urlStr = element.attr('href'),
                paramValue = urlStr.split("=")[1],
                action = urlStr.split("?")[0];
            $.ajax({
                method: 'get',
                url: action,
                dataType: 'json',
                data: {
                    'faculty-id': paramValue
                },
                success: function (data) {
                    result = data;
                    var i = 0;
                    $("#editFacultyForm input[name='name']").val(result.name);
                    $("#editFacultyForm input[name='count_state_funded_place']").val(result.countStateFundedPlace);
                    $("#editFacultyForm input[name='count_all_place']").val(result.allPlace);
                    $("#editFacultyForm input[name='id']").val(result.id);
                },
                error: function (xhr) {

                }
            });
            return false;

        },
        updateFaculty: function () {
            var form = $("#editFacultyForm");
            form.submit(function (event) {
                event.preventDefault();

                $.ajax({
                    method: form.attr('method'),
                    url: form.attr('action'),
                    dataType: 'json',
                    data: form.serialize(),
                    success: function (data) {
                        var res = data;
                        var row = $("#row-" + res.id +" td");
                        row.eq(0).html(res.name);
                        row.eq(1).html(res.firstSubject.name);
                        row.eq(2).html(res.secondSubject.name);
                        row.eq(3).html(res.thirdSubject.name);
                        row.eq(4).html(res.countStateFundedPlace);
                        row.eq(5).html(res.allPlace);
                        errorNotification(res.message);

                    },
                    complete: function () {
                        $("#myModal").toggleClass("fadeIn fadeOut").css("display", "none");
                        $("#myModal .modal-content").toggleClass("fadeOutUp fadeInDown");

                    }
                });
            });
            return false;
        }
    }
);

function fillSubj(data) {
    var str = "";
    for(var i = 0; i < data.length; i++)
    {
       str += "<td>" + data[i].name + "</td>";
    }
    return str;
}