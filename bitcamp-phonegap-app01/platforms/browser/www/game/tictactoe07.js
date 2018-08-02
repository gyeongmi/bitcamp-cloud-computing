var human = 'X',
    computer = 'O',
    cells = $('.cell'),
    count = 0,
    isCompleted = false,
    isWorking = false;

//이제 체크가 됐을 때 또 체크 하지 못하게..
//isCellChecked
//e의 타겟에 innerHTML != '' 빈 문자열이 아니면 리턴한다
//눌렀는데  누른대로 계쏙 반응하면 안되기 때문에..

/*$('#cell-box').on('complete', (e) => {
    alert('ok');
});

$('#cell-box').on('complete', (e, result) => {
    alert('ok');
});*/

$('.cell').click((e) => {
    //completed(게임이 끝나다) 되었거나 working(실행) 중이면 클릭하지 마라. return;
    //setTimeout() 진입하기 전에 isworking true 작업이 끝났을때 false
    
    if (isCompleted || isWorking) return;
    if (e.target.innerHTML != '') return;
    
    console.log($(e.target).attr('data-pos'));
    $(e.target).text(human);
    count++;
    
    var result = computeGame();
    if (result != 0) {
        $('#cell-box').trigger('complete', [result]);//trigger: 발생시키다 배열값이 있으면 배열값 넘겨준다.//임의의 태그에 complet 이벤트를 발생시키고 부가적인것을 배열로 넘기는 것이다.
        isCompleted = true;
    }
    
    if (isCompleted) return;
    
    //타이머를 가동하여 1초 후에 컴퓨터가 표시하게 한다.
    isWorking = true;
    setTimeout(() => {
        while (true) {
            var no = Math.floor(Math.random() * 9);
            if (!isCellChecked(no)) {
                checkCell(no, 'computer');
                break; //return; ---> 계산을 먼저 해야 돼서 break를 쓴다..
            }
        }
        
        var result = computeGame();
        if (result != 0) {
            $('#cell-box').trigger('complete', [result]);
            isCompleted = true;
        } else if (count == 5) {
            alert("비겼다!");
        }
        
        isWorking = false;
    }, 1000);
});

$('#new-game').click((e) => {
    cells.each((i, e) => {
        e.innerHTML = '';
    }); //반복하면서 모두 호출해라
   //초기화
    count = 0;
    isCompleted = false;
    isWorking = false;
});

function isCellChecked(no) {
    return cells[no].innerHTML != "" ? true : false;
}

function checkCell(no, gamer) {
    cells[no].innerHTML = 
        (gamer == 'computer') ? computer : human;
}

function computeGame() {
    console.log("-----------------");
    for (var row = 0; row < 9; row += 3) {
        var countHuman = 0;
        var countComputer = 0;
        for (var i = row; i < (row + 3); i++) {
            if (cells[i].innerHTML == human) countHuman++;
            else if (cells[i].innerHTML == computer) countComputer++;
        }
        if (countHuman == 3) return 1;
        else if (countComputer == 3) return -1;
        console.log(countHuman, countComputer);
    }
    
    for (var col = 0; col < 3; col++) {
        var countHuman = 0;
        var countComputer = 0;
        for (var i = col; i <= (col + 6); i += 3) {
            if (cells[i].innerHTML == human) countHuman++;
            else if (cells[i].innerHTML == computer) countComputer++;
        }
        if (countHuman == 3) return 1;
        else if (countComputer == 3) return -1;
        console.log(countHuman, countComputer);
    }
    
    var countHuman = 0;
    var countComputer = 0;
    for (var i = 0; i <= 8; i += 4) {
        if (cells[i].innerHTML == human) countHuman++;
        else if (cells[i].innerHTML == computer) countComputer++;
    }
    if (countHuman == 3) return 1;
    else if (countComputer == 3) return -1;
    console.log(countHuman, countComputer);
    
    var countHuman = 0;
    var countComputer = 0;
    for (var i = 2; i <= 6; i += 2) {
        if (cells[i].innerHTML == human) countHuman++;
        else if (cells[i].innerHTML == computer) countComputer++;
    }
    if (countHuman == 3) return 1;
    else if (countComputer == 3) return -1;
    console.log(countHuman, countComputer);
    
    return 0;
}

/*function computeGame(){
    for (var row=10; row <= 30; row+=10){
        var count = 0;
        for(var col=1; col<=3; col++){
            $('.cell[data-pos="${row+col}"]').text() 
            //대괄호 조건을 주는 거다
            //속성에 값이 
        }
            
    }
        
    
}
*/