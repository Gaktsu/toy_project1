package com.toyproject.shoppingManage.Member;

import com.toyproject.shoppingManage.Member.Exception.DuplicateMemberException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/member")
@Validated
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> requestGetMember(@PathVariable("id") @Min(value = 1) Long id){
        MemberResponseDTO responseBody = memberService.getMember(id);

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/register")
    public ResponseEntity<?> requestRegister(@Valid @RequestBody MemberRequestDTO member){
        MemberResponseDTO responseBody = memberService.registerMember(member);

        return ResponseEntity.ok().body(responseBody);
    }
}
