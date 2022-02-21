package blind.back.blind.domain.user.controller;

import blind.back.blind.domain.user.domain.dto.UserResponseDto;
import blind.back.blind.global.common.ResultResponseDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
@Api
public class UserController {

    @GetMapping
    @Operation(summary = "유저 정보 조회", description = "유저에 대한 정보를 반환합니다.")
    public ResultResponseDto<UserResponseDto> findUserController() {
        UserResponseDto userResponseDto = new UserResponseDto("monkey", LocalDateTime.now(),LocalDateTime.now());
        return new ResultResponseDto<>(HttpStatus.OK.value(), "조회완료",userResponseDto);
    }

    @PostMapping
    @Operation(summary = "유저 등록", description = "유저를 등록합니다.")
    public ResultResponseDto<String> createUserController() {
        String name = "monkey";
        return new ResultResponseDto<>(HttpStatus.OK.value(),"등록완료",name);
    }

    @PutMapping
    @Operation(summary = "유저 정보 수정", description = "유저 정보를 수정하고 수정 된 내용을 반환합니다.")
    public ResultResponseDto<UserResponseDto> updateUserInfoController() {
        UserResponseDto userResponseDto = new UserResponseDto("zlzl",LocalDateTime.now(),LocalDateTime.now());
        return new ResultResponseDto<>(HttpStatus.OK.value(),"수정완료",userResponseDto);
    }
}
