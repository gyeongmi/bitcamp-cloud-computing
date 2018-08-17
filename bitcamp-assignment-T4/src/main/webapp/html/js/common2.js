var serverApiAddr = "http://localhost:8080/bitcamp-assignment-T4";
$(()=>{
    console.log('1111')
});
//웹 페이지가 다 로딩된 후에 호출한다.

window.onload= () =>{
    console.log('onload()...')
}
//웹페이지를 모두 로딩한 후에 호출함.
//항상 먼저 이벤트가 발생하고 한 후에 먼저 등록된 순으로 $(()), $(document.body)의 메소드가 호출된다.

$(document.body).ready(()=>{
    console.log('read()...');
})
//document 돔 API를 완성했을 때 실행하라는 뜻이다. (문서가 다 호출됐을때 호출되는거)


