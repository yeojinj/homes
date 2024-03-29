package com.ssafy.homes.member.controller;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.homes.jwt.service.JwtService;
import com.ssafy.homes.member.model.MemberDto;
import com.ssafy.homes.member.service.MemberService;
import com.ssafy.homes.qna.model.QnaDto;
import com.ssafy.homes.qna.service.QnaService;

@RestController
@RequestMapping("/user")
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private QnaService qnaService;

	@Autowired
	private JwtService jwtService;
	
	
	/** GET : 아이디 중복 체크 **/
	@GetMapping("/{userid}")
	public ResponseEntity<String> idCheck(@PathVariable("userid") String userId) throws Exception {
		if( memberService.idCheck(userId)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);	
		}
		
	}

	
	/** POST : 로그인 **/
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody MemberDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			
			System.out.println("받아온 유저 정보 : {}"+ userDto);
			MemberDto loginUser = memberService.loginMember(userDto);

			
			if (loginUser != null) {

				// 내가 입력한 아이디를 가지고 accessToken 과 refreshToken 을 만든다.
				String accessToken = jwtService.createAccessToken("userid", loginUser.getUserId());// key, data
				String refreshToken = jwtService.createRefreshToken("userid", loginUser.getUserId());// key, data

				// 만들어진 refreshToken을 DB에 저장해라.
				memberService.saveRefreshToken(userDto.getUserId(), refreshToken);

				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);

				// 결과 map에 access-token과 refresh-token 을 담는다.
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	/** DELETE : 회원탈퇴 **/
	@DeleteMapping("/{userid}")
	public ResponseEntity<String> deleteMember(@PathVariable("userid") String userid) throws Exception {
		
		 qnaService.deleteQnaWithUserId(userid) ;
			 
		if (memberService.deleteMember(userid)){
			
			
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);	
		}
		
	}
	
	
	/**UPDATE: (PUT) 회원 정보 수정**/
	@PutMapping
	public ResponseEntity<String> updateMember(@RequestBody MemberDto memberDto) throws Exception {
		System.out.println("modifyArticle - 호출 {}" + memberDto);
		
		if (memberService.updateMember(memberDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	


	/** GET:회원 인증 **/
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("userid") String userid,
			HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED; 
		
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			//logger.info("사용 가능한 토큰!!!");
			try {
				MemberDto userDto = memberService.userInfo(userid);
				System.out.println(userDto);
				resultMap.put("userInfo", userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	
	/** GET : 로그아웃 **/
	@GetMapping("/logout/{userid}")
	public ResponseEntity<?> removeToken(@PathVariable("userid") String userid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userid);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
	/** POST : refresh Token  **/
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto userDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, memberDto : {}", token, userDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(userDto.getUserId()))) {
				String accessToken = jwtService.createAccessToken("userid", userDto.getUserId());
				logger.debug("token : {}", accessToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
		
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	/** POST : 회원가입 **/
	@PostMapping("/join")
	public ResponseEntity<String> joinMember( @RequestBody MemberDto userDto){
		logger.debug("join()...");
		System.out.println("회원가입 :" + userDto);
		try {
			memberService.joinMember(userDto);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
	}
	
	
	
	
	
	
}
