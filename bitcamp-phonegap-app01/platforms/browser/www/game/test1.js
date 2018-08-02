/* $('.cell').click((e)=>{
    console.log('cell.. clicked');
    console.log()
    console.log($(e.target).attr('data-pos'))
}) */


/*
var cells = document.querySelectorAll(".cell");
for(i=0; i<cells.length; i++){
    // 태그에 새 속성 추가해봄 cells[i].setAttribute("data-pos","100");
    
    cells[i].addEventListener('click', function(e){
        console.log(e.target.getAttribute('data-pos'));
        //이벤트가 발생한 태그 객체 //그것의 속성값 getAttribute
    });
}
*/

/*
var var = 'X';
var computer = 'O';
var cells = document.querySelectorAll('.cell');
for(i=0; i<cells.length; i++){
    cells[i].addEventListener('click', function(e){
        console.log(e.target.getAttribute('data-pos'));
        e.target.innerHTML = gamer;
        
    });
}
document.querySelector('#new-game').addEventListener('click', function(e){
    console.log('new game!');
})
 */
var human = 'XE',
    computer = 'O',
    cells = $('.cell'),
    count = 0;

$('.cell').click((e)=>{
    console.log($(e.target).attr('data-pos'));
    //오리지날 태그(e.target)에는 안  들어있다.오리지날 태그를 제이쿼리로 가공하게 되면 리턴하는 값은
    //이런 함수가 들어있는 태그값이다.
    $(e.target).text(human);
    
    //마지막 표시를 했으면 더이상 컴퓨터가 작업하지 않는다.
    console.log("count:"+count);
    if(++count == 5) 
        return;

    //타이머를 가동하여 1초 후에 컴퓨터가 표시하게 한다.
    setTimeout(()=>{
        
        
        while(true){
            var no = Math.floor(Math.random() * 9);
            console.log("random:"+no);
            if(!isCellChecked(no)){ 
                //공백이 아닐 때 !(true) = fasle
                //공백일 때 !(false) = true
                checkCell(no, 'computer');
                return;
            }

        }
        
    }, 1000);
    
});

$('#new-game').click((e)=>{
    console.log('new gmae!');
});

function isCellChecked(no){
    return cells[no].innerHTML != "" ? true : false;
    //cells[랜덤값:1] 이 안에 내용이 공백이 아니면 true, 공백이면 false.
}
function checkCell(no, gamer){
    cells[no].innerHTML =
        (gamer == 'computer') ? computer : human;
}