package univ.earthbreaker.namu.external.image;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import feign.Response;

@FeignClient(
	name = "imageApiCaller",
	url = "http://localhost:8080/external/image/",
	configuration = ImageFeignConfiguration.class)
public interface ImageApiCaller {

	@PostMapping(value = "/upload/success")
	Response uploadImage();

	@PostMapping(value = "/delete/success")
	Response deleteImage();
}
