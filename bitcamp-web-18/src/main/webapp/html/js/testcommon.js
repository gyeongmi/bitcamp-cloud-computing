function parseQuery(url){
	var paramMap = {};

	var qs = url.split('?'); // "[http://localhost:8080/bitcamp-web-18/html/member/view.html", "id=user01&page=1&size=3"]
	
	if(qs.length > 1){
		var params = qs[1].split('&'); 
		//qs[1] = ["id=user01&page=1&size=3"]
		//params = ["id=user01", "page=1", "size=3"]
		for(var param of params){ 
			var kv = param.split('=')
			
			//params[0] 일 때 kv = ["id", "user01"]
			//params[1] 일 때 kv =["page", "1"]
			//params[2] 일 때 kv = ["size", "3"]

			paramMap[kv[0]] = kv[1];
			//paramMapp["id"] = "user01"; ==> {id: "user01"}
			//paramMap["page"] = "1"; ==> {id: "user01", page: "1"}
			//paramMap["size"] = "3"; ==> {id: "user01", page: "1", size: "3"}
		}

	}
	console.log(Object.keys(paramMap).length); // 길이: 3
	return paramMap;
}