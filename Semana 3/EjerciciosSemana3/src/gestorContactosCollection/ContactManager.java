package gestorContactosCollection;

import java.util.*;
import java.util.Set;
import java.util.stream.*;

class ContactManager {
    // TreeSet ordenará por el compareTo (nombre) por defecto
    private final Set<Contact> contacts = new TreeSet<>();

    public boolean addContact(Contact contact) {
        // Antes de agregar al TreeSet, verificamos manualmente el email 
        // porque TreeSet usa compareTo (nombre) para detectar duplicados, 
        // pero el requisito pide que el email sea el identificador único.
        boolean emailExists = contacts.stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(contact.getEmail()));
        
        if (emailExists) return false;
        
        return contacts.add(contact);
    }

    public Optional<Contact> findByEmail(String email) {
        return contacts.stream()
            .filter(c -> c.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }

    public List<Contact> findByNamePrefix(String prefix) {
        return contacts.stream()
            .filter(c -> c.getName().toLowerCase().startsWith(prefix.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<Contact> getAllSortedBy(Comparator<Contact> comp) {
        // Usamos el stream para aplicar un comparador distinto al natural si se desea
        return contacts.stream()
            .sorted(comp)
            .collect(Collectors.toList());
    }

    public int size() { return contacts.size(); }

    public static void main(String[] args) {
        ContactManager mgr = new ContactManager();

        System.out.println("=== Agregando Contactos ===");
        System.out.println("Ana: " + mgr.addContact(
            new Contact("Ana Garcia", "ana@mail.com", "555-1111")));
        System.out.println("Luis: " + mgr.addContact(
            new Contact("Luis Lopez", "luis@mail.com", "555-2222")));
        System.out.println("Maria: " + mgr.addContact(
            new Contact("Maria Torres", "maria@mail.com", "555-3333")));
        
        // Este debe fallar (false) porque el email ya existe
        System.out.println("Ana duplicada: " + mgr.addContact(
            new Contact("Ana Garcia", "ana@mail.com", "555-9999")));
            
        System.out.println("Carlos: " + mgr.addContact(
            new Contact("Carlos Ruiz", "carlos@mail.com", "555-4444")));
        System.out.println("Total contactos: " + mgr.size());

        System.out.println("\n=== Orden Natural (por nombre) ===");
        mgr.getAllSortedBy(Comparator.naturalOrder())
            .forEach(System.out::println);

        System.out.println("\n=== Ordenados por Email ===");
        mgr.getAllSortedBy(Comparator.comparing(Contact::getEmail))
            .forEach(System.out::println);

        System.out.println("\n=== Buscar por Email ===");
        mgr.findByEmail("maria@mail.com")
            .ifPresentOrElse(
                c -> System.out.println("Encontrado: " + c),
                () -> System.out.println("No encontrado"));
        
        mgr.findByEmail("noexiste@mail.com")
            .ifPresentOrElse(
                c -> System.out.println("Encontrado: " + c),
                () -> System.out.println("No encontrado"));

        System.out.println("\n=== Buscar por Prefijo 'Ma' ===");
        mgr.findByNamePrefix("Ma").forEach(System.out::println);
    }
}