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
var human = 'X',
    computer = 'O',
    cells = $('.cell'),
    count = 0,
    isCompleted = false;


$('#cell-box').on('complete', (e, result) => {
    alert(result);
    alert('ok');
});


$('.cell').click((e)=>{
    if(isCompleted) return;
    
    console.log($(e.target).attr('data-pos'));
    //오리지날 태그(e.target)에는 안  들어있다.오리지날 태그를 제이쿼리로 가공하게 되면 리턴하는 값은
    //이런 함수가 들어있는 태그 값이다.
    $(e.target).text(human);
    
    count++;
    console.log("count:"+count);
  
/*  //마지막 표시를 했으면 더이상 컴퓨터가 작업하지 않는다.
 *     if(++count == 5) 
        return;
*/

    
    var result = computeGame();
    if(result != 0){
        $('#cell-box').trigger('complete', result); 
        // trigger: 발생시키다. 배열값이 있으면 배열값을 넘겨 준다.
        // 임의의 태그에 complete 이벤트를 발생시키고 부가적인 것을 배열로 넘기는 것이다.
        isCompleted =true;
    }
    
    if(isCompleted) return;
    
    
    //타이머를 가동하여 1초 후에 컴퓨터가 표시하게 한다.
    setTimeout(()=>{
        while(true){
            var no = Math.floor(Math.random() * 9);
            console.log("random:"+no);
            if(!isCellChecked(no)){ 
                //공백이 아닐 때 !(true) = fasle
                //공백일 때 !(false) = true
                checkCell(no, 'computer');
                break; //return; ---> 계산을 먼저 해야 돼서 break를 쓴다.
            }

        }
        
        var result = computeGame();
        if(result != 0){
            $('#cell-box').trigger('complete', result);
            isCompleted = true;
        }else if(count == 5){
            alert("비겼다!");
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


//총 8번 실행
function computeGame(){
    console.log("---------------------");
    for(var row = 0; row < 9; row += 3){
        // 0 ~ 8 
        //row = 0
        //row = 3
        //row = 6
        
        var countHuman = 0;
        var countComputer = 0;
        
        for(var i=row; i<(row+3); i++){
            //row=0, i=0 일때 i<3 i++ (i=0, i=1, i=2)
            //row=0, i=1 일때 i<3 i++ (i=0, i=1, i=2)
            //row=0, i=2 일때 i<3 i++ (i=0, i=1, i=2)
            
            //row=3, i=3 일때 i<6 i++ (i=3, i=4, i=5)
            //row=3, i=4 일때 i<6 i++ (i=3, i=4, i=5)
            //row=3, i=5 일때 i<6 i++ (i=3, i=4, i=5)
            
            //row=6, i=6 일때 i<9 i++ (i=6, i=7, i=8)
            //row=6, i=7 일때 i<9 i++ (i=6, i=7, i=8)
            //row=6, i=8 일때 i<9 i++ (i=6, i=7, i=8)
            
            
            if(cells[i].innerHTML == human) countHuman++;
            //cells[0], [1], [2]
            //cells[3], [4], [5]
            //cells[6], [7], [8]
            
            else if(cells[i].innerHTML == computer) countComputer++;
            //cells[0], [1], [2]
            //cells[3], [4], [5]
            //cells[6], [7], [8]
               
        }
        
        if(countHuman == 3) return 1;
        else if(countComputer == 3) return -1;
        console.log(countHuman, countComputer);
    }
    
    for(var col = 0; col < 3; col++){
        //col = 0
        //col = 1
        //col = 2
        
        var countHuman = 0;
        var countComputer = 0;
        
        for(var i = col; i <= (col + 6); i+=3){
            //col=0, i=0 일때 i<=6 i=0+3
            //col=0, i=3 일때 i<=6 i=3+3
            //col=0, i=6 일때 i<=6
            
            //col=1, i=1 일때 i<=7 i=1+3
            //col=1, i=4 일때 i<=7 i=4+3
            //col=1, i=7 일때 i<=7
            
            //col=2, i=2 일때 i<=8 i=2+3
            //col=2, i=5 일때 i<=8 i=5+3
            //col=2, i=8 일때 i<=8
            
            if(cells[i].innerHTML == human) countHuman++;
            //cells[0], [3], [6]
            //cells[1], [4], [7]
            //cells[2], [5], [8]
            else if(cells[i].innerHTML == computer) countComputer++;
            //cells[0], [3], [6]
            //cells[1], [4], [7]
            //cells[2], [5], [8]
        }
        if(countHuman == 3) return 1;
        if(countComputer == 3) return -1;
        console.log(countHuman, countComputer);
    }
    
    var countHuman = 0;
    var countComputer = 0;
    for(var i = 0; i <= 8; i += 4){
        // i=0, i<=8, i=0+4
        // i=4, i<=8, i=4+4
        // i=8, i<=8
        
        if(cells[i].innerHTML == human) countHuman++;
        // cells[0]
        // cells[4]
        // cells[8]
        
        else if(cells[i].innerHTML == computer) countComputer++;
        // cells[0]
        // cells[4]
        // cells[8]
    }
    if(countHuman == 3) return 1;
    else if(countComputer == 3) return -1;
    console.log(countHuman, countComputer);
    
    var countHuman = 0;
    var countComputer = 0;
    for(var i = 2; i <= 6; i += 2){
        //i=2, i<=6, i=2+2
        //i=4, i<=6, i=4+2
        //i=6, i<=6
        
        if(cells[i].innerHTML == human) countHuman++;
        // cells[2]
        // cells[4]
        // cells[6]
        else if(cells[i].innerHTML == computer) countComputer++;
        // cells[2]
        // cells[4]
        // cells[6]
    }
    if(countHuman == 3) return 1;
    else if(countComputer == 3) return -1;
    console.log(countHuman, countComputer);
    
    return 0;
    
}