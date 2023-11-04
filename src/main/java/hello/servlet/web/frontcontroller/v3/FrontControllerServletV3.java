package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // "FrontController"에서 "HttpServletRequest"가 제공하는 파라미터들을 "paramMap"에 담아
        // process()의 매개변수로 각 "Controller"에 보내면서 각 "Controller"를 호출.
        // process()의 리턴값은 ModelView 객체이다.
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        // 각 컨트롤러에서 process(paraMap)를 실행하여 전달 받은 "논리적 이름"을 변수(viewName)에 저장
        String viewName = mv.getViewName();
        // 전달 받은 "논리적 이름"을 "viewResolver()"에 넘겨 "물리적 이름"으로 변환한 MyView 객체 받기
        MyView view = viewResolver(viewName);
        // 각 "물리적 이름"에 해당하는 MyView 객체를 렌더링(JSP로 넘김)
        view.render(mv.getModel(),request, response);

    }

    // 논리적 이름 -> 물리적 이름
    // "물리적 이름"을 "viewPath"로 갖는 MyView 객체를 생성
    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    // "HttpServletRequest"를 통해 받은 파라미터들을 Map에 저장.
    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}

