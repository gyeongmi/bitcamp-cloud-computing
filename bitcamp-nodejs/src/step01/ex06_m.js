// 모듈 정의 IV
// - module.exports 에 함수를 저장한다.
//   그 함수는 객체를 리턴한다.

module.exports = function(){
    return{
        plus: function(a,b) {return a+b},
        minus: function(a,b) {return a-b},
        
        multiple(a,b) {return a*b},
        divide(a,b) {return a/b}
        
    };
};