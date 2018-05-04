public class Pattern implements Comparable<Pattern> {
    private String info = "";
    private double percent = 0.0;

    public void Pattern() {
        this.info = info;
        this.percent= percent;
    }

    public void Pattern(String info, double percent) {
        this.info = info;
        this.percent= percent;
    }



    public void setPattern(String info, double percent) {
        this.info = info;
        this.percent= percent;
    }



    public double getPercent(){
        return percent;
    }


    public String getInfo(){
        return info;
    }

    // getters
    @Override
    public int compareTo(Pattern other) {
        return -Double.compare(this.getPercent(), other.getPercent());
    }

    @Override
    public String toString(){
        return info;

    }
    

}
