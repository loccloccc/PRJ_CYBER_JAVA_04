package prj.model;

public class Pc {
    private int id;
    private String name;
    private int cate_id;
    private String status;
    private double priceHoue;

    public Pc() {}

    public Pc(int id, String name, int cate_id, String status, double priceHoue) {
        this.id = id;
        this.name = name;
        this.cate_id = cate_id;
        this.status = status;
        this.priceHoue = priceHoue;
    }

    public Pc(String name, int cate_id, String status, double priceHoue) {
        this.name = name;
        this.cate_id = cate_id;
        this.status = status;
        this.priceHoue = priceHoue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPriceHoue() {
        return priceHoue;
    }

    public void setPriceHoue(double priceHoue) {
        this.priceHoue = priceHoue;
    }
}