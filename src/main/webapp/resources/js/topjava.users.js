const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function changeStatus() {
    // let checkbox = $('#status');
     $('input:checkbox').change(function () {
         enable($(this).is(':checked'), $(this).closest('tr').attr("id"));
     });
    //enable($(this).is(':checked'), $(this).closest('tr').attr("id"));
}

function enable(enabled, id) {
    $.ajax({
        url: ctx.ajaxUrl + "changeStatus?enabled=" + enabled + "&id=" + id,
        type: "PUT"
    }).done(function () {
        successNoty("Updated");
    });
}

function updateTable() {
    $.get(ctx.ajaxUrl, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}