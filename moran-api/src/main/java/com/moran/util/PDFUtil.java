package com.moran.util;

import cn.hutool.core.io.FileUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import lombok.SneakyThrows;

import java.util.List;

/**
 * @author : moran
 */
public class PDFUtil {

    /**
     * html转pdf
     * @author : moran
     */
    @SneakyThrows
    private static void pdf2html(String htmlSrc, String outSrc, Integer height) {
        String html = FileUtil.readUtf8String(htmlSrc);
        PdfWriter writer = new PdfWriter(outSrc);
        PdfDocument pdfDoc = new PdfDocument(writer);
        PageSize pageSize = new PageSize(595, height);
        Document document = new Document(pdfDoc, pageSize);
        // 将 HTML 转换为 PDF
        HtmlConverter.convertToPdf(html, pdfDoc, new ConverterProperties());
        document.close();
    }

    /**
     * html转pdf并添加页脚和页眉
     * @author : moran
     */
    @SneakyThrows
    private static void pdfAddHeaders(String htmlSrc, String outSrc, Integer height) {
        String html = FileUtil.readUtf8String(htmlSrc);

        PdfWriter writer = new PdfWriter(outSrc);
        PdfDocument pdfDoc = new PdfDocument(writer);
        PageSize pageSize = new PageSize(595, height);
        Document document = new Document(pdfDoc, pageSize);
        document.setTopMargin(-200);
        // 添加页眉和页脚的事件处理器
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderFooterEventHandler());

        // 将 HTML 转换为 PDF
        HtmlConverter.convertToPdf(html, pdfDoc, new ConverterProperties());

        document.close();
    }


    /**
     * 多个pdf合成
     * @author : moran
     */
    @SneakyThrows
    public static void mergePdf(String multiSrc, String headerSrc, String footerSrc, String outScr) {
        // 读取多页面的 PDF
        PdfReader multiPageReader = new PdfReader(multiSrc);
        PdfDocument multiPagePdf = new PdfDocument(multiPageReader);
        // 创建一个新的 PDF
        PdfWriter writer = new PdfWriter(outScr);
        PdfDocument newPdf = new PdfDocument(writer);
        // 遍历多页面 PDF 的每一页
        for (int i = 1; i <= multiPagePdf.getNumberOfPages(); i++) {
            // 创建一个新页面（大小与多页面 PDF 的页面相同）
            PdfPage sourcePage = multiPagePdf.getPage(i);
            PdfPage newPage = newPdf.addNewPage(PageSize.A4);
            // 在新页面上绘制页眉
            PdfCanvas canvas = new PdfCanvas(newPage);
            // 添加页眉
            PdfReader headerReader = new PdfReader(headerSrc);
            PdfDocument headerPdf = new PdfDocument(headerReader);
            PdfFormXObject headerXObject = headerPdf.getFirstPage().copyAsFormXObject(newPdf);
            canvas.addXObjectAt(headerXObject, 0, 0);
            headerPdf.close();
            // 在新页面上绘制多页面 PDF 的内容
            PdfFormXObject sourceXObject = sourcePage.copyAsFormXObject(newPdf);
            canvas.addXObjectAt(sourceXObject, 0, 200);
            // 添加页眉
            PdfReader footReader = new PdfReader(footerSrc);
            PdfDocument footPdf = new PdfDocument(footReader);
            PdfFormXObject footXObject = footPdf.getFirstPage().copyAsFormXObject(newPdf);
            canvas.addXObjectAt(footXObject, 10, -642);
            footPdf.close();
        }

        // 关闭文档
        multiPagePdf.close();
        newPdf.close();
        System.out.println("PDF 处理完成，输出文件: " + outScr);
    }

    @SneakyThrows
    public static String merge(List<String> headers, String content, List<String> foots) {
        // 读取多页面的 PDF
        PdfReader multiPageReader = new PdfReader(content);
        PdfDocument multiPagePdf = new PdfDocument(multiPageReader);
        // 创建一个新的 PDF
        PdfWriter writer = new PdfWriter("C:\\Users\\12805\\Desktop\\12.pdf");
        PdfDocument newPdf = new PdfDocument(writer);
//        newPdf.setDefaultPageSize(PageSize.A4);

        // 遍历多页面 PDF 的每一页
        for (int i = 1; i <= multiPagePdf.getNumberOfPages(); i++) {
            PdfPage sourcePage = multiPagePdf.getPage(i);
//            Rectangle sourcePageSize = sourcePage.getPageSize();
//            PdfPage newPage = newPdf.addNewPage(new PageSize(sourcePageSize));
            PdfPage newPage = newPdf.addNewPage(PageSize.A4);
            // 创建画板
            PdfCanvas canvas = new PdfCanvas(newPage);
            if (i%2 == 0) {
                // 添加页眉
                PdfReader headerReader = new PdfReader(headers.get(i - 1));
                PdfDocument headerPdf = new PdfDocument(headerReader);
                // 将页眉的 PDF 转换为 XObject（可重复使用的对象）
                PdfFormXObject headerXObject = headerPdf.getFirstPage().copyAsFormXObject(newPdf);
                canvas.addXObjectAt(headerXObject, 0, 0);
                headerPdf.close();
            }

            // 在新页面上绘制多页面 PDF 的内容
            PdfFormXObject sourceXObject = sourcePage.copyAsFormXObject(newPdf);
            canvas.addXObjectAt(sourceXObject, 0, 200);

            if (i == multiPagePdf.getNumberOfPages()) {
                // 添加页脚
                PdfReader footReader = new PdfReader(foots.get(i - 1));
                PdfDocument footPdf = new PdfDocument(footReader);
                // 将页眉的 PDF 转换为 XObject（可重复使用的对象）
                PdfFormXObject footXObject = footPdf.getFirstPage().copyAsFormXObject(newPdf);
                canvas.addXObjectAt(footXObject, 10, -642);
                footPdf.close();
            }
        }
        // 关闭文档
        multiPagePdf.close();
        newPdf.close();
        return "file";
    }

    public static void main(String[] args) {
        List<String> headers = List.of("https://file.teamauto.sg/tap/pdf/1.pdf","https://file.teamauto.sg/tap/pdf/1.pdf","https://file.teamauto.sg/tap/pdf/1.pdf");
        List<String> foots = List.of("https://file.teamauto.sg/tap/pdf/2.pdf","https://file.teamauto.sg/tap/pdf/2.pdf","https://file.teamauto.sg/tap/pdf/2.pdf");
        String content = "https://file.teamauto.sg/tap/pdf/0.pdf";
//        String multiSrc = "https://file.teamauto.sg/tap/pdf/0.pdf";
//        String headerSrc = "https://file.teamauto.sg/tap/pdf/1.pdf";
//        String footerSrc = "https://file.teamauto.sg/tap/pdf/2.pdf";
//        String outScr = "C:\\Users\\12805\\Desktop\\11.pdf";
        merge(headers, content, foots);
    }
}
