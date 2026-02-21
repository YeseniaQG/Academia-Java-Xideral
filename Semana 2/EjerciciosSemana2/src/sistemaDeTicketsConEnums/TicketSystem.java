package sistemaDeTicketsConEnums;

import java.util.*;

public class TicketSystem {
    public static void main(String[] args) {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(1, "Login falla", Priority.CRITICAL));
        tickets.add(new Ticket(2, "Boton desalineado", Priority.LOW));
        tickets.add(new Ticket(3, "Error en pagos", Priority.HIGH));
        tickets.add(new Ticket(4, "Mejorar docs", Priority.MEDIUM));

        System.out.println("=== Todos los Tickets ===");
        tickets.forEach(System.out::println);

        System.out.println("\n=== Transiciones ===");
        tickets.get(0).transitionTo(TicketStatus.IN_PROGRESS);
        tickets.get(2).transitionTo(TicketStatus.IN_PROGRESS);
        tickets.get(2).transitionTo(TicketStatus.RESOLVED);
        tickets.get(2).transitionTo(TicketStatus.OPEN); // Transición inválida

        System.out.println("\n=== Estado Actualizado ===");
        tickets.forEach(System.out::println);

        System.out.println("\n=== Dashboard (EnumMap) ===");
        EnumMap<TicketStatus, Integer> conteo = new EnumMap<>(TicketStatus.class);
        for (TicketStatus s : TicketStatus.values()) conteo.put(s, 0);
        
        // Contar tickets por status
        for (Ticket t : tickets) {
            conteo.put(t.getStatus(), conteo.get(t.getStatus()) + 1);
        }

        conteo.forEach((status, count) ->
            System.out.printf("  %s: %d%n", status, count));

        System.out.println("\n=== Tickets Urgentes (EnumSet) ===");
        // Crear un conjunto con las prioridades que consideramos urgentes
        EnumSet<Priority> urgentes = EnumSet.of(Priority.HIGH, Priority.CRITICAL);
        
        // Filtrar e imprimir
        tickets.stream()
            .filter(t -> urgentes.contains(t.getPriority()))
            .forEach(System.out::println);
    }
}