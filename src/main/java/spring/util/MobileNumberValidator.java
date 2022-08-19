package spring.util;

import org.springframework.stereotype.Component;
import spring.exception.CustomException;

import static spring.constant.ApplicationConstant.INVALID_INPUT;

@Component
public class MobileNumberValidator {


    private static final String MOBILE_NUMBER_START_PREFIX = "+94";

    public String convertMobileNumber(String inputMobileNumber) {

        String convertedMobileNumber;

        if (inputMobileNumber == null || inputMobileNumber.length() == 0)
            throw new CustomException(INVALID_INPUT, "Input field is empty");

        if (inputMobileNumber.startsWith("+94") && inputMobileNumber.length() == 12) {
            convertedMobileNumber = inputMobileNumber;

        } else if (inputMobileNumber.startsWith("94") && inputMobileNumber.length() == 11) {
            convertedMobileNumber = MOBILE_NUMBER_START_PREFIX + inputMobileNumber.substring(2);

        } else if (inputMobileNumber.startsWith("0") && inputMobileNumber.length() == 10) {
            convertedMobileNumber = MOBILE_NUMBER_START_PREFIX + inputMobileNumber.substring(1);

        } else if (inputMobileNumber.startsWith("7") && inputMobileNumber.length() == 9) {
            convertedMobileNumber = MOBILE_NUMBER_START_PREFIX + inputMobileNumber;

        } else {
            throw new CustomException(INVALID_INPUT, "Invalid mobile number");
        }

        return convertedMobileNumber;
    }
}
