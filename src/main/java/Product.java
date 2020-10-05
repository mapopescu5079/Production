public abstract class Product implements Item {
    int id;
    ItemType type;
    String manufacturer;
    String name;

    //Item.id = int id;
    //Item.setName = new String name;
    //Item.manufacturer = manufacturer;

    Product(){}

    Product(String name, String manufacturer, ItemType type) {
        this.type = type;
        this.name = name;
        this.manufacturer = manufacturer;
    } // end constructor

    public int getId() {
        return id;
    }

    public String toString() {
        return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: " + type.code;
    } // end toString()

} // end Product

class Widget extends Product {

    Widget(String name, String manufacturer, ItemType type) {
        super(name, manufacturer, type);
    }

    @Override
    public String getname() {
        return name;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public void setManufacturer(String manufacturer) {

    }
}