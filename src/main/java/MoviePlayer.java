public class MoviePlayer extends Product implements MultimediaControl{

    Screen screen;
    MonitorType monitorType;

    public MoviePlayer(String name, String manufacturer){
       // this(name, manufacturer, screen, monitorType);
    }

    public MoviePlayer(String name,String manufacturer,Screen screen,MonitorType monitorType){
        super();
        this.id=Item.id;
        this.name=name;
        this.manufacturer=manufacturer;
        this.type = ItemType.VISUAL;
        this.screen = screen;
        this.monitorType = monitorType;
    }


    public Screen getScreen() {
        return screen;
    }
    public void setScreen(Screen screen){
        this.screen = screen;
    }

    public MonitorType getMonitorType() {
        return monitorType;
    }
    public void setMonitorType(MonitorType monitorType){
        this.monitorType=monitorType;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String videoName) {
        this.name=videoName;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public void setManufacturer(String videoManufacturer) {
        this.manufacturer=videoManufacturer;
    }


    @Override
    public void play() {
        System.out.println("Playing movie");
    }

    @Override
    public void stop() {
        System.out.println("Stopping movie");
    }

    @Override
    public void previous(){
        System.out.println("Previous movie");
    }

    @Override
    public void next() {
        System.out.println("Next movie");
    }

    @Override
    public String toString(){
        return super.toString() + "\n"
                + screen + "\n"
                + "Monitor Type: " + monitorType;
    }

} // end MoviePlayer
