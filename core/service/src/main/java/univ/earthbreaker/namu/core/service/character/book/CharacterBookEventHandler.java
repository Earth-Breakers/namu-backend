package univ.earthbreaker.namu.core.service.character.book;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import univ.earthbreaker.namu.core.domain.character.book.infra.AddFinalCharacterDbCommand;
import univ.earthbreaker.namu.core.domain.character.book.infra.MemberCharacterRepository;
import univ.earthbreaker.namu.event.character.AddCharacterBookEvent;

@Component
public class CharacterBookEventHandler {

	private final MemberCharacterRepository memberCharacterRepository;

	public CharacterBookEventHandler(MemberCharacterRepository memberCharacterRepository) {
		this.memberCharacterRepository = memberCharacterRepository;
	}

	@EventListener
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addFinalCharacterToCharacterBook(AddCharacterBookEvent event) {
		AddFinalCharacterDbCommand command = new AddFinalCharacterDbCommand(event.memberNo(), event.characterNo());
		memberCharacterRepository.createOrUpdate(command);
	}
}
