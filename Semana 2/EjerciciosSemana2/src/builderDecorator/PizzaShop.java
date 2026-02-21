package builderDecorator;

public class PizzaShop {
    public static void main(String[] args) {
        System.out.println("=== Pedido 1: Pizza Simple ===");
        PizzaOrder pizza1 = new Pizza.Builder(Size.MEDIUM, Crust.CLASSIC)
            .addTopping("Pepperoni")
            .addTopping("Champiñones")
            .build();
        System.out.println(pizza1.getDescription());
        System.out.printf("Precio: $%.2f%n", pizza1.getPrice());

        System.out.println("\n=== Pedido 2: Pizza con Extras ===");
        PizzaOrder pizza2 = new Pizza.Builder(Size.LARGE, Crust.STUFFED)
            .addTopping("Pepperoni")
            .addTopping("Champiñones")
            .addTopping("Aceitunas")
            .build();
        
        // Aplicamos los decoradores envolviendo la pizza original
        PizzaOrder pedido2 = new GiftBoxDecorator(new ExtraCheeseDecorator(pizza2));
        
        System.out.println(pedido2.getDescription());
        System.out.printf("Precio: $%.2f%n", pedido2.getPrice());

        System.out.println("\n=== Pedido 3: Validaciones ===");
        try {
            // Error: No tiene toppings
            new Pizza.Builder(Size.SMALL, Crust.THIN).build();
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            // Error: Intentar agregar 6 toppings
            Pizza.Builder b = new Pizza.Builder(Size.SMALL, Crust.THIN);
            for (int i = 1; i <= 6; i++) b.addTopping("Topping " + i);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}