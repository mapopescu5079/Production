public class Screen implements ScreenSpec {
    String resolution;
    int refreshRate;
    int responseTime;

    public Screen(){}

    public Screen(String resolution, int refreshRate, int responseTime){
        this.resolution=resolution;
        this.refreshRate=refreshRate;
        this.responseTime=responseTime;
    }


    @Override
    public String getResolution() {
        return this.resolution;
    }
    public void setResolution(String screenResolution) {
        this.resolution = screenResolution;
    }

    public int getRefreshRate(){
        return this.refreshRate;
    }
    public void setRefreshRate(int screenRefreshRate){
        this.refreshRate = screenRefreshRate;
    }

    public int getResponseTime(){
        return this.responseTime;
    }
    public void setResponseTime(int screenResponseTime){
        this.responseTime = screenResponseTime;
    }

    @Override
    public String toString() {
        return "Screen:" + "\n" +
                "Resolution: " + resolution + "\n" +
                "Refresh rate: " + refreshRate + "\n" +
                "Response time: " + responseTime;
    }

} // end Screen Class
