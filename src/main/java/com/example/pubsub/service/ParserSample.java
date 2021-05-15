package com.example.pubsub.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.pubsub.integration.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Service
public class ParserSample {

	public Order getParser() {
		
		File file = new File("/home/shiv/Music/xmls/simple_bean.xml");
	    XmlMapper xmlMapper = new XmlMapper();
	    String xml = null;
		try {
			xml = inputStreamToString(new FileInputStream(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Order value = null;
		try {
			value = xmlMapper.readValue(xml, Order.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return value;
	}
	
	private String inputStreamToString(InputStream is) throws IOException {
	    
		StringBuilder sb = new StringBuilder();
	    String line;
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    while ((line = br.readLine()) != null) {
	        sb.append(line);
	    }
	    br.close();
	    return sb.toString();
	}
	
	public List<Order> getExcel()  {
		
		try {
			String fileLocation = "/home/shiv/Music/xmls/new_simple_bean.xlsx";
			List<Order> list = new ArrayList<>();
	        
			
			FileInputStream file = new FileInputStream(new File(fileLocation));
			Workbook workbook = new XSSFWorkbook(file);
			
			//Workbook workbook = WorkbookFactory.create(new File(fileLocation));
	        
			Sheet sheet = workbook.getSheetAt(0);
			
			for(Row row: sheet) {
				if(row.getRowNum() != 0) {
					Order sb = new Order();
					for(Cell cell : row) {
						
						if(cell.getColumnIndex() == 0) {
							
							switch (cell.getCellTypeEnum()) {
							
				            	case NUMERIC: sb.setAmount(cell.getNumericCellValue()); 
							}
							
						}else {
							
							switch (cell.getCellTypeEnum()) {
							
			            		case STRING: sb.setAddress(cell.getStringCellValue()); 
							}
						}
					}
					list.add(sb);
				}
				}
				
	        // Closing the workbook
	        workbook.close();
        
        	return list;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String jaxbObjectToXML(Order order) 
    {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(order, sw);
             
            //Verify XML Content
            String xmlContent = sw.toString();
            System.out.println( xmlContent );
            return xmlContent;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
