// 주제: 여러 개의 요청 처리하기 - 회원 목록 조회/등록/변경/삭제 하기
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
    
    if(urlInfo.pathname !== '/member/list'
        && urlInfo.pathname !== '/member/add'
        && urlInfo.pathname !== '/member/update'
        && urlInfo.pathname !== '/member/delete'){
        
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
    
    var mid = urlInfo.query.id
    var email = urlInfo.query.email
    var pwd = urlInfo.query.password
    
    
    pool.getConnection(function(err, con){
        if(err){
            res.end('DB 조회 중 예외 발생!')
            return;
        }
        
        console.log('연결 객체를 얻었다.');
        
        if(urlInfo.pathname === '/member/list'){
            con.query('select mid, email from pms2_member limit ?, ?',
                    [startIndex, pageSize],
                    function(err, results) {
                if(err){
                    res.end('DB 조회 중 예외 발생!')
                    return;
                }
                
                for (var row of results){
                    res.write(`${row.email}, ${row.mid}\n`);
                }
                res.end(); //응답 완료는 여기서.
                con.release(); //커넥션 풀에 연결 객체를 반납한다.
            });
            
        }
        if(urlInfo.pathname === '/member/add'){
            con.query('insert into pms2_member(mid, email, pwd) values(?, ?, password(?))',
                    [mid, email, pwd],
                    function(err) {
                if(err){
                    res.end('DB 조회 중 예외 발생!')
                    return;
                }
                
                res.write('등록 성공!')
                res.end(); //응답 완료는 여기서.
                con.release(); //커넥션 풀에 연결 객체를 반납한다.
            });            
        }
        if(urlInfo.pathname === '/member/update'){
            con.query('update pms2_member set email=?, pwd=password(?) where mid=?',
                    [email, pwd, mid],
                    function(err) {
                if(err){
                    res.end('DB 조회 중 예외 발생!')
                    return;
                }
                
                res.write('변경 성공!')
                res.end(); //응답 완료는 여기서.
                con.release(); //커넥션 풀에 연결 객체를 반납한다.
            });            
        }
        if(urlInfo.pathname === '/member/delete'){
            con.query('delete from pms2_member where mid=?',
                    [mid],
                    function(err) {
                if(err){
                    res.end('DB 조회 중 예외 발생!')
                    return;
                }
                
                res.write('삭제 성공!')
                res.end(); //응답 완료는 여기서.
                con.release(); //커넥션 풀에 연결 객체를 반납한다.
            });            
        }
    });
});

server.listen(8000, () => {
    console.log('서버가 시작됨!');
})