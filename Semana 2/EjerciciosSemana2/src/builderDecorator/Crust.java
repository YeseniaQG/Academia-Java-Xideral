package builderDecorator;

enum Crust {
    THIN(0), CLASSIC(1.50), STUFFED(3.00);
    private final double extraCost;
    Crust(double extraCost) { this.extraCost = extraCost; }
    public double getExtraCost() { return extraCost; }
}