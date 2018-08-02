var gamer = 'X';
var computer = 'O';
var cells = $('.cell');

$('.cell').click((e) => {
    console.log($(e.target).attr('data-pos'));
    $(e.target).text(gamer);
    
    //타이머를 가동하여 1초 후에 컴퓨터가 표시하게 한다.
    setTimeout(()=>{
        //console.log('computer')

        
        var no = Math.floor(Math.random() * 9);
        //var no = Math.random() * 9;
        //console.log(no, Math.floor(no));
        //console.log(no, parseInt(no));
        
        //백틴을 하면 변수명을 바로 집어넣을 수 있다.
        $('.cell').each((i, e) => {
            //console.log(i, e); //a태그를 작성한 순서 그대로 나온다. e는 오리지날 태그. text는 제이쿼리 함수.
            console.log($(e).text());
        });
    }, 1000);
});

$('#new-game').click((e) => {
   console.log('new game'); 
});
