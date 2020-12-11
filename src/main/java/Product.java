public class Product implements Item {
    int id;
    ItemType type;
    String manufacturer;
    String name;

    Product(){}

    public Product(String name, String manufacturer, ItemType type) {
        this.type = type;
        this.name = name;
        this.manufacturer = manufacturer;
    } // end constructor

    public Product(int id, String name, String manufacturer, ItemType type){
        this.id=id;
        this.type = type;
        this.name = name;
        this.manufacturer = manufacturer;
    } // end constructor

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        this.manufacturer=manufacturer;
    }


    public String toString() {
        return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: " + type.name();
    } // end toString()

} // end Product