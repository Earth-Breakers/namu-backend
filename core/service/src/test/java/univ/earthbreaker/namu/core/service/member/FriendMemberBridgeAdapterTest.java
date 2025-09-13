package univ.earthbreaker.namu.core.domain.member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.FOLLOWING_MEMBER_NO;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.MEMBER;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.member.infra.MemberRepository;

@ExtendWith(MockitoExtension.class)
class FriendMemberBridgeAdapterTest {

	private @Mock MemberRepository memberRepository;
	private @InjectMocks FriendMemberBridgeAdapter friendMemberBridgeAdapter;

	@DisplayName("팔로우할 회원이 존재하는지 여부를 확인하고, 존재하지 않는다면 예외를 발생시킨다")
	@Test
	void checkExist_throwException() {
	    // given
		when(memberRepository.findMemberNoOrNull(FOLLOWING_MEMBER_NO))
			.thenReturn(null);

	    // when, then
		assertThatThrownBy(() -> friendMemberBridgeAdapter.checkExist(FOLLOWING_MEMBER_NO))
			.isInstanceOf(MemberNotFoundException.class)
			.hasMessage(MemberNotFoundException.notFound(FOLLOWING_MEMBER_NO).getMessage());
	}

	@DisplayName("팔로우할 회원이 존재하는지 여부를 확인하고, 존재한다면 아무 예외도 발생하지 않는다")
	@Test
	void checkExist_do_not_throwException() {
		// given
		when(memberRepository.findMemberNoOrNull(FOLLOWING_MEMBER_NO))
			.thenReturn(MEMBER);

		// when, then
		assertDoesNotThrow(() -> friendMemberBridgeAdapter.checkExist(FOLLOWING_MEMBER_NO));
	}
}
