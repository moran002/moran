package com.moran.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author : moran
 */
@Service
public class Pdf2Util {
    @Resource
    private TemplateService templateService;

    @SneakyThrows
    public static File createTempFileWithSuffix(String suffix) {
        // 创建文件，通过 UUID 保证唯一
        File file = File.createTempFile(IdUtil.simpleUUID(), suffix);
        // 标记 JVM 退出时，自动删除
        file.deleteOnExit();
        return file;
    }

    /**
     * HTML 转 PDF 用于后续合成完整pdf,
     * 1.页眉 页脚 和内容, 三个pdf 高度相加小于等于 纸张高度 ,当前默认为A4纸宽度
     *
     * @param path   html路径
     * @param out    输出路径
     * @param height 每页的高度, A4纸 宽高: 595, 842
     */
    @SneakyThrows
    public void html2pdf(String path, String out, Integer height) {
        String html = FileUtil.readUtf8String(path);

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        PageSize pageSize = new PageSize(595, height);
        Document document = new Document(pdfDoc, pageSize);
        // 将 HTML 转换为 PDF
        HtmlConverter.convertToPdf(html, pdfDoc, new ConverterProperties());

        document.close();
    }

    @SneakyThrows
    private File createPdfFile(String htmlUrl, Integer height, Object data) {
        File headerHtml = createTempFileWithSuffix(".html");
        templateService.renderTemplateToFile(htmlUrl, data, headerHtml.getAbsolutePath());
        File headerPdf = createTempFileWithSuffix(".pdf");
        html2pdf(headerHtml.getAbsolutePath(), headerPdf.getAbsolutePath(), height);
        return headerPdf;
    }

    @SneakyThrows
    public String createPdf2(Object data) {
        // 创建一个最终合成的pdf
        File dest = new File("C:\\Users\\12805\\Desktop\\3.pdf");
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument newPdf = new PdfDocument(writer);
        File pdfHtml = createPdfFile("https://file.teamauto.sg/tap/invoice/index.html", 642, data);
        // 读取pdf
        PdfReader multiPageReader = new PdfReader(pdfHtml.getAbsolutePath());
        PdfDocument multiPagePdf = new PdfDocument(multiPageReader);
        //遍历内容pdf
        float height = 1000;
        for (int i = 1; i <= multiPagePdf.getNumberOfPages(); i++) {
            // 创建一个新页面（大小与多页面 PDF 的页面相同）
            PdfPage sourcePage = multiPagePdf.getPage(i);
            PdfPage newPage = newPdf.addNewPage(PageSize.A4);
            // 创建画板
            PdfCanvas canvas = new PdfCanvas(newPage);
            // 开始绘制固定头部
            File headerPdf = createPdfFile("https://file.teamauto.sg/tap/invoice/header.html",842, data);
            PdfReader headerReader = new PdfReader(headerPdf.getAbsolutePath());
            PdfDocument headerDocument = new PdfDocument(headerReader);
            PdfFormXObject headerXObject = headerDocument.getFirstPage().copyAsFormXObject(newPdf);
            canvas.addXObjectAt(headerXObject, 0, 0);
            headerDocument.close();
            // 绘制正文内容
            PdfFormXObject xObject = sourcePage.copyAsFormXObject(newPdf);
            canvas.addXObjectAt(xObject, 0, 100);
            if (i == multiPagePdf.getNumberOfPages()) {
                PdfHeightListener listener = new PdfHeightListener();
                PdfCanvasProcessor parser = new PdfCanvasProcessor(listener);
                parser.processPageContent(sourcePage);
                height = listener.getHeight();
            }
            //开始绘制底部
            File footPdf = createPdfFile("https://file.teamauto.sg/tap/invoice/foot.html", 200, null);
            PdfReader footReader = new PdfReader(footPdf.getAbsolutePath());
            PdfDocument footPdfDocument = new PdfDocument(footReader);
            PdfFormXObject footXObject = footPdfDocument.getFirstPage().copyAsFormXObject(newPdf);
            canvas.addXObjectAt(footXObject, 10, -50);
            footPdfDocument.close();
        }
        // 获取最后一页计算距离选择写入或创建新的一页写入
        File fixedPdf = createPdfFile("https://file.teamauto.sg/tap/invoice/fixed.html", 500, data);
        PdfReader fixedReader = new PdfReader(fixedPdf.getAbsolutePath());
        PdfDocument fixedPdfDocument = new PdfDocument(fixedReader);
        PdfFormXObject fixedXObject = fixedPdfDocument.getFirstPage().copyAsFormXObject(newPdf);
        PdfHeightListener listener = new PdfHeightListener();
        PdfCanvasProcessor parser = new PdfCanvasProcessor(listener);
        parser.processPageContent(fixedPdfDocument.getPage(1));
        float fixedHeight = listener.getHeight();
        System.out.println(height + "==" + fixedHeight);
        if (height + fixedHeight  + 330 > 840) {
            PdfPage newPage = newPdf.addNewPage(PageSize.A4);
            // 创建画板
            PdfCanvas canvas = new PdfCanvas(newPage);
            // 开始绘制固定头部
            File headerPdf = createPdfFile("https://file.teamauto.sg/tap/invoice/header.html",842, data);
            PdfReader headerReader = new PdfReader(headerPdf.getAbsolutePath());
            PdfDocument headerDocument = new PdfDocument(headerReader);
            PdfFormXObject headerXObject = headerDocument.getFirstPage().copyAsFormXObject(newPdf);
            canvas.addXObjectAt(headerXObject, 0, 0);
            headerDocument.close();
            //开始绘制底部
            File footPdf = createPdfFile("https://file.teamauto.sg/tap/invoice/foot.html", 200, null);
            PdfReader footReader = new PdfReader(footPdf.getAbsolutePath());
            PdfDocument footPdfDocument = new PdfDocument(footReader);
            PdfFormXObject footXObject = footPdfDocument.getFirstPage().copyAsFormXObject(newPdf);
            canvas.addXObjectAt(footXObject, 10, -50);
            footPdfDocument.close();
            // 绘制附加部分
            canvas.addXObjectAt(fixedXObject, 10, 20);
            fixedPdfDocument.close();
        }else {
            PdfPage page = newPdf.getPage(multiPagePdf.getNumberOfPages());
            PdfCanvas pdfCanvas = new PdfCanvas(page);
            pdfCanvas.addXObjectAt(fixedXObject, 10, 20);
            fixedPdfDocument.close();
        }
        multiPagePdf.close();
        newPdf.close();
        return dest.getAbsolutePath();
    }
}
