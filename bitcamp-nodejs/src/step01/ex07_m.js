// 모듈 정의 V
// - 응용: private 변수를 갖는 객체 만들기

module.exports = function() {
    var result = 0;
    return{
        //로컬변수는 사라지지만 복제한 함수는 사라지지 않는다..
        //바깥함수의 로컬변수(값)을 공통으로 사용하는 메모리에 저장됨... (클로저)
        //result 변수는 계속 유지된다.
        plus(value) {result += value},
        minus(value) {result -= value},
        multiple(value) {result *=value},
        divide(value) {result /= value},
        getResult(){return result}
    };
};
