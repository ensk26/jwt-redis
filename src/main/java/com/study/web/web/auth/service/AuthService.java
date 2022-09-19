package com.study.web.web.auth.service;

import com.study.web.DuplicationValidate;
import com.study.web.domain.jwtToken.service.RefreshTokenService;
import com.study.web.domain.member.entity.Member;
import com.study.web.domain.member.service.MemberService;
import com.study.web.global.error.exception.ErrorCode;
import com.study.web.global.error.exception.NotValidTokenException;
import com.study.web.global.jwt.JwtTokenUtil;
import com.study.web.web.auth.dto.UpdatePasswordRequestDto;
import com.study.web.web.auth.dto.JwtResponseDto;
import com.study.web.web.auth.dto.MemberLoginRequestDto;
import com.study.web.web.auth.dto.MemberSignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.study.web.global.jwt.JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME;

@Service
//@Transactional
//오류 발생시 롤백 해주는 어노테이션
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    //private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final DuplicationValidate duplicationValidate;
    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;

    public String signup(MemberSignupRequestDto requestDto) throws Exception {

        //이미 해당 이메일로 된 계정이 존재한다.
        if (memberService.findMember(requestDto.getEmail())!=null){
            throw new Exception("이미 해당 이메일로 된 계정이 존재");
        }

        Member member = requestDto.toEntity(passwordEncoder);
        memberService.saveMember(member);
        return member.getEmail();
    }

    public JwtResponseDto login(MemberLoginRequestDto requestDto) throws Exception {

        //스프링 시큐러티로 만든 provider가 반환한 Authentication 객체는 나중에 구현 해보자

        Member member = memberService.findMember(requestDto.getEmail());
        if (member == null) {
            throw new NoSuchElementException("해당 이메일이 존재하지 않음");
        }
                //.orElseThrow(()->new NoSuchElementException("해당 이메일이 존재하지 않음"));
        //.orElseThrow 비어있는 파라미터를 던지면, NoSuchElementException가 발생함

        //비밀번호가 일치하는지
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀림");
            //적절하지 않은 인자나 메소드를 받을때 발생하는 오류
        }
        //uuid로 토큰 생성
        String email = member.getEmail();
        String accessToken = jwtTokenUtil.generateAccessToken(email,member.getRole());
        String refreshToken= jwtTokenUtil.generateRefreshToken(email,member.getRole());

        refreshTokenService.saveRefreshToken(email, refreshToken, REFRESH_TOKEN_EXPIRATION_TIME.getValue());

        //왜 refreshToken은 생성때 accessToken과 같은 만료 기간을 사용해 생성해서, redis의 생성기간을 또 지정을 해주지
        //JwtTokenUtil.validateToken(accessToken);
        return JwtResponseDto.toEntity(accessToken, refreshToken);
    }

    //캐시 제거
    public void logout(String email) {
        //refreshToken 삭제
        removeCache(email);
        refreshTokenService.deleteRefreshToken(email);
    }

    public void Withdrawal(String email) {
        removeCache(email);
        refreshTokenService.deleteRefreshToken(email);
        memberService.deleteMember(email);
    }

    //todo: 이메일 매개 변수로 받아와야함, CacheEvict 캐시 삭제를 위해 지정해줄 key 값 필요
    //닉네임,비밀번호 변경 구현
    //앞에서 간단히 검증, 여기서 db에서 회원정보확인하고 처리

    public JwtResponseDto reIssueAccessToken(String token) {

        jwtTokenUtil.validateRefreshTokn(token); //refresh toekn이 유효한지 확인
        String email = jwtTokenUtil.getEmail(token);

        if (!token.equals(refreshTokenService.findRefreshToken(email))) {
            log.error("존재하지 않는 refresh Token");
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }
        Member member = memberService.findMember(email);
        String accessToken = jwtTokenUtil.generateAccessToken(member.getEmail(),member.getRole());
        String refreshToken = jwtTokenUtil.generateRefreshToken(member.getEmail(), member.getRole());

        refreshTokenService.saveRefreshToken(email, refreshToken, REFRESH_TOKEN_EXPIRATION_TIME.getValue());

        return JwtResponseDto.toEntity(accessToken, refreshToken);
        //todo 오류 메시지 정리하기
        //중복되는거 나중에 private로 빼기
    }

    public void UpdatePassword(Long id, UpdatePasswordRequestDto requestDto) {
        //패스워드가 같은지 비교
        if (!requestDto.getPassword().equals(requestDto.getRepassword())) {
            log.error("입력한 비밀번호가 서로 다르다.");
            throw new IllegalArgumentException("입력한 비밀번호가 다릅니다.");
        }
        //같으면 패스워드 암호화 하고 id를 db에 저장
        String password = passwordEncoder.encode(requestDto.getPassword());
        memberService.updatePassword(id,password);
    }

    public void UpdateName(Long id, String name) {
        if (name == null) {
            log.error("입력한 이름이 비어있다.");
            throw new IllegalArgumentException("입력한 이름이 비어있다.");
        }
        memberService.updateName(id,name);
    }


    //@CacheEvict(value = CacheKey.USER,key = "#email")
    private void removeCache(String email) {
    }

    //todo 비밀번호 변경, 닉네임 변경 하기

    //todo 리프레시 토큰 탈취시 액세스 토큰 재발급해 이용하는것을 맏기 위해 로그인을 알려주는 기능을 구현하기

}
