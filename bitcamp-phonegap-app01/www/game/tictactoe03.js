$('.cell').click((e) => {
    //console.log('cell.. clicked');
    //console.log(e.target.getAttribute('data-pos')); //이벤트가 발생한 태그 객체 //그것의 속성값 getAttribute
    //console.log($(e.target).attr('data-pos'));//오리지날 태그(e.target)에는 안 들어 있다. 오리지날 태그를 제이쿼리로 가공하게 되면 리턴하는 값은 이런 함수가 들어있는 태그값이다.
    console.log($(e.target).attr('data-pos'));
});

$('#new-game').click((e) => {
    console.log('new game!');
});