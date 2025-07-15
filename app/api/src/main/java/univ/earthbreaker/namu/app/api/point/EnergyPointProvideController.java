package univ.earthbreaker.namu.core.api.point;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.support.AuthMapping;
import univ.earthbreaker.namu.core.support.LoginMember;
import univ.earthbreaker.namu.core.domain.point.EnergyPointProvideService;

@RestController
@RequestMapping("/v1/points")
public class EnergyPointProvideController {

	private final EnergyPointProvideService energyPointProvideService;

	public EnergyPointProvideController(EnergyPointProvideService energyPointProvideService) {
		this.energyPointProvideService = energyPointProvideService;
	}

	@AuthMapping
	@PostMapping("/use")
	public ResponseEntity<Void> provideEnergyToCharacter(
		@LoginMember Long memberNo,
		@RequestBody ProvideEnergyRequest request
	) {
		energyPointProvideService.provideEnergyToCharacter(request.toCommand(memberNo));
		return ResponseEntity.noContent().build();
	}
}
