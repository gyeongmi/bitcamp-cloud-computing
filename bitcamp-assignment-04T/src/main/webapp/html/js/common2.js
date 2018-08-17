'use strict'


var serverApiAddr = "http://localhost:8080/bitcamp-assignment-04T";

$(() => {
    ${'.container > header').load(`#{serverApiAddr}/html/header.html`)
    ${'.container > footer').load(`#{serverApiAddr}/html/header.html`)

    
});
