package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> paraMap) {
        return new ModelView("new-form");
        // 논리적 이름 저장
        // 물리적 이름은 "FrontController"에서 처리
    }
}
