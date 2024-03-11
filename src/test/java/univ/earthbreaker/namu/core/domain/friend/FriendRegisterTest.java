package univ.earthbreaker.namu.core.domain.friend;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FriendRegisterTest {

	private static final Long MEMBER_NO = 1L;
	private static final Long TARGET_MEMBER_NO = 2L;

	private @Mock FriendRepository friendRepository;
	private @InjectMocks FriendRegister friendRegister;

	@DisplayName("팔로우할 주체 회원 번호와 팔로우 할 상대의 회원 번호를 받아 등록한다")
	@Test
	void register() {
	    // given
		FriendRelationCommand friendRelationCommand = new FriendRelationCommand(MEMBER_NO, TARGET_MEMBER_NO);

		// when
		friendRegister.register(friendRelationCommand);

	    // then
		verify(friendRepository).register(MEMBER_NO, TARGET_MEMBER_NO);
	}
}
