package com.corporaciongpf.web.filters;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.corporaciongpf.adm.vo.Auth;
import com.corporaciongpf.web.utils.Errors;
import com.corporaciongpf.web.utils.ParametersInt;
import com.corporaciongpf.web.vo.ResponseAuth;

public class GeneralFilter {

	ResponseAuth responseAuth = new ResponseAuth();
	String login = "s4l3sF0rc3S3rv1c3sC0rp0r4c10nGPF";
	String secretKey = "i72MGKMNqklP5ZSu";
	
	public GeneralFilter() {
	}
	
	public boolean getAuth(Auth json) {
		if(json.getLogin() == null) {
			return false;
		} else {
			if(!this.login.equals(json.getLogin())) {
				this.responseAuth.status.status = "failed";
				this.responseAuth.status.reason = Errors.E101.getStatus();
				this.responseAuth.status.message = Errors.E101.getMensaje();
				this.responseAuth.status.date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault())).format(new Date());
				return false;
			}
		}
		
		if(json.getTranKey()== null) {
			return false;
		}else {
			if( !this.getTranKey(base64Decode(json.getNonce()), json.getSeed()).equals(json.getTranKey()) ) {
				this.responseAuth.status.status = "failed";
				this.responseAuth.status.reason = Errors.E102.getStatus();
				this.responseAuth.status.message = Errors.E102.getMensaje();
				this.responseAuth.status.date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault())).format(new Date());
				return false;
			}
		}
		
		if(json.getNonce() == null) {
			return false;
		}
		if(json.getSeed() == null) {
			return false;
		} else {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mmZ");
			
			DateTime request = DateTime.parse(json.getSeed(), formatter);
			DateTime current = formatter.parseDateTime( (new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault())).format(new Date()) );
			Minutes diference = Minutes.minutesBetween(request, current);
			if( !(diference.getMinutes() <= ParametersInt.MAX_MINUTES_AUTH.getValue()) ) {
				this.responseAuth.status.status = "failed";
				this.responseAuth.status.reason = Errors.E105.getStatus();
				this.responseAuth.status.message = Errors.E105.getMensaje();
				this.responseAuth.status.date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault())).format(new Date());
				return false;
			}
		}
		return true;
	}
	
	public ResponseAuth getResponseAuth() {
		return this.responseAuth;
	}
	
    public String getTranKey(String nonce, String seed) {
        try {
            return base64Encode(sha1(nonce + seed + this.secretKey));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    static byte[] sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        return mDigest.digest(input.getBytes());
    }
    
    static String base64Encode(byte[] input) {
        byte[] encodedBytes = (Base64.getEncoder()).encode(input);
        return new String(encodedBytes);
    }

    static String base64Decode(String input) {
        byte[] decodeBytes = (Base64.getDecoder()).decode(input);
        return new String(decodeBytes);
    }
    
}