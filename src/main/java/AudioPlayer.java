public class AudioPlayer extends Product implements MultimediaControl {

  String supportedAudioFormats;
  String supportedPlaylistFormats;
  //    String name;
  //    String manufacturer;

  /**
   * used when user inputs products from the GUI. Calls overloaded constructor to pass parameters.
   *
   * @param name
   * @param manufacturer
   */
  public AudioPlayer(String name, String manufacturer) {
    this(name, manufacturer, "audioFormats", "playlistFormats");
    // should I call again???
    this.id = id;
    this.name = name;
    this.manufacturer = manufacturer;
  }

  /**
   * calls parent and sets ItemType to AUDIO
   *
   * @param name
   * @param manufacturer
   * @param supportedAudioFormats
   * @param supportedPlaylistFormats
   */
  public AudioPlayer(
      String name,
      String manufacturer,
      String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super();
    this.id = Item.id;
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = ItemType.AUDIO;
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  public String getSupportedAudioFormats() {
    return supportedAudioFormats;
  }

  public void setSupportedAudioFormats(String supportedAudioFormats) {
    this.supportedAudioFormats = supportedAudioFormats;
  }

  public String getSupportedPlaylistFormats() {
    return supportedPlaylistFormats;
  }

  public void setSupportedPlaylistFormats(String supportedPlaylistFormats) {
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int audioID) {
    this.id = audioID;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String audioName) {
    this.name = audioName;
  }

  @Override
  public String getManufacturer() {
    return manufacturer;
  }

  @Override
  public void setManufacturer(String audioManufacturer) {
    this.manufacturer = audioManufacturer;
  }

  @Override
  public ItemType getType() {
    return type;
  }

  @Override
  public void setType(ItemType type) {
    this.type = type;
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
  public void previous() {
    System.out.println("Previous");
  }

  @Override
  public void next() {
    System.out.println("Next");
  }

  public String toString() {
    return super.toString()
        + "\n"
        + "Supported Audio Formats: "
        + supportedAudioFormats
        + "\n"
        + "Supported Playlist Formats: "
        + supportedPlaylistFormats;
  } // end toString()
} // end AudioPlayer
