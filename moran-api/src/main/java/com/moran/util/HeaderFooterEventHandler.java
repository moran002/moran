package com.moran.util;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEvent;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEventHandler;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.SneakyThrows;

/**
 * @author : moran
 */
public class HeaderFooterEventHandler extends AbstractPdfDocumentEventHandler {

    @SneakyThrows
    @Override
    protected void onAcceptedEvent(AbstractPdfDocumentEvent event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = page.getPageSize();

        // 添加页眉
        PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        Canvas canvas = new Canvas(pdfCanvas, pageSize);
        // 添加页眉
        canvas.showTextAligned(new Paragraph("this is header -----------------------"), pageSize.getWidth() / 2, pageSize.getTop() - 30, TextAlignment.CENTER);
        canvas.close();

        // 添加页脚
        pdfCanvas = new PdfCanvas(page.newContentStreamAfter(), page.getResources(), pdfDoc);
        canvas = new Canvas(pdfCanvas, pageSize);
        canvas.showTextAligned(new Paragraph("this is foot -----------------------"), pageSize.getWidth() / 2, 30, TextAlignment.CENTER);
        canvas.close();
    }
}
