const allMealsAjaxUrl = "ajax/meals/";
const filterAjaxUrl = allMealsAjaxUrl + "filter"

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: allMealsAjaxUrl
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

function getBetween() {
    let startDate = $('#startDate').val();
    let endDate = $('#endDate').val();
    let startTime = $('#startTime').val();
    let endTime = $('#endTime').val();
    ctx.ajaxUrl = filterAjaxUrl + "?startDate=" + startDate + "&endDate=" + endDate + "&startTime=" + startTime + "&endTime=" + endTime;
    updateTable();
    successNoty("filtered");
    ctx.ajaxUrl = allMealsAjaxUrl
}

function updateWithFilter() {

}

function setRootUrl() {
    ctx.ajaxUrl = allMealsAjaxUrl;
}

function setFilterUrl() {
    ctx.ajaxUrl = filterAjaxUrl + "?startDate=" + $('#startDate').val() + "&endDate=" +
        $('#endDate').val() + "&startTime=" + $('#startTime').val() + "&endTime=" + $('#endTime').val();
}

function checkFilterParams() {
    return $('#startDate').val() != null || $('#endDate').val() != null || $('#startTime').val() != null || $('#endTime').val() != null;
}

function dropFilter() {
    $('#startDate').val(null);
    $('#endDate').val(null);
    $('#startTime').val(null);
    $('#endTime').val(null);
    updateTable();
}

function updateTable() {
    if (checkFilterParams()) {
        setFilterUrl();
        update();
        setRootUrl();
    }else {
        update();
    }
}

function update() {
    $.get(ctx.ajaxUrl, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}