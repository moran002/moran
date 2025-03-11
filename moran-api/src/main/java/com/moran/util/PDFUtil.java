package com.moran.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import lombok.SneakyThrows;

import java.io.File;
import java.util.List;
import java.util.Set;

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

        // 遍历多页面 PDF 的每一页
        for (int i = 1; i <= multiPagePdf.getNumberOfPages(); i++) {
            PdfPage sourcePage = multiPagePdf.getPage(i);
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

    @SneakyThrows
    public static File createTempFileWithSuffix(String suffix) {
        // 创建文件，通过 UUID 保证唯一
        File file = File.createTempFile(IdUtil.simpleUUID(), suffix);
        // 标记 JVM 退出时，自动删除
        file.deleteOnExit();
        return file;
    }

    @SneakyThrows
    public static String mergePdf(String index, String headerSrc, String footerSrc, String fixed, String outScr) {
        // 读取多页面的 PDF
        PdfReader multiPageReader = new PdfReader(index);
        PdfDocument multiPagePdf = new PdfDocument(multiPageReader);
        // 创建一个新的 PDF
        File dest = createTempFileWithSuffix(".pdf");
        PdfWriter writer = new PdfWriter(dest);
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
        return dest.getAbsolutePath();
    }

    // 自定义事件监听器
    static class MaxYListener implements IEventListener {
        private float maxY = 0;

        @Override
        public void eventOccurred(IEventData data, EventType type) {
            if (data instanceof TextRenderInfo) {
                // 处理文本内容
                TextRenderInfo textData = (TextRenderInfo) data;
                float textY = textData.getBaseline().getStartPoint().get(Vector.I2);
                if (textY > maxY) {
                    maxY = textY;
                }
            } else if (data instanceof ImageRenderInfo) {
                // 处理图像内容
                ImageRenderInfo imageData = (ImageRenderInfo) data;
                Matrix ctm = imageData.getImageCtm(); // 获取图像的变换矩阵
                float imageY = ctm.get(Matrix.I32); // 提取图像的 Y 坐标
                if (imageY > maxY) {
                    maxY = imageY;
                }
            }
        }

        @Override
        public Set<EventType> getSupportedEvents() {
            return null; // 返回 null 表示支持所有事件
        }

        // 获取记录的最高 Y 坐标
        public float getMaxY() {
            return maxY;
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        pdf2html("C:\\Users\\12805\\Desktop\\fixed.html", "C:\\Users\\12805\\Desktop\\fixed.pdf", 500);
        // 打开 PDF 文档
//        PdfDocument pdfDoc = new PdfDocument(new PdfReader("C:\\Users\\12805\\Desktop\\1.pdf"));
//        PdfPage page = pdfDoc.getPage(3); // 获取第一页
//        Rectangle pageSize = page.getPageSize();
//        float pageHeight = pageSize.getHeight();
//
//        // 自定义事件监听器
//        MaxYListener listener = new MaxYListener();
//        PdfCanvasProcessor parser = new PdfCanvasProcessor(listener);
//
//        // 解析页面内容
//        parser.processPageContent(page);
//
//        // 获取有效内容的高度
//        float maxY = listener.getMaxY();
//        float effectiveContentHeight = pageHeight - maxY;
//        System.out.println("Effective Content Height: " + effectiveContentHeight);
//        System.out.println("maxY: " + maxY);
//        System.out.println("pageHeight: " + pageHeight);
//
//        // 关闭文档
//        pdfDoc.close();
    }
}
