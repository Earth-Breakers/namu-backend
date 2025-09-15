package univ.earthbreaker.namu.core.service.pushnotification;

import org.springframework.stereotype.Component;

import univ.earthbreaker.namu.core.domain.pushnotification.CharacterQuery;

@Component
public interface CurrentCharacterBridge {
	CharacterQuery findCurrentCharacter(long memberNo);
}
