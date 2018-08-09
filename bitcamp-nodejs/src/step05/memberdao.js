// 주제: DAO 모듈 만들기

//pool 주입 받기
var pool;

exports.connectiontPool = (connectionPool) => {
    pool = connectionPool;
};


/*
 * 비동기는 동기처럼 위에서 아래로 흐르는게 아니다. 갑자기 중간에서 실행될수도 있고 그래서 디버깅이 가장 힘들다.
 */
exports.list = (pageNo = 1, pageSize = 3, handler) => {
    var startIndex = (pageNo - 1) * pageSize;
    
    pool.query('select mid, email from pms2_member limit ?, ?',
            [startIndex, pageSize],
            function(err, results) {
        /*
         * if (err) { res.end('DB 조회 중 예외 발생!') return; }
         * 
         * for (var row of results) { res.write(`${row.email}, ${row.mid}\n`); }
         * res.end();
         */
        
        handler(err, results); // 작업이 끝나면 이 handler에게 err나 results를 전달하는 것
    });
}

exports.add = (data, handler) => {
    pool.query(
            'insert into pms2_member(mid,email,pwd)\
            values(?, ?, password(?))',
        [data.id,
         data.email,
         data.password],
        function(err, result) {
            handler(err, result);
    });
}

exports.update = (data, handler) => {
    pool.query(
            'update pms2_member set\
             email=?,\
             pwd=?\
             where mid=?',
        [data.email,
         data.password,
         data.id],
        function(err, result) {
             handler(err, result);
    });
}

exports.remove = (data, handler) =>{
    pool.query('delete from pms2_member where mid=?',
            [data.id],
            function(err, result) {
                handler(err, result);
        });
}