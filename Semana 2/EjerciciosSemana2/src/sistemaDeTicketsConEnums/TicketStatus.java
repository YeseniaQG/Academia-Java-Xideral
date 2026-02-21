package sistemaDeTicketsConEnums;

enum TicketStatus {
    OPEN, IN_PROGRESS, RESOLVED, CLOSED;

    public boolean canTransitionTo(TicketStatus target) {
        // Implementación de la máquina de estados usando Switch Expressions
        return switch (this) {
            case OPEN -> target == IN_PROGRESS;
            case IN_PROGRESS -> target == RESOLVED || target == OPEN;
            case RESOLVED -> target == CLOSED || target == IN_PROGRESS;
            case CLOSED -> false; // Un ticket cerrado no se mueve a ningún lado
        };
    }
}
