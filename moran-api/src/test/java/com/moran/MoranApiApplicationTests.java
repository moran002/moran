package com.moran;

import com.moran.model.Header;
import com.moran.model.Invoice;
import com.moran.util.TemplateService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

@SpringBootTest
class MoranApiApplicationTests {
    private final static String templateName = "header.html";
    private final static String out = "C:\\Users\\12805\\Desktop\\1.html";
    @Resource
    private TemplateService templateService;
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

}
