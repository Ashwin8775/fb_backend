package com.iocl.fb.payload;

public class CaptchaResponse {

	private String secret;
	  private String captchaImage;
	  private String validMagic;

	  public CaptchaResponse() {
	    super();
	  }

	  /**
	   * @param timestamp
	   * @param captchaImage
	   */

	  /**
	   * @return the secret
	   */
	  public String getSecret() {
	    return secret;
	  }

	  public CaptchaResponse(String secret, String captchaImage, String validMagic) {
	    super();
	    this.secret = secret;
	    this.captchaImage = captchaImage;
	    this.validMagic = validMagic;
	  }

	  /**
	   * @param secret the secret to set
	   */
	  public void setSecret(String secret) {
	    this.secret = secret;
	  }

	  /**
	   * @return the captchaImage
	   */
	  public String getCaptchaImage() {
	    return captchaImage;
	  }

	  /**
	   * @param captchaImage the captchaImage to set
	   */
	  public void setCaptchaImage(String captchaImage) {
	    this.captchaImage = captchaImage;
	  }

	  /*
	   * (non-Javadoc)
	   * 
	   * @see java.lang.Object#toString()
	   */

	  @Override
	  public String toString() {
	    return "CAPTCHAResponse [captchaImage=" + captchaImage + ", secret=" + secret + "]";
	  }

	  public String getValidMagic() {
	    return validMagic;
	  }

	  public void setValidMagic(String validMagic) {
	    this.validMagic = validMagic;
	  }
	  
}
