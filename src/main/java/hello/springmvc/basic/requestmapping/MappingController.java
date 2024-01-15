package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = {"/hello-basic", "/hello-go"}, method = RequestMethod.GET)
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * 편리한 축약 애노테이션 (코드보기) * @GetMapping
     *
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능 (아래 생략 예시)
     * @PathVariable("userId") String userId -> @PathVariable userId
     * /mapping/userA
     */

//    @GetMapping("/mapping/{userId}")
//    public String mappingPath(@PathVariable("userId") String data) {
//        log.info("mappingPath userId={}", data);
//        return "ok";
//    }

    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {
        log.info("mappingPath userId={}", userId);
        return "ok";
    }

    /**
     * PathVariable 다중 사용
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"} -> 두 경우 중 하나일 때 호출
     */
    // url에 mode=debug라는 파라미터가 들어가야 매핑되도록 설정
    // /mapping-param?mode=debug -> 이렇게 해야 매핑되어 정상 호출됨
    @GetMapping(value = "/mapping-param", params = "mode=param")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    // 요청 정보의 header에 "mode" 값을 "debug"로 설정한 뒤 요청해야 매핑됨 (postman)
    // mode=debug 뿐만 아니라 원하는 대로 다 가능 -> int=3, abc=123 등
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    // header의 Content-Type이 application/json인 경우에만 매핑
    // consumes : 해당 애노테이션이 붙은 컨트롤러가 처리할 수 있는 요청 Content-Type을 제한함
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    // HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑한다
    // produces : 클라이언트의 Accept와 관련
    // 클라이언트가 보낸 Accept 헤더 : 클라이언트 입장에서 자신이 받을 수 있는 Content-Type을 서버로 전달해 준 것
    // produces를 클라이언트가 보낸 Accept 헤더의 Content-Type과 비교하여 맞지 않으면 Error를 내보냄
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }


    // 아래 22,33과 같이 스프링에서 만들어둔 형식이 있음
    @PostMapping(value = "/mapping-produce22", produces = MediaType.APPLICATION_JSON_VALUE)
    public String mappingProduces22() {
        log.info("mappingProduces22");
        return "ok";
    }

    @PostMapping(value = "/mapping-produce33", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces33() {
        log.info("mappingProduces33");
        return "ok";
    }

}
