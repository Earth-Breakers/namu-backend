package univ.earthbreaker.namu.core.domain.character.book;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import univ.earthbreaker.namu.event.character.AddCharacterBookEvent;

@Component
public class CharacterBookEventHandler {

	private final MemberCharacterRepository memberCharacterRepository;

	public CharacterBookEventHandler(MemberCharacterRepository memberCharacterRepository) {
		this.memberCharacterRepository = memberCharacterRepository;
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addFinalCharacterToCharacterBook(@NotNull AddCharacterBookEvent event) {
		AddFinalCharacterDbCommand command = new AddFinalCharacterDbCommand(event.memberNo(), event.characterNo());
		memberCharacterRepository.createOrUpdate(command);
	}

	public record AddFinalCharacterDbCommand(long memberNo, long characterNo) {
	}
}
