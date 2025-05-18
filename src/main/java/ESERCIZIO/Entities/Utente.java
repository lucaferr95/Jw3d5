package ESERCIZIO.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "utenti")

public class Utente {
    @Id
    @GeneratedValue
    @Column (name="num_tessera")
    private int NumeroTessera;
private String nome;
private String cognome;

@Column(name ="data_nascita")
private LocalDate dataNascita;

@OneToMany (mappedBy = "utente")
private List<Prestito> prestiti;


public Utente(){}

    public Utente(String nome, String cognome, LocalDate dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
    }

    public int getNumeroTessera() {
        return NumeroTessera;
    }

    public void setNumeroTessera(int numeroTessera) {
        NumeroTessera = numeroTessera;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                '}';
    }
}
