// 주제: 여러 개의 요청 처리하기 - 각 요청을 함수로 분리하기
// [ 실행 URL ]
// => http://localhost:8000/member/delete?id=user100
// [ 출력 결과 ]
// 삭제 성공입니다.

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
    
    if(urlInfo.pathname !== '/member/list'){
        res.end('해당 URL을 지원하지 않습니다!')
        return;
    }
    
    var pageNo = 1;
    var pageSize = 3;
    if(urlInfo.query.pageNo){
        pageNo = parseInt(urlInfo.query.pageNo);
    }
    if(urlInfo.query.pageSize){
        pageSize = parseInt(urlInfo.query.pageSize);
    }
    var startIndex = (pageNo - 1) * pageSize; //0부터 시작하니까,,
    //res.write(`${pageNo} ${pageSize} ${startIndex} \n`);
    
    
    pool.query('select mid, email from pms2_member limit ?, ?',
            [startIndex, pageSize],
            function(err, results) { 
                // if(err) throw err; 예외 던지기 X 예외가 발생하면 응답받아야 한다
                if(err){
                    res.end('DB 조회 중 예외 발생!')
                    return;
                }
                
                for (var row of results){
                    res.write(`${row.email}, ${row.mid}\n`);
                }
                //pool.end(); 서버가 계속 실행 중에 있으면 pool 객체가 종료되어서는 안 된다.
                res.end(); //응답 완료는 여기서.
    });
    
    
});

server.listen(8000, () => {
    console.log('서버가 시작됨!');
})