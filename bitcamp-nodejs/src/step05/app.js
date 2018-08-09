// 주제: 회원관리, 팀관리, 게시판, 강좌관리 웹 애플리케이션 만들기

const express = require('express')
const app = express();

const bodyParser = require('body-parser')
app.use(bodyParser.urlencoded({extended: false}))

app.use(express.static('public'))

const consolidate = require('consolidate');

app.engine('html', consolidate.handlebars)

app.set('view engine', 'html')

const path = require('path')
app.set('views', path.join(__dirname, 'templates'))

app.use('/member', require('./member'))

app.get('/hello', (req, res) => {
    res.writeHead(200, {'Content-Type':'text/plain;charset=UTF-8'});
    res.write('Hello')
    res.end();
});

app.listen(8000, () => {
    console.log('서버 실행 중...')
})