package com.trytecom.mainteste;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BadElementException;

import com.trytecom.pdf.InfoBalcaoGestorEntity;
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
        String path="C:\\EclipseProjectsBAI\\workspace\\LayoutPdfPSXPS2\\pdf";
        String filename = path+"\\TestePDF.pdf";
        InfoBalcaoGestorEntity infoBalcaoGestorHeader = new InfoBalcaoGestorEntity("551","U2947","Ana Maria Correia Tavares");
        PDF.createPDF(path, filename, "./image/logoBAI_V2.png", getdadosPSX(), "UNITEL Pós-Pago", "15", "0", "15", "AKZ", false, false, "004000000708163710118", "D", "Ordenado", infoBalcaoGestorHeader);
        System.out.println("Fim");
    }

    public static List<List<String>> getdadosPSX(){


        List<List<String>> lista = new ArrayList<>();

        List<String> dados = new ArrayList<>();
        dados.add("1");
        dados.add("PGTO DE SALARIOS");
        dados.add("2014-11-24");
        dados.add("32000000.00");
        dados.add("000600001629687230127");
        dados.add("CÓDIGO DE NOTA NÃO EXISTE.");
        lista.add(dados);
        return lista;


    }
}
