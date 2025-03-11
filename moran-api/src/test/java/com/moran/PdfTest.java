package com.moran;

import cn.hutool.json.JSONUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.moran.model.Header;
import com.moran.model.Invoice;
import com.moran.model.InvoiceRespVO;
import com.moran.util.Pdf2Util;
import com.moran.util.PdfHeightListener;
import com.moran.util.TemplateService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

@SpringBootTest
class PdfTest {
    private final static String templateName = "header.html";
    private final static String out = "C:\\Users\\12805\\Desktop\\1.html";
    @Resource
    private TemplateService templateService;
    @Resource
    private Pdf2Util pdf2Util;
    @Test
    void contextLoads() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "hello");
        map.put("age", 18);
        templateService.renderTemplateToFile(templateName, map, out);
    }

    @Test
    void writeHeader() {
        Header header = new Header();
        header.setImg("https://file.teamauto.sg/0257412a29161b92bde2e215e2e2f779b99014bb32e120837384b4656b58f6fd.png");
        header.setInvoiceNo("no-12345678");
        header.setInvoiceDate("2025-3-4 13:47:35");
        header.setTerm("14");
        templateService.renderTemplateToFile(templateName, header, out);
    }

    @Test
    void writeIndex() {
        Invoice invoice = new Invoice();
        invoice.setOrderSn("no-12345678");
        invoice.setIssueTime("2025-3-4 15:42:37");
        invoice.setDueTime("2025-3-4 15:42:56");
        invoice.setTerms(24);
        invoice.setCompanyName("company-name");
        invoice.setCompanyAddress("company-address");
        invoice.setCompanyPhone("1871234567");
        invoice.setMemberName("member-name");
        invoice.setMemberPhone("1871234567");
        invoice.setMemberEmail("123@123.com");
        invoice.setRaisedBy("raised");
        invoice.setRefferralBy("referred");
        invoice.setSubTotal(BigDecimal.TEN);
        invoice.setTotalAmount(BigDecimal.TEN);
        invoice.setDiscountPrice(BigDecimal.ONE);
        invoice.setTaxPrice(BigDecimal.ONE);
        invoice.setItems(new ArrayList<>());
        for (int i = 0; i < 20; i++) {
            Invoice.InvoiceItem item = new Invoice.InvoiceItem();
            item.setCategory("category");
            item.setDescription("description");
            item.setQuantity(BigDecimal.ONE);
            item.setUnitAmount(BigDecimal.TEN);
            item.setAmount(BigDecimal.TEN);
            invoice.getItems().add(item);
        }
        String templateName = "https://file.teamauto.sg/24hrs/pdf/index.html";
        String out = "C:\\Users\\12805\\Desktop\\0.html";
        templateService.renderTemplateToFile(templateName, invoice, out);
    }

    @Test
    void html2pdf() {
        pdf2Util.html2pdf("C:\\Users\\12805\\Desktop\\index.html", "C:\\Users\\12805\\Desktop\\11.pdf", 642);
    }

    @Test
    @SneakyThrows
    void y() {
        PdfReader fixedReader = new PdfReader("C:\\Users\\12805\\Desktop\\10.pdf");
        PdfDocument fixedPdfDocument = new PdfDocument(fixedReader);
        PdfHeightListener listener = new PdfHeightListener();
        PdfCanvasProcessor parser = new PdfCanvasProcessor(listener);
        parser.processPageContent(fixedPdfDocument.getPage(1));
        System.out.println(listener.getMaxY());
        System.out.println(listener.getMinY());
        System.out.println(listener.getHeight()); // 221.57
    }

    @Test
    void writeInvoice() {
//        String j = "{\"id\":32,\"orderType\":\"tow\",\"orderSn\":\"T202501161216571\",\"invoiceSn\":\"2502-0027\",\"status\":3,\"statusName\":\"Pending\",\"issueTime\":1739771509000,\"dueTime\":1742363509000,\"terms\":30,\"companyName\":\"tow\",\"memberName\":\"发送\",\"memberPhone\":\"17899996662\",\"memberId\":\"13368\",\"raisedBy\":\"TowAdmin\",\"subTotal\":9.95,\"totalAmount\":18.95,\"paidAmount\":0,\"notPaidAmount\":18.95,\"discountPrice\":0,\"taxPrice\":9,\"discount\":0,\"discountAmount\":0,\"discountRate\":0,\"tax\":1,\"taxRate\":0,\"taxAmount\":9,\"createTime\":1739771509000,\"items\":[{\"categoryId\":31,\"category\":\"Towing Fee\",\"quantity\":1,\"unitAmount\":0.9,\"amount\":0.9},{\"categoryId\":30,\"category\":\"Towing Inventory\",\"quantity\":1,\"unitAmount\":9.05,\"amount\":9.05}]}";
        String j = "{\n" +
                "    \"id\": 32,\n" +
                "    \"orderType\": \"tow\",\n" +
                "    \"orderSn\": \"T202501161216571\",\n" +
                "    \"invoiceSn\": \"2502-0027\",\n" +
                "    \"status\": 3,\n" +
                "    \"statusName\": \"Pending\",\n" +
                "    \"issueTime\": 1739771509000,\n" +
                "    \"dueTime\": 1742363509000,\n" +
                "    \"terms\": 30,\n" +
                "    \"companyName\": \"tow\",\n" +
                "    \"memberName\": \"发送\",\n" +
                "    \"memberPhone\": \"17899996662\",\n" +
                "    \"memberId\": \"13368\",\n" +
                "    \"raisedBy\": \"TowAdmin\",\n" +
                "    \"subTotal\": 9.95,\n" +
                "    \"totalAmount\": 18.95,\n" +
                "    \"paidAmount\": 0,\n" +
                "    \"notPaidAmount\": 18.95,\n" +
                "    \"discountPrice\": 0,\n" +
                "    \"taxPrice\": 9,\n" +
                "    \"discount\": 0,\n" +
                "    \"discountAmount\": 0,\n" +
                "    \"discountRate\": 0,\n" +
                "    \"tax\": 1,\n" +
                "    \"taxRate\": 0,\n" +
                "    \"taxAmount\": 9,\n" +
                "    \"createTime\": 1739771509000,\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"categoryId\": 31,\n" +
                "            \"category\": \"Towing Fee\",\n" +
                "            \"quantity\": 1,\n" +
                "            \"unitAmount\": 0.9,\n" +
                "            \"amount\": 0.9\n" +
                "        },\n" +
                "        {\n" +
                "            \"categoryId\": 30,\n" +
                "            \"category\": \"Towing Inventory\",\n" +
                "            \"quantity\": 1,\n" +
                "            \"unitAmount\": 9.05,\n" +
                "            \"amount\": 9.05\n" +
                "        },\n" +
                "        {\n" +
                "            \"categoryId\": 30,\n" +
                "            \"category\": \"Towing Inventory\",\n" +
                "            \"quantity\": 1,\n" +
                "            \"unitAmount\": 9.05,\n" +
                "            \"amount\": 9.05\n" +
                "        },\n" +
                "        {\n" +
                "            \"categoryId\": 30,\n" +
                "            \"category\": \"Towing Inventory\",\n" +
                "            \"quantity\": 1,\n" +
                "            \"unitAmount\": 9.05,\n" +
                "            \"amount\": 9.05\n" +
                "        },\n" +
                "        {\n" +
                "            \"categoryId\": 30,\n" +
                "            \"category\": \"Towing Inventory\",\n" +
                "            \"quantity\": 1,\n" +
                "            \"unitAmount\": 9.05,\n" +
                "            \"amount\": 9.05\n" +
                "        },\n" +
                "        {\n" +
                "            \"categoryId\": 30,\n" +
                "            \"category\": \"Towing Inventory\",\n" +
                "            \"quantity\": 1,\n" +
                "            \"unitAmount\": 9.05,\n" +
                "            \"amount\": 9.05\n" +
                "        },\n" +
                "        {\n" +
                "            \"categoryId\": 30,\n" +
                "            \"category\": \"Towing Inventory\",\n" +
                "            \"quantity\": 1,\n" +
                "            \"unitAmount\": 9.05,\n" +
                "            \"amount\": 9.05\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        InvoiceRespVO data = JSONUtil.toBean(j, InvoiceRespVO.class);
        String pdf2 = pdf2Util.createPdf2(data);
        System.out.println(pdf2);
    }
}
