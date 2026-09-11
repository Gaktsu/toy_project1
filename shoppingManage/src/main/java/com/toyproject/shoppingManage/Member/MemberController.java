package com.toyproject.shoppingManage.Member;

import com.toyproject.shoppingManage.Member.Exception.DuplicateMemberException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@Validated
public class MemberController {

    // ----------------------- FIELD --------------------------//

    private final MemberService memberService;

    // ----------------------- CONSTRUCTOR --------------------------//

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    // ----------------------- RESTAPI : GET --------------------------//

    @GetMapping("/{id}")
    public ResponseEntity<?> requestGetMember(@PathVariable("id") @Min(value = 1) Long id){
        MemberResponseDTO responseBody = memberService.requestGetMember(id);

        return ResponseEntity.ok().body(responseBody);
    }

    // ----------------------- RESTAPI : POST --------------------------//

    @PostMapping
    public ResponseEntity<?> requestRegister(@Valid @RequestBody MemberRequestDTO request){
        MemberResponseDTO responseBody = memberService.requestRegisterMember(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }
}
