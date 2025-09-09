package univ.earthbreaker.namu.external.image;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import univ.earthbreaker.namu.server.external.api.image.ImageRequest;
import univ.earthbreaker.namu.server.external.api.image.ImageUploadRequest;

@FeignClient(
	name = "imageApiCaller",
	url = "http://localhost:8082/external/image",
	configuration = ImageFeignConfiguration.class)
public interface ImageApiCaller {

	@PostMapping(value = "/upload")
	ExternalImageResult uploadImage(@RequestBody ImageUploadRequest request);

	@PostMapping(value = "/delete")
	ExternalImageResult deleteImage(@RequestBody ImageRequest request);

	@GetMapping
	ExternalImageResult getImage(@RequestBody ImageRequest request);
}
