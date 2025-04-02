package univ.earthbreaker.namu.core.domain.member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.MEMBER;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.MEMBER_NO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberFinderTest {

	private @Mock MemberRepository memberRepository;
	private @InjectMocks MemberFinder memberFinder;

	@DisplayName("회원 번호를 받아 해당 회원을 찾아서 반환한다")
	@Test
	void success_find() {
	    // given
		when(memberRepository.findMemberNoOrNull(MEMBER_NO))
			.thenReturn(MEMBER);

	    // when
		Member member = memberFinder.find(MEMBER_NO);

		// then
		assertThat(member).isNotNull();
		assertThat(member).isEqualTo(MEMBER);
	}

	@DisplayName("회원 번호를 받아서 찾고, 해당 회원이 없다면 예외를 발생시킨다")
	@Test
	void fail_find() {
	    // given
		when(memberRepository.findMemberNoOrNull(MEMBER_NO))
			.thenReturn(null);

	    // when, then
		assertThatThrownBy(() -> memberFinder.find(MEMBER_NO))
			.isInstanceOf(MemberNotFoundException.class)
			.hasMessage(MemberNotFoundException.notFound(MEMBER_NO).getMessage());
	}
}
