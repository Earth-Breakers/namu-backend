package univ.earthbreaker.namu.core.api.point;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.point.EnergyPointGiftService;

@RestController
@RequestMapping("/v1/points")
public class EnergyPointGiftController {

	private final EnergyPointGiftService energyPointGiftService;

	public EnergyPointGiftController(EnergyPointGiftService energyPointGiftService) {
		this.energyPointGiftService = energyPointGiftService;
	}

	@AuthMapping
	@PostMapping("/gift/{targetMemberNo}")
	public ResponseEntity<Void> giveEnergyPointToFriend(
		@LoginMember Long memberNo,
		@PathVariable Long targetMemberNo,
		@RequestBody EnergyGiftRequest request
	) {
		energyPointGiftService.giftEnergyPointToFriend(request.toCommand(memberNo, targetMemberNo));
		return ResponseEntity.noContent().build();
	}
}
