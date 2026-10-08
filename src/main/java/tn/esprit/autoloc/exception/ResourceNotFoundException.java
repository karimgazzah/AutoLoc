package tn.esprit.autoloc.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " introuvable avec l'identifiant " + id);
    }
}
