package ESERCIZIO.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "prestiti")

public class Prestito {
    @Id
    @GeneratedValue
    private int prestitoId;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "elemento_id")
    private ElementoCatalogo elementoPrestato;
    private LocalDate inizioPrestito;
    private LocalDate finePrestito;
    private LocalDate restituzione;

    public Prestito(){};

    public Prestito(Utente utente, ElementoCatalogo elementoPrestato, LocalDate inizioPrestito, LocalDate finePrestito, LocalDate restituzione) {
        this.utente = utente;
        this.elementoPrestato = elementoPrestato;
        this.inizioPrestito = inizioPrestito;
        this.finePrestito = finePrestito;
        this.restituzione = restituzione;
    }

    public int getPrestitoId() {
        return prestitoId;
    }

    public void setPrestitoId(int prestitoId) {
        this.prestitoId = prestitoId;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public ElementoCatalogo getElementoPrestato() {
        return elementoPrestato;
    }

    public void setElementoPrestato(ElementoCatalogo elementoPrestato) {
        this.elementoPrestato = elementoPrestato;
    }

    public LocalDate getInizioPrestito() {
        return inizioPrestito;
    }

    public void setInizioPrestito(LocalDate inizioPrestito) {
        this.inizioPrestito = inizioPrestito;
    }

    public LocalDate getFinePrestito() {
        return finePrestito;
    }

    public void setFinePrestito(LocalDate finePrestito) {
        this.finePrestito = finePrestito;
    }

    public LocalDate getRestituzione() {
        return restituzione;
    }

    public void setRestituzione(LocalDate restituzione) {
        this.restituzione = restituzione;
    }

    @Override
    public String toString() {
        return "Prestito{" +
                "utente=" + utente +
                ", elementoPrestato=" + elementoPrestato +
                ", inizioPrestito=" + inizioPrestito +
                ", finePrestito=" + finePrestito +
                ", restituzione=" + restituzione +
                '}';
    }
}
