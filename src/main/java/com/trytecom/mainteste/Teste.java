package com.trytecom.mainteste;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BadElementException;

import com.trytecom.pdf.PDF;
/**
 * Created by admin on 09-09-2016.
 */
public class Teste {
    /**
     * @param args
     * @throws IOException
     * @throws MalformedURLException
     * @throws BadElementException
     */
    public static void main(String[] args) throws BadElementException, MalformedURLException, IOException {
        String path="C:\\EclipseProjectsBAI\\workspace\\LayoutPdfVisanet\\pdf";
        String filename = path+"\\TestePDF.pdf";
        String [] dadosOrdenante = new String[4];
        dadosOrdenante[0] = "VSS20141023-746.EPD";
        dadosOrdenante[1] = "USD";
        dadosOrdenante[2] = "17SEP14";
        dadosOrdenante[3] = "JULY'13 Payroll";

        PDF.createVisanet(path, filename, "./image/logoBAI_V2.png", getdadosPSX(),dadosOrdenante, "-2.438.688,09", "-2.438.688,09",false, false);
        System.out.println("Fim");
    }

    public static List<List<String>> getdadosPSX(){

        List<List<String>> lista = new ArrayList<>();

        List<String> l1 = new ArrayList<>();
        l1.add("SETTLEMENT SUMMARY");
        l1.add("CREDITO");
        l1.add("Net ATM Cash");
        l1.add("84.807,88");
        l1.add("Compensacao ATM EMIS/VISA");
        l1.add("2502010205954");// CCB Credito
        l1.add("12010218304");// CCB Debito
        l1.add("70564890");
        l1.add("Sucesso na Operação!");

        List<String> l2 = new ArrayList<>();
        l2.add("SETTLEMENT SUMMARY");
        l2.add("DEBITO");
        l2.add("Net Merchandise Credit");
        l2.add("0,00");
        l2.add("Merchandise Credit");
        l2.add("12010218304");// CCB Credito
        l2.add("2502010205956");// CCB Debito
        l2.add("0");
        l2.add("Montante da transacção igual a zero.");

        List<String> l3 = new ArrayList<>();
        l3.add("SETTLEMENT SUMMARY");
        l3.add("CREDITO");
        l3.add("ORIGINAL SALE - Net Purchase");
        l3.add("57.073,34");
        l3.add("Compensacao Fecho EMIS/VISA");
        l3.add("2502010205956");// CCB Credito
        l3.add("12010218304");// CCB Debito
        l3.add("70564890");
        l3.add("Sucesso na Operação!");

        List<String> l4 = new ArrayList<>();
        l4.add("SETTLEMENT SUMMARY");
        l4.add("CREDITO");
        l4.add("Chargeback - Net Purchase");
        l4.add("0,00");
        l4.add("CHARGEBACK");
        l4.add("1803080960");// CCB Credito
        l4.add("12010218304");// CCB Debito
        l4.add("0");
        l4.add("Montante da transacção igual a zero.");

        List<String> l5 = new ArrayList<>();
        l5.add("SETTLEMENT SUMMARY");
        l5.add("CREDITO");
        l5.add("Representment - Net Purchase");
        l5.add("0,00");
        l5.add("REPRESENTMENT");
        l5.add("1803080960");// CCB Credito
        l5.add("12010218304");// CCB Debito
        l5.add("0");
        l5.add("Montante da transacção igual a zero.");

        List<String> l6 = new ArrayList<>();
        l6.add("SETTLEMENT SUMMARY");
        l6.add("CREDITO");
        l6.add("Total Acquirer");
        l6.add("1.044,59");
        l6.add("REIMBURSEMENT FEES");
        l6.add("5101080109570");// CCB Credito
        l6.add("12010218304");// CCB Debito
        l6.add("70564890");
        l6.add("Sucesso na Operação!");

        List<String> l7 = new ArrayList<>();
        l7.add("SETTLEMENT SUMMARY");
        l7.add("DEBITO");
        l7.add("Total Acquirer");
        l7.add("-976,58");
        l7.add("REIMBURSEMENT FEES");
        l7.add("12010218304");// CCB Credito
        l7.add("5101080209570");// CCB Debito
        l7.add("70564890");
        l7.add("Sucesso na Operação!");

        List<String> l8 = new ArrayList<>();
        l8.add("SETTLEMENT SUMMARY");
        l8.add("CREDITO");
        l8.add("Total Acquirer");
        l8.add("0,74");
        l8.add("VISA CHARGES");
        l8.add("5101080109570");// CCB Credito
        l8.add("12010218304");// CCB Debito
        l8.add("70564890");
        l8.add("Sucesso na Operação!");

        List<String> l9 = new ArrayList<>();
        l9.add("SETTLEMENT SUMMARY");
        l9.add("DEBITO");
        l9.add("Total Acquirer");
        l9.add("-120,27");
        l9.add("VISA CHARGES");
        l9.add("12010218304");// CCB Credito
        l9.add("5101080209570");// CCB Debito
        l9.add("70564890");
        l9.add("Sucesso na Operação!");

        //Banco Africano
        List<String> l10 = new ArrayList<>();
        l10.add("BANCO AFRICANO");
        l10.add("DEBITO");
        l10.add("TOTAL ISSUER INTERCHANGE VALUE");
        l10.add("-2.481.614,75");
        l10.add("Compensacao VISA BAI Card");
        l10.add("12010218304");// CCB Credito
        l10.add("2803080953");// CCB Debito
        l10.add("70564890");
        l10.add("Sucesso na Operação!");

        List<String> l11 = new ArrayList<>();
        l11.add("BANCO AFRICANO");
        l11.add("DEBITO");
        l11.add("TOTAL ISSUER INTERCHANGE VALUE");
        l11.add("-47,13");
        l11.add("Incoming Fee Collection (840)");
        l11.add("12010218304");// CCB Credito
        l11.add("2803080953");// CCB Debito
        l11.add("70564890");
        l11.add("Sucesso na Operação!");

        List<String> l12 = new ArrayList<>();
        l12.add("BANCO AFRICANO");
        l12.add("CREDITO");
        l12.add("TOTAL ISSUER REIMBURSEMENT FEES CREDIT");
        l12.add("18.501,11");
        l12.add("REIMBURSEMENT FEES");
        l12.add("5101080209571");// CCB Credito
        l12.add("12010218304");// CCB Debito
        l12.add("70564890");
        l12.add("Sucesso na Operação!");

        List<String> l13 = new ArrayList<>();
        l13.add("BANCO AFRICANO");
        l13.add("DEBITO");
        l13.add("TOTAL ISSUER REIMBURSEMENT FEES DEBIT");
        l13.add("-8.102,39");
        l13.add("REIMBURSEMENT FEES");
        l13.add("12010218304");// CCB Credito
        l13.add("5101080209571");// CCB Debito
        l13.add("70564890");
        l13.add("Sucesso na Operação!");

        List<String> l14 = new ArrayList<>();
        l14.add("BANCO AFRICANO");
        l14.add("CREDITO");
        l14.add("TOTAL ISSUER VISA CHARGES CREDIT");
        l14.add("35,89");
        l14.add("VISA CHARGES");
        l14.add("5101080209571");// CCB Credito
        l14.add("12010218304");// CCB Debito
        l14.add("70564890");
        l14.add("Sucesso na Operação!");

        List<String> l15 = new ArrayList<>();
        l15.add("BANCO AFRICANO");
        l15.add("DEBITO");
        l15.add("TOTAL ISSUER VISA CHARGES DEBIT");
        l15.add("-24.364,21");
        l15.add("VISA CHARGES");
        l15.add("12010218304");// CCB Credito
        l15.add("5101080209571");// CCB Debito
        l15.add("70564890");
        l15.add("Sucesso na Operação!");

        //Banco privado
        List<String> l16 = new ArrayList<>();
        l16.add("BANCO PRIVADO");
        l16.add("DEBITO");
        l16.add("TOTAL ISSUER INTERCHANGE VALUE");
        l16.add("-43.338,01");
        l16.add("Compensacao VISA BAI Card");
        l16.add("12010218304");// CCB Credito
        l16.add("2803080963");// CCB Debito
        l16.add("70564890");
        l16.add("Sucesso na Operação!");

        List<String> l17 = new ArrayList<>();
        l17.add("BANCO PRIVADO");
        l17.add("DEBITO");
        l17.add("TOTAL ISSUER INTERCHANGE VALUE");
        l17.add("0,00");
        l17.add("Incoming Fee Collection (840)");
        l17.add("12010218304");// CCB Credito
        l17.add("2803080963");// CCB Debito
        l17.add("0");
        l17.add("Montante da transacção igual a zero.");

        List<String> l18 = new ArrayList<>();
        l18.add("BANCO PRIVADO");
        l18.add("CREDITO");
        l18.add("TOTAL ISSUER REIMBURSEMENT FEES CREDIT");
        l18.add("666,47");
        l18.add("REIMBURSEMENT FEES");
        l18.add("2803080963");// CCB Credito
        l18.add("12010218304");// CCB Debito
        l18.add("70564890");
        l18.add("Sucesso na Operação!");

        List<String> l19 = new ArrayList<>();
        l19.add("BANCO PRIVADO");
        l19.add("DEBITO");
        l19.add("TOTAL ISSUER REIMBURSEMENT FEES DEBIT");
        l19.add("-40,47");
        l19.add("REIMBURSEMENT FEES");
        l19.add("12010218304");// CCB Credito
        l19.add("2803080963");// CCB Debito
        l19.add("70564890");
        l19.add("Sucesso na Operação!");

        List<String> l20 = new ArrayList<>();
        l20.add("BANCO PRIVADO");
        l20.add("CREDITO");
        l20.add("TOTAL ISSUER VISA CHARGES CREDIT");
        l20.add("0,00");
        l20.add("VISA CHARGES");
        l20.add("2803080963");// CCB Credito
        l20.add("12010218304");// CCB Debito
        l20.add("0");
        l20.add("Montante da transacção igual a zero.");

        List<String> l21 = new ArrayList<>();
        l21.add("BANCO PRIVADO");
        l21.add("DEBITO");
        l21.add("TOTAL ISSUER VISA CHARGES DEBIT");
        l21.add("-428,77");
        l21.add("VISA CHARGES");
        l21.add("12010218304");// CCB Credito
        l21.add("2803080963");// CCB Debito
        l21.add("70564890");
        l21.add("Sucesso na Operação!");

        //Banco Regional
        List<String> l22 = new ArrayList<>();
        l22.add("BANCO REGIONAL");
        l22.add("DEBITO");
        l22.add("TOTAL ISSUER INTERCHANGE VALUE");
        l22.add("-41.779,88");
        l22.add("Compensacao VISA BAI Card");
        l22.add("12010218304");// CCB Credito
        l22.add("2803080963");// CCB Debito
        l22.add("70564890");
        l22.add("Sucesso na Operação!");

        List<String> l23 = new ArrayList<>();
        l23.add("BANCO REGIONAL");
        l23.add("DEBITO");
        l23.add("TOTAL ISSUER INTERCHANGE VALUE");
        l23.add("-12,00");
        l23.add("Incoming Fee Collection (840)");
        l23.add("12010218304");// CCB Credito
        l23.add("2803080963");// CCB Debito
        l23.add("70564890");
        l23.add("Sucesso na Operação!");

        List<String> l24 = new ArrayList<>();
        l24.add("BANCO REGIONAL");
        l24.add("CREDITO");
        l24.add("TOTAL ISSUER REIMBURSEMENT FEES CREDIT");
        l24.add("441,03");
        l24.add("REIMBURSEMENT FEES");
        l24.add("2803080963");// CCB Credito
        l24.add("12010218304");// CCB Debito
        l24.add("70564890");
        l24.add("Sucesso na Operação!");

        List<String> l25 = new ArrayList<>();
        l25.add("BANCO REGIONAL");
        l25.add("DEBITO");
        l25.add("TOTAL ISSUER REIMBURSEMENT FEES DEBIT");
        l25.add("-16,90");
        l25.add("REIMBURSEMENT FEES");
        l25.add("12010218304");// CCB Credito
        l25.add("2803080963");// CCB Debito
        l25.add("70564890");
        l25.add("Sucesso na Operação!");

        List<String> l26 = new ArrayList<>();
        l26.add("BANCO REGIONAL");
        l26.add("CREDITO");
        l26.add("TOTAL ISSUER VISA CHARGES CREDIT");
        l26.add("0,00");
        l26.add("VISA CHARGES");
        l26.add("2803080963");// CCB Credito
        l26.add("12010218304");// CCB Debito
        l26.add("0");
        l26.add("Montante da transacção igual a zero.");

        List<String> l27 = new ArrayList<>();
        l27.add("BANCO REGIONAL");
        l27.add("DEBITO");
        l27.add("TOTAL ISSUER VISA CHARGES DEBIT");
        l27.add("-417.78");
        l27.add("VISA CHARGES");
        l27.add("12010218304");// CCB Credito
        l27.add("2803080963");// CCB Debito
        l27.add("70564890");
        l27.add("Sucesso na Operação!");

        lista.add(l1);
        lista.add(l2);
        lista.add(l3);
        lista.add(l4);
        lista.add(l5);
        lista.add(l6);
        lista.add(l7);
        lista.add(l8);
        lista.add(l9);
        lista.add(l10);

        lista.add(l11);
        lista.add(l12);
        lista.add(l13);
        lista.add(l14);
        lista.add(l15);
        lista.add(l16);
        lista.add(l17);
        lista.add(l18);
        lista.add(l19);
        lista.add(l20);

        lista.add(l21);
        lista.add(l22);
        lista.add(l23);
        lista.add(l24);
        lista.add(l25);
        lista.add(l26);
        lista.add(l27);
        return lista;

    }
}
