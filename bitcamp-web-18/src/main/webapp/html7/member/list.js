"use strict"
var trTemplateSrc = $('#tr-template-src').text();
var trTemplateFn = Handlebars.compile(trTemplateSrc);

var {page, size} = $.parseQuery(location.href);

let tbody = $('#eListTable > tbody'); 
let data = null;

if (page != undefined && size != undefined) {
    loadList(page, size);
} else {
    loadList(1, 3);
}
$(ePrevBtn).click(function() {
    loadList(data.page - 1, data.size);
});

$(eNextBtn).click(function() {
    loadList(data.page + 1, data.size);
});

function loadList(page, size) {
    $.getJSON(serverApiAddr + '/json/member/list', //별도로 분리시켰음
        {
            page: page,
            size: size
        }, function() {console.log("로딩 성공!")}) 
        .done(function(result) { 
            data = result; 
            
            var trListHTML = trTemplateFn({list: data.list}); //list(객체)란 이름으로 배열을 넘기세요.
            tbody.html(trListHTML);
                
            $(ePageNo).html(data.page);
            if (data.page <= 1)
                $(ePrevBtn).attr('disabled', '');
            else 
                $(ePrevBtn).removeAttr('disabled');                
            if (data.page >= data.totalPage)
                $(eNextBtn).attr('disabled', '');
            else
                $(eNextBtn).removeAttr('disabled');
        });
    }

tbody.on('click', 'a.viewLink', function(event){ 
    alert('okok');
    event.preventDefault();
    var id = $(event.target).attr('data-id');
    location.href = `view.html?id=${id}&page=${data.page}&size=${data.size}`;
});