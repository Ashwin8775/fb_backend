package com.iocl.fb.payload;

public class LoginRequest {

	private String userName;
	private String password;
	private String secret;
	private String captchaAnswer;
	

	public LoginRequest() {
		super();

	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @return the captchaAnswer
	 */
	public String getCaptchaAnswer() {
		return captchaAnswer;
	}

	/**
	 * @param captchaAnswer the captchaAnswer to set
	 */
	public void setCaptchaAnswer(String captchaAnswer) {
		this.captchaAnswer = captchaAnswer;
	}

}
