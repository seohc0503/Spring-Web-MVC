package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

// 뷰 템플릿 사용 -> 뷰 리졸버를 사용해야하므로 @ResponseBody 사용 X
@Controller
public class ResponseViewController {

    // @ResponseBody가 없으면 "/response/hello"를 논리 이름으로 뷰 리졸버가 실행되어 뷰를 찾고 렌더링
    // @ResponseBody가 있으면 해당 텍스트가 그대로 메시지 바디에 들어가 출력 됨
    // ModelAndView에 View의 논리 이름을 넣어서 뷰 리졸버로 전달
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("/response/hello")
                .addObject("data", "hello!");
        return mav;
    }

    // @Controller 가 있으면서 String을 반환하면 return 값이 뷰의 논리 이름이 된다
    // Model을 파라미터로 바로 받는 경우, View의 논리 이름은 String으로 바로 전달
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!!");
        return "response/hello";
    }

    // 권장X
    // @Controller 를 사용하고, HttpServletResponse , OutputStream(Writer) 같은
    // HTTP 메시지 바디를 처리하는 파라미터가 없으면 요청 URL을 논리 뷰 이름으로 사용
    @RequestMapping("response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!!!");

    }
}
