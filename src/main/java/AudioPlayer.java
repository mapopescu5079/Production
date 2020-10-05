// concrete class
public class AudioPlayer extends Product implements MultimediaControl{

    String supportedAudioFormats;
    String supportedPlaylistFormats;



    public String getSupportedAudioFormats(){
        return supportedAudioFormats;
    }
    public void setSupportedAudioFormats(String supportedAudioFormats){
        supportedAudioFormats = supportedPlaylistFormats;
    }

    public String getSupportedPlaylistFormats(){
        return supportedPlaylistFormats;
    }
    public void setSupportedPlaylistFormats(String supportedPlaylistFormats){
        supportedPlaylistFormats = supportedPlaylistFormats;
    }

    @Override
    public String getname() {
        return Item.name;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getManufacturer() {
        return Item.manufacturer;
    }

    @Override
    public void setManufacturer(String manufacturer) {

    }

    public AudioPlayer(String audioPlayerName, String audioPlayerManufacturer, String supportedAudioFormats, String supportedPlaylistFormats) {
        super();
        name = audioPlayerName;
        manufacturer = audioPlayerManufacturer;
        type = ItemType.AUDIO;
        this.supportedAudioFormats = supportedAudioFormats;
        this.supportedPlaylistFormats = supportedPlaylistFormats;
    }


    @Override
    public void play() {
        System.out.println("Playing");
    }

    @Override
    public void stop() {
        System.out.println("Stopping");
    }

    @Override
    public void previous(){
        System.out.println("Previous");
    }
    @Override
    public void next() {
        System.out.println("Next");
    }

    public String toString(){
        return super.toString() + "Supported Audio Formats: " + supportedAudioFormats + "\n" + "Supported Playlist Formats: " + supportedPlaylistFormats;
    } // end toString()

} // end AudioPlayer
