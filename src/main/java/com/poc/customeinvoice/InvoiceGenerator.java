package com.poc.customeinvoice;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

public class InvoiceGenerator {
	Document document;
	PdfDocument pdfDocument;
	String pdfName;
	float threecol = 190f;
	float twocol = 285f;
	float twocol150 = twocol + 150f;
	float twocolumnWidth[] = { twocol150, twocol };
	float threeColumnWidth[] = { threecol, threecol, threecol };
	float fullwidth[] = { threecol * 3 };
	ByteArrayOutputStream byteArrayOutputStream;

	public InvoiceGenerator(String pdfName) {
		this.pdfName = pdfName;
		this.byteArrayOutputStream = new ByteArrayOutputStream();
	}

	public void createDocument() throws FileNotFoundException {
		PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
		pdfDocument = new PdfDocument(pdfWriter);
		pdfDocument.setDefaultPageSize(PageSize.A4);
		this.document = new Document(pdfDocument);
	}

	public void createTermsAndConditions(List<String> TermsAndConditionsList, Boolean lastPage, String imagePath) {
		if (lastPage) {
			float threecol = 190f;
			float fullwidth[] = { threecol * 3 };
			Table tb = new Table(fullwidth);
			tb.addCell(new Cell().add(new Paragraph("TERMS AND CONDITIONS\n")).setBold().setBorder(Border.NO_BORDER));
			
		    TermsAndConditionsList.forEach(tnc -> tb.addCell(new Cell().add(new Paragraph(tnc)).setBorder(Border.NO_BORDER)));
		    
			document.add(tb);
		} else {
			pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new MyFooter(document, TermsAndConditionsList, imagePath));
		}

		document.close();
	}

	public void createProduct(List<Product> productList) {
		float threecol = 190f;
		float fullwidth[] = { threecol * 3 };
		Table threeColTable2 = new Table(threeColumnWidth);
		float totalSum = getTotalSum(productList);
		for (Product product : productList) {
			float total = product.getQuantity() * product.getPriceperpeice();
			threeColTable2
					.addCell(new Cell().add(new Paragraph(product.getPname().orElse(""))).setBorder(Border.NO_BORDER))
					.setMarginLeft(10f);
			threeColTable2.addCell(new Cell().add(new Paragraph(String.valueOf(product.getQuantity())))
					.setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
			threeColTable2.addCell(new Cell().add(new Paragraph(String.valueOf(total)))
					.setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
		}

		document.add(threeColTable2.setMarginBottom(20f));
		float onetwo[] = { threecol + 125f, threecol * 2 };
		Table threeColTable4 = new Table(onetwo);
		threeColTable4.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
		threeColTable4.addCell(new Cell().add(fullwidthDashedBorder(fullwidth)).setBorder(Border.NO_BORDER));
		document.add(threeColTable4);

		Table threeColTable3 = new Table(threeColumnWidth);
		threeColTable3.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
		threeColTable3.addCell(new Cell().add(new Paragraph("Total")).setTextAlignment(TextAlignment.CENTER)
				.setBorder(Border.NO_BORDER));
		threeColTable3.addCell(new Cell().add(new Paragraph(String.valueOf(totalSum)))
				.setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);

		document.add(threeColTable3);
		document.add(fullwidthDashedBorder(fullwidth));
		document.add(new Paragraph("\n"));
		document.add(
				getDividerTable(fullwidth).setBorder(new SolidBorder(new DeviceGray(0.5f), 1)).setMarginBottom(15f));
	}

	public float getTotalSum(List<Product> productList) {
		return (float) productList.stream().mapToLong((p) -> (long) (p.getQuantity() * p.getPriceperpeice())).sum();
	}

	public List<Product> getDummyProductList() {
		List<Product> productList = new ArrayList<>();
		productList.add(new Product("apple", 2, 159));
		productList.add(new Product("mango", 4, 205));
		productList.add(new Product("banana", 2, 90));
		productList.add(new Product("grapes", 3, 10));
		productList.add(new Product("apple", 5, 159));
		productList.add(new Product("kiwi", 2, 90));
		return productList;
	}

	public void createTableHeader(ProductTableHeader productTableHeader) {
		Paragraph producPara = new Paragraph("Products");
		document.add(producPara.setBold());
		Table threeColTable1 = new Table(threeColumnWidth);
		threeColTable1.setBackgroundColor(new DeviceGray(0.0f), 0.7f);

		threeColTable1.addCell(new Cell().add(new Paragraph("Description")).setBold().setFontColor(new DeviceGray(1.0f))
				.setBorder(Border.NO_BORDER));
		threeColTable1.addCell(new Cell().add(new Paragraph("Quantity")).setBold().setFontColor(new DeviceGray(1.0f))
				.setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
		threeColTable1.addCell(new Cell().add(new Paragraph("Price")).setBold().setFontColor(new DeviceGray(1.0f))
				.setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
		document.add(threeColTable1);
	}

	public void createAddress(AddressDetails addressDetails) {
		Table twoColTable = new Table(twocolumnWidth);
		twoColTable.addCell(getBillingandShippingCell(addressDetails.getBillingInfoText()));
		twoColTable.addCell(getBillingandShippingCell(addressDetails.getShippingInfoText()));
		document.add(twoColTable.setMarginBottom(12f));
		// iNFO FIRST ROW
		Table twoColTable2 = new Table(twocolumnWidth);
		twoColTable2.addCell(getCell10fLeft(addressDetails.getBillingCompanyText(), true));
		twoColTable2.addCell(getCell10fLeft(addressDetails.getShippingNameText(), true));
		twoColTable2.addCell(getCell10fLeft(addressDetails.getBillingCompany(), false));
		twoColTable2.addCell(getCell10fLeft(addressDetails.getShippingName(), false));
		document.add(twoColTable2);

		Table twoColTable3 = new Table(twocolumnWidth);
		twoColTable3.addCell(getCell10fLeft(addressDetails.getBillingNameText(), true));
		twoColTable3.addCell(getCell10fLeft(addressDetails.getShippingAddressText(), true));
		twoColTable3.addCell(getCell10fLeft(addressDetails.getBillingName(), false));
		twoColTable3.addCell(getCell10fLeft(addressDetails.getShippingAddress(), false));
		document.add(twoColTable3);
		float oneColoumnwidth[] = { twocol150 };

		Table oneColTable1 = new Table(oneColoumnwidth);
		oneColTable1.addCell(getCell10fLeft(addressDetails.getBillingAddressText(), true));
		oneColTable1.addCell(getCell10fLeft(addressDetails.getBillingAddress(), false));
		oneColTable1.addCell(getCell10fLeft(addressDetails.getBillingEmailText(), true));
		oneColTable1.addCell(getCell10fLeft(addressDetails.getBillingEmail(), false));
		document.add(oneColTable1.setMarginBottom(10f));
		document.add(fullwidthDashedBorder(fullwidth));
	}

	public void createHeader(HeaderDetails header) {
		Table table = new Table(twocolumnWidth);
		table.addCell(new Cell().add(new Paragraph(header.getInvoiceTitle())).setFontSize(20f)
				.setBorder(Border.NO_BORDER).setBold());
		Table nestedtabe = new Table(new float[] { twocol / 2, twocol / 2 });
		nestedtabe.addCell(getHeaderTextCell(header.getInvoiceNoText()));
		nestedtabe.addCell(getHeaderTextCellValue(header.getInvoiceNo()));
		nestedtabe.addCell(getHeaderTextCell(header.getInvoiceDateText()));
		nestedtabe.addCell(getHeaderTextCellValue(header.getInvoiceDate()));
		table.addCell(new Cell().add(nestedtabe).setBorder(Border.NO_BORDER));
		Border gb = new SolidBorder(header.getBorderColor(), 2f);
		document.add(table);
		document.add(getNewLineParagraph());
		document.add(getDividerTable(fullwidth).setBorder(gb));
		document.add(getNewLineParagraph());
	}

	public List<Product> modifyProductList(List<Product> productList) {
		Map<String, Product> map = new HashMap<>();
		productList.forEach((i) -> {
			if (map.containsKey(i.getPname().orElse(""))) {
				i.setQuantity(map.getOrDefault(i.getPname().orElse(""), null).getQuantity() + i.getQuantity());
				map.put(i.getPname().orElse(""), i);
			} else {
				map.put(i.getPname().orElse(""), i);
			}
		});
		return map.values().stream().collect(Collectors.toList());

	}

	static Table getDividerTable(float[] fullwidth) {
		return new Table(fullwidth);
	}

	static Table fullwidthDashedBorder(float[] fullwidth) {
		Table tableDivider2 = new Table(fullwidth);
		Border dgb = new DashedBorder(new DeviceGray(0.5f), 0.5f);
		tableDivider2.setBorder(dgb);
		return tableDivider2;
	}

	static Paragraph getNewLineParagraph() {
		return new Paragraph("\n");
	}

	static Cell getHeaderTextCell(String textValue) {

		return new Cell().add(new Paragraph(textValue)).setBold().setBorder(Border.NO_BORDER)
				.setTextAlignment(TextAlignment.RIGHT);
	}

	static Cell getHeaderTextCellValue(String textValue) {

		return new Cell().add(new Paragraph(textValue)).setBorder(Border.NO_BORDER)
				.setTextAlignment(TextAlignment.LEFT);
	}

	static Cell getBillingandShippingCell(String textValue) {

		return new Cell().add(new Paragraph(textValue)).setFontSize(12f).setBold().setBorder(Border.NO_BORDER)
				.setTextAlignment(TextAlignment.LEFT);
	}

	static Cell getCell10fLeft(String textValue, Boolean isBold) {
		Cell myCell = new Cell().add(new Paragraph(textValue)).setFontSize(10f).setBorder(Border.NO_BORDER)
				.setTextAlignment(TextAlignment.LEFT);
		return isBold ? myCell.setBold() : myCell;

	}

	public byte[] getPdfBytes() {
		document.close();
		return byteArrayOutputStream.toByteArray();
	}
}