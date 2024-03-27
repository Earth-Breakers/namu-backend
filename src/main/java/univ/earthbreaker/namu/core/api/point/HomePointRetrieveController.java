package univ.earthbreaker.namu.core.api.point;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.earthbreaker.namu.core.api.auth.support.AuthMapping;
import univ.earthbreaker.namu.core.api.auth.support.LoginMember;
import univ.earthbreaker.namu.core.domain.point.Energy;
import univ.earthbreaker.namu.core.domain.point.EnergyPointRetrieveService;

@RestController
@AuthMapping
@RequestMapping("/v1/points")
public class HomePointRetrieveController {

	private final EnergyPointRetrieveService energyPointRetrieveService;

	public HomePointRetrieveController(EnergyPointRetrieveService energyPointRetrieveService) {
		this.energyPointRetrieveService = energyPointRetrieveService;
	}

	@GetMapping("/home")
	public ResponseEntity<HomePointResponse> retrieve(@LoginMember Long memberNo) {
		Energy energyPoint = energyPointRetrieveService.retrieve(memberNo);
		return ResponseEntity.ok(HomePointResponse.from(energyPoint));
	}
}
