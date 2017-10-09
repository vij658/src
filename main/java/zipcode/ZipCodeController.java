package zipcode;

import java.util.Arrays;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZipCodeController {

	@ExceptionHandler(ZipCodeException.class)
	@RequestMapping(value = "/Get", method = RequestMethod.GET)
	public ZipCode getJson(@RequestParam("zipCodeList") int[] zipCodeList) throws ZipCodeException {
		validate(zipCodeList);
		ZipCode zipCode = new ZipCode(zipCodeList);
		zipCode.setFinalOutput(processZipCode(zipCode));
		return zipCode;

	}

	private void validate(int[] zipCodeList) throws ZipCodeException {
		if (zipCodeList.length < 0) {
			throw new ZipCodeException("please enter atleast one valid zip code, a zipcode size is 5 digits");
		} else {
			for (int zipcode : zipCodeList) {
				if (!(Integer.toString(zipcode).length() == 5)) {
					throw new ZipCodeException("please enter atleast one valid zip code, a zipcode size is 5 digits");
				}
			}
		}
	}

	private String processZipCode(ZipCode zipCode) {
		int[] zipCodeList = zipCode.getZipCodeList();
		Arrays.sort(zipCodeList);
		return "The maximum of the zip code list :" + zipCodeList[zipCodeList.length-1];
	}
}
