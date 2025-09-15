package univ.earthbreaker.namu.core.service.member.friend;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import univ.earthbreaker.namu.core.domain.member.friend.FriendRelationCommand;
import univ.earthbreaker.namu.core.domain.member.friend.infra.FriendRepository;
import univ.earthbreaker.namu.core.service.member.MemberFixture;

@ExtendWith(MockitoExtension.class)
class FriendRegisterTest {

	private @Mock FriendRepository friendRepository;
	private @InjectMocks FriendRegister friendRegister;

	@DisplayName("팔로우할 주체 회원 번호와 팔로우 할 상대의 회원 번호를 받아, 이미 팔로우가 되어있다면 등록하지 않는다")
	@Test
	void do_not_register() {
	    // given
		FriendRelationCommand friendRelationCommand = new FriendRelationCommand(
			MemberFixture.MEMBER_NO, MemberFixture.FOLLOWING_MEMBER_NO);
		when(friendRepository.existsBy(MemberFixture.MEMBER_NO, MemberFixture.FOLLOWING_MEMBER_NO))
			.thenReturn(true);

		// when
		friendRegister.register(friendRelationCommand);

	    // then
		verify(friendRepository, never()).register(MemberFixture.MEMBER_NO, MemberFixture.FOLLOWING_MEMBER_NO);
	}

	@DisplayName("팔로우할 주체 회원 번호와 팔로우 할 상대의 회원 번호를 받아, 이미 팔로우가 되어있지 않다면 등록한다")
	@Test
	void do_register() {
		// given
		FriendRelationCommand friendRelationCommand = new FriendRelationCommand(
			MemberFixture.MEMBER_NO, MemberFixture.FOLLOWING_MEMBER_NO);
		when(friendRepository.existsBy(MemberFixture.MEMBER_NO, MemberFixture.FOLLOWING_MEMBER_NO))
			.thenReturn(false);

		// when
		friendRegister.register(friendRelationCommand);

		// then
		verify(friendRepository).register(MemberFixture.MEMBER_NO, MemberFixture.FOLLOWING_MEMBER_NO);
	}
}
