package com.trytecom.pdf;

/**
 * Created by admin on 09-09-2016.
 */
public class InfoBalcaoGestorEntity {
    private String balcaoDomicilio;
    private String codigoGestor;
    private String nomeGestor;

    public InfoBalcaoGestorEntity() {
    }

    public InfoBalcaoGestorEntity(String balcaoDomicilio, String codigoGestor, String nomeGestor) {
        this.balcaoDomicilio = balcaoDomicilio;
        this.codigoGestor = codigoGestor;
        this.nomeGestor = nomeGestor;
    }

    public String getBalcaoDomicilio() {
        return balcaoDomicilio;
    }

    public void setBalcaoDomicilio(String balcaoDomicilio) {
        this.balcaoDomicilio = balcaoDomicilio;
    }

    public String getCodigoGestor() {
        return codigoGestor;
    }

    public void setCodigoGestor(String codigoGestor) {
        this.codigoGestor = codigoGestor;
    }

    public String getNomeGestor() {
        return nomeGestor;
    }

    public void setNomeGestor(String nomeGestor) {
        this.nomeGestor = nomeGestor;
    }
}
