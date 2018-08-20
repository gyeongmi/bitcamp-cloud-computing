'use strict'

$('#loginBtn').click(() => {
    $.post(`${serverApiAddr}/json/auth/signIn`, {
        'email': $('#fEmail').val(),
        'password': $('#fPassword').val(),
        'saveEmail': $('#fSaveEmail').prop('checked')
    }, (result) => {
        if (result.status === 'success') {
            location.href = 'businesscard/index.html'
        } else {
            alert('로그인 실패!')
        }
    }, 'json')
    .fail(() => {
        alert('서버 요청 중 오류 발생!')
    });
});
/*
$('#loginBtn').click(() => {
    $.post(`${serverApiAddr}/json/auth/signIn`, {
        'email': $('#fEmail').val(),
        'password': $('#fPassword').val(),
        'saveEmail': $('#fSaveEmail').prop('checked')
    }, (result) => {
        console.log(result);
        if(result === 'success'){
            location.href = 'businesscard/index.html'
        }else{
            alert('로그인 실패!')
        }
        
        200 ok 가 안뜨면 fail로 오류를 확인할 수 있다.
        
    }, 'json')
    .fail(() => {
        alert('서버 요청 중에 오류 발생!')
    });
});
*/