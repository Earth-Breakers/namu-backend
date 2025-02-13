package univ.earthbreaker.namu.external.image;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import univ.earthbreaker.namu.external.server.ExternalImageResponse;

@FeignClient(
	name = "imageApiCaller",
	url = "http://localhost:8080/external/image/",
	configuration = ImageFeignConfiguration.class)
public interface ImageApiCaller {

	@PostMapping(value = "/upload")
	ExternalImageResponse uploadImage();

	@PostMapping(value = "/delete")
	ExternalImageResponse deleteImage();
}
