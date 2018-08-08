// 주제: SQL 요청 처리하기 - 회원 변경하기
// [ 실행 URL ]
// => http://localhost:8000/member/update?id=user100&email=user100@test.com&password=1111
// [ 출력 결과 ]
// 변경 성공입니다.

const http = require('http')
const url = require('url')
const mysql = require('mysql');

var pool = mysql.createPool({
    connectionLimit: 10, //최대 10개
    host: '13.209.21.223', 
    database: 'studydb',
    user: 'study',
    password: '1111'
});

const server = http.createServer((req, res) => {
    var urlInfo = url.parse(req.url, true);
    
    if(urlInfo.pathname === '/favicon.ico') {
        res.end();
        return;
    }
    
    res.writeHead(200, {
        'Content-Type': 'text/plain;charset=UTF-8'
    });
    
    if(urlInfo.pathname !== '/member/update'){
        res.end('해당 URL을 지원하지 않습니다!')
        return;
    }
    
    var email = urlInfo.query.email
    var mid = urlInfo.query.id
    var pwd = urlInfo.query.password
    
    //res.write(`${email} ${mid} ${pwd}`)
   
    pool.query('update pms2_member set email=?, pwd=password(?) where mid=?',
            [email, pwd, mid],
            function(err) { 
                // if(err) throw err; 예외 던지기 X 예외가 발생하면 응답받아야 한다
                if(err){
                    res.end('DB 조회 중 예외 발생!')
                    return;
                }
                res.write('변경 성공!')
                res.end(); //응답 완료는 여기서.
    });
});

server.listen(8000, () => {
    console.log('서버가 시작됨!');
})