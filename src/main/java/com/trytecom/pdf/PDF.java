package com.trytecom.pdf;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Created by admin on 09-09-2016.
 */
public class PDF {
    /** Inner class to add a header and a footer. */
    static class HeaderFooter extends PdfPageEventHelper {
        /** Alternating phrase for the header. */
        Phrase[] header = new Phrase[2];
        /** Current page number (will be reset for every chapter). */
        int pagenumber;
        /** Image Logo file */
        String imageFileWithCompletePath;

        public HeaderFooter(String imageFileWithCompletePath){
            this.imageFileWithCompletePath = imageFileWithCompletePath;
        }

        /**
         * Initialize one of the headers.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document) {
            //header[0] = new Phrase("Movie history");
            Image logo;
            try {
                logo = Image.getInstance(imageFileWithCompletePath);
                logo.setAlignment(Image.MIDDLE);
                logo.scaleAbsoluteHeight(20);
                logo.scaleAbsoluteWidth(20);
                logo.scalePercent(100);
                Chunk chunk = new Chunk(logo, 0, -45);
                header[0] = new Phrase(chunk);
            } catch (BadElementException e) {
                // TODO Auto-generated catch block
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }

        }

        /**
         * Initialize one of the headers, based on the chapter title;
         * reset the page number.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onChapter(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document, float,
         *      com.itextpdf.text.Paragraph)
         */
        public void onChapter(PdfWriter writer, Document document,
                              float paragraphPosition, Paragraph title) {
            header[1] = new Phrase(title.getContent());
            pagenumber = 1;
        }

        /**
         * Increase the page number.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onStartPage(PdfWriter writer, Document document) {
            pagenumber++;
        }

        /**
         * Adds the header and the footer.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            if(writer.getPageNumber()==1){
                ColumnText.showTextAligned(writer.getDirectContent(),
                        Element.ALIGN_RIGHT, header[0], rect.getRight()+250, rect.getTop()-250, 0);
            }
            if(writer.getPageNumber() > 1){
                ColumnText.showTextAligned(writer.getDirectContent(),
                        Element.ALIGN_RIGHT, new Phrase(String.format("%d", pagenumber),new Font(Font.FontFamily.HELVETICA, 7)), rect.getRight(-250), rect.getBottom() - 20, 0);
            }
        }
    }

    public static boolean createPDF(String path, String filename, String imageLogoFileWithCompletePath, List<List<String>> data, String entity, String total, String rejected, String accepted, String moeda, boolean validation, boolean processed, String contaheader, String debitoOuCreditoHeader, String descritivoTipoServico, InfoBalcaoGestorEntity infoBalcaoGestorHeader) throws BadElementException, MalformedURLException, IOException {
        boolean result = false;
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);


        String[] headers = null;
        if(debitoOuCreditoHeader.equals("D")){
            headers = new String[6];
            headers[0]="Referência Transferência";
            headers[1]="Referência Pagamento";
            headers[2]="Data";
            headers[3]="Valor";
            headers[4]="Conta Destino";
            headers[5]="Mensagem / Número";
        }else if(debitoOuCreditoHeader.equals("C")){
            headers = new String[9];
            headers[0]="Referência Transferência";
            headers[1]="Referência Pagamento";
            headers[2]="Data";
            headers[3]="Valor";
            headers[4]="Conta Origem";
            headers[5]="Balcão";
            headers[6]="Gestor";
            headers[7]="Nome Gestor";
            headers[8]="Mensagem / Número";
        }

        //
        // Create a new document.
        //
        //Document document = new Document(PageSize.A4); // pagina na vertical
        Document document = new Document(PageSize.A4.rotate());// pagina na horizontal
        //document.setMargins(-10,-10,0,0);
        document.setMargins(-65,-65,40,40);
        document.setMarginMirroringTopBottom(true);

        try {
            //
            // Get an instance of PdfWriter and create a Table.pdf file
            // as an output.
            //
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(filename)));
            HeaderFooter header = new HeaderFooter(imageLogoFileWithCompletePath);
            writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
            writer.setPageEvent(header);

            document.open();

            Paragraph preface = new Paragraph();

            ///////////////////////////////////////////////// CRIAR TITULO DO PDF
            // We add one empty line
            addEmptyLine(preface, 4);
            // Lets write a big header
            Paragraph paragraph = new Paragraph(entity, catFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            preface.add(paragraph);

            addEmptyLine(preface, 1);
            Paragraph paragraph2 = new Paragraph("Banco Angolano de Investimentos", smallBold);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            preface.add(paragraph2);

            Paragraph paragraph3 = new Paragraph("Relatório", smallBold);
            paragraph3.setAlignment(Element.ALIGN_CENTER);
            preface.add(paragraph3);

            addEmptyLine(preface, 1);

            total = formatAmountView(total);
            try {
                rejected = formatAmountView(rejected);
            } catch (NumberFormatException e) {
                rejected = formatAmountView("0");
            }
            accepted = formatAmountView(accepted);

            preface.add(createTableTotais(total, rejected, accepted, moeda));
            document.add(preface);
            preface.clear();
            addEmptyLine(preface, 2);

            preface.add(createTableContaOrigemOuDestino(contaheader, debitoOuCreditoHeader, descritivoTipoServico,infoBalcaoGestorHeader));
            document.add(preface);
            preface.clear();
            addEmptyLine(preface, 2);

            //preface.add(createTableContaOrigemOuDestino(contaheader, debitoOuCreditoHeader, descritivoTipoServico,infoBalcaoGestorHeader));
            preface.add(createTable(headers, data));
            document.add(preface);

            result = true;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return result;
    }
    private static PdfPTable createTable(String[] headers, List<List<String>> data) throws DocumentException{
        PdfPTable table = new PdfPTable(headers.length);
        table.setHeaderRows(1);
        int maxIndex = 0;
        if(headers.length==6){
            float[] widths = new float[] { 20f, 20f, 10f, 10f, 20f, 50f};
            table.setWidths(widths);
            maxIndex = 6;
        }else if(headers.length==9){
            float[] widths = new float[] { 15f, 20f, 10f, 15f, 25f, 8f, 8f, 25f, 50f};
            table.setWidths(widths);
            maxIndex = 9;
        }
        for (int i = 0; i < headers.length; i++) {
            String header = headers[i];
            PdfPCell cell = new PdfPCell();
            //cell.setGrayFill(0.7f);
            //cell.setBackgroundColor(new BaseColor(57, 86, 112));
            cell.setBackgroundColor(new BaseColor(233, 112, 0));
            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE)));
            table.addCell(cell);
        }
        table.completeRow();

        for (List<String> l : data) {
            int index = 0;
            for (String s : l) {
                PdfPCell cell = new PdfPCell();
                if (s != null) {
                    if (maxIndex==6){
                        if(index < 6){
                            cell.setPhrase(new Phrase(s.toUpperCase(), new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                        }else {
                            cell.setPhrase(new Phrase(s, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                        }
                        if(index==3){
                            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        }else if(index == 4) {
                            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        }
                        index++;
                        if (index >= maxIndex){
                            index=0;
                        }
                    }else if (maxIndex==9){
                        if (index < 6){
                            cell.setPhrase(new Phrase(s.toUpperCase(), new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                        }else{
                            cell.setPhrase(new Phrase(s, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                        }
                        if(index==3){
                            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        }else if(index==4) {
                            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        }
                        index++;
                        if (index >= maxIndex) {
                            index = 0;
                        }
                    }

                    table.addCell(cell);
                }
            }
            table.completeRow();
        }
        return table;
    }

    private static PdfPTable createTableTotais(String total, String reject, String accepted, String moeda) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        float[] widths = new float[] { 20f, 60f };
        table.setWidths(widths);
        PdfPCell cellValorTotal = new PdfPCell();
        cellValorTotal.setPhrase(new Phrase("Valor Total", new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD)));
        cellValorTotal.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cellValorTotal);
        PdfPCell cellTotal = new PdfPCell();
        cellTotal.setPhrase(new Phrase(total + moeda, new Font(Font.FontFamily.HELVETICA, 7)));
        cellTotal.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cellTotal);
        table.completeRow();

        if(!reject.equals("0,00") || !reject.equals("0.00")){
            PdfPCell cellValorRejeitado = new PdfPCell();
            cellValorRejeitado.setPhrase(new Phrase("Total Rejeitado", new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD)));
            cellValorRejeitado.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            table.addCell(cellValorRejeitado);
            PdfPCell cellRejeitado = new PdfPCell();
            cellRejeitado.setPhrase(new Phrase(reject + moeda, new Font(Font.FontFamily.HELVETICA, 7)));
            cellRejeitado.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            table.addCell(cellRejeitado);
            table.completeRow();
        }

        if(!accepted.equals("0,00") || !accepted.equals("0.00")){
            PdfPCell cellValorAprovado = new PdfPCell();
            cellValorAprovado.setPhrase(new Phrase("Total Aprovado", new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD)));
            cellValorAprovado.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            table.addCell(cellValorAprovado);
            PdfPCell cellAprovado = new PdfPCell();
            cellAprovado.setPhrase(new Phrase(accepted + moeda, new Font(Font.FontFamily.HELVETICA, 7)));
            cellAprovado.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            table.addCell(cellAprovado);
            table.completeRow();
        }

        return table;
    }
    private static PdfPTable createTableContaOrigemOuDestino(String contaheader,String debitoOuCreditoHeader, String descritivoTipoServico, InfoBalcaoGestorEntity infoBalcaoGestor) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        float[] widths = new float[] { 20f, 60f };
        table.setWidths(widths);
        String designacao = "";
        if(debitoOuCreditoHeader.equals("C")){
            designacao = "Conta Destino";
        }else{
            designacao = "Conta Origem";
        }
        PdfPCell cellTipoServico = new PdfPCell();
        cellTipoServico.setPhrase(new Phrase("Serviço", new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD)));
        cellTipoServico.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cellTipoServico);
        PdfPCell cellDesignacaoTipoServico = new PdfPCell();
        cellDesignacaoTipoServico.setPhrase(new Phrase(descritivoTipoServico, new Font(Font.FontFamily.HELVETICA, 7)));
        cellDesignacaoTipoServico.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cellDesignacaoTipoServico);
        table.completeRow();

        PdfPCell cellConta = new PdfPCell();
        cellConta.setPhrase(new Phrase(designacao, new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD)));
        cellConta.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cellConta);
        PdfPCell cellTotal = new PdfPCell();
        cellTotal.setPhrase(new Phrase(contaheader, new Font(Font.FontFamily.HELVETICA, 7)));
        cellTotal.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cellTotal);
        table.completeRow();

        PdfPCell cellBalcaoDomicilio = new PdfPCell();
        cellBalcaoDomicilio.setPhrase(new Phrase("Balcão de Domicilio", new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD)));
        cellBalcaoDomicilio.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cellBalcaoDomicilio);
        PdfPCell cellValueBalcaoDomicilio = new PdfPCell();
        cellValueBalcaoDomicilio.setPhrase(new Phrase(infoBalcaoGestor.getBalcaoDomicilio(), new Font(Font.FontFamily.HELVETICA, 7)));
        cellValueBalcaoDomicilio.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cellValueBalcaoDomicilio);
        table.completeRow();

        PdfPCell cellGestor = new PdfPCell();
        cellGestor.setPhrase(new Phrase("Gestor", new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD)));
        cellGestor.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cellGestor);
        PdfPCell cellValueGestor = new PdfPCell();
        cellValueGestor.setPhrase(new Phrase(infoBalcaoGestor.getCodigoGestor()+" - "+infoBalcaoGestor.getNomeGestor(), new Font(Font.FontFamily.HELVETICA, 7)));
        cellValueGestor.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cellValueGestor);
        table.completeRow();

        return table;
    }
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    public static String formatAmountView(String amount) {
        if (amount.contains(".")) {
            amount = amount.replace(".", "");
        }
        if (amount.contains(",")) {
            amount = amount.replace(",", "");
        }
        while (amount.length() < 13) {
            amount = "0" + amount;
        }
        String parte_inteira = amount.substring(0, 11);
        String parte_decimal = amount.substring(11, amount.length());
        amount = parte_inteira + "." + parte_decimal;
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("pt", "POR"));
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(""); // Don't use null.
        formatter.setDecimalFormatSymbols(symbols);
        amount = formatter.format(Double.parseDouble(amount));
        //amount = amount.replace("¤", "");
        amount = amount.replace(" ", "");
        return amount;

    }
}
