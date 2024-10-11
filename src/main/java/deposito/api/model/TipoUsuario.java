package deposito.api.model;

public enum TipoUsuario {
    ADMIN("admin"),
    USER("user"),
    BLOQUEADO("bloqueado");

    private String role;

    TipoUsuario(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
