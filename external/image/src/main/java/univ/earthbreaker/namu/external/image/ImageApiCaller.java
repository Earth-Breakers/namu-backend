package univ.earthbreaker.namu.external.image;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
	name = "imageApiCaller",
	url = "http://localhost:8082/external/image",
	configuration = ImageFeignConfiguration.class)
public interface ImageApiCaller {

	@PostMapping(value = "/upload")
	ExternalImageResult uploadImage();

	@PostMapping(value = "/delete")
	ExternalImageResult deleteImage();

	@GetMapping
	ExternalImageResult getImage();
}
