package univ.earthbreaker.namu.core.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class CommonExceptionController {

	@PostMapping("/exception")
	public void commonException(@RequestBody ExceptionRequest request) {
		throw new RuntimeException("공통 예외 형식 문서화를 위한 예외");
	}

	record ExceptionRequest(
		String body
	) {
	}
}
