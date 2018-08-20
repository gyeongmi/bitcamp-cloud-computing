'use strict'

var serverApiAddr = "http://localhost:8080/bitcamp-assignment-T4-last";

$(() => {
    $('footer').load(`${serverApiAddr}/html/footer.html`)
});
