package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paraMap) {
        String username = paraMap.get("username");
        int age = Integer.parseInt(paraMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save-result"); // 논리적 이름
        mv.getModel().put("member", member);
        // "model"은 단순한 "map"이므로 "model"에 뷰에서 필요한 member 객체를 담고 반환.

        return mv;
    }
}
