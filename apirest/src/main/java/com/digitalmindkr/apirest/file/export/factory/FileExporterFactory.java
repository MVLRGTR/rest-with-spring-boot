package com.digitalmindkr.apirest.file.export.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.digitalmindkr.apirest.exception.BadRequestException;
import com.digitalmindkr.apirest.file.export.MediaTypes;
import com.digitalmindkr.apirest.file.export.contract.FileExporter;
import com.digitalmindkr.apirest.file.export.impl.CsvExporter;
import com.digitalmindkr.apirest.file.export.impl.XlsxExporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class FileExporterFactory {

	private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

	@Autowired
	private ApplicationContext context;

	public FileExporter getExporter(String acceptHeader) throws Exception {
		if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
			return context.getBean(XlsxExporter.class);
		} else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
			return context.getBean(CsvExporter.class);
		} else {
			throw new BadRequestException("Invalid File Format!");
		}
	}

}
