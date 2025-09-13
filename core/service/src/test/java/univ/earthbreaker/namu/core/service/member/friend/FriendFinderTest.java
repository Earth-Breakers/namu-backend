package univ.earthbreaker.namu.core.domain.member.friend;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.FRIEND_EXIST;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.FRIEND_NOT_EXIST;
import static univ.earthbreaker.namu.core.domain.member.MemberFixture.MEMBER_NO;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.member.friend.infra.FriendRepository;

@ExtendWith(MockitoExtension.class)
class FriendFinderTest {

	private @Mock FriendRepository friendRepository;
	private @InjectMocks FriendFinder friendFinder;

	@DisplayName("회원 번호를 받아 해당 회원의 친구 정보를 반환한다")
	@ParameterizedTest
	@MethodSource("provideFriend")
	void find(Friend expect) {
		// given
		Mockito.when(friendRepository.findAll(MEMBER_NO))
			.thenReturn(expect);

		// when
		Friend actual = friendFinder.findAll(MEMBER_NO);

		// then
		assertThat(actual).isNotNull();
		assertThat(actual).isEqualTo(expect);
	}

	private static Stream<Arguments> provideFriend() {
		return Stream.of(
			Arguments.of(FRIEND_EXIST),
			Arguments.of(FRIEND_NOT_EXIST)
		);
	}
}
