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

    public static boolean createVisanet(String path, String filename, String imageLogoFileWithCompletePath, List<List<String>> data, String [] dadosOrdenante, String montanteTotalEsperado, String montanteTotalProcessado, boolean validation, boolean processed) throws BadElementException, MalformedURLException, IOException {
        boolean result = false;
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        //Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);


        String[] tableDadosOrdenante = new String[4];
        tableDadosOrdenante[0]="Nome do Ficheiro:";
        tableDadosOrdenante[1]="Moeda:";
        tableDadosOrdenante[2]="Data:";
        tableDadosOrdenante[3]="Descritivo:";


        String[] tableHeaders = new String[9];
        tableHeaders[0]="Tema de Descritivo";
        tableHeaders[1]="Operação";
        tableHeaders[2]="Descritivo VSS";
        tableHeaders[3]="Montante";
        tableHeaders[4]="Descritivo CCB";
        tableHeaders[5]="CCB Crédito";
        tableHeaders[6]="CCB Débito";
        tableHeaders[7]="Nº Operação";
        tableHeaders[8]="Mensagem";


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
            // We add one empty line
            addEmptyLine(preface, 4);
            // Lets write a big header
            Paragraph paragraph = new Paragraph("RELATÓRIO VISANET", catFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            preface.add(paragraph);

            addEmptyLine(preface, 2);
            preface.add(createTableInfoEntity(tableDadosOrdenante, dadosOrdenante));
            document.add(preface);
            preface.clear();
            addEmptyLine(preface, 1);
            preface.add(createTable(tableHeaders, data, "VISANET"));
            document.add(preface);
            preface.clear();
            preface.add(createTableMontanteTotal(montanteTotalEsperado,montanteTotalProcessado));
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

    private static PdfPTable createTableInfoEntity(String [] tableDadosOrdenante, String [] valores) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        float[] widths = new float[] { 20f, 60f };
        table.setWidths(widths);
        for (int i = 0; i < tableDadosOrdenante.length; i++) {
            PdfPCell cellName = new PdfPCell();
            cellName.setPhrase(new Phrase(tableDadosOrdenante[i], new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD)));
            cellName.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cellName.setBorderWidth(0);
            table.addCell(cellName);
            PdfPCell cellValue = new PdfPCell();
            cellValue.setPhrase(new Phrase(valores[i], new Font(Font.FontFamily.HELVETICA, 7)));
            cellValue.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cellValue.setBorderWidth(0);
            table.addCell(cellValue);
            table.completeRow();
        }


        return table;
    }
    private static PdfPTable createTableMontanteTotal(String montanteTotalEsperado, String montanteTotalProcessado) throws DocumentException {
        PdfPTable table = new PdfPTable(9);
        float[] widths = new float[] { 25f, 15f, 50f, 20f, 35f, 15f, 15f, 15f, 50f};
        table.setWidths(widths);
        PdfPCell emptyCell = new PdfPCell();
        emptyCell.setBorderWidth(0);
        table.addCell(emptyCell);
        table.addCell(emptyCell);

        PdfPCell cellTotalEsperado = new PdfPCell();
        cellTotalEsperado.setPhrase(new Phrase("TOTAL ESPERADO:", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
        cellTotalEsperado.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cellTotalEsperado.setBorderWidth(0);
        table.addCell(cellTotalEsperado);

        PdfPCell cellMontanteTotalEsperado = new PdfPCell();
        cellMontanteTotalEsperado.setPhrase(new Phrase(montanteTotalEsperado, new Font(Font.FontFamily.HELVETICA, 6)));
        cellMontanteTotalEsperado.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cellMontanteTotalEsperado.setBorderWidth(0);
        table.addCell(cellMontanteTotalEsperado);

        table.addCell(emptyCell);
        table.addCell(emptyCell);
        table.addCell(emptyCell);
        table.addCell(emptyCell);
        table.addCell(emptyCell);

        table.completeRow();

        table.addCell(emptyCell);
        table.addCell(emptyCell);

        PdfPCell cellTotalAplicado = new PdfPCell();
        cellTotalAplicado.setPhrase(new Phrase("TOTAL APLICADO:", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD)));
        cellTotalAplicado.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cellTotalAplicado.setBorderWidth(0);
        table.addCell(cellTotalAplicado);

        PdfPCell cellMontanteTotalAplicado = new PdfPCell();
        cellMontanteTotalAplicado.setPhrase(new Phrase(montanteTotalProcessado, new Font(Font.FontFamily.HELVETICA, 6)));
        cellMontanteTotalAplicado.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cellMontanteTotalAplicado.setBorderWidth(0);
        table.addCell(cellMontanteTotalAplicado);

        table.addCell(emptyCell);
        table.addCell(emptyCell);
        table.addCell(emptyCell);
        table.addCell(emptyCell);
        table.addCell(emptyCell);

        table.completeRow();

        return table;
    }

    private static PdfPTable createTable(String[] headers, List<List<String>> data, String tipo) throws DocumentException {
        PdfPTable table = new PdfPTable(headers.length);
        table.setHeaderRows(1);
        int maxIndex = 0;
        if(tipo.equals("VISANET")){
            float[] widths = new float[] { 25f, 15f, 50f, 20f, 35f, 15f, 15f, 15f, 50f};
            table.setWidths(widths);
            maxIndex = 9;
        }

        for (int i = 0; i < headers.length; i++) {
            String header = headers[i];
            PdfPCell cell = new PdfPCell();
            //cell.setGrayFill(0.7f);
            //cell.setBackgroundColor(new BaseColor(139, 0, 0));
            cell.setBackgroundColor(new BaseColor(57, 86, 112));
            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE)));
            table.addCell(cell);
        }
        table.completeRow();
        if(data!=null){
            for (List<String> l : data) {
                int index = 0;
                for (String s : l) {
                    PdfPCell cell = new PdfPCell();
                    if (s != null) {
                        if (maxIndex==9){
                            if(index < 8){
                                cell.setPhrase(new Phrase(s.toUpperCase(), new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                            }else{
                                cell.setPhrase(new Phrase(s, new Font(Font.FontFamily.HELVETICA, 6, Font.NORMAL)));
                            }
                            if(index==3 || index==7){
                                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                            }else if(index==5 || index==6){
                                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                            }
                            table.addCell(cell);
                            index++;
                            if(index >= maxIndex){
                                index=0;
                            }
                        }

                    }
                }
                table.completeRow();
            }
        }

        return table;
    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
