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
        $(`.cell:nth-child(${no})`).each((i, e) => {
            console.log(no, getCellText(no)); // 자식 번호는 0부터 시작하는 게 아니라 1부터 시작한다.
        });
        
        /*$('.cell').each((i, e) => {
            //console.log(i, e); //a태그를 작성한 순서 그대로 나온다. e는 오리지날 태그. text는 제이쿼리 함수.
            console.log($(e).text());
        });*/
    }, 1000);
});

$('#new-game').click((e) => {
   console.log('new game'); 
});

//번호에 해당되는 텍스트를 가져오는 것이다.
function getCellText(cellNo){
    var value;
    console.log(cells[cellNo]);
    /*cells.each((i, e) => { //함수가 함수이다 보니까 var 변수에 저장시켰다가 리턴해야 한다.
        //console.log(i, e);
        //i = index, e = element
        //html은 $('') 를 하게 되면 매번 처음부터 끝까지 찾아내기 때문에 한번 찾은걸 반복문을 돌리자..
        if(i==cellNo){
            console.log(e);
            value = e.innerHTML;
        }        
    });*/
    return value;
}