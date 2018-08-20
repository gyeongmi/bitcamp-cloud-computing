'use strict'

var selectedCardNo = 0;
var formState = 'view';

$('.edit-ctrl').css('display', 'none');

$(document.body).on('show.detail', (e, no) => {
    /*alert(no);*/
    $('.edit-ctrl').css('display', 'none');
    $('.view-ctrl').css('display', '');
    formState = 'view';
    $.getJSON(`${serverApiAddr}/json/businesscard/${no}`, (result) => {
        if(result.status !== 'success') {
            selectedCardNo = 0;
            return;}
        
        selectedCardNo = no;
        $('#f-name').val(result.data.name);
        $('#f-mobile-tel').val(result.data.mobileTel);
        $('#f-tel').val(result.data.tel);
        $('#f-fax').val(result.data.fax);
        $('#f-email').val(result.data.email);
        $('#f-memo').val(result.data.memo);
        
    })
})

$('#add-btn').click(()=>{
    formState = '';

    $('#reset-btn').trigger('click', ['add']); //프로그램이 클릭이벤트를 발생시킴(form태그를 초기상태로 만들어준다. 값을 지우는것이 아님.)
    
    $('.form-control-plaintext').addClass('form-control')
        .removeClass('form-control-plaintext'); //스타일을 찾아서 찾은 모든 태그(목록) 반복문 돌면서 form-control 추가시키고 form-control-plaintext는 삭제시킨다.

    $('.edit-ctrl').css('display', '');
    $('.view-ctrl').css('display', 'none');
})

$('#update-btn').click(()=>{
    $.getJSON(`${serverApiAddr}/json/businesscard/delete`, {
            'no':selectedCardNo
        }, (result)=>{
            $(document.body).trigger('refresh.list');
        }
})

$('#delete-btn').click(()=>{
    
    $('.form-control-plaintext').addClass('form-control')
        .removeClass('form-control-plaintext'); //스타일을 찾아서 찾은 모든 태그(목록) 반복문 돌면서 form-control 추가시키고 form-control-plaintext는 삭제시킨다.

    $('.edit-ctrl').css('display', '');
    $('.view-ctrl').css('display', 'none');
})
$('#ok-btn').click(()=>{
    formState = 'edit';
    if(action === 'add'){
        $.post(`${serverApiAddr}/json/businesscard/add`, {
            'name': $('#f-name').val(),
            'mobileTel': $('#f-mobile-tel').val(),
            'tel': $('#f-tel').val(),
            'fax': $('#f-fax').val(),
            'email': $('#f-email').val(),
            'memo': $('#f-memo').val(),
            
        }, (result) => {
            console.log(result);
            if(result.status !== 'success') return;
            $(document.body).trigger('refresh.list');
        }, 'json')
        .fail(() => {
            alert('서버 요청 중 오류 발생!')
        });
    }else(action === 'update'){
        $.post(`${serverApiAddr}/json/businesscard/update`, {
            'no': selectedCardNo,
            'name': $('#f-name').val(),
            'mobileTel': $('#f-mobile-tel').val(),
            'tel': $('#f-tel').val(),
            'fax': $('#f-fax').val(),
            'email': $('#f-email').val(),
            'memo': $('#f-memo').val(),
            
        }, (result) => {
            console.log(result);
            if(result.status !== 'success') return;
            $(document.body).trigger('refresh.list');
        }, 'json')
        .fail(() => {
            alert('서버 요청 중 오류 발생!')
        });
        
    }
    
    
})
$('#reset-btn').click((e, action)=>{
    $('.form-control').addClass('form-control-plaintext')
        .removeClass('form-control');
    
    $('.edit-ctrl').css('display', 'none');
    $('.view-ctrl').css('display', '');
    
    //'추가' 버튼을 클릭한 후 리셋할 때는
    //기존의 명함 정보를 로딩해서는 안된다. 그냥 빈 입력폼을 출력해야 한다.
    //만약 사용자가 취소 버튼을 눌렀다면,
    //그때는 이전 명함 정보를 로딩해야 한다.
    if(selectedCardNo > 0 && actcion !== 'add'){
        $(document.body).trigger('show.detail',[selectedCardNo]);
        formState = 'view';
    }
    
})