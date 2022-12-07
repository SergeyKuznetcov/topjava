const allMealsAjaxUrl = "ajax/meals/";
const filterAjaxUrl = allMealsAjaxUrl + "filter?";
let useFilter = false;

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: allMealsAjaxUrl,
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
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

function dropFilter() {
    $('#filter').trigger('reset');
    useFilter = false;
    updateTable();
}

function getFiltered() {
    $.ajax({
        type: "GET",
        url: filterAjaxUrl,
        data: $('#filter').serialize()
    }).done(function () {
        useFilter = true;
        updateTable();
        successNoty("filtered");
    });
}

function updateTable() {
    if (useFilter) {
        update(filterAjaxUrl + $('#filter').serialize());
    } else {
        update(allMealsAjaxUrl);
    }
}

function update(url) {
    $.get(url, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}