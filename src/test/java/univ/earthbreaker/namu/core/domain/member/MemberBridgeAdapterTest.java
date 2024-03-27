package univ.earthbreaker.namu.core.domain.member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.MEMBER;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.MEMBER_NO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.pushnotification.MemberQuery;

@ExtendWith(MockitoExtension.class)
class MemberBridgeAdapterTest {

	private @Mock MemberFinder memberFinder;
	private @InjectMocks MemberBridgeAdapter memberBridgeAdapter;

	@DisplayName("회원 번호를 받아 회원을 찾고 회원의 닉네임을 반환한다")
	@Test
	void findMember() {
		// given
		when(memberFinder.find(MEMBER_NO))
			.thenReturn(MEMBER);

		// when
		MemberQuery actual = memberBridgeAdapter.findMember(MEMBER_NO);

		// then
		assertThat(actual).isNotNull();
		assertThat(actual.nickname()).isEqualTo(MEMBER.getNickname());
	}
}
