package univ.earthbreaker.namu.core.domain.mission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import univ.earthbreaker.namu.core.domain.common.SelfValidating;

public class CertifiedMissionPostCommand extends SelfValidating<CertifiedMissionPostCommand> {

	private final @NotNull Long memberNo;
	private final @NotBlank String content;
	private final @NotBlank String imagePathKey;

	public CertifiedMissionPostCommand(Long memberNo, String content, String imagePathKey) {
		this.memberNo = memberNo;
		this.content = content;
		this.imagePathKey = imagePathKey;
		this.validateSelf("memberNo 가 null 이거나, content, imagePathKey 는 공백일 수 없습니다");
	}

	public Long getMemberNo() {
		return memberNo;
	}

	public String getContent() {
		return content;
	}

	public String getImagePathKey() {
		return imagePathKey;
	}
}
