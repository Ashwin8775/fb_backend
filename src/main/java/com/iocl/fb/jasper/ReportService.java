package com.iocl.fb.jasper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Year;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.iocl.fb.dto.GenerateTakeoverPdfDto;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportService {

	public String claimData(GenerateTakeoverPdfDto pdfDto) throws FileNotFoundException, JRException {

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		File file = ResourceUtils.getFile("classpath:take_over_report.jrxml");

		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(pdfDto.getItemDets());
		Map<String, Object> hashMap = new HashMap<>();

		hashMap.put("ecNo", pdfDto.getOccempcode());
		hashMap.put("flatNo", pdfDto.getHouseNo());
		hashMap.put("year", String.valueOf(Year.now().getValue()));
		hashMap.put("elecMetNo", pdfDto.getElecMetNo());
		hashMap.put("onDate", pdfDto.getFormattedOnDate());
		hashMap.put("reading", pdfDto.getReading());
		hashMap.put("employeeName",pdfDto.getOccempName());
		hashMap.put("hemployeeName", pdfDto.getEmpName());
		hashMap.put("hecNo", pdfDto.getEmpName());
		hashMap.put("hdesignation", pdfDto.getDesignation());
		hashMap.put("temployeeName", "");
		hashMap.put("tecNo", "");
		hashMap.put("tdesignation", "");
		hashMap.put("CollectionBeanParam", datasource);

		JasperPrint fillReport = JasperFillManager.fillReport(jasperReport, hashMap, new JREmptyDataSource());

		JasperExportManager.exportReportToPdfStream(fillReport, outputStream);
		byte[] output = outputStream.toByteArray();
		String pdfbase64 = Base64.getEncoder().encodeToString(output);

		return pdfbase64;
	}

}
