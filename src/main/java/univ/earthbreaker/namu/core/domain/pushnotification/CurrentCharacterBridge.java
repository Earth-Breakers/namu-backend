package univ.earthbreaker.namu.core.domain.pushnotification;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public interface CurrentCharacterBridge {
	@NotNull CharacterQuery findCurrentCharacter(long memberNo);
}
