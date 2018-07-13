package bitcamp.pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 프론트컨트롤러(caller:호출자)와 페이지 컨트롤러(callee:호출당하는객체) 사이의 규칙!
public interface PageController {
    String service(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception;
}
