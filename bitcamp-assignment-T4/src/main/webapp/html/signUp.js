/**
 * 
 */
$('#addBtn').click(()=>{
/*    console.log('=====>'); */
    $.post(`${serverApiAddr}/json/member/signUp`,{ 
        'email': $('#fEmail').val(),
        'name': $('#fName').val(),
        'password': $('#fPassword').val()
    },(result) => {
        console.log(result);
    }, 'json')
    .fail(()=>{
        alert('회원 가입 중에 오류 발생!');
        
    })
    //url(상대경로X 모바일로 이전할때 다 바꿔야 해서)
    //그 리턴 값에 대해서 fail이라는 메ㅗ소드를 등록하면 post를 호출하면서 ul요청해서 서버에서 데이터 받을 때 호출될 것이고, 받아왔는데 에러일 경우 등록된 함수가 호출될 것..
});
