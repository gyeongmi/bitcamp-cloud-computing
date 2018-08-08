// 주제: 클라이언트에게 출력하기 - 멀티라인 처리
// 

const http = require('http')

const server = http.createServer((req, res) => {
    console.log('요청 받았음!')
    
    res.writeHead(200, {
        'Content-Type': 'text/html;charset=UTF-8'
    }); //200 ok, {응답헤더지정}
    
    // write()를 사용하여 콘텐트를 출력할 수 있다.
    res.write('<html>\n\
<head>\n\
<title>Node.js</title>\n\
</head>\n\
<body>\n\
<h1>안녕안녕안녕안녕!</h1>\n\
</bdoy>\n\
</html>\n');
    //줄 끝에 \를 하면 한 문자열이 된다.
    //공백도 문자열로 출력되기 때문에 앞에 공백 없앤다.
    res.end(); //end에서 꼭 출력할 필요 없다.
    
});

server.listen(8000, () => {
    console.log('서버가 시작됨!');
});