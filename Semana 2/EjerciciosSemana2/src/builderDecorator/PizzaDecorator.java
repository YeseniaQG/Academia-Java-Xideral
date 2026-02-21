package builderDecorator;
//--- Decorator ---
abstract class PizzaDecorator implements PizzaOrder {
 protected final PizzaOrder wrapped;
 PizzaDecorator(PizzaOrder wrapped) { this.wrapped = wrapped; }
}

class ExtraCheeseDecorator extends PizzaDecorator {
 ExtraCheeseDecorator(PizzaOrder wrapped) { super(wrapped); }

 @Override
 public String getDescription() {
     return wrapped.getDescription() + " + Extra Queso";
 }

 @Override
 public double getPrice() {
     // Sumamos el precio del objeto envuelto + $2.50
     return wrapped.getPrice() + 2.50;
 }
}

class GiftBoxDecorator extends PizzaDecorator {
 GiftBoxDecorator(PizzaOrder wrapped) { super(wrapped); }

 @Override
 public String getDescription() {
     return wrapped.getDescription() + " + Caja Regalo";
 }

 @Override
 public double getPrice() {
     // Sumamos el precio del objeto envuelto + $3.00
     return wrapped.getPrice() + 3.00;
 }
}