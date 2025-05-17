package ESERCIZIO;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;

public class ProvaCatalogo {
    private static final Archivio archivio = new Archivio();
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        boolean exit= false;

        while(!exit) {
            showMenu();
            try {
                int scelta = Integer.parseInt(scanner.nextLine());
                switch (scelta) {
                    case 1 -> aggiungi();
                    case 2 -> ricerca();
                    case 3 -> rimuovi();
                }
            }
            catch(Exception e){
                    System.out.println("Input errore");
                }

            }
        }
        private static void showMenu() {
            System.out.println("***MENU***" +
                    "1.Aggiungi elemento" +
                    "2.Ricerca per isbn" +
                    "3.Rimuovi per isbn" +
                    "seleziona il metodo da eseguire");
        }

        private static void aggiungi() {
            System.out.println("Tipo (L= LIBRO, R= RIVISTA");
            String tipo= scanner.nextLine().toUpperCase();
            try {
                System.out.println("ISBN"); String isbn= scanner.nextLine();
                System.out.println(("Titolo"); String titolo= scanner.nextLine();
                System.out.println(("Anno"); int anno= Integer.parseInt((scanner.nextLine())
                );
                System.out.println("Pagine"); int pag= Integer.parseInt(scanner.nextLine()););
            if(tipo.equals("L")) {
                System.out.println("Autore: ");
                String autore = scanner.nextLine();
                System.out.println("Genere:");
                String genere = scanner.nextLine();
                archivio.add(new Libro(isbn, titolo, anno, pag, autore, genere));
            } else System.out.println("Periodicita:"); Periodicity periodicity= Periodicity.valueOf(scanner.nextLine());
            archivio.add(new Rivista(isbn, titolo, anno, pag, periodicity));

            } catch (Exception e) {
                System.out.println("Errore");
            }
        //-----------METODO VOID RICERCA//
        public static  void ricerca () {
            System.out.println("ISBN da cercare");
            try {
                ElementoCatalogo e = archivio.findByIsbn(scanner.nextLine());
                System.out.println("TROVATO" + e);
            } catch (Exception e) {
                System.out.println("Errore");;
            }
            }
        }
        }

