// 주제: 클라이언트에게 출력하기 - 콘텐트 출력 II
// 

const http = require('http')

const server = http.createServer((req, res) => {
    console.log('요청 받았음!')
    
    res.writeHead(200, {
        'Content-Type': 'text/html;charset=UTF-8'
    }); //200 ok, {응답헤더지정}
    
    // write()를 사용하여 콘텐트를 출력할 수 있다.
    res.write('<html>\n')
    res.write('<head>\n')
    res.write('<title>Node.js</title>\n')
    res.write('</head>\n')
    res.write('<body>\n')
    res.write('<h1>안녕안녕안녕안녕!</h1>\n')
    res.write('</bdoy>\n')
    res.write('</html>\n')
    // 나중에 페이지소스 볼 때 유지보수 쉽게 하려고.. \n 줄바꿈해준다
    res.end(); //end에서 꼭 출력할 필요 없다.
    
});

server.listen(8000, () => {
    console.log('서버가 시작됨!');
});