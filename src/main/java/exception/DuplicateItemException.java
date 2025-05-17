package exception;


    public class DuplicateItemException extends Exception {
        public DuplicateItemException(String isbn){
            super("L'elemento con ISBN " + isbn + " è già presente in archivio");


        }
    }

