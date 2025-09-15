package univ.earthbreaker.namu.core.service.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static univ.earthbreaker.namu.core.service.member.MemberFixture.MEMBER;
import static univ.earthbreaker.namu.core.service.member.MemberFixture.MEMBER_NO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.service.post.PostMemberBridge;

@ExtendWith(MockitoExtension.class)
class PostMemberBridgeAdapterTest {

	private @Mock MemberFinder memberFinder;
	private @InjectMocks PostMemberBridgeAdapter postMemberBridgeAdapter;

	@DisplayName("회원의 번호를 받아 게시글을 작성하고자 하는 회원의 정보(회원 번호, 회원 닉네임)을 반환한다")
	@Test
	void findMemberInfo() {
		// given
		Mockito.when(memberFinder.find(MEMBER_NO))
			.thenReturn(MEMBER);

		// when
		PostMemberBridge.PostMemberDto actual = postMemberBridgeAdapter.findMemberInfo(MEMBER_NO);

		// then
		assert actual != null;
		assertAll(
			() -> assertThat(actual).isNotNull(),
			() -> assertThat(actual.memberNo()).isEqualTo(MEMBER.getNo()),
			() -> assertThat(actual.nickname()).isEqualTo(MEMBER.getNickname())
		);
	}
}
