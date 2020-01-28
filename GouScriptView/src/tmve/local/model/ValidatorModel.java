/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.model;

import java.util.regex.Pattern;

/**
 *
 * @author P05144
 */
public class ValidatorModel {
    private static final String zeroTo255
            = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
 
    private static final String IP_REGEXP
            = zeroTo255 + "\\." + zeroTo255 + "\\."
            + zeroTo255 + "\\." + zeroTo255;
 
    private static final Pattern IP_PATTERN
            = Pattern.compile(IP_REGEXP);
    // Return true when *address* is IP Address
    public static boolean isValidIp(String address) {
        return IP_PATTERN.matcher(address).matches();
    }
}
