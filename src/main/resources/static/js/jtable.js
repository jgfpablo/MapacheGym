$(document).ready(function () {
    $("#tabla").DataTable({
        language: {
            url: "//cdn.datatables.net/plug-ins/1.11.5/i18n/es-ES.json",
        },
        searching: true,
        pageLength: 5,
        lengthMenu: [5, 10, 25, 50],
        pagingType: "simple",
    });
});
