package univ.earthbreaker.namu.core.api.energy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.energy.EnergyGiftCommand;
import univ.earthbreaker.namu.core.domain.energy.EnergyGiftService;

@RestController
@RequestMapping("/v1/energy/gift")
public class EnergyGiftController {

	private final EnergyGiftService energyGiftService;

	public EnergyGiftController(EnergyGiftService energyGiftService) {
		this.energyGiftService = energyGiftService;
	}

	@AuthMapping
	@PostMapping("/{friendNo}")
	public ResponseEntity<Void> giftToFriend(
		@LoginMember Long memberNo,
		@PathVariable Long friendNo,
		@RequestBody GiftRequest request
	) {
		energyGiftService.givePointToFriend(new EnergyGiftCommand(memberNo, friendNo, request.giftPoint()));
		return ResponseEntity.ok().build();
	}
}
