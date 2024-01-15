package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    // ObjectMapper.writeValue : Java 객체 -> JSON
    // ObjectMapper.readValue : JSON -> Java 객체
    private ObjectMapper objectMapper = new ObjectMapper();

    // json 형식의 HTTP 메시지 바디를 읽어서 문자로 변환한 뒤 messageBody 에 저장
    // 문자로 된 JSON 데이터인 messageBody를  JAVA 객체(Hello 클래스 객체)로 변환
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        // messageBody 읽어온 JSON
        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        // JSON 을 helloData 객체로 변환한 것
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws JsonProcessingException {

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    // JSON 을 문자로 변환한 뒤 JAVA 객체로 변환 X -> 한번에 JAVA 객체(HelloData)로 변환 가능
    // @RequestBody 생략하면 HelloData는 단순타입(int, String..)이 아니기 때문에
    // @RequestBody가 아니라 @ModelAttribute가 자동으로 붙게 됨 -> 생략 불가능
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) {

        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    // HttpEntity 사용 (요청 메시지의 Head, Body를 읽을 수 있는 기능)
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {

        HelloData data = httpEntity.getBody();
        log.info("HelloData={}", data);
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }

    // 응답도 특정 형식으로 바로 가능
    // HelloData 형식으로 내보낸 응답을 컨버터가 JSON형식으로 바꿔서 내보내줌 (Accept가 JSON이어야 함)
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {

        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return data;
    }
}
