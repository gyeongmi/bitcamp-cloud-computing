"use strict"
//자주 사용할 함수를 라이브러리화 시킨다.

//let bit = {};
let bit = function(value) {
    var el = [];
    if (value instanceof HTMLElement) {
        el[0] = value;
        //넘어온 값이 String이 아니고 HTMLElement. 모든 태그는 HTMLElement이다.
        //e에 값을 담고 그 태그에 도우미 함수를... e.html.  
    } else if (value.startsWith('<')) {
        el[0] = document.createElement(
                value.substr(1, value.length - 2));
        //createElement는 양쪽에 꺽새가 있으면 안된다. 텍스트만 추출..
    } else {
        var list = document.querySelectorAll(value);
        
        //selector로 찾은 태그들을 빈 배열로 옮긴다.
        for(var i = 0; i < list.length; i++) {
            el[i] = list[i];
        }
    }
    
    //if(el.length ==0) return null; //list04 하고 난 후에 지운다.
    
    // 개발자가 쓰기 좋은 함수를 추가해서 리턴하자!
    el.html = function(value) {
        //파라미터 값을 안 줬다는 것은 이 태그의 값을 달라는 것
        if (arguments.length == 0) {
            return el[0].innerHTML;
        }
        for(var e of el){
            e.innerHTML = value;
        }
        return el;//객체를 쓰든 쓰지않든 리턴해준다. 그러면 list.html에서 tr이란 변수가 필요없어지고.. 체인식으로..
    };
    
    el.append = function(child) { //부모에 어펜드된 자식.. 한 자식은 여러부모의 자식이 될 수 X 맨 마지막 부모의 자식이 될거임
        //반복문 돌아야 하는 이유: 부모가 여러명일 수 있어서
        for(var e of el){
            e.appendChild(child);
        }
        return e;
    };
    
    el.appendTo = function(parent) { //어차피 넘어오는 것들이 배열이라
        //한 부모의 자식이 여러명일 경우. 여러개의 자식을 순서대로 어펜드 하기 위해.
        for(var e of el){ //막내 부모에 자식을 다 추가(어펜드)시킨다.
            parent[parent.length - 1].appendChild(e);
        }
        return e;
    };
    
    el.attr = function(name, value) {
        if (arguments.length < 2) {
            return el[0].getAttribute(name);
        }
        
        for(var e of el)
            e.setAttribute(name, value);
        return el;
    };
    
    el.removeAttr = function(name) {
        for(var e of el)
            e.removeAttribute(name);
        return el;
    };
    
    el.on = function(name, p2, p3){//이벤트 이름, 함수, 함수
        var selector = null;
        var handler = null;
        
        if(arguments.length == 2) handler = p2; //값이 두개만 넘어오면
        if(arguments.length == 3) { //값이 세개 넘어오면
            selector = p2;
            handler = p3;
        }
        
        for(var e of el){
            if(!selector){
                // selector 지정되지 않으면,
                // 해당 태그에 대해 이벤트가 발생했을 때 핸들러를 호출한다.
                e.addEventListener(name, handler);
            } else{
                // selector 가 지정되어 있으면,
                // 핸들러를 호출하기 전에 selector에 해당하는 것인지 검사하는
                // 함수가 먼저 호출되게 한다.
                e.addEventListener(name, function(event){ //tbody가 호출
                    //console.log('임의로 만든 핸들러를 호출한다.')
                    
                    //현재 태그의 자식 태그 중에서 selector 조건에 해당되는
                    //자식 태그들을 찾는다.
                    var selectorTargets = e.querySelectorAll(selector);
                    
                    //그 자식 태그들 중에 이벤트가 발생된 태그를 찾는다
                    for (var target of selectorTagets){
                        //만약 이벤트가 발생된 태그와 일치하는 자식 태그가 있다면,
                        //그때서야 핸들러를 호출해 준다.
                        if(evnet.target == target){
                            handler(event);
                            break;
                        }
                    }
                })
            }
        }
        return el;
    };
    el.click = function(handler){
        return e.on('click', handler);
    };
    
    return el;
};


bit.parseQuery = function(url) {
    var paramMap = {};

    var qs = url.split('?');
    if (qs.length > 1) {
        var params = qs[1].split('&');
        for (var param of params) {
            var kv = param.split('=')
            paramMap[kv[0]] = kv[1];
        }
    }
    return paramMap;
};

bit.ajax = function(url, settings) {
    if (settings == undefined) 
        settings = {};
    
    if (settings.dataType == undefined)
        settings.dataType = 'text'; //서버에서 리턴한 걸 text로 그대로 리턴해
    
    if (settings.method == undefined) 
        settings.method = 'GET';
    
    var xhr = new XMLHttpRequest();
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState < 4) return;
        if (xhr.status !== 200) {
            if (settings.error)
                error();
            return;
        }
        let data = xhr.responseText;
        if (settings.dataType == 'json') {
            data = JSON.parse(xhr.responseText);
        }

        if (settings.success) { //정의되어 있다면
            settings.success(data); //success라는 함수에 data를 넘겨준다.
        }; 
        
        //done() 에 의해 등록된 함수가 있다면 그 함수를 호출한다.
        if (done) {  
            done(data);
        }
    };
    
    //settings에 서버로 보낼 data가 있다면 url에 포함해야 한다.
    if (settings.data) {
        var qs = ''; //반드시 빈 무자열
        for (var propName in settings.data) {
            qs += `&${propName}=${settings.data[propName]}`;
        //setting라는 데이터 객체에 들어있는 값이 page=, size= 라면 이것을 쿼리 String으로 page= &size= 로 바꾸겠다고
        }
        if (url.indexOf('?') == -1)
            url += '?';
        url += qs;
    }
    console.log(url);
    xhr.open(settings.method, url, true);
    xhr.send();
    
    // XMLHttpRequest 객체를 리턴하기 전에 함수를 추가한다.
    let done;
    xhr.done = function(func) {
        done = func;
    };
    
    return xhr;
};

bit.getJSON = function(url, p2, p3) {
    let data = {};
    let success = null;
    
    if (arguments.length > 1) {
        if (typeof p2 == "function") success = p2;
        else data = p2;
        
        if (typeof p3 == "function") success = p3;
    }
    
    return bit.ajax(url, {
        dataType: 'json',
        data: data,
        success: success
    });
}

let $ = bit; //bit와 $은 같은 것이 됨.