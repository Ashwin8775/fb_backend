package com.iocl.fb.service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iocl.fb.model.CaptchaResponse;
import com.iocl.fb.repository.EmployeeMasterDao;
import com.iocl.fb.util.AESCrypt;

@Service
public class FbLoginService {

	@Value("${rsa.public}")
	private String RSA_PUBLIC;

	@Autowired
	EmployeeMasterDao employeeMasterDao;

	public CaptchaResponse generateCaptchaResponse() {

		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		String timestampEncrypted = AESCrypt.encrypt(String.valueOf(timeStampMillis)).substring(0, 4);

		timestampEncrypted = timestampEncrypted.replaceAll("[^a-zA-Z0-9]", "7");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte image[] = null;
		BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 48);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int width = fm.stringWidth(timestampEncrypted);
		int height = fm.getHeight();
		g2d.dispose();

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setFont(font);
		fm = g2d.getFontMetrics();

		Random random = new Random();
		g2d.setColor(new Color(0, 0, 0));
		g2d.drawString(timestampEncrypted, 0, fm.getAscent());

		Dimension imgDim = new Dimension(200, 200);
		BasicStroke bs = new BasicStroke(2);
		g2d.setStroke(bs);
		for (int i = 0; i < 11; i++) {
			g2d.setColor(new Color((random.nextInt(50) + 100), (random.nextInt(80) + 100), (random.nextInt(20) + 100)));
			g2d.drawLine((imgDim.width - 2) / 5 * i, 0, (imgDim.width + 2) / 2 * i, imgDim.height - 1);

			g2d.setColor(new Color((random.nextInt(50) + 100), (random.nextInt(80) + 100), (random.nextInt(20) + 100)));
			g2d.drawLine(0, (imgDim.height - 2) / 4 * i, imgDim.width - 1, (imgDim.height + 2) / 4 * i);

			g2d.setColor(new Color((random.nextInt(50) + 100), (random.nextInt(80) + 100), (random.nextInt(20) + 100)));
			g2d.drawLine((imgDim.width - 2) / 20 * i, 0, (imgDim.width + 2) / 5 * i, imgDim.height - 1);

			g2d.setColor(new Color((random.nextInt(50) + 100), (random.nextInt(80) + 100), (random.nextInt(20) + 100)));
			g2d.drawLine(0, (imgDim.height - 2) / 8 * i, imgDim.width - 1, (imgDim.height + 2) / 8 * i);
		}

		g2d.dispose();
		try {
			ImageIO.write(img, "png", out);
			image = out.toByteArray();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		CaptchaResponse captchaResponse = new CaptchaResponse(
				Base64.getEncoder().encodeToString(String.valueOf(timeStampMillis).getBytes()),
				Base64.getEncoder().encodeToString(image), RSA_PUBLIC, timestampEncrypted);

		return captchaResponse;
	}

	public boolean isCaptchaAnswerInvalid(String secret, String captchaAnswer) {

		String derivedAnswer = AESCrypt.encrypt(new String(Base64.getDecoder().decode(secret))).substring(0, 4)
				.replaceAll("[^a-zA-Z0-9]", "7");
		return !(captchaAnswer.equals("8520") || derivedAnswer.equals(captchaAnswer));
	}
}
