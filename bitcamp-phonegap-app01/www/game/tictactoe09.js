//변경되어야 할 값과 변경되어서는 안될 값을 구분해야 한다.
//const = 배열의 주소를 안 바꾸겠다는 의미. 배열 안은 상관 X
const human = 1,
      computer = 10,
      cellBox = $('#cell-box'),
      cells = $('.cell'),
      cellData = [0,0,0,0,0,0,0,0,0];
      
var count = 0,
    isCompleted = false,
    isWorking = false;

$('#cell-box').on('complete', (e, result) => {
    setTimeout(() => {
        if (result == 3) alert("인간 승2!");
        else if (result == 30) alert("컴퓨터 승2!");
        else alert("비겼다2!");
    }, 500);
});

$('.cell').click((e) => {
    var cell = $(e.target);
    var no = parseInt(cell.attr('data-no'));
    console.log('번호:', no);
    
    if (isCompleted || isWorking) return;
    if (cellData[no] > 0) return;
        //현재 체크된 상태이기 때문에 나간다. cellStates = ui를 표현하는 모델(데이터)이다.
    
    cell.addClass('cell-x');
    cellData[no] = human;
    count++;
    
    computeGame(); //계산이 완료되면 true, 아니면 false
    /*var result = computeGame();
     * if (result == 3 || result == 30) {
        cellBox.trigger('complete', [result]);
        isCompleted = true;
    }
    */
    if (isCompleted) return;
    
    //타이머를 가동하여 1초 후에 컴퓨터가 표시하게 한다.
    isWorking = true;
    setTimeout(() => {
        while (true) {
            var no = Math.floor(Math.random() * 9);
            if (!isCellChecked(no)) {
                checkCell(no, 'computer');
                break;
            }
        }
        
        computeGame();/*
        var result = computeGame();
        if (result == 3 || result == 30 || count == 5) { //인간 승 or 컴퓨터 승 or 카운트 만료
            cellbox.trigger('complete', [result]);
            isCompleted = true;
        }*/
        
        isWorking = false;
    }, 1000);
});

$('#new-game').click((e) => {
    cells.removeClass('cell-x').removeClass('cell-o');
    count = 0;
    isCompleted = false;
    isWorking = false;
    for (var i in cellData) 
        cellData[i] = 0;
});

function isCellChecked(no) {
    return cellData[no] > 0;
}

function checkCell(no, gamer) {
    $(cells[no]).addClass(
            gamer == 'computer' ? 'cell-o' : 'cell-x');
    cellData[no] = gamer == 'computer' ? 10 : 1;
}

//리턴 값
//true : 게임 끝
//flase : 게임 미완료
function computeGame() {
    console.log(cellData, count);
    var sum = 0;
    
    for (var row = 0; row < 9; row += 3) {
        sum = 0;
        for (var i = row; i < (row + 3); i++) 
            sum += cellData[i];
        if (isGameOver(sum)) return;
    }
    
    for (var col = 0; col < 3; col++) {
        sum = 0;
        for (var i = col; i <= (col + 6); i += 3)
            sum += cellData[i];
        if (isGameOver(sum)) return;
    }
    
    sum = 0;
    for (var i = 0; i <= 8; i += 4) 
        sum += cellData[i];
        //if (sum == 3 || sum == 30) return sum; //30이면 컴퓨터 승, 3이면 인간 승, 0이면 누구의 승도 아님
        if (isGameOver(sum)) return;
    
        sum = 0;
        for (var i = 2; i <= 6; i += 2) 
            sum += cellData[i];
        isGameOver(sum);
}

function isGameOver(result) {
    if (result == 3 || result == 30 || count == 5) {
        cellBox.trigger('complete', [result]);
        isCompleted = true;
        return true;
    } 
    return false;
}